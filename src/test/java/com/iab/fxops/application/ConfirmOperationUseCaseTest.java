package com.iab.fxops.application;

import com.iab.fxops.domain.Operation;
import com.iab.fxops.domain.OperationNotFoundException;
import com.iab.fxops.domain.OperationState;
import com.iab.fxops.domain.Side;
import com.iab.fxops.infrastructure.persistence.OperationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfirmOperationUseCaseTest {

    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private ConfirmOperationUseCase confirmOperationUseCase;

    @Test
    void confirmsAnExistingOperation(){
        Operation operation = new Operation("USD/BRL", new BigDecimal("1000"), new BigDecimal("5.0"), Side.BUY);
        when(operationRepository.findWithPartiesById(1L)).thenReturn(Optional.of(operation));
        when(operationRepository.save(any(Operation.class))).thenReturn(operation);

        Operation result = confirmOperationUseCase.execute(1l);

        assertThat(result.getState()).isEqualTo(OperationState.CONFIRMED);
        verify(operationRepository).save(operation);
    }

    @Test
    void throwsWhenOperationNotFound(){
        when(operationRepository.findWithPartiesById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> confirmOperationUseCase.execute(99L))
                .isInstanceOf(OperationNotFoundException.class);

        verify(operationRepository, never()).save(any());
    }


}
