package com.kevinduran.parcial20.models.dtos;

import java.util.List;

import com.kevinduran.parcial20.models.entities.Playlist;
import com.kevinduran.parcial20.models.entities.Song;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowsongbyPlaylistDTO {

	private Playlist playlist;
	
	private List<Song> song;
	
}
