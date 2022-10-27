package com.anycompany.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anycompany.datalayer.CustomerReposistory;
import com.anycompany.dto.CustomerDto;
import com.anycompany.dto.OrderDto;
import com.anycompany.exceptions.BusinessException;
import com.anycompany.exceptions.CustomerNotFoundException;
import com.anycompany.model.Customer;
import com.anycompany.model.Order;
import com.anycompany.service.CustomerService;
import com.anycompany.utility.AnyCompanyMapperUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	private static final String CUSTOMER_ID_ERROR_MESSAGE_DETAILS = "Customer id can not be 0.";
	private static final String CUSTOMER_ID_ERROR_MESSAGE = "Please enter valid customer id.";
	private static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer details not found.";

	@Autowired
	private CustomerReposistory customerReposistory;

	@Override
	public CustomerDto getCustomerDetails(int customerId) {

		if (customerId <= 0) {
			throw new BusinessException(CUSTOMER_ID_ERROR_MESSAGE, CUSTOMER_ID_ERROR_MESSAGE_DETAILS);
		}

		Optional<Customer> customerWrapper = customerReposistory.findById(customerId);
		if (customerWrapper.isEmpty()) {
			throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE,
					"Customer details not present with customer id " + customerId
							+ ". Please try with valid customer id.");
		}
		CustomerDto customerDto = new CustomerDto();
		Customer customer = customerWrapper.get();
		BeanUtils.copyProperties(customer, customerDto);
		customerDto.setOrders(customer.getOrders().stream().map(AnyCompanyMapperUtility::orderDtoMapper)
				.collect(Collectors.toList()));
		return customerDto;
	}

	@Override
	public List<CustomerDto> getAllCustomerDetails() {
		/*
		 * Function<Customer, CustomerDto> mapper = (consumer) -> { CustomerDto
		 * customerDto = new CustomerDto(); BeanUtils.copyProperties(consumer,
		 * customerDto);
		 * 
		 * 
		 * Function<Order, OrderDto> innerMapper = (order) -> { OrderDto orderDto = new
		 * OrderDto(); BeanUtils.copyProperties(order, orderDto); return orderDto; };
		 * 
		 * customerDto.setOrders(consumer.getOrders().stream().map(
		 * AnyCompanyMapperUtility::orderDtoMapper).collect(Collectors.toList()));
		 * return customerDto; };
		 */

		List<CustomerDto> customers = customerReposistory.findAll().stream()
				.map(AnyCompanyMapperUtility::customerDtoMapper).collect(Collectors.toList());
		return customers;
	}

	@Override
	public CustomerDto saveCustomerDetails(CustomerDto customerDto) {
		Customer customer = Customer.builder().country(customerDto.getCountry())
				.dateOfBirth(customerDto.getDateOfBirth()).name(customerDto.getName()).build();
		customer = customerReposistory.save(customer);
		BeanUtils.copyProperties(customer, customerDto);
		return customerDto;
	}

}