package com.iftm.song_rating.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iftm.song_rating.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findUserByEmail(String email);
	
}
