package com.example.productcommandservice.query.controller;

import com.example.productcommandservice.query.entity.ProductQuery;
import com.example.productcommandservice.query.service.ProductQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductQueryController {

    @Autowired
    private ProductQueryService queryService;

    @GetMapping
    public List<ProductQuery> fetchAllProducts(){
        return queryService.getProducts();
    }


}