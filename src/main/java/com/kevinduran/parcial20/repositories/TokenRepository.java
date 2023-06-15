package com.kevinduran.parcial20.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.kevinduran.parcial20.models.entities.Token;
import com.kevinduran.parcial20.models.entities.User;

public interface TokenRepository 
extends ListCrudRepository<Token, UUID>{ 

	List<Token> findByUserAndActive(User user, Boolean active);

}
