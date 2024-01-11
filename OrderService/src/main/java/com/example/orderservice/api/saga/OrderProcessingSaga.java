package com.example.orderservice.api.saga;

import com.example.commonservice.commands.*;
import com.example.commonservice.contanst.OrderStatus;
import com.example.commonservice.events.*;
import com.example.commonservice.model.User;
import com.example.commonservice.queries.GetUserPaymentDetailsQuery;
import com.example.orderservice.api.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Saga
@Slf4j
@Service
public class OrderProcessingSaga {
    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty =  "orderId")
    private void handle(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent in Saga for OrderId: {}", event.getOrderId());

        GetUserPaymentDetailsQuery getUserPaymentDetailsQuery
                = new GetUserPaymentDetailsQuery(event.getUserId());

        User user = User.builder().build();
        System.out.println(user);
        try {
            user = queryGateway.query(getUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
        } catch (Exception e) {
            log.error(e.getMessage());
            cancelOrderCommand(event.getOrderId());
        }

        ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
                .cardDetails(user.getCardDetails())
                .orderId(event.getOrderId())
                .paymentId(UUID.randomUUID().toString())
                .build();

        commandGateway.sendAndWait(validatePaymentCommand);
    }

    private void cancelOrderCommand(String orderId) {
        CancelOrderCommand cancelOrderCommand = CancelOrderCommand.builder()
                .orderId(orderId)
                .orderStatus(OrderStatus.CANCELLED.toString())
                .build();
        commandGateway.send(cancelOrderCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    private void handle(PaymentProcessEvent event) {
        log.info("PaymentProcessEvent in saga for orderId: {}", event.getOrderId());
        try {
            // Introduce a retry mechanism here
            int maxRetries = 3;
            for (int retryCount = 0; retryCount < maxRetries; retryCount++) {
                try {
                    if(true) {
                        throw new Exception();
                    }
                    ShipOrderCommand shipOrderCommand = ShipOrderCommand.builder()
                            .shipmentId(UUID.randomUUID().toString())
                            .orderId(event.getOrderId())
                            .build();
                    commandGateway.send(shipOrderCommand);
                    // If successful, break out of the retry loop
                    log.info("[PaymentProcessEvent] completion successful on retry attempt: {}", retryCount + 1);
                    return;
                } catch (Exception e) {
                    log.error("[PaymentProcessEvent] Error completing order on retry attempt: {}", retryCount + 1);
                    if (retryCount == maxRetries - 1) {
                        // If max retries reached, log error and exit the loop
                        log.error("Max retries reached. Unable to complete order.");
                        cancelPaymentCommand(event);
                        return;
                    }
                    // Introduce a delay before retrying (optional)
                    try {
                        Thread.sleep(5000); // 5 second delay (adjust as needed)
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            cancelPaymentCommand(event);
        }
    }

    private void cancelPaymentCommand(PaymentProcessEvent event) {
        CancelPaymentCommand cancelPaymentCommand = CancelPaymentCommand.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .paymentStatus(OrderStatus.CANCELLED.toString())
                .build();
        commandGateway.send(cancelPaymentCommand);
    }


    @SagaEventHandler(associationProperty = "orderId")
    private void handle(OrderShippedEvent event) {
        System.out.println("OrderShippedEvent start");
        log.info("OrderShippedEvent in saga for orderId: {}", event.getOrderId());
        try {
            // Introduce a retry mechanism here
            int maxRetries = 3;
            for (int retryCount = 0; retryCount < maxRetries; retryCount++) {
                try {
                    CompleteOrderCommand completeOrderCommand = CompleteOrderCommand.builder()
                            .orderId(event.getOrderId())
                            .orderStatus(OrderStatus.APPROVE.toString())
                            .build();

                    commandGateway.send(completeOrderCommand);

                    // If successful, break out of the retry loop
                    log.info("[OrderShippedEvent] completion successful on retry attempt: {}", retryCount + 1);
                    break;
                } catch (Exception e) {
                    log.error("[OrderShippedEvent] Error completing order on retry attempt: {}", retryCount + 1);
                    if (retryCount == maxRetries - 1) {
                        // If max retries reached, log error and exit the loop
                        log.error("Max retries reached. Unable to complete order.");
                        break;
                    }
                    // Introduce a delay before retrying (optional)
                    try {
                        Thread.sleep(1000); // 1 second delay (adjust as needed)
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(OrderCompletedEvent event) {
        log.info("OrderCompletedEvent in saga for orderId: {}", event.getOrderId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCancelEvent event) {
        log.info("OrderCancelEvent in saga for orderId: {}", event.getOrderId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentCancelEvent event) {
        log.info("PaymentCancelEvent in saga for orderId: {}, paymentId: {}", event.getOrderId(), event.getPaymentId());
        cancelOrderCommand(event.getOrderId());
    }
}
