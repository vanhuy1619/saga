package com.example.userservice.controller;

import com.example.commonservice.model.Orders;
import com.example.commonservice.model.User;
import com.example.commonservice.queries.GetOrderDetailsQuery;
import com.example.commonservice.queries.GetUserPaymentDetailsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private transient QueryGateway queryGateway;

    @GetMapping("/payments/{userId}")
    public User getUserPaymentDetails(@PathVariable String userId) {
        GetUserPaymentDetailsQuery getUserPaymentDetailsQuery =  new GetUserPaymentDetailsQuery(userId);

        User user = queryGateway.query(getUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
        return user;
    }

    @GetMapping("/order/{orderId}")
    public Orders getOrderDetails(@PathVariable String orderId) {
        GetOrderDetailsQuery getOrderDetailsQuery =  GetOrderDetailsQuery.builder().orderId(orderId).build();

        Orders orders = queryGateway.query(getOrderDetailsQuery, ResponseTypes.instanceOf(Orders.class)).join();
        return orders;
    }

}
