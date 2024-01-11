package com.example.orderservice.api.aggregate;

import com.example.commonservice.commands.CancelOrderCommand;
import com.example.commonservice.commands.CompleteOrderCommand;
import com.example.commonservice.events.OrderCancelEvent;
import com.example.commonservice.events.OrderCompletedEvent;
import com.example.orderservice.api.events.OrderCreatedEvent;
import com.example.orderservice.api.command.CreateOrderCommand;
import lombok.Builder;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private Integer quantity;
    private String orderStatus;

    private OrderAggregate() {


    }

    @CommandHandler //đánh dấu một phương thức trong một class là một xử lý lệnh (command handler). Command handler là nơi xử lý các lệnh được gửi đến một aggregate
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        //validate the command
        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder().build();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
        AggregateLifecycle.apply(orderCreatedEvent); //áp dụng sự kiện (events) và thay đổi trạng thái của aggregate
    }

    @EventSourcingHandler //@EventSourcingHandler. Các hàm này sẽ được gọi khi các event gắn liền với chúng xuất hiện.
    public void on(OrderCreatedEvent event) {
        this.orderStatus = event.getOrderStatus();
        this.userId = event.getUserId();
        this.orderId = event.getOrderId();
        this.quantity = event.getQuantity();
        this.productId = event.getProductId();
        this.addressId = event.getAddressId();
    }

    @CommandHandler
    public void handle(CompleteOrderCommand completeOrderCommand) {
        //validate the commnad
        //publish order complete event
        OrderCompletedEvent orderCompletedEvent = OrderCompletedEvent.builder()
                .orderId(completeOrderCommand.getOrderId())
                .orderStatus(completeOrderCommand.getOrderStatus())
                .build();
        AggregateLifecycle.apply(orderCompletedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event) {
        this.orderStatus = event.getOrderStatus();
    }

    @CommandHandler
    public void handle(CancelOrderCommand cancelOrderCommand) {
        OrderCancelEvent orderCancelEvent = OrderCancelEvent.builder().build();
        BeanUtils.copyProperties(cancelOrderCommand, orderCancelEvent);

        AggregateLifecycle.apply(orderCancelEvent);
    }

    @EventSourcingHandler //danh dau agg trong cqrs sd eventsourcing
    public void on(OrderCancelEvent event) {
        this.orderStatus = event.getOrderStatus();
    }

}
