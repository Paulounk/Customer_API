package com.challenge.keeggo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@Entity
@Table(name = "adresses")
@Data
public class Address {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@NotBlank 
	private String street;
	
	@Size(max=15)
	@NotBlank
	private String number;
	
	private String complement;
	
	@NotBlank
	private String neighborhood;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String state;
	
	@OneToOne
	@JsonIgnoreProperties("address")
	private Customer customer;
	
}
