package com.example.service;


import com.example.exception.ShopException;
import com.example.exception.ShopExceptionTitle;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ShopService {
    private final StringJsonConverter stringJsonConverter = new StringJsonConverter();
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private ShopOrderRepository shopOrderRepository;

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

        throw new ShopException(ShopExceptionTitle.PRODUCT_WITH_SUCH_ID_DOESNT_EXIST);
    }

    public String readAllProducts() {
        List<Product> productList = productRepository.findAll();
        return stringJsonConverter.toString(productList);
    }


    public String updateProduct(String jsonProduct) {
        Product product = stringJsonConverter.toObject(jsonProduct, Product.class);
        Optional<Product> optionalProduct = productRepository.findById(product.getProductId());
        if (optionalProduct.isPresent()) {
            Product savedProduct = productRepository.save(product);
            return stringJsonConverter.toString(savedProduct);
        }
        throw new ShopException(ShopExceptionTitle.PRODUCT_WITH_SUCH_ID_DOESNT_EXIST);
    }

    public void deleteProduct(UUID id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ShopException(ShopExceptionTitle.PRODUCT_WITH_SUCH_ID_DOESNT_EXIST);
        }

    }

    public String createCustomer(String jsonCustomer) {
        Customer customer = stringJsonConverter.toObject(jsonCustomer, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return stringJsonConverter.toString(savedCustomer);
    }


    public String createOrder(String jsonOrderDto) {
        ShopOrderDTO shopOrderDTO = stringJsonConverter.toObject(jsonOrderDto, ShopOrderDTO.class);
        // get consumer
        Optional<Customer> optionalCustomer = customerRepository.findById(shopOrderDTO.getCustomerId());
        if (optionalCustomer.isEmpty()) {
            throw new ShopException(ShopExceptionTitle.CUSTOMER_WITH_SUCH_ID_DOESNT_EXIST);
        }

        // get products
        List<Product> acceptableProducts = productRepository
                .findAllById(shopOrderDTO.getProductsId())
                .stream()
                .filter(product -> {
                    return product.getQuantityInStock() > product.getOrders().size();
                })
                .toList();

        if (acceptableProducts.isEmpty()) {
            throw new ShopException(ShopExceptionTitle.EMPTY_PRODUCTS_LIST);
        }
        ShopOrder order = new ShopOrder(shopOrderDTO);

        acceptableProducts.forEach(product -> {
            order.getProducts().add(product);
            product.getOrders().add(order);
        });
        productRepository.saveAll(acceptableProducts);

        //save order
        order.setCustomer(optionalCustomer.get());
        order.setProducts(acceptableProducts);
        BigDecimal totalPrice = acceptableProducts.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(totalPrice);
        ShopOrder savedOrder = shopOrderRepository.save(order);
        return stringJsonConverter.toString(savedOrder);
    }


    public String readOrder(UUID id) {
        Optional<ShopOrder> optionalShopOrder = shopOrderRepository.findById(id);

        if (optionalShopOrder.isPresent()) {
            return stringJsonConverter.toString(optionalShopOrder.get());
        }
        throw new ShopException(ShopExceptionTitle.ORDER_WITH_SUCH_ID_DOESNT_EXIST);
    }
}

