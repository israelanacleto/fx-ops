package com.iab.fxops.infrastructure.web;

import com.iab.fxops.application.*;
import com.iab.fxops.domain.Operation;
import com.iab.fxops.infrastructure.web.dto.CreateOperationRequest;
import com.iab.fxops.infrastructure.web.dto.OperationResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/operations")
public class OperationController {

    private final CreateOperationUseCase createOperation;
    private final ListOperationsUseCase listOperations;
    private final GetOperationUseCase getOperation;
    private final ConfirmOperationUseCase confirmOperation;
    private final SettleOperationUseCase settleOperation;
    private final CancelOperationUseCase cancelOperation;

    public OperationController(CreateOperationUseCase createOperation,
                               ListOperationsUseCase listOperations,
                               GetOperationUseCase getOperation,
                               ConfirmOperationUseCase confirmOperation,
                               SettleOperationUseCase settleOperation,
                               CancelOperationUseCase cancelOperation) {
        this.createOperation = createOperation;
        this.listOperations = listOperations;
        this.getOperation = getOperation;
        this.confirmOperation = confirmOperation;
        this.settleOperation = settleOperation;
        this.cancelOperation = cancelOperation;
    }

    @PostMapping
    public ResponseEntity<OperationResponse> create(@RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey,
            @Valid @RequestBody CreateOperationRequest request){
        Operation created = createOperation.execute(request.toCommand(), idempotencyKey);
        OperationResponse body = OperationResponse.from(created);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(body);
    }

    @GetMapping
    public List<OperationResponse> list() {
        return listOperations.execute().stream()
                .map(OperationResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public OperationResponse getById(@PathVariable Long id){
        return OperationResponse.from(getOperation.execute(id));
    }

    @PostMapping("/{id}/confirm")
    public OperationResponse confirm(@PathVariable Long id){
        return OperationResponse.from(confirmOperation.execute(id));
    }

    @PostMapping("/{id}/settle")
    public OperationResponse settle(@PathVariable Long id){
        return OperationResponse.from(settleOperation.execute(id));
    }

    @PostMapping("/{id}/cancel")
    public OperationResponse cancel(@PathVariable Long id){
        return OperationResponse.from(cancelOperation.execute(id));
    }
}
