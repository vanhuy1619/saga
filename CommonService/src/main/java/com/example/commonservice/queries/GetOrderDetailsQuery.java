package com.example.commonservice.queries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderDetailsQuery {
    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private String quantity;
    private String orderStatus;
}
