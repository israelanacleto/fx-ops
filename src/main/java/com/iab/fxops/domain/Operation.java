package com.iab.fxops.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 7)
    private String currencyPair;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(nullable = false, precision = 19, scale = 6)
    private BigDecimal rate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Side side;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private OperationState state;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "operation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Party> parties = new ArrayList<>();

    protected Operation() {
    }

    public Operation(String currencyPair, BigDecimal amount, BigDecimal rate, Side side) {
        this.currencyPair = currencyPair;
        this.amount = amount;
        this.rate = rate;
        this.side = side;
        this.state = OperationState.CREATED;
        this.createdAt = Instant.now();
    }

    public void addParty(Party party){
        parties.add(party);
        party.setOperation(this);
    }

    public Long getId() { return id; }
    public String getCurrencyPair() { return currencyPair; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getRate() { return rate; }
    public Side getSide() { return side; }
    public OperationState getState() { return state; }
    public Instant getCreatedAt() { return createdAt; }
    public List<Party> getParties() { return parties; }


}
