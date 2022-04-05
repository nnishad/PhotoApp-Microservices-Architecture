package com.nikhilnishad.photoApp.userService.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.nikhilnishad.photoApp.userService.shared.UserDto;

public interface UsersService extends UserDetailsService {

	public UserDto createUser(UserDto userDetails);
	
	public UserDto getUserDetailsByEmail(String email);
}
