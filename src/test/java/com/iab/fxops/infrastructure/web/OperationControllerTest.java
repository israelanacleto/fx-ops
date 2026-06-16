package com.iab.fxops.infrastructure.web;

import com.iab.fxops.application.*;
import com.iab.fxops.domain.Operation;
import com.iab.fxops.domain.OperationNotFoundException;
import com.iab.fxops.domain.Side;
import com.iab.fxops.infrastructure.security.JwtAuthenticationFilter;
import com.iab.fxops.infrastructure.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OperationController.class,
excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {SecurityConfig.class, JwtAuthenticationFilter.class}))
@AutoConfigureMockMvc(addFilters = false)
class OperationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateOperationUseCase createOperationUseCase;
    @MockitoBean
    private ListOperationsUseCase listOperationsUseCase;
    @MockitoBean
    private GetOperationUseCase getOperationUseCase;
    @MockitoBean
    private ConfirmOperationUseCase confirmOperationUseCase;
    @MockitoBean
    private SettleOperationUseCase settleOperationUseCase;
    @MockitoBean
    private CancelOperationUseCase cancelOperationUseCase;

    @Test
    void listReturnsOkAndOperations() throws Exception {
        Operation op = new Operation("USD/BRL", new BigDecimal("1000"), new BigDecimal("5.0"), Side.BUY);
        when(listOperationsUseCase.execute()).thenReturn(List.of(op));

        mockMvc.perform(get("/operations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].currencyPair").value("USD/BRL"))
                .andExpect(jsonPath("$[0].state").value("CREATED"));
     }

     @Test
     void getByIdReturns404WhenNotFound() throws Exception {
        when(getOperationUseCase.execute(99L)).thenThrow(new OperationNotFoundException(99L));

        mockMvc.perform(get("/operations/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Operação não encontrada"));
     }
}
