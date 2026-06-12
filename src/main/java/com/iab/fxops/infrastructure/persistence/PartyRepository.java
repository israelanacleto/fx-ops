package com.iab.fxops.infrastructure.persistence;

import com.iab.fxops.domain.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartyRepository extends JpaRepository<Party, Long> {
    List<Party> findByDocument(String document);
}
