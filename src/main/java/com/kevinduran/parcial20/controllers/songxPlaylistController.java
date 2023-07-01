package com.kevinduran.parcial20.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kevinduran.parcial20.models.dtos.MessageDTO;
import com.kevinduran.parcial20.models.dtos.SaveSongDTO;
import com.kevinduran.parcial20.models.dtos.ShowsongbyPlaylistDTO;
import com.kevinduran.parcial20.models.entities.Playlist;
import com.kevinduran.parcial20.models.entities.Song;
import com.kevinduran.parcial20.models.entities.SongxPlaylist;
import com.kevinduran.parcial20.services.PlaylistService;
import com.kevinduran.parcial20.services.SongService;
import com.kevinduran.parcial20.services.SongxPlaylistService;

@RestController
@RequestMapping("/playlist")
public class songxPlaylistController {

	@Autowired
	private SongxPlaylistService songxPlaylistService;
	
	@Autowired
	private SongService songService;
	
	@Autowired
	private PlaylistService playlistService;
	
	
	//@Autowired
	//private PlaylistService playlistService;
	
	@PostMapping("/add/{playlist_code}")
	public ResponseEntity<?> addCancionToPlaylist(@PathVariable String playlist_code, 
											@RequestBody SaveSongDTO saveSong){
		
		UUID playlist_codeUuid = UUID.fromString(playlist_code);
		
		UUID song_codeUuid = UUID.fromString(saveSong.getCode_song());
		
		Playlist playlist = playlistService.findPlaylistByCode(playlist_codeUuid);
		Song song = songService.findOneByCode(song_codeUuid);
		
		if(song!=null) {
			
			if(playlist!=null) {
				
				songxPlaylistService.save(playlist_codeUuid, song_codeUuid);
				return new ResponseEntity<>(new MessageDTO("Cancion Agregada a la Playlist"), HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>(new MessageDTO("Playlist no existe"), HttpStatus.NOT_FOUND);
			}
			
		}else {
			return new ResponseEntity<>(new MessageDTO("Cancion no existe"), HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	
	@GetMapping("/all/{playlist_code}")
	public ResponseEntity<?> findallsongs(@PathVariable("playlist_code") String playlist_code,
											@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
		
		UUID playlistUuid = UUID.fromString(playlist_code);
		
		Playlist playlist = playlistService.findPlaylistByCode(playlistUuid);
		
		if(playlist==null) {
			return new ResponseEntity<>(new MessageDTO("Playlist No encontrada"), HttpStatus.NOT_FOUND);
		} else {
			Pageable pageable =  PageRequest.of(page, size);
			Page<SongxPlaylist> songxPlaylist = songxPlaylistService.findAllSongsByPlaylist(pageable, playlist);
			List<Song> songsCodes = new ArrayList<>();
			
			songxPlaylist.forEach(s->{
				
				songsCodes.add(s.getSong());
				
			});
			
			ShowsongbyPlaylistDTO songsDto = new ShowsongbyPlaylistDTO(playlist,songsCodes);
			
			return new ResponseEntity<>(songsDto, HttpStatus.OK);
			
		}
	}

}
