package com.kevinduran.parcial20.models.dtos;

import java.sql.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindSongByPlaylistDTO {

	private UUID code;
	
	private String title;
	
	private Integer duration;
	
	private Date dateAdded;
	
}
