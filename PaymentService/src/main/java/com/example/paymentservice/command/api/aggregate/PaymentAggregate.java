package com.example.paymentservice.command.api.aggregate;

import com.example.commonservice.commands.CancelPaymentCommand;
import com.example.commonservice.commands.ValidatePaymentCommand;
import com.example.commonservice.contanst.OrderStatus;
import com.example.commonservice.events.PaymentCancelEvent;
import com.example.commonservice.events.PaymentProcessEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Slf4j
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus;

    public PaymentAggregate() {

    }

    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand) {
        //validate payment details
        //publish the payment processed event
        log.info("executing ValidatePaymentCommand for orderId: {} anf paymentId: {}",validatePaymentCommand.getOrderId(), validatePaymentCommand.getPaymentId());

        PaymentProcessEvent paymentProcessEvent = new PaymentProcessEvent(validatePaymentCommand.getPaymentId(), validatePaymentCommand.getOrderId());

        AggregateLifecycle.apply(paymentProcessEvent);

        log.info("PaymentProcessEvent applied");
    }

    @EventSourcingHandler
    public void on(PaymentProcessEvent event) {
        this.paymentId = event.getPaymentId();
        this.orderId = event.getOrderId();
    }

    @CommandHandler
    public void handle(CancelPaymentCommand event) {
        PaymentCancelEvent paymentCancelEvent = PaymentCancelEvent.builder().build();
        BeanUtils.copyProperties(event, paymentCancelEvent);
        AggregateLifecycle.apply(paymentCancelEvent);
    }

    @EventSourcingHandler
    public void on(PaymentCancelEvent event) {
        this.paymentStatus = event.getPaymentStatus();
    }
}
