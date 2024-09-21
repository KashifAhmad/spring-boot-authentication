package com.apprack.pm_ms.repository;

import com.apprack.pm_ms.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    Products findByProductName(String name);

}
