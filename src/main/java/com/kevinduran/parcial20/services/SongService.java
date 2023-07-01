package com.kevinduran.parcial20.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kevinduran.parcial20.models.entities.Song;

public interface SongService {

	//public List<Song> findAll();
	public Page<Song> findAll(Pageable pageable);
	
	Page<Song> findByTitle(Pageable pageable, String fragment);
	
	Song findOneByCode(UUID playlist_codeUuid);
}
