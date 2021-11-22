package com.challenge.keeggo.dto;
import lombok.Data;

@Data
public class CustomerLoginDTO {
	
	private String email;
	private String password;
	private String token;
	
}
