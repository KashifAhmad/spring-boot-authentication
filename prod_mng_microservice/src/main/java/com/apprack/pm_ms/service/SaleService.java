package com.apprack.pm_ms.service;

import com.apprack.pm_ms.model.ApiResponse;
import com.apprack.pm_ms.model.Products;
import com.apprack.pm_ms.model.sale.SaleItem;
import com.apprack.pm_ms.model.sale.SaleRequest;
import com.apprack.pm_ms.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.apprack.pm_ms.contants.HttpResponseCodes.SUCCESS_CODE;
import static com.apprack.pm_ms.contants.HttpResponseMessages.SUCCESS_MESSAGE;

@Service
public class SaleService {

    @Autowired
    private ProductRepository productRepository;

    public ApiResponse<String> processSale(SaleRequest saleRequest) {
        List<SaleItem> saleItems = saleRequest.getProducts();

        for (SaleItem item : saleItems) {
            Products product = productRepository.findByProductName(item.getProductName());
            if (product == null) {
                throw new RuntimeException("Product not found: " + item.getProductName());
            }

            if (product.getQuantity() < item.getQuantity()) {
                return ApiResponse.error(400, "Insufficient stock for product: " + product.getProductName());
            }

            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);
        }

        return  ApiResponse.success(SUCCESS_CODE, saleRequest.invoiceNumber , SUCCESS_MESSAGE);
    }
}