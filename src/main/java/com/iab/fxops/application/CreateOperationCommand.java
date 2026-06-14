package com.iab.fxops.application;
import com.iab.fxops.domain.Side;
import java.math.BigDecimal;
import java.util.List;

public record CreateOperationCommand(
        String currencyPair,
        BigDecimal amount,
        BigDecimal rate,
        Side side,
        List<PartyData> parties
) {
    public record PartyData(String name, String document, com.iab.fxops.domain.PartyRole role){
    }
}
