package com.example.productcommandservice.command.entity;

import com.example.productcommandservice.common.type.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT_COMMAND")
public class Product extends ProductType {
}
