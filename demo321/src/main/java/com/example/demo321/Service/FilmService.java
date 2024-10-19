package com.example.demo321.Service;


import com.example.demo321.config.Config;
import com.example.demo321.entity.FilmResponseEntityFilter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import com.example.demo321.DTO.PostFilmDTO;
import com.example.demo321.entity.Film;
import com.example.demo321.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class FilmService {

    @Autowired
    private RestClient restClient;

    @Autowired
    private Config config;

//    @Autowired
//    private Converter converter;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmResponseEntityFilter filmResponseEntity;

    public Page<Film> getALlfilmsWithParam(PostFilmDTO postFilmDTO, int page, int size) {
        return filmRepository.findAll(postFilmDTO, page, size);
    }

    public PostFilmDTO findFilmByKinopoiskId(int kinopoiskId) {
        return filmRepository.findFilmByKinopoiskId(kinopoiskId);
    }

    public PostFilmDTO findFilmByNameRu(String nameRu) {
        return filmRepository.findFilmByNameRu(nameRu);
    }

    public PostFilmDTO findFilmByRatingKinopoisk(double ratingKinopoisk) {
        return filmRepository.findFilmByRatingKinopoisk(ratingKinopoisk);
    }

    public PostFilmDTO findFilmByGenre(String genre){
        return filmRepository.findFilmByGenre(genre);
    }

    public List<HttpStatus> saveFilmsWithoutDublicate(@RequestBody @Valid PostFilmDTO filmDTO) throws IllegalAccessException {
        List<HttpStatus> status = new ArrayList<>();
        String src = "https://kinopoiskapiunofficial.tech/api/v2/films";
        FilmResponseEntityFilter saved = restTemplate.exchange(
                src,
                HttpMethod.POST,
                createHttpEntityWithValidation(filmDTO),
                FilmResponseEntityFilter.class).getBody();
        if (saved != null) {
            status.add(HttpStatus.CREATED);
        } else {
            status.add(HttpStatus.BAD_REQUEST);
        }
        return status;
    }

    public HttpEntity<?> createHttpEntityWithValidation(PostFilmDTO dto) throws IllegalAccessException {
        boolean isDuplicate = false;
        for (Film f : filmRepository.findAll()) {
            if (dto.getKinopoiskId() == f.getKinopoiskId()) {
                isDuplicate = true;
                break;
            }
        }
        if (!isDuplicate) {

            Film film = new Film();
            film.setKinopoiskId(dto.getKinopoiskId());
            film.setNameRu(dto.getNameRu());
            film.setRatingKinopoisk(dto.getRatingKinopoisk());
            film.setGenre(dto.getGenre());

            // Преобразование Entity в DTO
            PostFilmDTO postDto = new PostFilmDTO();
            postDto.setKinopoiskId(film.getKinopoiskId());
            postDto.setNameRu(film.getNameRu());
            postDto.setRatingKinopoisk(film.getRatingKinopoisk());
            postDto.setGenre(film.getGenre());

            return config.getHttpEntity();
        } else {
            throw new IllegalAccessException("Duplicate entry found");
        }
    }

    public ResponseEntity<String> enviroment(@PathVariable("id") int id){
        ResponseEntity<String> s = restClient.get().uri("https://kinopoiskapiunofficial.tech/api/v2.2/films/{id}", id)
                .header("X-API-KEY", "91ecfa0f-93c6-4204-b2b1-6cecf1d6e7a6")
                .header("Content-Type", "application/json").retrieve().toEntity(String.class);
        return s;
    }
}
