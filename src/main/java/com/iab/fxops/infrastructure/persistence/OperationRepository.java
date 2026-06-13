package com.iab.fxops.infrastructure.persistence;

import com.iab.fxops.domain.Operation;
import com.iab.fxops.domain.OperationState;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findByState(OperationState state);

    @EntityGraph(attributePaths = "parties")
    @Query("SELECT o FROM Operation o")
    List<Operation> findAllWithParties();
}
