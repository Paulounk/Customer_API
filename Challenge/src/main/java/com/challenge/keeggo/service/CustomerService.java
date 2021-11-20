package com.challenge.keeggo.service;

import java.nio.charset.Charset;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import com.challenge.keeggo.exception.BusinessRuleException;
import com.challenge.keeggo.exception.AuthenticationException;
import com.challenge.keeggo.model.Customer;
import com.challenge.keeggo.model.UserCostumer;
import com.challenge.keeggo.repository.CustomerRepository;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repository;

	public Customer customerSignup(Customer customer) {

		validatesCustomerDuplication(customer);
		validateCPF(customer.getCpf());
		encryptPassword(customer);

		return repository.save(customer);
	}
	
	public UserCostumer customerLogin(UserCostumer customerLogin) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Customer> customer = repository.findByEmailContainingIgnoreCase(customerLogin.getEmail());
		
		if(customer.isPresent()) {
			
			if (encoder.matches(customerLogin.getPassword(), customer.get().getPassword())) {
				
				String auth = customerLogin.getEmail() + ":" + customerLogin.getPassword();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);

				customerLogin.setToken(authHeader);
				customerLogin.setEmail(customer.get().getEmail());
				customerLogin.setPassword(customer.get().getPassword());
				
				return customerLogin;
			}else {
				throw new AuthenticationException("Password is invalid.");
			}
		}else {
			throw new AuthenticationException("User not found for login.");
		}
	}
	
	public Optional<Customer> customerUpdate(Customer customer) {

		boolean customerPresent = repository.findById(customer.getId()).isPresent();

		if (customerPresent){
			
			try {
				validateCPF(customer.getCpf());
				validatesCustomerDuplication(customer);
				encryptPassword(customer);
				return Optional.of(repository.save(customer));
				
			} catch (BusinessRuleException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), null);
			}
			
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for update!", null);
		}
	}


	@Transactional
	public void customerDelete(Long id){

		Optional<Customer> customerExists = repository.findById(id);
		
		if(customerExists.isPresent()) {
			 repository.deleteById(id); 
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for deletion!", null);
		}
	}

	public void validateCPF(String cpf) {

		CPFValidator cpfValidate = new CPFValidator();

		try {
			cpfValidate.assertValid(cpf);
		} catch (InvalidStateException e) {
			throw new BusinessRuleException("Invalid CPF.");
		}
	}

	public void validatesCustomerDuplication(Customer customer) {

		Optional<Customer> customerEmail = repository.findByEmailContainingIgnoreCase(customer.getEmail());
		Optional<Customer> customerCpf = repository.findByCpf(customer.getCpf());

		boolean existsEmail = repository.existsByEmail(customer.getEmail());
		boolean existsCPF = repository.existsByCpf(customer.getCpf());

		if (existsEmail && customerEmail.get().getId() != customer.getId()) {
			throw new BusinessRuleException("Already exists a user with this e-mail.");
		}
		if (existsCPF && customerCpf.get().getId() != customer.getId()) {
			throw new BusinessRuleException("Already exists a user with this CPF.");
		}
	}

	public void encryptPassword(Customer customer) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String passwordEncode = encoder.encode(customer.getPassword());
		customer.setPassword(passwordEncode);
	}


}
