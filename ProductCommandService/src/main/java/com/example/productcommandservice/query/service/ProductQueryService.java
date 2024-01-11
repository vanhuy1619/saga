package com.example.productcommandservice.query.service;

import com.example.productcommandservice.command.dto.ProductCommandEvent;
import com.example.productcommandservice.common.utils.JsonUtils;
import com.example.productcommandservice.query.dto.ProductQueryEvent;
import com.example.productcommandservice.query.entity.ProductQuery;
import com.example.productcommandservice.query.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQueryService {
    @Autowired
    private ProductRepository repository;

    public List<ProductQuery> getProducts() {
        return repository.findAll();
    }

    @KafkaListener(topics = "${product-command.topic}", groupId = "${product-command.groupId}", autoStartup = "${product-command.autoStartup}")
    public void processProductEvents(@Payload ProductCommandEvent message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println(message);
        ProductQueryEvent productQueryEvent = JsonUtils.fromJson(JsonUtils.toJson(message), ProductQueryEvent.class);

        ProductQuery product = productQueryEvent.getProduct();
        if (productQueryEvent.getType().equals("CreateProduct")) {
            product.setIdCommand(message.getProduct().getId());
            repository.save(product);
        }
        if (productQueryEvent.getType().equals("UpdateProduct")) {
            ProductQuery existingProduct = repository.findByIdCommand(product.getIdCommand()).get();
            System.out.println(existingProduct);
            existingProduct.setName(product.getName());
            repository.save(existingProduct);
        }
    }
}
