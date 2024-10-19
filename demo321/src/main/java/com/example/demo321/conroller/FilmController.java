package com.example.demo321.conroller;

import com.example.demo321.DTO.PostFilmDTO;
import com.example.demo321.Service.FilmService;
import com.example.demo321.config.Config;
import com.example.demo321.entity.Film;
import com.example.demo321.entity.FilmResponseEntityFilter;
import com.example.demo321.repository.FilmRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v2/films")
public class FilmController {


    private final Config config;
    private final RestTemplate restTemplate;
    private final FilmRepository filmRepository;
    private final HttpEntity<String> httpEntity;
    private final FilmService filmService;
    private final FilmResponseEntityFilter filmResponseEntity;

    public FilmController(Config config, HttpEntity<String> httpEntity, RestTemplate restTemplate, FilmRepository filmRepository, FilmService filmService, ModelMapper mapper, FilmResponseEntityFilter filmResponseEntity) {
        this.config = config;
        this.httpEntity = httpEntity;
        this.restTemplate = restTemplate;
        this.filmRepository = filmRepository;
        this.filmService = filmService;
        this.filmResponseEntity = filmResponseEntity;
    }

    @PostMapping("/filter")
    ///films/filter?page=0&size=10 - пример использования
    public Page<Film> findALlfilmsWithParam(
            @RequestParam(required = false) Integer kinopoiskId,
            @RequestParam(required = false) String nameRu,
            @RequestParam(required = false) Double raitingKinopoisk,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {

        PostFilmDTO postFilmDTO = new PostFilmDTO();

        postFilmDTO.setKinopoiskId(kinopoiskId);
        postFilmDTO.setNameRu(nameRu);
        postFilmDTO.setRatingKinopoisk(raitingKinopoisk);
        postFilmDTO.setGenre(genre);

        return filmResponseEntity.filterFilms(postFilmDTO, page, size);
    }

    @PostMapping("/save")
    public List<HttpStatus> saveFilms(@RequestBody @Valid Film film) throws IllegalAccessException {
        return filmService.saveFilmsWithoutDublicate(new PostFilmDTO(film.getKinopoiskId(), film.getNameRu(), film.getYear(), film.getGenre()));
    }


    @GetMapping("/openAllInfoById/{id}")
    public ResponseEntity<String> openAllInfoById(@PathVariable("id") Integer id) {
        return filmService.enviroment(id);
    }

    @GetMapping("/findByKinopoiskId")
    public PostFilmDTO findKinopoiskId(Integer kinopoiskId) {
        return filmRepository.findFilmByKinopoiskId(kinopoiskId);
    }

    @GetMapping("/findByNaeRu")
    private PostFilmDTO findNameRu(String nameRu) {
        return filmRepository.findFilmByNameRu(nameRu);
    }

    @GetMapping("/Rating")
    public PostFilmDTO findFilmByRatingKinopoisk(Double ratingKinopoisk) {
        return filmRepository.findFilmByRatingKinopoisk(ratingKinopoisk);
    }


    @GetMapping("/genre")
    public PostFilmDTO findFilmByGenre(String genre) {
        return filmRepository.findFilmByGenre(genre);
    }
}
