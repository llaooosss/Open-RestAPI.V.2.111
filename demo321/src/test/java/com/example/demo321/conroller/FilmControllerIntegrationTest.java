package com.example.demo321.conroller;

import com.example.demo321.DTO.PostFilmDTO;
import com.example.demo321.Service.FilmService;
import com.example.demo321.entity.Film;
import com.example.demo321.repository.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmService filmService;

    @BeforeEach
    public void setUp() {
        filmRepository.deleteAll();

        PostFilmDTO film1 = new Film("OpenAI", 8.0, 2017, "Drama");
        PostFilmDTO film2 = new Film("Ambidekster", 7.0, 2018, "Horror");
        PostFilmDTO film3 = new Film("Kalambur", 6.3, 2019, "Science");

        filmRepository.saveAll(Arrays.asList(film1, film2, film3));

    }


    @Test
    public void testFilterFilms() throws Exception {
        mockMvc.perform(get("/films/filter")
                        .param("ratingKinopoiskFrom", "7.0")
                        .param("ratingKinopoiskTo", "9.0")
                        .param("YearFrom", "2015")
                        .param("YearTo", "2020")
                        .param("genre", "Drama"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Santa-Barbara"));
    }
}
