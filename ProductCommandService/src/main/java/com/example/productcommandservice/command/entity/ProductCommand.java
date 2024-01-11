package com.example.productcommandservice.command.entity;

import com.example.productcommandservice.common.type.ProductType;
import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_COMMAND")
public class ProductCommand extends ProductType {
    public ProductCommand() {
        super();
    }

    public ProductCommand(ProductType productType) {
        this.setName(productType.getName());
        this.setPrice(productType.getPrice());
        this.setDescription(productType.getDescription());
    }
}
