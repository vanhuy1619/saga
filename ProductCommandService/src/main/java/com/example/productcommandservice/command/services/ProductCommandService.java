package com.example.productcommandservice.command.services;

import com.example.productcommandservice.command.config.ProductConfig;
import com.example.productcommandservice.command.dto.ProductCommandEvent;
import com.example.productcommandservice.command.entity.ProductCommand;
import com.example.productcommandservice.command.repositories.ProductRepository;
import com.example.productcommandservice.common.type.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private KafkaTemplate<String, ProductCommandEvent> kafkaTemplate;

    @Autowired
    private ProductConfig productConfig;

    public ProductCommand createProduct(ProductType productType){
        ProductCommandEvent productCommandEvent = ProductCommandEvent.builder()
                .type("CreateProduct")
                .product(new ProductCommand(productType))
                .build();
        ProductCommand productDO = repository.save(productCommandEvent.getProduct());
        kafkaTemplate.send(productConfig.getTopic(), productCommandEvent);
        return productDO;
    }

    public ProductCommand updateProduct(long id, ProductType productType){
        ProductCommand existingProduct = repository.findById(id).get();
        ProductCommand newProduct = new ProductCommand(productType);
        existingProduct.setName(newProduct.getName());
        existingProduct.setPrice(newProduct.getPrice());
        existingProduct.setDescription(newProduct.getDescription());
        ProductCommand productDO = repository.save(existingProduct);
        ProductCommandEvent event = new ProductCommandEvent("UpdateProduct", productDO);
        kafkaTemplate.send(productConfig.getTopic(), event);
        return productDO;
    }
}
