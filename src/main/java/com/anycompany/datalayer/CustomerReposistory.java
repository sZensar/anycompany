package com.anycompany.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anycompany.model.Customer;

@Repository
public interface CustomerReposistory extends JpaRepository<Customer, Integer> {

}
