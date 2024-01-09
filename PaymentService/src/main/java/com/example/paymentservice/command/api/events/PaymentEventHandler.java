package com.example.paymentservice.command.api.events;

import com.example.commonservice.contanst.OrderStatus;
import com.example.commonservice.events.PaymentCancelEvent;
import com.example.commonservice.events.PaymentProcessEvent;
import com.example.paymentservice.command.api.data.Payment;
import com.example.paymentservice.command.api.repositories.PaymentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Component
@Service
public class PaymentEventHandler {
    @Autowired
    private PaymentRepository paymentRepository;

    @EventHandler
    public void on(PaymentProcessEvent event) {
        Payment payment = Payment.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .paymentStatus(OrderStatus.COMPLETED.toString())
                .timeStamp(new Date())
                .build();

        paymentRepository.save(payment);
    }

    @EventHandler
    public void on(PaymentCancelEvent event) {
        Payment payment = paymentRepository.findById(event.getPaymentId()).get();
        payment.setPaymentStatus(event.getPaymentStatus());

        paymentRepository.save(payment);
    }
}
