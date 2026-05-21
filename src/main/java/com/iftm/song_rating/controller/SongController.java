package com.iftm.song_rating.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.iftm.song_rating.model.Song;
import com.iftm.song_rating.service.SongService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/songs") // Rota padrão REST no plural para recursos
public class SongController {

    @Autowired
    private SongService songService;


    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = songService.getAllSongs();
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        try {
            Song song = songService.getSongById(id);
            return ResponseEntity.ok(song);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<?> createSong(@Valid @RequestBody Song song, BindingResult result) {
        if (result.hasErrors()) {

            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        
        songService.saveSong(song);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(song.getId())
                .toUri();


        return ResponseEntity.created(location).body(song);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateSong(@PathVariable Long id, @Valid @RequestBody Song songDetails, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        try {

            songDetails.setId(id);
            songService.saveSong(songDetails);
            
            return ResponseEntity.ok(songDetails);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        try {           
            songService.deleteSongById(id);
            

            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}