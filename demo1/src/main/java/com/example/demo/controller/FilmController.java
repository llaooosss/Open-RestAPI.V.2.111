package com.example.demo.controller;

import com.example.demo.DTO.FilmDto;
import com.example.demo.entity.Film;
import com.example.demo.service.FilmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2.2/films")
@Slf4j
public class FilmController {

    @Autowired
    private FilmService filmService;


    @GetMapping("/filter")
    public String showFilterPage() {
        return "filter"; // имя HTML-файла без расширения, например, filter.html
    }

//    @PostMapping("/filter")
//    public ResponseEntity<Page<Film>> filterFilms(
//            @RequestBody FilmDto filmDTO,
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "5") int size) {
//        Page<Film> films = filmService.searchFilm(filmDTO, page, size);
//        return ResponseEntity.ok(films);
//    }

    @PostMapping("/{id}")
    public ResponseEntity<Film> addFilm(@PathVariable Long id) {
        Film addedFilm = filmService.addFilmFromExternalApi(id);
        return ResponseEntity.ok(addedFilm);
    }

    @DeleteMapping("/delete/{filmId}")
    public ResponseEntity<Film> deleteFilm(@PathVariable Long filmid) {
        Film deleteFilm = filmService.deleteFilmById(filmid);
        return ResponseEntity.ok(deleteFilm);
    }


    @GetMapping("/searchfilmname/{filmname}")
    public Optional<Film> findName(@PathVariable String filmname) {
        return filmService.findFilmName(filmname);
    }

    @GetMapping("getrating/{rating}")
    public Optional<Film> findFilmsByRating(@PathVariable Double rating) {
        return filmService.findFilmsByRating(rating);
    }

    @GetMapping("getid/{id}")
    public Optional<Film> findById(@PathVariable Long id) {
        return filmService.findFilmsById(id);
    }

    @GetMapping("/search")
    // /search?minRating=4.5&maxRating=9&startYear=2000&endYear=2023&page=0&size=2
    public Page<Film> searchMovies(@RequestParam(required = false) Double minRating,
                                   @RequestParam(required = false) Double maxRating,
                                   @RequestParam(required = false) Integer startYear,
                                   @RequestParam(required = false) Integer endYear,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return filmService.findFilms(minRating, maxRating, startYear, endYear, PageRequest.of(page, size));
    }
}
