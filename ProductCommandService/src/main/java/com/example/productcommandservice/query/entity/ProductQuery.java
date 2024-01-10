package com.example.productcommandservice.query.entity;

import com.example.productcommandservice.common.type.ProductType;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "PRODUCT_QUERY")
public class Product extends ProductType {
}
