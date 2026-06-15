package com.iab.fxops.infrastructure.persistence;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "idempotency_keys")
public class IdempotencyKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idempotency_key", nullable = false, unique = true, length = 100)
    private String key;

    @Column(nullable = false)
    private Long operationId;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected IdempotencyKey() {
    }

    public IdempotencyKey(String key, Long operationId){
        this.key = key;
        this.operationId = operationId;
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getKey() { return key; }
    public Long getOperationId() { return operationId; }
    public Instant getCreatedAt() { return createdAt; }

}
