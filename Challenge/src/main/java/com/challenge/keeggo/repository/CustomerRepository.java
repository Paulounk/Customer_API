package com.challenge.keeggo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.keeggo.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	public Optional<Customer> findByEmailContainingIgnoreCase (String email);
	public Optional<Customer> findByCpf (String cpf);
	
	public boolean existsByEmail(String email);
	public boolean existsByCpf(String cpf);
	
}
