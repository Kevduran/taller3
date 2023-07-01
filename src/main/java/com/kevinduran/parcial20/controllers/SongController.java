package com.kevinduran.parcial20.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kevinduran.parcial20.models.entities.Song;
import com.kevinduran.parcial20.services.SongService;

@RestController
@RequestMapping("/song")
public class SongController {

	@Autowired
	private SongService songService;
	
	@GetMapping("/")
	public ResponseEntity<?> findAllSong(@RequestParam(value = "fragment", required = false) String fragment, 
									@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
		
		Pageable pageable = PageRequest.of(page, size);
		
		if (fragment != null) {
			Page<Song> songs = songService.findByTitle(pageable, fragment);
			return new ResponseEntity<>(songs, HttpStatus.OK);
		}else {
			
			Page<Song> songs = songService.findAll(pageable);
			return new ResponseEntity<>(songs, HttpStatus.OK);
		}
		
	}
	
}
