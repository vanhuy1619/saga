package com.example.commonservice.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCancelEvent {
    private String paymentId;
    private String orderId;
    private String paymentStatus;
}
