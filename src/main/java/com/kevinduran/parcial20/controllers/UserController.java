package com.kevinduran.parcial20.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kevinduran.parcial20.models.dtos.MessageDTO;
import com.kevinduran.parcial20.models.dtos.SaveUserDTO;
import com.kevinduran.parcial20.models.dtos.TokenDTO;
import com.kevinduran.parcial20.models.dtos.UserLoginDTO;
import com.kevinduran.parcial20.models.entities.Token;
import com.kevinduran.parcial20.models.entities.User;
import com.kevinduran.parcial20.services.UserService;
import com.kevinduran.parcial20.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid UserLoginDTO info, BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			User user = userService.findOneByUsername(info.getIdentifier());
			
			if(user == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			if(!userService.comparePassword(info.getPassword(), user.getPassword())) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
			
			Token token = userService.registerToken(user);
			return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);
			
			//TODO: Crear token
			//TODO: Registrar Token
			//TODO: Devolver token
		} catch (Exception e) {
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid SaveUserDTO info, BindingResult validations){
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()),
					HttpStatus.BAD_REQUEST);
		}
		
		try {
			User user = userService.findOneByUsername(info.getUsername());
			
			if (user!=null) {
				return new ResponseEntity<>( new MessageDTO("Usuario ya existente"), HttpStatus.CONFLICT);
			}
			
			userService.save(info);
			return new ResponseEntity<>( new MessageDTO("Usuario creado con exito"), HttpStatus.CREATED);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Ha sucedido un Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}
