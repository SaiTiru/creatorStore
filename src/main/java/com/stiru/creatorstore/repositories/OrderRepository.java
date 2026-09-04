package com.stiru.creatorstore.repositories;

import com.stiru.creatorstore.entites.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
