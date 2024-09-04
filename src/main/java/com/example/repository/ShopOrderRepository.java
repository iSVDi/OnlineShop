package com.example.repository;

import com.example.model.Product;
import com.example.model.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, UUID> {
    List<ShopOrder> findShopOrderByProductsContains(Product product);
}
