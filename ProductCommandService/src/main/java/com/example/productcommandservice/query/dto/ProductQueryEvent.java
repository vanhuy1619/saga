package com.example.productcommandservice.query.dto;

import com.example.productcommandservice.query.entity.ProductQuery;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductEvent {
    private String type;
    private ProductQuery product;
}
