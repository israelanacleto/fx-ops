package com.iab.fxops.application;

import com.iab.fxops.domain.Operation;
import com.iab.fxops.domain.OperationNotFoundException;
import com.iab.fxops.infrastructure.persistence.OperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetOperationUseCase {

    private final  OperationRepository operationRepository;

    public GetOperationUseCase(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Transactional(readOnly = true)
    public Operation execute(Long id) {
        return operationRepository.findWithPartiesById(id)
                .orElseThrow(() -> new OperationNotFoundException(id));
    }
}
