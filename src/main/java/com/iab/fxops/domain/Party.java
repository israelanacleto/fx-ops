package com.iab.fxops.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "parties")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 14)
    private String document;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private PartyRole role;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "operation_id")
    private Operation operation;

    protected Party() {
    }

    public Party(String name, String document, PartyRole role){
        this.name = name;
        this.document = document;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDocument() { return document; }
    public PartyRole getRole() { return role; }
    public Operation getOperation() { return operation; }

    void setOperation(Operation operation){
        this.operation = operation;
    }
}
