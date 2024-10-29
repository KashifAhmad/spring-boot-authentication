package com.apprack.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.apprack.inventory", "com.apprack.auth"})
public class InventoryMicroserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryMicroserviceApplication.class, args);
    }

}
