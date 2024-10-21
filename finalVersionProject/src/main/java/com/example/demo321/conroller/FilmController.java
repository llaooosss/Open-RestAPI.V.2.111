package com.example.demo321.conroller;

import com.example.demo321.DTO.PostFilmDTO;
import com.example.demo321.Service.FilmService;
import com.example.demo321.config.Config;
import com.example.demo321.entity.Film;
import com.example.demo321.entity.FilmResponseEntityFilter;
import com.example.demo321.entity.FilmSpecification;
import com.example.demo321.repository.FilmRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v2/films")
public class FilmController {

    private final Config config;
    private final RestTemplate restTemplate;
    private final FilmRepository filmRepository;
    private final FilmService filmService;
    private final FilmResponseEntityFilter filmResponseEntity;

    public FilmController(Config config,
                          RestTemplate restTemplate,
                          FilmRepository filmRepository,
                          FilmService filmService,
                          ModelMapper mapper,
                          FilmResponseEntityFilter filmResponseEntity) {
        this.config = config;
        this.restTemplate = restTemplate;
        this.filmRepository = filmRepository;
        this.filmService = filmService;
        this.filmResponseEntity = filmResponseEntity;
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<Film>> filterFilms(
            @RequestParam(required = false) String nameRu,
            @RequestParam(required = false) BigDecimal ratingKinopoiskTo,
            @RequestParam(required = false) BigDecimal ratingKinopoiskFrom,
            @RequestParam(required = false) Integer yearFrom,
            @RequestParam(required = false) Integer yearTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Film> films = filmService.filterFilms(nameRu, ratingKinopoiskTo, ratingKinopoiskFrom, yearFrom, yearTo, page, size);
        return ResponseEntity.ok(films);
    }

    @PostMapping("/save")
    public List<HttpStatus> saveFilms(@RequestBody @Valid Film film) throws IllegalAccessException {
        return filmService.saveFilmsWithoutDublicate(new PostFilmDTO(film.getKinopoiskId(), film.getNameRu(), film.getRatingKinopoiskFrom(), film.getRatingKinopoiskTo(), film.getYearFrom(), film.getYearTo()));
    }


    @GetMapping("/openAllInfoById/{id}")
    public ResponseEntity<String> openAllInfoById(@PathVariable("id") Integer id) {
        return filmService.getAllInfo(id);
    }

    @GetMapping("/findByKinopoiskId")
    public PostFilmDTO findKinopoiskId(Integer kinopoiskId) {
        return filmService.findFilmByKinopoiskId(kinopoiskId);
    }

    @GetMapping("/findByNaeRu")
    private PostFilmDTO findNameRu(String nameRu) {
        return filmService.findFilmByNameRu(nameRu);
    }


    @GetMapping("/genre")
    public PostFilmDTO findFilmByGenre(String genre) {
        return filmService.findFilmByGenre(genre);
    }
}
