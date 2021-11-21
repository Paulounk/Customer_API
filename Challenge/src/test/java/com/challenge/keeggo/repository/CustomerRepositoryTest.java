package com.challenge.keeggo.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.challenge.keeggo.entity.Customer;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerRepositoryTest {

	@Autowired
	CustomerRepository repository;
	
	@Test
	public void mustCheckForAnEmail() {
		
		//Scenery
		Customer customer = Customer.builder()
				.email("paulo@hotmail.com")
				.password("123456")
				.name("Paulo")
				.cpf("848484856").build();
		
		repository.save(customer);
		
		//Action 
		boolean result = repository.existsByEmail(customer.getEmail());
		
		//Verification
		Assertions.assertThat(result).isTrue();
	}
	
}
