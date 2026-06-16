package com.iab.fxops.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OperationTest {

    private Operation newOperation() {
        return new Operation("USD/BRL", new BigDecimal("1000"), new BigDecimal("5.0"), Side.BUY);
    }

    @Test
    void newOperationStartsInCreatedState() {
        Operation op = newOperation();
        assertThat(op.getState()).isEqualTo(OperationState.CREATED);
    }

    @Test
    void confirmMovesFromCreatedToConfirmed() {
        Operation op = newOperation();
        op.confirm();
        assertThat(op.getState()).isEqualTo(OperationState.CONFIRMED);
    }

    @Test
    void confirmingTwiceThrows() {
        Operation op = newOperation();
        op.confirm();
        assertThatThrownBy(op::confirm)
                .isInstanceOf(InvalidOperationStateException.class);
    }

    @Test
    void settleOnlyWorksFromConfirmed(){
        Operation op = newOperation();
        assertThatThrownBy(op::settle)
                .isInstanceOf(InvalidOperationStateException.class);
    }

    @Test
    void cancelingACreatedOperationWorks(){
        Operation op = newOperation();
        op.cancel();
        assertThat(op.getState()).isEqualTo(OperationState.CANCELLED);
    }

    @Test
    void settledOperationCannotBeCancelled(){
        Operation op = newOperation();
        op.confirm();
        op.settle();
        assertThatThrownBy(op::cancel)
                .isInstanceOf(InvalidOperationStateException.class);
    }
}
