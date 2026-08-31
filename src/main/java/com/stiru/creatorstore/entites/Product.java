package com.stiru.creatorstore.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Cannot be Null")
    @Column(nullable = false)
    private String name;

    private String description;

    private String category;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price Must Be Greater Than 0")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull(message = "Stock Quantity is required")
    @Min(value = 0, message = "Stock can not be less than 0")
    @Column(name = "stock_quantity",nullable = false)
    private Integer stockQuantity;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Orderitems> orderItems;
}


