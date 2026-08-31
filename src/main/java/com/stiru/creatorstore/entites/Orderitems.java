package com.stiru.creatorstore.entites;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orderitems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "price_at_purchase", nullable = false)
    private Boolean priceAtPurchase;

    private LocalDateTime createdAt;

    //TO DO RELATIONS
    @ManyToOne
    @JoinColumn(name ="order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
