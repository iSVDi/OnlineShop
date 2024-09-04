package com.example.controller;

import com.example.model.Product;
import com.example.model.ShopOrder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
@Validated
public class ShopController {

    // create product
    @PostMapping("createProduct")
    void createProduct(@Validated @RequestBody Product product) {
        System.out.println();
    }

    // read product
    @GetMapping("readProduct")
    Product readProduct(@RequestParam(value = "id") UUID id) {
        return new Product();
    }

    //  read all products
    @GetMapping("readAllProducts")
    List<Product> readAllProduct() {
        return new ArrayList<Product>();

    }

    // update product
    @PutMapping("updateProduct")
    Product updateProduct(@Validated @RequestBody Product product) {
        return new Product();
    }

    // delete product
    @DeleteMapping("deleteProduct")
    void deleteProduct(@RequestParam(value = "id") UUID id) {
        System.out.println();
    }

    //create order
    @PostMapping("createOrder")
    void createOrder(@Validated @RequestBody ShopOrder order) {
        System.out.println();
    }

    // read order
    @GetMapping("readOrder")
    ShopOrder readOrder(@RequestParam(value = "id") UUID id) {
        return new ShopOrder();
    }

}
