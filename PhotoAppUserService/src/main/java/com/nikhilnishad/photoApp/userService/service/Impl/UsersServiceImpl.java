package com.nikhilnishad.photoApp.userService.service.Impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikhilnishad.photoApp.userService.data.UserEntity;
import com.nikhilnishad.photoApp.userService.data.UsersRepository;
import com.nikhilnishad.photoApp.userService.service.UsersService;
import com.nikhilnishad.photoApp.userService.shared.UserDto;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	UsersRepository userRepository;
	
	@Override
	public UserDto createUser(UserDto userDetails) {
		// TODO Auto-generated method stub
		userDetails.setUserId(UUID.randomUUID().toString());
		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity=modelMapper.map(userDetails, UserEntity.class);
		userEntity.setEncryptedPassword("test");
		UserDto createdUser=modelMapper.map(userRepository.save(userEntity),UserDto.class);
		return createdUser;
	}

}
