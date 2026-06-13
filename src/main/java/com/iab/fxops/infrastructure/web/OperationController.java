package com.iab.fxops.infrastructure.web;

import com.iab.fxops.application.ListOperationsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/operations")
public class OperationController {

    private final ListOperationsUseCase listOperationsUseCase;

    public OperationController(ListOperationsUseCase listOperationsUseCase) {
        this.listOperationsUseCase = listOperationsUseCase;
    }

    @GetMapping
    public List<String> list() {
        return listOperationsUseCase.execute();
    }
}
