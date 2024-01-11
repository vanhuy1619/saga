package com.example.accountservice.command.controller;

import com.example.accountservice.command.command.CreateAccountCommand;
import com.example.accountservice.command.dto.CreateAccountRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.UUID;

@RequestMapping("/account")
@Service
public class CreateAccountController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountRequest request) {
        try {
            String accountId = UUID.randomUUID().toString();

            CreateAccountCommand createAccountCommand = CreateAccountCommand.builder()
                    .accountId(accountId)
                    .balance(request.getBalance())
                    .build();

            // Send the command asynchronously
            commandGateway.send(createAccountCommand);

            return new ResponseEntity<>(accountId, HttpStatus.CREATED);
        } catch (Exception e) {

            return new ResponseEntity<>("Error creating account: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
