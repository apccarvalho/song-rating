package com.iftm.song_rating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.iftm.song_rating.model.User;
import com.iftm.song_rating.service.IUserService;


@Controller
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	// Go to Registration Page
	@GetMapping("register")
	public String register() {
		return "user/registerUser";
	}
	
	// Read Form data to into DB
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, Model model) {
		Integer id = userService.saveUser(user);
		String message = "User '" + id + "' saved succerfully!";
		model.addAttribute("msg", message);
		return "user/registerUser";
	}
	
	@GetMapping("/accesDenied")
	public String getAccersDeniedPage() {
		return "user/accersDeniedPage";
	}
	
	
}
