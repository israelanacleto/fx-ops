package com.iab.fxops.application;

import com.iab.fxops.domain.Operation;
import com.iab.fxops.infrastructure.persistence.OperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListOperationsUseCase {

    private final OperationRepository operationRepository;

    public ListOperationsUseCase(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Transactional(readOnly = true)
    public List<String> execute() {
        List<Operation> operations = operationRepository.findAllWithParties();

        return operations.stream().map(op -> op.getCurrencyPair() +
                " tem " + op.getParties().size() + " parties").toList();
    }
}
