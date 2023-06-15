package com.kevinduran.parcial20.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kevinduran.parcial20.models.dtos.SaveUserDTO;
import com.kevinduran.parcial20.models.entities.Token;
import com.kevinduran.parcial20.models.entities.User;
import com.kevinduran.parcial20.repositories.TokenRepository;
import com.kevinduran.parcial20.repositories.UserRepository;
import com.kevinduran.parcial20.services.UserService;
import com.kevinduran.parcial20.utils.JWTTools;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTTools jwtTools;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(SaveUserDTO info) throws Exception {
		
		User newUser = new User();
		
		newUser.setUsername(info.getUsername());
		newUser.setEmail(info.getEmail());
		newUser.setPassword(
					passwordEncoder.encode(info.getPassword())
				); //No se si es mala practica (creo que si) crear el usuario y codificar su contraseña y luego verificar si existe
		
		userRepository.save(newUser);
		
	}
	
	@Override
	public User findOneByIdentifier(String identifier) {
		return userRepository.findOneByUsernameOrEmail(identifier, identifier);
	}


	@Override
	public User findOneByUsername(String username) {
		return userRepository.findOneByUsername(username);
	}


	@Override
	public User findOneByCode(UUID uuid) throws Exception {
		return userRepository.findByCode(uuid);
	}


	@Override
	public Boolean comparePassword(String toCompare, String current) {
		return passwordEncoder.matches(toCompare, current);
	}
	
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public Token registerToken(User user) throws Exception {
		cleanTokens(user);
		
		String tokenString = jwtTools.generateToken(user);
		Token token = new Token(tokenString, user);
		
		tokenRepository.save(token);
		
		return token;
	}

	@Override
	public Boolean isTokenValid(User user, String token) {
		try {
			cleanTokens(user);
			List<Token> tokens = tokenRepository.findByUserAndActive(user, true);
			
			tokens.stream()
				.filter(tk -> tk.getContent().equals(token))
				.findAny()
				.orElseThrow(() -> new Exception());
			
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void cleanTokens(User user) throws Exception {
		List<Token> tokens = tokenRepository.findByUserAndActive(user, true);
		
		tokens.forEach(token -> {
			if(!jwtTools.verifyToken(token.getContent())) {
				token.setActive(false);
				tokenRepository.save(token);
			}
		});
		
	}
	
	@Override
	public User findUserAuthenticated() {
		String username = SecurityContextHolder
			.getContext()
			.getAuthentication()
			.getName();
		
		return userRepository.findOneByUsernameOrEmail(username, username);
	}

}
