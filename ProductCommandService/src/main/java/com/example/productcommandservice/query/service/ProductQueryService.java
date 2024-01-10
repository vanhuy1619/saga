package com.example.productcommandservice.query.service;

import com.example.productcommandservice.query.dto.ProductEvent;
import com.example.productcommandservice.query.entity.ProductQuery;
import com.example.productcommandservice.query.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQueyService {
    @Autowired
    private ProductRepository repository;

    public List<ProductQuery> getProducts() {
        return repository.findAll();
    }

    @KafkaListener(topics = "${product-command.topic}",groupId = "${product-command.groupId}", autoStartup = "${product-command.autoStartup}")
    public void processProductEvents(ProductEvent productEvent) {
        ProductQuery product = productEvent.getProduct();
        if (productEvent.getType().equals("CreateProduct")) {
            repository.save(product);
        }
        if (productEvent.getType().equals("UpdateProduct")) {
            ProductQuery existingProduct = repository.findById(product.getId()).get();
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());
            repository.save(existingProduct);
        }
    }
}
