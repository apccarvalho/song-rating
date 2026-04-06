package com.iftm.song_rating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.iftm.song_rating.model.Song;
import com.iftm.song_rating.service.SongService;

@Controller
public class SongController {
	
	@Autowired
	private SongService songService;
	
	@GetMapping("/song")
	public String index (Model model) {
		model.addAttribute("songsList", songService.getAllSongs());
		return "song/index";
	}
	
	@GetMapping("/song/create")
	public String create(Model model) {
		model.addAttribute("song", new Song());
		return "song/create";
	}
	
	@PostMapping("/song/save")
	public String postMethodName(@ModelAttribute("song") Song song){
		songService.saveSong(song);
		return "redirect:/song";
	}
	
	@GetMapping("/song/delete/{id}")
	public String delete(@PathVariable Long id) {
		this.songService.deleteSongById(id);
		return "redirect:/song";
	}
	
	@GetMapping("/song/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		Song song = songService.getSongById(id);
		model.addAttribute("song", song);
		return "song/edit";
	}
	
	
	
}
