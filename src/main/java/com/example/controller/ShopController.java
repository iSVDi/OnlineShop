package com.example.controller;

import com.example.service.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
@AllArgsConstructor
@Validated
public class ShopController {

    private ShopService shopService;

    //createCustomer

    @PostMapping("createCustomer")
    String createCustomer(@RequestBody String jsonCustomer) {
        return shopService.createCustomer(jsonCustomer);
    }

    // create product
    @PostMapping("createProduct")
    String createProduct(@RequestBody String jsonProduct) {
        return shopService.createProduct(jsonProduct);
    }

    // read product
    @GetMapping("readProduct")
    String readProduct(@RequestParam(value = "id") UUID id) {
        return shopService.readProduct(id);
    }

    //  read all products
    @GetMapping("readAllProducts")
    String readAllProduct() {
        return shopService.readAllProducts();

    }

    // update product
    @PutMapping("updateProduct")
    String updateProduct(@RequestBody String jsonProduct) {
        return shopService.updateProduct(jsonProduct);
    }

    // delete product
    @DeleteMapping("deleteProduct")
    void deleteProduct(@RequestParam(value = "id") UUID id) {
        shopService.deleteProduct(id);
    }

    //create order
    @PostMapping("createOrder")
    String createOrder(@RequestBody String jsonShopOrederDTO) {
        return shopService.createOrder(jsonShopOrederDTO);
    }

    // read order
    @GetMapping("readOrder")
    String readOrder(@RequestParam(value = "id") UUID id) {
        return shopService.readOrder(id);
    }

}
