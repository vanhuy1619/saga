package com.example.productcommandservice.command.controller;


import com.example.productcommandservice.command.entity.ProductCommand;
import com.example.productcommandservice.command.services.ProductCommandService;
import com.example.productcommandservice.command.dto.ProductCommandEvent;
import com.example.productcommandservice.common.type.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductCommandController {
    @Autowired
    private ProductCommandService commandService;

    @PostMapping
    public ProductCommand createProduct(@RequestBody ProductType request) {
        return commandService.createProduct(request);
    }

    @PutMapping("/{id}")
    public ProductCommand updateProduct(@PathVariable long id, @RequestBody ProductType request) {
        return commandService.updateProduct(id, request);
    }
}
