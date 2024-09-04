package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID customerId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^\\+7\\([0-9]{3}\\)-[0-9]{3}-[0-9]{2}-[0-9]{2}$")
    @NotBlank
    private String contactNumber;

    @OneToOne
    @JoinColumn(name = "order_id")
    @Valid
    @JsonIgnore
    private ShopOrder order;

}
