package com.example.accountservice.command.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateAccountRequest {
    private BigDecimal balance; //startingBalance
}
