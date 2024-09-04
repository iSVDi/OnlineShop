package com.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Setter
public class ShopOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @Valid
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<@Valid Product> products;

    @Past
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate orderDate;

    @NotBlank
    private String shippingAddress;

    @Positive
    private BigDecimal totalPrice;

    @NotBlank
    private String status;

    public ShopOrder(ShopOrderDTO dto) {
        this.orderDate = dto.getOrderDate();
        this.shippingAddress = dto.getShippingAddress();
        this.status = dto.getStatus();
    }


}
