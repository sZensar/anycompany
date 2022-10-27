package com.anycompany.service.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anycompany.datalayer.CustomerReposistory;
import com.anycompany.datalayer.OrderReposistry;
import com.anycompany.dto.OrderDto;
import com.anycompany.exceptions.CustomerNotFoundException;
import com.anycompany.model.Customer;
import com.anycompany.model.Order;
import com.anycompany.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private CustomerReposistory customerReposistory;

	@Autowired
	private OrderReposistry orderReposistry;

	@Override
	public OrderDto createOrder(OrderDto orderDto) {
		Optional<Customer> customerWrapper = customerReposistory.findById(orderDto.getCustomerId());
		if (customerWrapper.isEmpty()) {
			throw new CustomerNotFoundException("Customer details not found.",
					"Customer details not present with customer id " + orderDto.getCustomerId()
							+ ". Please try with valid customer id.");
		}

		Order order = Order.builder().amount(orderDto.getAmount()).customer(customerWrapper.get()).build();
		if ("UK".equalsIgnoreCase(order.getCustomer().getCountry())) {
			order.setVat(0.2d);
		} else {
			order.setVat(0.0d);
		}

		order = orderReposistry.save(order);
		BeanUtils.copyProperties(orderDto, order);

		return orderDto;
	}
}
