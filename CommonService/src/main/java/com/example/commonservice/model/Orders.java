package com.example.commonservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private String quantity;
    private String orderStatus;
}