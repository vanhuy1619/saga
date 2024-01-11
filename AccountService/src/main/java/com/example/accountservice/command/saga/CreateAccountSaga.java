//package com.example.accountservice.command.saga;
//
//import com.example.accountservice.command.command.CreateAccountCommand;
//import com.example.accountservice.command.events.AccountCreatedEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.axonframework.commandhandling.gateway.CommandGateway;
//import org.axonframework.eventhandling.saga.SagaEventHandler;
//import org.axonframework.modelling.saga.StartSaga;
//import org.axonframework.spring.stereotype.Saga;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Saga
//@Slf4j
//@Service
//public class CreateAccountSaga {
//    @Autowired
//    private transient CommandGateway commandGateway;
//
//    @StartSaga
//    @SagaEventHandler(associationProperty = "accountId")
//    private void handle(AccountCreatedEvent event) {
//        log.info("AccountCreatedEvent in Saga for OrderId: {}", event.getAccountId());
//        CreateAccountCommand createAccountCommand = CreateAccountCommand.builder()
//                        .accountId(UUID.randomUUID().toString())
//                                .balance(event.)
//        commandGateway.send(new CreateAccountCommand(
//                UUID.randomUUID().toString(),
//                createAccountRequest.getStartingBalance())
//        );
//    }
//}
