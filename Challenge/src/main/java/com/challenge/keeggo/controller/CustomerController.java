package com.challenge.keeggo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.challenge.keeggo.exception.BusinessRuleException;
import com.challenge.keeggo.model.Address;
import com.challenge.keeggo.model.Customer;
import com.challenge.keeggo.model.UserCostumer;
import com.challenge.keeggo.repository.AddressRepository;
import com.challenge.keeggo.repository.CustomerRepository;
import com.challenge.keeggo.service.CustomerService;

@RestController
@RequestMapping("/customers")
@CrossOrigin("*")
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private CustomerService service;

	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return ResponseEntity.ok(customerRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {

		return customerRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@PostMapping("/sign-up")
	public ResponseEntity<?> customerSave(@RequestBody @Valid Customer customer) {
		
		try {
			Customer customerSave = service.customerSignup(customer);
			Optional<Address> address = addressRepository.findById(customer.getId());
			address.get().setCustomer(customer);
			addressRepository.save(address.get());
			
			return ResponseEntity.status(HttpStatus.CREATED).body(customerSave);
		} catch (BusinessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> customerLogin(@RequestBody UserCostumer customerLogin) {
		
		try {
			UserCostumer customer = service.customerLogin(customerLogin);
			return ResponseEntity.ok(customer);
		} catch (BusinessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> customerUpdate(@RequestBody @Valid Customer customer) {
		
		try {
			service.customerUpdate(customer);
			return ResponseEntity.ok(customer);
		} catch (ResponseStatusException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
		try {
			service.customerDelete(id);
			return ResponseEntity.ok("User deleted successfully!");
		}catch(ResponseStatusException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
