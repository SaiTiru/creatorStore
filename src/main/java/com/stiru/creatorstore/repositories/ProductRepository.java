package com.stiru.creatorstore.repositories;

import com.stiru.creatorstore.entites.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

