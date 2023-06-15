package com.kevinduran.parcial20.services;

import java.util.List;
import java.util.UUID;

import com.kevinduran.parcial20.models.entities.Song;

public interface SongService {

	public List<Song> findAll();
	List<Song> findByTitle(String fragment);
	Song findOneByCode(UUID playlist_codeUuid);
}
