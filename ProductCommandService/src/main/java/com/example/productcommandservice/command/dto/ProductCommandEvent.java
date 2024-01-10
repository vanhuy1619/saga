package com.example.productcommandservice.command.dto;

import com.example.productcommandservice.command.entity.ProductCommand;
import com.example.productcommandservice.common.type.ProductType;
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
public class ProductEvent{
    private String type;
    private ProductCommand product;
}
