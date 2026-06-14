package com.iab.fxops.application;

import com.iab.fxops.domain.Operation;
import com.iab.fxops.domain.OperationNotFoundException;
import com.iab.fxops.infrastructure.persistence.OperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SettleOperationUseCase {

    private final OperationRepository operationRepository;

    public SettleOperationUseCase(OperationRepository operationRepository){
        this.operationRepository = operationRepository;
    }

    @Transactional
    public Operation execute(Long id){
        Operation operation = operationRepository.findById(id)
                .orElseThrow(() -> new OperationNotFoundException(id));
        operation.settle();
        return operationRepository.save(operation);
    }
}
