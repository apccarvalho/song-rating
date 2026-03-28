package com.iftm.song_rating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	
}
