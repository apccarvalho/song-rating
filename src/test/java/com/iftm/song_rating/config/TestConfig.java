package com.iftm.song_rating.config;

import org.springframework.context.annotation.Bean;
import org.mockito.Mockito;
import com.iftm.song_rating.service.SongService;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class TestConfig {
    
	@Bean
    public SongService songService() {
        return Mockito.mock(SongService.class);
    }
}