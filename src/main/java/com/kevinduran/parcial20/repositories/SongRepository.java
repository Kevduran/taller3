package com.kevinduran.parcial20.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kevinduran.parcial20.models.entities.Song;

public interface SongRepository 
			extends JpaRepository<Song, UUID> {

	Page<Song> findByTitleContaining(Pageable pageable, String title);
	Song findByCode(UUID code);
}
