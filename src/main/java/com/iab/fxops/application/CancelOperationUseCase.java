package com.iab.fxops.application;

import com.iab.fxops.domain.Operation;
import com.iab.fxops.domain.OperationNotFoundException;
import com.iab.fxops.infrastructure.persistence.OperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelOperationUseCase {

    private final OperationRepository operationRepository;

    public CancelOperationUseCase(OperationRepository operationRepository){
        this.operationRepository = operationRepository;
    }

    @Transactional
    public Operation execute(Long id){
        Operation operation = operationRepository.findById(id)
                .orElseThrow(() -> new OperationNotFoundException(id));
        operation.cancel();
        return operationRepository.save(operation);
    }
}
