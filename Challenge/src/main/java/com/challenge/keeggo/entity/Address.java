package com.challenge.keeggo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@Size(max=50)
	@NotBlank 
	private String street;
	
	@Size(max=10)
	@NotBlank
	private String number;
	
	@Size(max=10)
	private String complement;
	
	@Size(max=50)
	@NotBlank
	private String neighborhood;
	
	@Size(max=30)
	@NotBlank
	private String city;
	
	@Size(max=30)
	@NotBlank
	private String state;

}
