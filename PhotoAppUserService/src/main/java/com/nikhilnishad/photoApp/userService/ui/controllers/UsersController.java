package com.nikhilnishad.photoApp.userService.ui.controllers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikhilnishad.photoApp.userService.model.CreateUserRequestModel;
import com.nikhilnishad.photoApp.userService.service.UsersService;
import com.nikhilnishad.photoApp.userService.shared.UserDto;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	UsersService userService;
	
	@GetMapping("/status")
	public String status() {
		return "running on port "+env.getProperty("local.server.port");
	}
	
	
	@PostMapping
	public String createUser(@RequestBody CreateUserRequestModel userDetails) {
		
		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto=modelMapper.map(userDetails, UserDto.class);
		userService.createUser(userDto);
		return "Create user method called";
	}

}
