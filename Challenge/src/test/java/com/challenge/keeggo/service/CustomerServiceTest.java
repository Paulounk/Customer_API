package com.challenge.keeggo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import com.challenge.keeggo.entity.Customer;
import com.challenge.keeggo.exception.InvalidInformationException;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerServiceTest {

	@SpyBean
	CustomerServiceImpl service;

	@Test
	public void shouldMessageErrorIfCpfIsInvalid() {

		// Scenery
		Customer customer = Customer.builder()
				.email("paulo@hotmail.com")
				.password("123456")
				.name("Paulo")
				.cpf("848484856").build();

		// Action
		Throwable erro = Assertions.catchThrowable(() -> service.validateCPF(customer.getCpf()));

		// Verification
		Assertions.assertThat(erro).isInstanceOf(InvalidInformationException.class).hasMessage("Invalid CPF.");
	}
}
