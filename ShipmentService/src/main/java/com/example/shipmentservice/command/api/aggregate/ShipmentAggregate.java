package com.example.shipmentservice.command.api.aggregate;

import com.example.commonservice.commands.ShipOrderCommand;
import com.example.commonservice.contanst.OrderStatus;
import com.example.commonservice.events.OrderShippedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class ShipmentAggregate {
    @AggregateIdentifier
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;

    public ShipmentAggregate() {

    }

    @CommandHandler
    public ShipmentAggregate(ShipOrderCommand shipOrderCommand){
        //validate the command
        //publish the order shipped event
        log.info("Received payment successfully");
        OrderShippedEvent orderShippedEvent = OrderShippedEvent.builder()
                .orderId(shipOrderCommand.getOrderId())
                .shipmentId(shipOrderCommand.getShipmentId())
                .shipmentStatus(OrderStatus.COMPLETED.toString())
                .build();

        AggregateLifecycle.apply(orderShippedEvent);
    }

    @EventSourcingHandler
    public void on(OrderShippedEvent event) {
        this.orderId = event.getOrderId();
        this.shipmentId = event.getShipmentId();
        this.shipmentStatus = event.getShipmentStatus();
    }
}
