package com.challenge.keeggo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "customers")
@Data
public class Customer {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@Size(max=100)
	@NotBlank(message = "The fild name is required.")
	private String name;
	
	@Size(max=11)
	@NotBlank(message = "The fild CPF is required.")
	private String cpf;
	
	@OneToOne
	@JsonIgnoreProperties("customer")
	private Address address;
	
	@Email
	private String email;
	
	@Size(min=6, message = "Password must contain at least 6 characters.")
	private String password;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateRegister = new java.sql.Date(System.currentTimeMillis());

}
