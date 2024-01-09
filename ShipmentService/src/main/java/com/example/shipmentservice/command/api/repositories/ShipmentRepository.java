package com.example.shipmentservice.command.api.repositories;

import com.example.shipmentservice.command.api.data.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, String> {
}
