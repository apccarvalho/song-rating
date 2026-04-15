package com.iftm.song_rating.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(min = 3, max = 50, message= "Nome deve conter pelo menos 3 caracteres")
    @NotBlank(message= "Nome é um campo obrigatório")
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotBlank(message= "Artista é um campo obrigatório")
    @Column(name = "artist", nullable = false)
    private String artist;
    
    @NotBlank(message= "Gênero é um campo obrigatório")
    @Column(name = "genre", nullable = false)
    private String genre;
    
    @Min(value = 0, message = "O tempo não pode ser negativo")
    @NotNull(message= "Informe um tempo válido")
    @Column(name = "duration", nullable = false)
    private Float duration;
    
    @Min(value = 0, message = "A nota não pode ser negativa")
    @NotNull(message= "Informe uma nota válida")
    @Column(name = "rate", nullable = false)
    private Integer rate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Float getDuration() {
		return duration;
	}

	public void setDuration(Float duration) {
		this.duration = duration;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}
    
    
	
}
