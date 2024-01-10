package com.example.productcommandservice.common.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.MappedSuperclass;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Product {
    private String name;
    private Double price;
    private String description;
}

