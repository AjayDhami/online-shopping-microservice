package com.ajay.microservices.product.service;

import com.ajay.microservices.product.dto.ProductRequest;
import com.ajay.microservices.product.dto.ProductResponse;
import com.ajay.microservices.product.model.Product;
import com.ajay.microservices.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        Product savedProduct = productRepository.save(product);
        log.info("Product created successfully");
        return new ProductResponse(savedProduct.getId(), savedProduct.getName(), savedProduct.getDescription(), savedProduct.getPrice());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> productList = productRepository.findAll();

        return productList.
                stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice()))
                .toList();
    }
}
