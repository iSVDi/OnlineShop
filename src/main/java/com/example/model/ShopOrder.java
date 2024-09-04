package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter

/*
//TODO add ShopOrderDTO
//TODO implement ShopOrderDTO-> ShopOrder caster
OrderDTO
    customerID
    productsID
    orderDate
    shippingAddress
    status
    TODO add check count of products
 */


public class ShopOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID orderId;

    @OneToOne(mappedBy = "order", cascade = CascadeType.REMOVE)
    @Valid
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<@Valid Product> products;

    @Past
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate orderDate;

    @NotBlank
    private String shippingAddress;

    @Positive
    private BigDecimal totalPrice;

    @NotBlank
    private String status;



}
