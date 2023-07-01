package com.kevinduran.parcial20.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kevinduran.parcial20.models.entities.Playlist;
import com.kevinduran.parcial20.models.entities.User;

public interface PlaylistRepository 
			extends JpaRepository<Playlist, UUID> {

	Playlist findOneByTitle(String title);

	Optional<Playlist> findByTitleAndUserUsername(String nombrePlaylist, String username);
	
	List<Playlist> findByUserCodeAndTitleContaining(UUID id, String fragment);

	Page<Playlist> findAllByUser(Pageable pageable, Optional<User> user);

	Optional<Playlist> findByUser(Optional<User> user);

	Page<Playlist> findAllByUserAndTitleContaining(Pageable pageable, Optional<User> user, String fragment);

	Playlist findByCode(UUID uuid);

}
