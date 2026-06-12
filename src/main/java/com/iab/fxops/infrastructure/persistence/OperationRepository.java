package com.iab.fxops.infrastructure.persistence;

import com.iab.fxops.domain.Operation;
import com.iab.fxops.domain.OperationState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findByState(OperationState state);
}
