package com.anycompany.service;

import java.util.List;

import com.anycompany.dto.CustomerDto;

public interface CustomerService {
	
	public CustomerDto getCustomerDetails(int customerId);
	
	public List<CustomerDto> getAllCustomerDetails();
	
	public CustomerDto saveCustomerDetails(CustomerDto customerDto);

}
