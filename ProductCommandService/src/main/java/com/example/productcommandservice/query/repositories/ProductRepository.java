package com.example.productcommandservice.query.repositories;

import com.example.productcommandservice.query.entity.ProductQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository("queryProductRepository")
public interface ProductRepository extends JpaRepository<ProductQuery, Long> {
    Optional<ProductQuery> findByIdCommand(Long idCommand);
}
