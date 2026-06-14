package com.iab.fxops.application;

import com.iab.fxops.domain.Operation;
import com.iab.fxops.domain.Party;
import com.iab.fxops.infrastructure.persistence.OperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateOperationUseCase {

    private final OperationRepository operationRepository;

    public CreateOperationUseCase(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Transactional
    public Operation execute(CreateOperationCommand command) {
        Operation operation = new Operation(
                command.currencyPair(),
                command.amount(),
                command.rate(),
                command.side()
        );

        for (CreateOperationCommand.PartyData partyData : command.parties()) {
            operation.addParty(new Party(partyData.name(), partyData.document(), partyData.role()));
        }

        return operationRepository.save(operation);
    }
}
