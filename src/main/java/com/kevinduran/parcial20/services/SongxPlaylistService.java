package com.kevinduran.parcial20.services;

import java.util.List;
import java.util.UUID;

import com.kevinduran.parcial20.models.entities.Playlist;
import com.kevinduran.parcial20.models.entities.Song;
import com.kevinduran.parcial20.models.entities.SongxPlaylist;


public interface SongxPlaylistService {

	public void save(UUID playlist_code, UUID song_code);
	List<Song> findSongsByPlaylist(Playlist playlist);
	public List<SongxPlaylist> findAllSongsByPlaylist(Playlist playlist);
	
}
