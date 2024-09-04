package com.example.service;


import com.example.model.Customer;
import com.example.model.Product;
import com.example.repository.CustomerRepository;
import com.example.repository.ProductRepository;
import com.example.repository.ShopOrderRepository;
import com.example.tools.StringJsonConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ShopService {
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private ShopOrderRepository shopOrderRepository;

    //TODO handle exceptions for crud operation product
    public String createProduct(String jsonProduct) {
        Product product = StringJsonConverter.toObject(jsonProduct, Product.class);
        Product savedProduct = productRepository.save(product);
        return StringJsonConverter.toString(savedProduct);
    }

    public String readProduct(UUID id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return StringJsonConverter.toString(optionalProduct.get());
        }
        // TODO add throw Exception
        throw new RuntimeException("product with id not exist");
    }

    public String readAllProducts() {
        List<Product> productList = productRepository.findAll();
        return StringJsonConverter.toString(productList);
    }


    public String updateProduct(String jsonProduct) {
        Product product = StringJsonConverter.toObject(jsonProduct, Product.class); // TODO what if product has got with null id?
        Optional<Product> optionalProduct = productRepository.findById(product.getProductId());
        if (optionalProduct.isPresent()) {
            Product savedProduct = productRepository.save(product);
            return StringJsonConverter.toString(savedProduct);
        }

        // TODO add throw Exception
        throw new RuntimeException("product with id not exist");
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    public String createCustomer(String jsonCustomer) {
        Customer customer = StringJsonConverter.toObject(jsonCustomer, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return StringJsonConverter.toString(savedCustomer);
    }
}

