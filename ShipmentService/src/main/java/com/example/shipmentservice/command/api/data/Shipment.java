package com.example.shipmentservice.command.api.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Shipment {
    @Id
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;
}
