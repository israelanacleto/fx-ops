package com.iab.fxops.application;

import com.iab.fxops.domain.Operation;
import com.iab.fxops.domain.OperationNotFoundException;
import com.iab.fxops.domain.Party;
import com.iab.fxops.infrastructure.persistence.IdempotencyKey;
import com.iab.fxops.infrastructure.persistence.IdempotencyKeyRepository;
import com.iab.fxops.infrastructure.persistence.OperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CreateOperationUseCase {

    private final OperationRepository operationRepository;
    private final IdempotencyKeyRepository idempotencyKeyRepository;

    public CreateOperationUseCase(OperationRepository operationRepository,  IdempotencyKeyRepository idempotencyKeyRepository) {
        this.operationRepository = operationRepository;
        this.idempotencyKeyRepository = idempotencyKeyRepository;
    }

    @Transactional
    public Operation execute(CreateOperationCommand command, String idempotencyKey) {
        boolean hasKey = idempotencyKey != null && !idempotencyKey.isBlank();

        if (hasKey){
            Optional<IdempotencyKey> existing =  idempotencyKeyRepository.findByKey(idempotencyKey);
            if (existing.isPresent()){
                Long existingId = existing.get().getOperationId();
                return operationRepository.findWithPartiesById(existingId).orElseThrow(() -> new OperationNotFoundException(existingId));
            }
        }

        Operation operation = new Operation(
                command.currencyPair(),
                command.amount(),
                command.rate(),
                command.side()
        );

        for (CreateOperationCommand.PartyData partyData : command.parties()) {
            operation.addParty(new Party(partyData.name(), partyData.document(), partyData.role()));
        }
        Operation saved = operationRepository.save(operation);

        if (hasKey){
            idempotencyKeyRepository.save(new IdempotencyKey(idempotencyKey, saved.getId()));
        }

        return saved;
    }
}
