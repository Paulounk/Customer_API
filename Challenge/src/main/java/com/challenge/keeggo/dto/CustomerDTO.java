package com.challenge.keeggo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.challenge.keeggo.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

	private Long id;
	
	@Email
	@NotBlank(message = "The fild email is required.")
	private String email;
	
	@NotBlank(message = "The fild password is required.")
	private String password;
	
	@NotBlank(message = "The fild name is required.")
	private String name;
	
	@NotBlank(message = "The fild CPF is required.")
	private String cpf;
	
	@NotNull(message = "The fild CPF is required.")
	private Address address;
	
}
