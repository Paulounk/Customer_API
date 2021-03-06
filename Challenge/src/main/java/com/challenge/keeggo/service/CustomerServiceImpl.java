package com.challenge.keeggo.service;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.challenge.keeggo.dto.CustomerLoginDTO;
import com.challenge.keeggo.dto.CustomerDTO;
import com.challenge.keeggo.entity.Customer;
import com.challenge.keeggo.exception.AuthenticationException;
import com.challenge.keeggo.exception.InvalidInformationException;
import com.challenge.keeggo.repository.CustomerRepository;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerRepository repository;
	
	@Override
	public CustomerDTO customerSignup(CustomerDTO customerDTO) {
		
		validatesCustomerDuplication(customerDTO);
		validateCPF(customerDTO.getCpf());
		encryptPassword(customerDTO);
		
		Customer custumer = convertDTOCustomer(customerDTO);
		repository.save(custumer);
		customerDTO.setId(custumer.getId());

		return customerDTO;
	}

	@Override
	public CustomerLoginDTO customerLogin(CustomerLoginDTO customerLoginDTO) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Customer> customer = repository.findByEmailContainingIgnoreCase(customerLoginDTO.getEmail());
		
		if(customer.isPresent()) {
			if (encoder.matches(customerLoginDTO.getPassword(), customer.get().getPassword())) {
				
				String auth = customerLoginDTO.getEmail() + ":" + customerLoginDTO.getPassword();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);

				customerLoginDTO.setToken(authHeader);
				customerLoginDTO.setPassword(customer.get().getPassword());
				
				return customerLoginDTO;
			}else {
				throw new AuthenticationException("Password is invalid.");
			}
		}else {
			throw new AuthenticationException("User not found for login.");
		}
	}

	@Override
	public Optional<CustomerDTO> customerAlteration(CustomerDTO customerDTO) {
		
		Optional<Customer> customerPresent = repository.findById(customerDTO.getId());

		if (customerPresent.isPresent()){
			try {
				validateCPF(customerDTO.getCpf());
				validatesCustomerDuplication(customerDTO);
				encryptPassword(customerDTO);
				
				Customer customer = convertDTOCustomer(customerDTO);
				customer.setId(customerPresent.get().getId());
				customer.setDateRegister(customerPresent.get().getDateRegister());
				repository.save(customer);
				
				return Optional.of(customerDTO);
				
			}catch(InvalidInformationException e){
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), null);
			}
		}else{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for update!", null);
		}
		
	}
	
	@Override
	public void customerDelete(Long id) {
		
		boolean customerExists = repository.findById(id).isPresent();
		
		if(customerExists) {
			 repository.deleteById(id); 
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for deletion!", null);
		}
	}
	
	@Override
	public void validateCPF(String cpf) {
		
		CPFValidator cpfValidate = new CPFValidator();

		try {
			cpfValidate.assertValid(cpf);
		} catch (InvalidStateException e) {
			throw new InvalidInformationException("Invalid CPF.");
		}
		
	}

	@Override
	public void validatesCustomerDuplication(CustomerDTO customerDTO) {
	
		Optional<Customer> customerEmail = repository.findByEmailContainingIgnoreCase(customerDTO.getEmail());
		Optional<Customer> customerCpf = repository.findByCpf(customerDTO.getCpf());

		boolean existsEmail = repository.existsByEmail(customerDTO.getEmail());
		boolean existsCPF = repository.existsByCpf(customerDTO.getCpf());

		if (existsEmail && customerEmail.get().getId() != customerDTO.getId()) {
			throw new InvalidInformationException("Already exists a user with this e-mail.");
		}
		if (existsCPF && customerCpf.get().getId() != customerDTO.getId()) {
			throw new InvalidInformationException("Already exists a user with this CPF.");
		}
		
	}

	@Override
	public Optional<CustomerDTO> getCustomerById(Long id) {
		
		Optional<Customer> customer = repository.findById(id);
		
		if(customer.isPresent()) {
			CustomerDTO convertCustomerToDTO = convertCustomerToDTO(customer.get());
			return Optional.of(convertCustomerToDTO);
		}else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!", null);
		}
	}

	@Override
	public List<CustomerDTO> listAllCustomers() {
		
		return repository.findAll().stream()
				.map(this::convertCustomerToDTO)
				.collect(Collectors.toList());
	}
	
	@Override
	public void encryptPassword(CustomerDTO customerDTO) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String passwordEncode = encoder.encode(customerDTO.getPassword());
		customerDTO.setPassword(passwordEncode);
	}
	
	
	//CONVERSION METHODS
	public Customer convertDTOCustomer(CustomerDTO customerDTO) {
		Customer customer = Customer.builder()
							.email(customerDTO.getEmail())
							.password(customerDTO.getPassword())
							.name(customerDTO.getName())
							.cpf(customerDTO.getCpf())
							.address(customerDTO.getAddress()).build();
		return customer;
	}
	
	public CustomerDTO convertCustomerToDTO(Customer customer) {
		CustomerDTO customerDTO = CustomerDTO.builder()
							.id(customer.getId())
							.email(customer.getEmail())
							.password(customer.getPassword())
							.name(customer.getName())
							.cpf(customer.getCpf())
							.address(customer.getAddress()).build();
		return customerDTO;
	}

}
