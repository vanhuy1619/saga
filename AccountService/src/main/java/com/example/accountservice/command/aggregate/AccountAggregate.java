package com.example.accountservice.command.aggregate;

import com.example.accountservice.command.command.CreateAccountCommand;
import com.example.accountservice.command.events.AccountCreatedEvent;
import com.example.commonservice.contanst.AccountStatus;
import com.example.commonservice.events.AccountActivatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import java.math.BigDecimal;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private BigDecimal balance;
    private String status;

    private AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        log.info("CreateAccountCommand received.");
        AccountCreatedEvent accountCreatedEvent = AccountCreatedEvent.builder().build();
        BeanUtils.copyProperties(createAccountCommand, accountCreatedEvent);
        AggregateLifecycle.apply(accountCreatedEvent);
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        log.info("An AccountCreatedEvent occurred.");
        this.accountId = event.getAccountId();
        this.balance = event.getBalance();
        this.status = AccountStatus.CREATED.toString();

        AggregateLifecycle.apply(new AccountActivatedEvent(this.accountId, AccountStatus.ACTIVATED.toString()));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent accountActivatedEvent) {
        log.info("An AccountActivatedEvent occurred.");
        this.status = accountActivatedEvent.getStatus();
    }
}
