package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter

public class ShopOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID orderId;

    @NotBlank
    private String address;

    @Positive
    private BigDecimal price;

    @NotBlank
    private String status;

    @OneToOne(mappedBy = "order", cascade = CascadeType.REMOVE)
    @Valid
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<@Valid Product> products;

}
