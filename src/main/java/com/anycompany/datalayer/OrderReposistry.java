package com.anycompany.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anycompany.model.Order;

@Repository
public interface OrderReposistry extends JpaRepository<Order, Integer> {

}
