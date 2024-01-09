package com.example.userservice.projection;

import com.example.commonservice.model.CardDetails;
import com.example.commonservice.model.Orders;
import com.example.commonservice.model.User;
import com.example.commonservice.queries.GetOrderDetailsQuery;
import com.example.commonservice.queries.GetUserPaymentDetailsQuery;
import com.example.orderservice.api.data.Order;
//import com.example.userservice.repositories.OrderRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UserProjection {
//    @Autowired
//    private OrderRepository orderRepository;

    @QueryHandler
    public User getUserPaymentDetails(GetUserPaymentDetailsQuery query) {
       //get the details from db
       CardDetails cardDetails = CardDetails.builder()
               .name("Huy")
               .validUntilMonth(12)
               .validUntilYear(2024)
               .cardNumber("123456789")
               .cvv(111)
               .build();

       return User.builder()
               .userId(query.getUserId())
               .firstname("Nguyen")
               .lastname("Huy")
               .cardDetails(cardDetails)
               .build();
    }

//    @QueryHandler
//    public Orders getOrderDetails(GetOrderDetailsQuery query) {
//        Optional<Order> orderEntityOptional = orderRepository.findById(query.getOrderId());
//
//        // Kiểm tra xem đơn hàng có tồn tại hay không
//        if (orderEntityOptional.isPresent()) {
//            // Chuyển đổi từ entity sang đối tượng Orders
//            Order orderEntity = orderEntityOptional.get();
//            return Orders.builder()
//                    .orderId(orderEntity.getOrderId())
//                    .productId(orderEntity.getProductId())
//                    .userId(orderEntity.getUserId())
//                    .addressId(orderEntity.getAddressId())
//                    .quantity(orderEntity.getQuantity())
//                    .orderStatus(orderEntity.getOrderStatus())
//                    .build();
//        } else {
//            return null;
//        }
//    }

}
