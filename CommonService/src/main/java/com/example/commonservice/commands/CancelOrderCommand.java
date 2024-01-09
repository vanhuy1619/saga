package com.example.commonservice.commands;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
@Builder
public class CancelOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String orderStatus;
}
