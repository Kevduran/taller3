package com.kevinduran.parcial20.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import com.kevinduran.parcial20.models.entities.Playlist;
import com.kevinduran.parcial20.models.entities.Song;
import com.kevinduran.parcial20.models.entities.SongxPlaylist;

public interface SongxPlaylistRepository 
		extends ListCrudRepository<SongxPlaylist, UUID> {

	List<Song> findSongByPlaylist(Playlist playlist);

	Page<SongxPlaylist> findAllSongsByPlaylist(Pageable pageable, Playlist playlist);
	
}
