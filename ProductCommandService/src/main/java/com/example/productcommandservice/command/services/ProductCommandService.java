package com.example.productcommandservice.services;

import com.example.productcommandservice.config.ProductConfig;
import com.example.productcommandservice.dto.ProductEvent;
import com.example.productcommandservice.entity.Product;
import com.example.productcommandservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private KafkaTemplate<String, ProductEvent> kafkaTemplate;

    @Autowired
    private ProductConfig productConfig;

    public Product createProduct(ProductEvent productEvent){
        Product productDO = repository.save(productEvent.getProduct());
        ProductEvent event=new ProductEvent("CreateProduct", productDO);
        kafkaTemplate.send(productConfig.getTopic(), event);
        return productDO;
    }

    public Product updateProduct(long id,ProductEvent productEvent){
        Product existingProduct = repository.findById(id).get();
        Product newProduct=productEvent.getProduct();
        existingProduct.setName(newProduct.getName());
        existingProduct.setPrice(newProduct.getPrice());
        existingProduct.setDescription(newProduct.getDescription());
        Product productDO = repository.save(existingProduct);
        ProductEvent event=new ProductEvent("UpdateProduct", productDO);
        kafkaTemplate.send(productConfig.getTopic(), event);
        return productDO;
    }
}
