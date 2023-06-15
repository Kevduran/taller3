package com.kevinduran.parcial20.models.dtos;

import java.util.List;
import java.util.UUID;

import com.kevinduran.parcial20.models.entities.Playlist;
import com.kevinduran.parcial20.models.entities.Song;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowPlaylist {

	private Playlist playlist;
	
	private List<Song> songs;
	
}
