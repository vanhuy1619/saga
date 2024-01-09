package com.example.orderservice.api.controller;

import com.example.orderservice.api.model.OrderRestModel;
import com.example.orderservice.api.command.CreateOrderCommand;
import com.example.commonservice.contanst.OrderStatus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderCommandController {
    private CommandGateway commandGateway;

    public OrderCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderRestModel orderRestModel) {
        String orderId = UUID.randomUUID().toString();
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(orderId)
                .addressId(orderRestModel.getAddressId())
                .productId(orderRestModel.getProductId())
                .quantity(orderRestModel.getQuantity())
                .orderStatus(OrderStatus.CREATED.toString())
                .userId(orderRestModel.getUserId())
                .build();
        commandGateway.sendAndWait(createOrderCommand);

        return "Order Created";
    }
}
