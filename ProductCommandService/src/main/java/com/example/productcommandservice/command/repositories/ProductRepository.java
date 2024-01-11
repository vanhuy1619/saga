package com.example.productcommandservice.command.repositories;

import com.example.productcommandservice.command.entity.ProductCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("commandProductRepository")
public interface ProductRepository extends JpaRepository<ProductCommand, Long> {
}
