package com.iftm.song_rating.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iftm.song_rating.model.Song;
import com.iftm.song_rating.repository.SongRepository;
import com.iftm.song_rating.service.SongService;

@Service
public class SongServiceImpl implements SongService{

	@Autowired
	private SongRepository songRepository;
	
	@Override
	public List<Song> getAllSongs() {
		return songRepository.findAll();
	}

	@Override
	public void saveSong(Song song) {
		this.songRepository.save(song);
		
	}

	@Override
	public Song getSongById(long id) {
		Optional < Song > optional = songRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}else {
			throw new RuntimeException("Song not found with id: " + id);
		}
	}

	@Override
	public void deleteSongById(long id) {
		this.songRepository.deleteById(id);
		
	}
	
}
