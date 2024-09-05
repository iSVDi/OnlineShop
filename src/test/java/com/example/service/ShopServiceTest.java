package com.example.service;

import com.example.model.Customer;
import com.example.model.Product;
import com.example.model.ShopOrder;
import com.example.repository.CustomerRepository;
import com.example.repository.ProductRepository;
import com.example.repository.ShopOrderRepository;
import com.example.tools.StringJsonConverter;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShopServiceTest {

    private final StringJsonConverter stringJsonConverter = new StringJsonConverter();
    @Mock
    private ShopOrderRepository shopOrderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private ShopService shopService;
    private String jsonProduct;
    private Product product;
    private String jsonCustomer;
    private Customer customer;
    private String jsonOrderDto;
    private String jsonOrder;
    private ShopOrder shopOrder;

    @BeforeEach
    void init() {
        jsonProduct = """
                {
                    "productId": "8d00a199-446b-476d-8f21-09e123310b57",
                    "name": "Phone",
                    "description": "Ergonomic wireless mouse with adjustable",
                    "price": 29.99,
                    "quantityInStock": 2
                }
                """;

        product = Product.builder()
                .productId(UUID.fromString("8d00a199-446b-476d-8f21-09e123310b57"))
                .name("Phone")
                .description("Ergonomic wireless mouse with adjustable")
                .price(BigDecimal.valueOf(29.99))
                .orders(new ArrayList<>())
                .quantityInStock(2).build();

        jsonCustomer = """
                {
                  "customerId": "aa7937ac-1246-4fad-b8ba-2e24a1f60af3",
                  "firstName": "Donald",
                  "lastName": "Doe",
                  "email": "john.doe@example.com",
                  "contactNumber": "+7(123)-456-78-90"
                }
                                
                """;

        customer = Customer.builder()
                .customerId(UUID.fromString("aa7937ac-1246-4fad-b8ba-2e24a1f60af3"))
                .firstName("Donald")
                .lastName("Doe")
                .email("john.doe@example.com")
                .contactNumber("+7(123)-456-78-90").build();

        jsonOrderDto = """
                {
                    "customerId": "aa7937ac-1246-4fad-b8ba-2e24a1f60af3",
                    "productsId": [
                        "8d00a199-446b-476d-8f21-09e123310b57"
                    ],
                    "orderDate": "10.09.1980",
                    "shippingAddress": "address",
                    "status": "status"
                }
                """;

        jsonOrder = """
                {
                         "orderId": "c69ccd65-c76c-4779-aae1-81ead0b65d5a",
                         "customer": {
                             "customerId": "aa7937ac-1246-4fad-b8ba-2e24a1f60af3",
                             "firstName": "Donald",
                             "lastName": "Doe",
                             "email": "john.doe@example.com",
                             "contactNumber": "+7(123)-456-78-90"
                         },
                         "products": [
                             {
                                 "productId": "8d00a199-446b-476d-8f21-09e123310b57",
                                 "name": "Phone",
                                 "description": "Ergonomic wireless mouse with adjustable",
                                 "price": 29.99,
                                 "quantityInStock": 2
                             }
                         ],
                         "orderDate": "10.09.1980",
                         "shippingAddress": "address",
                         "totalPrice": 1999.99,
                         "status": "status"
                     }
                """;

        shopOrder = ShopOrder.builder()
                .orderId(UUID.fromString("c69ccd65-c76c-4779-aae1-81ead0b65d5a"))
                .customer(customer)
                .products(List.of(product))
                .orderDate(LocalDate.of(1980, 9, 10))
                .shippingAddress("address")
                .totalPrice(BigDecimal.valueOf(1999.99))
                .status("status").build();

    }

    @Test
    void createProduct() {
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        String savedProductString = shopService.createProduct(jsonProduct);
        Assertions.assertEquals(product, stringJsonConverter.toObject(savedProductString, Product.class));
    }

    @Test
    void readProduct() {
        when(productRepository.findById(product.getProductId())).thenReturn(Optional.ofNullable(product));

        String savedProductString = shopService.readProduct(product.getProductId());
        Assertions.assertEquals(product, stringJsonConverter.toObject(savedProductString, Product.class));
    }

    @Test
    void updateProduct() {
        when(productRepository.findById(product.getProductId())).thenReturn(Optional.ofNullable(product));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        String savedProductString = shopService.updateProduct(jsonProduct);
        Assertions.assertEquals(product, stringJsonConverter.toObject(savedProductString, Product.class));

    }

    @Test
    void deleteProduct() {
        when(productRepository.existsById(product.getProductId())).thenReturn(true);
        assertAll(() -> shopService.deleteProduct(product.getProductId()));
    }

    @Test
    void createCustomer() {
        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);

        String saveCustomerString = shopService.createCustomer(jsonCustomer);
        Assertions.assertEquals(stringJsonConverter.toObject(saveCustomerString, Customer.class), customer);
    }

    @Test
    void createOrder() {
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.ofNullable(customer));
        when(productRepository.findAllById(Mockito.any())).thenReturn(List.of(product));
        when(productRepository.saveAll(Mockito.any())).thenReturn(List.of(product));
        when(shopOrderRepository.save(Mockito.any(ShopOrder.class))).thenReturn(shopOrder);

        String savedJsonOrder = shopService.createOrder(jsonOrderDto);
        ShopOrder savedOrder = stringJsonConverter.toObject(savedJsonOrder, ShopOrder.class);
        Assertions.assertEquals(savedOrder, shopOrder);
    }

    @Test
    void readOrder() {
        when(shopOrderRepository.findById(shopOrder.getOrderId())).thenReturn(Optional.ofNullable(shopOrder));

        String jsonSavedOrder = shopService.readOrder(shopOrder.getOrderId());
        ShopOrder savedOrder = stringJsonConverter.toObject(jsonSavedOrder, ShopOrder.class);
        Assertions.assertEquals(savedOrder, shopOrder);
    }



}
