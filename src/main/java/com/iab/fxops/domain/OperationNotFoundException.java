package com.iab.fxops.domain;

public class OperationNotFoundException extends RuntimeException {

    public OperationNotFoundException(Long id){
        super("Operação não encontrada: " + id);
    }
}
