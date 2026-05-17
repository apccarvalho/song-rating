package com.iftm.song_rating.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.iftm.song_rating.model.Song; // Importação do seu modelo Song
import com.iftm.song_rating.repository.SongRepository; // Importação do seu repositório Song
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa application-test.properties (garanta que o H2 está ativo nele)
@Transactional // Limpa o banco de dados (H2) após a execução do teste
public class SongIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SongRepository songRepository;

    @Test
    @WithMockUser(authorities = { "Admin" }) // Simula um usuário administrador logado
    void testSaveSongIntegration() throws Exception {

        // 1. Cenário: Criando uma música válida com os atributos corretos
        Song songA = new Song();
        songA.setName("Música A");
        songA.setArtist("Artista A");
        songA.setGenre("Rock");
        songA.setDuration(3.45f);
        songA.setRate(5);

        // 2. Ação: Dispara o POST real que passa por toda a estrutura (Controller -> Service -> Banco H2)
        mockMvc.perform(post("/song/save")
                .with(csrf())
                .flashAttr("song", songA))
                .andExpect(status().is3xxRedirection()) // Espera um redirecionamento (302 Found)
                .andExpect(redirectedUrl("/song"));     // Espera voltar para a listagem de músicas

        // 3. Verificação de Integração: Vai direto no banco de dados ver se o registro realmente foi gravado
        assertTrue(songRepository.findAll()
                .stream()
                .anyMatch(s -> "Música A".equals(s.getName())));
    }
}