package com.example.productcommandservice.command.dto;

import com.example.productcommandservice.command.entity.ProductCommand;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCommandEvent {
    @JsonProperty("type")
    private String type;

    @JsonProperty("product")
    private ProductCommand product;
}
