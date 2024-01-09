package com.example.accountservice.command.aggregate;

import com.example.accountservice.command.command.CreateAccountCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private BigDecimal balance;
    private String status;

    private AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {

    }
}
