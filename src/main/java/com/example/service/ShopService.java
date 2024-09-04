package com.example.service;


import com.example.model.Customer;
import com.example.model.Product;
import com.example.model.ShopOrder;
import com.example.model.ShopOrderDTO;
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
    private final StringJsonConverter stringJsonConverter = new StringJsonConverter();

    //TODO handle exceptions for crud operation product
    public String createProduct(String jsonProduct) {
        Product product = stringJsonConverter.toObject(jsonProduct, Product.class);
        Product savedProduct = productRepository.save(product);
        return stringJsonConverter.toString(savedProduct);
    }

    public String readProduct(UUID id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return stringJsonConverter.toString(optionalProduct.get());
        }
        // TODO add throw Exception
        throw new RuntimeException("product with id not exist");
    }

    public String readAllProducts() {
        List<Product> productList = productRepository.findAll();
        return stringJsonConverter.toString(productList);
    }


    public String updateProduct(String jsonProduct) {
        Product product = stringJsonConverter.toObject(jsonProduct, Product.class); // TODO what if product has got with null id?
        Optional<Product> optionalProduct = productRepository.findById(product.getProductId());
        if (optionalProduct.isPresent()) {
            Product savedProduct = productRepository.save(product);
            return stringJsonConverter.toString(savedProduct);
        }

        // TODO add throw Exception
        throw new RuntimeException("product with id not exist");
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    public String createCustomer(String jsonCustomer) {
        Customer customer = stringJsonConverter.toObject(jsonCustomer, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return stringJsonConverter.toString(savedCustomer);
    }


    //TODO add checking count of products
    public String createOrder(String jsonOrderDto) {
        ShopOrderDTO shopOrderDTO = stringJsonConverter.toObject(jsonOrderDto, ShopOrderDTO.class);
        // get consumer
        Optional<Customer> optionalCustomer = customerRepository.findById(shopOrderDTO.getCustomerId());
        if (optionalCustomer.isEmpty()) {
            // TODO add throw custom Exception
            throw new RuntimeException("Customer with id not exist");
        }
        // get products
        List<Product> products = productRepository.findAllById(shopOrderDTO.getProductsId());
        if (products.isEmpty()) {
            // TODO add throw custom Exception
            throw new RuntimeException("Cannot create order without products");
        }
        //save order
        ShopOrder order = new ShopOrder(shopOrderDTO);
        order.setCustomer(optionalCustomer.get());
        order.setProducts(products);
        ShopOrder savedOrder = shopOrderRepository.save(order);
        return stringJsonConverter.toString(savedOrder);
    }


    public String readOrder(UUID id) {
        return "need implement";
    }
}

