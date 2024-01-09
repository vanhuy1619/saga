package com.example.paymentservice.command.api.repositories;

import com.example.paymentservice.command.api.data.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
