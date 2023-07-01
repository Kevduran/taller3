package com.kevinduran.parcial20.models.dtos;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.kevinduran.parcial20.models.entities.Playlist;
import com.kevinduran.parcial20.models.entities.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserAndListOfPlaylistDTO {

	@NotEmpty
	private Optional<User> user;
	
	
	private Page<Playlist> playlists;

	public UserAndListOfPlaylistDTO(Optional<User> user, Page<Playlist> playlists) {
		this.user = user;
		this.playlists = playlists;
	}
	
	
}
