package com.example.productcommandservice.query.entity;

import com.example.productcommandservice.common.type.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT_QUERY")
public class ProductQuery extends ProductType {
    @Column(name = "ID_COMMAND")
    private long idCommand;
}
