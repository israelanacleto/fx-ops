package com.iab.fxops.infrastructure.web.dto;

import com.iab.fxops.domain.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OperationResponse(
        Long id,
        String currencyPair,
        BigDecimal amount,
        BigDecimal rate,
        Side side,
        OperationState state,
        Instant createdAt,
        List<PartyResponse> parties
        ) {

    public static OperationResponse from(Operation operation) {
        List<PartyResponse> parties = operation.getParties().stream()
                .map(PartyResponse::from)
                .toList();
        return new OperationResponse(
                operation.getId(),
                operation.getCurrencyPair(),
                operation.getAmount(),
                operation.getRate(),
                operation.getSide(),
                operation.getState(),
                operation.getCreatedAt(),
                parties
        );
    }


    public record PartyResponse(Long id, String name, String document, PartyRole role) {
        public static PartyResponse from(Party party) {
            return new PartyResponse(
                    party.getId(),
                    party.getName(),
                    party.getDocument(),
                    party.getRole()
            );
        }
    }
}
