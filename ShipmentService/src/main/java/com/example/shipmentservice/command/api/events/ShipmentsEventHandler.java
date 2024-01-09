package com.example.shipmentservice.command.api.events;

import com.example.commonservice.events.OrderShippedEvent;
import com.example.shipmentservice.command.api.data.Shipment;
import com.example.shipmentservice.command.api.repositories.ShipmentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class ShipmentsEventHandler {
    @Autowired
    private ShipmentRepository shipmentRepository;

    @EventHandler
    public void on(OrderShippedEvent event) {
        Shipment shipment = new Shipment();

        BeanUtils.copyProperties(event, shipment);
        shipmentRepository.save(shipment);
    }
}
