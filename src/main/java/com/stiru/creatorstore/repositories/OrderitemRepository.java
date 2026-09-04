package com.stiru.creatorstore.repositories;

import com.stiru.creatorstore.entites.Orderitems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderitemRepository extends JpaRepository<Orderitems, Long> {
}
