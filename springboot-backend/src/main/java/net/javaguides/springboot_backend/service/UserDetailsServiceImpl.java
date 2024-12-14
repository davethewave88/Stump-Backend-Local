package net.javaguides.springboot_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.javaguides.springboot_backend.domain.User;
import net.javaguides.springboot_backend.domain.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User currentUser = repository.findByEmail(username);

		UserBuilder builder = null;
		if (currentUser!=null) {
			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			builder.password(currentUser.getPassword());
			// setting role to XXXX is equivalent to the authority ROLE_XXXX
			builder.roles(String.valueOf(currentUser.getType()));
		} else {
			System.out.println("User not found.");
			throw new UsernameNotFoundException("User not found.");
		}

		return builder.build();	    
	}
}
