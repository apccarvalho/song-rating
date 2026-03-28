package com.iftm.song_rating.service;

import java.util.List;

import com.iftm.song_rating.model.Song;

public interface SongService {

	List <Song> getAllSongs();
	void saveSong(Song song);
	Song getSongById(long id);
	void deleteSongById(long id);
	
}
