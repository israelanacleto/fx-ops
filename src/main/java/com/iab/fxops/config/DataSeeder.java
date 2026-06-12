package com.iab.fxops.config;

import com.iab.fxops.domain.Operation;
import com.iab.fxops.domain.Party;
import com.iab.fxops.domain.PartyRole;
import com.iab.fxops.domain.Side;
import com.iab.fxops.infrastructure.persistence.OperationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(OperationRepository operations){
        return args -> {
            if (operations.count() > 0) {
                return;
            }

            Operation op1 = new Operation("USD/BRL", new BigDecimal("100000.00"),
                    new BigDecimal("5.4321"), Side.BUY);
            op1.addParty(new Party("Banco Alfa", "11222333000144", PartyRole.BUYER));
            op1.addParty(new Party("Fundo Beta", "55666777000188", PartyRole.SELLER));
            operations.save(op1);

            Operation op2 = new Operation("EUR/BRL", new BigDecimal("250000.00"),
                    new BigDecimal("6.108750"), Side.SELL);
            op2.addParty(new Party("Tesouraria Gama", "99888777000166", PartyRole.SELLER));
            op2.addParty(new Party("Banco Alfa", "11222333000144", PartyRole.BUYER));
            operations.save(op2);

            Operation op3 = new Operation("GBP/BRL", new BigDecimal("75000.00"),
                    new BigDecimal("7.011200"), Side.BUY);
            op3.addParty(new Party("Corretora Delta", "44555666000122", PartyRole.BUYER));
            op3.addParty(new Party("Fundo Beta", "55666777000188", PartyRole.SELLER));
            operations.save(op3);
        };
    }
}
