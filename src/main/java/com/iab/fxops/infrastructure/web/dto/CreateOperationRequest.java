package com.iab.fxops.infrastructure.web.dto;

import com.iab.fxops.application.CreateOperationCommand;
import com.iab.fxops.domain.PartyRole;
import com.iab.fxops.domain.Side;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record CreateOperationRequest(
        @NotBlank String currencyPair,
        @NotNull @Positive BigDecimal amount,
        @NotNull @Positive BigDecimal rate,
        @NotNull Side side,
        @NotEmpty @Valid List<PartyRequest> parties
        ) {

    public CreateOperationCommand toCommand() {
        List<CreateOperationCommand.PartyData> partyData = parties.stream()
                .map(p -> new CreateOperationCommand.PartyData(
                        p.name(), p.document(), p.role())).toList();
        return new CreateOperationCommand(currencyPair, amount, rate, side, partyData);
    }

    public record PartyRequest(
            @NotBlank String name,
            @NotBlank String document,
            @NotNull PartyRole role
    ) {
    }
}
