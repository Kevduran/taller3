package com.kevinduran.parcial20.services.implementations;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kevinduran.parcial20.models.entities.Song;
import com.kevinduran.parcial20.repositories.SongRepository;
import com.kevinduran.parcial20.services.SongService;

import jakarta.transaction.Transactional;

@Service
public class SongServiceImpl implements SongService {

	@Autowired
	private SongRepository songRepository;
	
	/*@Override
	public List<Song> findAll() {
		return songRepository.findAll();
	}*/

	@Override
	public Page<Song> findByTitle(Pageable pageable, String fragment) {
		
		return songRepository.findByTitleContaining(pageable, fragment);
		
	}

	@Override
	public Song findOneByCode(UUID playlist_codeUuid) {
		return songRepository.findByCode(playlist_codeUuid);
	}

	@Override
	@Transactional
	public Page<Song> findAll(Pageable pageable) {
		return songRepository.findAll(pageable);
	}
	
}
