package com.apprack.inventory.repository;

import com.apprack.inventory.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Products, Long> {
    Products findByProductName(String name);
}
