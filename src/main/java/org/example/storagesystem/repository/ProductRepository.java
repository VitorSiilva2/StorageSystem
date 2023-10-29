package org.example.storagesystem.repository;

import org.example.storagesystem.model.LocationModel;
import org.example.storagesystem.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductModel, UUID> {

    Optional<ProductModel> findById(UUID id);

    @Query("SELECT l FROM ProductModel l WHERE l.productSKU  = :sku")
    Optional<ProductModel> findBySku(@Param("sku") String sku);
}
