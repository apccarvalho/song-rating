package com.iftm.song_rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iftm.song_rating.model.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

	
}
