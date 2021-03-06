package com.challenge.keeggo.resource;

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

import com.challenge.keeggo.dto.CustomerDTO;
import com.challenge.keeggo.dto.CustomerLoginDTO;
import com.challenge.keeggo.exception.AuthenticationException;
import com.challenge.keeggo.exception.InvalidInformationException;
import com.challenge.keeggo.service.CustomerService;

@RestController
@RequestMapping("/customers")
@CrossOrigin("*")
public class CustomerResource {

	@Autowired
	private CustomerService service;

	@GetMapping
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
		return ResponseEntity.ok(service.listAllCustomers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
		Optional<CustomerDTO> customer = service.getCustomerById(id);
		return ResponseEntity.ok(customer.get());
	}

	@PostMapping("/sign-up")
	public ResponseEntity<?> customerSignup(@RequestBody @Valid CustomerDTO customerDTO) {
		try {
			CustomerDTO customerSign = service.customerSignup(customerDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(customerSign);
		} catch (InvalidInformationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> customerLogin(@RequestBody CustomerLoginDTO customerLoginDTO) {
		try {
			CustomerLoginDTO customer = service.customerLogin(customerLoginDTO);
			return ResponseEntity.ok(customer);
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/alteration")
	public ResponseEntity<?> customerUpdate(@RequestBody @Valid CustomerDTO customerDTO) {
		try {
			service.customerAlteration(customerDTO);
			return ResponseEntity.ok(customerDTO);
		} catch (ResponseStatusException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
		try {
			service.customerDelete(id);
			return ResponseEntity.ok("User deleted successfully!");
		}catch(ResponseStatusException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
