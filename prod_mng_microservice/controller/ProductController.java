package prod_mng_microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prod_mng_microservice.model.ApiResponse;
import prod_mng_microservice.model.Category;
import prod_mng_microservice.model.Products;
import prod_mng_microservice.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add_product")
    public ResponseEntity<ApiResponse<Products>> addProduct(@RequestBody Products products) {
        ApiResponse<Products> response = productService.addProduct(products);
        HttpStatus status = HttpStatus.valueOf(response.getCode());
        return new ResponseEntity<>(response, status);
    }

    @PostMapping("/add_category")
    public ResponseEntity<ApiResponse<Category>> addCategory(@RequestBody Category category) {
        System.out.println("Received_category1: " + category.getName()); // Debug log
        ApiResponse<Category> response = productService.addCategory(category);
        HttpStatus status = HttpStatus.valueOf(response.getCode());
        return new ResponseEntity<>(response, status);
    }

}
