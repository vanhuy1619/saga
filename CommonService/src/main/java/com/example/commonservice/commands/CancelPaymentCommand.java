package com.example.commonservice.commands;

import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
@Builder
public class CancelPaymentCommand {
    @TargetAggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus;
}
