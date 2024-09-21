package prod_mng_microservice.repository;

import auth_mircroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prod_mng_microservice.model.Category;
import prod_mng_microservice.model.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    Products findByProductName(String name);

}
