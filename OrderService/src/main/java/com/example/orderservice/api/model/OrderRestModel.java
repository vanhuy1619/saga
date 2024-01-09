package com.example.orderservice.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRestModel {
    private String productId;
    private String userId;
    private String addressId;
    private Integer quantity;
}
