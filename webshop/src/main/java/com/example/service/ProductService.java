package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;  // Import repository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;  // Inject ProductRepository

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);  // Hoặc xử lý logic khác nếu cần
    }

}
