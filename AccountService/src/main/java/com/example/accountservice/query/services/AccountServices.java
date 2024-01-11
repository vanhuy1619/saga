package com.example.accountservice.query.services;

import com.example.accountservice.command.events.AccountCreatedEvent;
import com.example.accountservice.query.entity.Account;
import com.example.accountservice.query.repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountServices {
    @Autowired
    private AccountRepository accountRepository;

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        log.info("Handling AccountCreatedEvent...");
        Account account = new Account();
        account.setAccountId(accountCreatedEvent.getAccountId());
        account.setBalance(accountCreatedEvent.getBalance());
        account.setStatus(accountCreatedEvent.getStatus());

        accountRepository.save(account);
    }
}
