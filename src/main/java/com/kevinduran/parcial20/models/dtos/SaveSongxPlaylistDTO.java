package com.kevinduran.parcial20.models.dtos;

import java.sql.Date;

import com.kevinduran.parcial20.models.entities.Playlist;
import com.kevinduran.parcial20.models.entities.Song;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveSongxPlaylistDTO {

	@NotEmpty
	private Song song;
	
	@NotEmpty
	private Playlist playlist;
	
	private Date date_added;
	
}
