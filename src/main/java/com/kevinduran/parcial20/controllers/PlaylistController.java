package com.kevinduran.parcial20.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kevinduran.parcial20.models.dtos.MessageDTO;
import com.kevinduran.parcial20.models.dtos.SavePlaylistDTO;
import com.kevinduran.parcial20.models.dtos.UserAndListOfPlaylistDTO;
import com.kevinduran.parcial20.models.entities.User;
import com.kevinduran.parcial20.services.PlaylistService;
import com.kevinduran.parcial20.services.UserService;
import com.kevinduran.parcial20.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

	@Autowired
	private PlaylistService playlistService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	@PostMapping("/")
	public ResponseEntity<?> savePlaylist(@RequestBody @Valid SavePlaylistDTO info, BindingResult validations){
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()),
					HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			User user = userService.findUserAuthenticated();
			
			boolean bool = playlistService.existPlaylistRepeted(info.getTitle(), user.getUsername());
			
			if (bool) {
				return new ResponseEntity<>( new MessageDTO("Playlist ya existe para este usuario"), HttpStatus.BAD_REQUEST);
			}else {
				playlistService.save(info);
				return new ResponseEntity<>( new MessageDTO("Playlist creada con exito"), HttpStatus.CREATED);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Ha sucedido un Error"), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<?> showAllPlaylist(@RequestParam(value = "fragment", required = false) String fragment){
		if (fragment!=null) {
			
			try { 
				User user = userService.findUserAuthenticated();
				
				if (user!=null) {
					UUID uuid = user.getCode();
					 
					UserAndListOfPlaylistDTO userAndList = playlistService.getUserAndPlaylistsWithFragment(uuid, fragment);
					return new ResponseEntity<>(userAndList, HttpStatus.OK);
				}else {
					return new ResponseEntity<>(new MessageDTO("Usuario no encontrado"), HttpStatus.NOT_FOUND);
				}
			} catch (Exception e) {
				return new ResponseEntity<>(new MessageDTO("Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
		}else {
			
			try {
				User user = userService.findUserAuthenticated();
				
				if (user!=null) {
					
					UUID uuid = user.getCode();
					
					UserAndListOfPlaylistDTO userAndList = playlistService.getUserAndPlaylists(uuid);
					return new ResponseEntity<>(userAndList, HttpStatus.OK);
				}else {
					return new ResponseEntity<>(new MessageDTO("Usuario no encontrado"), HttpStatus.NOT_FOUND);
				}
			} catch (Exception e) {
				return new ResponseEntity<>(new MessageDTO("Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		
	}
	
}
