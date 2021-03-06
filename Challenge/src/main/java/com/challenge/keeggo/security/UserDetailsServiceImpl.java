package com.challenge.keeggo.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.challenge.keeggo.entity.Customer;
import com.challenge.keeggo.repository.CustomerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private CustomerRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Customer> user = repository.findByEmailContainingIgnoreCase(username);	
		user.orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
		
		return user.map(UserDetailsImpl::new).get();
	}

	
}

