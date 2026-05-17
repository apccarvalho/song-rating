package com.iftm.song_rating.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.iftm.song_rating.config.TestConfig;
import com.iftm.song_rating.model.Song;
import com.iftm.song_rating.service.SongService;



@WebMvcTest(SongController.class)
@Import(TestConfig.class)
public class SongControllerTest {
	   @Autowired
	    private MockMvc mockMvc;

	    @Autowired
	    private SongService songService;

	    @AfterEach
	    void resetMocks() {
	        reset(songService);
	    }

	    private List<Song> testCreateSongList(){
	        Song songB = new Song();
	        songB.setId(1L);
	        songB.setName("Musica B");
	        songB.setArtist("Artista B");
	        songB.setGenre("Genero B");
	        songB.setDuration(5f);
	        songB.setRate(1);

	        return List.of(songB);
	    }

	    @Test
	    @DisplayName("GET /song - Listar produtos na tela index sem usuário autenticado")
	    void testIndexNotAuthenticatedUser() throws Exception {
	         mockMvc.perform(get("/song"))
	            .andExpect(status().isUnauthorized());
	    }

	    @Test
	    @WithMockUser
	    @DisplayName("GET /song - Listar músicas na tela index com usuário logado")
	    void testIndexAuthenticatedUser() throws Exception {
	        when(songService.getAllSongs()).thenReturn(testCreateSongList());

	        mockMvc.perform(get("/song"))
	               .andExpect(status().isOk())
	               .andExpect(view().name("song/index"))
	               .andExpect(model().attributeExists("songsList"))
	               .andExpect(content().string(containsString("Listagem de Música")))
	               .andExpect(content().string(containsString("Musica B")));
	    }

	    @Test
	    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
	    @DisplayName("GET /song/create - Exibe formulário de criação")
	    void testCreateFormAuthorizedUser() throws Exception {
	        mockMvc.perform(get("/song/create"))
	                .andExpect(status().isOk())
	                .andExpect(view().name("song/create"))
	                .andExpect(model().attributeExists("song"))
	                .andExpect(content().string(containsString("Cadastrar Música")));
	    }

	    @Test
	    @WithMockUser(username = "aluno2@iftm.edu.br", authorities = { "Manager" })
	    @DisplayName("GET /song - Verificar o link de cadastrar para um usuario não admin logado")
	    void testCreateFormNotAuthorizedUser() throws Exception {
	        when(songService.getAllSongs()).thenReturn(testCreateSongList());
	       // Obter o HTML da página renderizada pelo controlador
	       mockMvc.perform(get("/song/create"))
	            .andExpect(status().isOk())
	            .andExpect(view().name("song/create"))
	            .andExpect(model().attributeExists("song"))
	            .andExpect(content().string(not(containsString("<a class=\"dropdown-item\" href=\"/song/create\">Cadastrar</a>"))));
	    }

	    @Test
	    @WithMockUser
	    @DisplayName("POST /song/save - Falha na validação e retorna para o formulário")
	    void testSaveSongValidationError() throws Exception {
	        Song song = new Song(); // Musica sem nome, o que causará erro de validação

	        mockMvc.perform(post("/song/save")
	                        .with(csrf())
	                        .flashAttr("song", song))
	                .andExpect(status().isOk())
	                .andExpect(view().name("song/create"))
	                .andExpect(model().attributeHasErrors("song"));

	        verify(songService, never()).saveSong(any(Song.class));
	    }

	    @Test
	    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
	    @DisplayName("POST /song/save - Produto válido é salvo com sucesso")
	    void testSaveValidSong() throws Exception {
	        Song song = new Song();
	        song.setName("Nova Musica");
	        song.setArtist("Novo Artista");
	        song.setGenre("Novo Genero");
	        song.setDuration(9.1f);
	        song.setRate(5);

	        mockMvc.perform(post("/song/save")
	                        .with(csrf())
	                        .flashAttr("song", song))
	                .andExpect(status().is3xxRedirection())
	                .andExpect(view().name("redirect:/song"));

	        verify(songService).saveSong(any(Song.class));
	    }
}
