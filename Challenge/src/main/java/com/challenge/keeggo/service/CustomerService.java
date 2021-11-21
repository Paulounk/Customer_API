package com.challenge.keeggo.service;

import java.util.List;
import java.util.Optional;

import com.challenge.keeggo.dto.CustomerLoginDTO;
import com.challenge.keeggo.dto.CustomerDTO;

public interface CustomerService {

	CustomerDTO customerSignup (CustomerDTO customerDTO);
	CustomerLoginDTO customerLogin (CustomerLoginDTO customerLoginDTO);
	
	Optional<CustomerDTO> customerAlteration(CustomerDTO customerDTO);
	Optional<CustomerDTO> getCustomerById(Long id);
	
	List<CustomerDTO> listAllCustomers();

	void customerDelete(Long id);
	void validateCPF(String cpf);
	void validatesCustomerDuplication(CustomerDTO customerDTO);
	void encryptPassword (CustomerDTO customerDTO);
	
}
