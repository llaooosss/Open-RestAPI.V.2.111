package com.example.demo.service;


import com.example.demo.DTO.FilmDto;
import com.example.demo.entity.Film;
import com.example.demo.entity.FilmSpecification;
import com.example.demo.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmSpecification filmSpecification;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final String EXTERNAL_API_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/{id}";

        public Film addFilmFromExternalApi(Long filmId) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-API-KEY", "91ecfa0f-93c6-4204-b2b1-6cecf1d6e7a6");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<FilmDto> response = restTemplate.exchange(
                    EXTERNAL_API_URL,
                    HttpMethod.GET,
                    entity,
                    FilmDto.class,
                    filmId
            );

            FilmDto filmDTO = response.getBody();
            if (filmDTO == null) {
                throw new RuntimeException("Film not found");
            }

            Optional<Film> existingFilm = filmRepository.findByFilmId(filmDTO.getKinopoiskId());
            if (existingFilm.isPresent()) {
                throw new RuntimeException("Film already exists");
            }

            Film film = new Film();
            film.setFilmName(filmDTO.getNameRu());
            film.setFilmId(filmDTO.getKinopoiskId());
            film.setRating(filmDTO.getRatingKinopoisk());
            film.setYear(filmDTO.getYear());
            film.setDescription(filmDTO.getDescription());

            List<String> genres = filmDTO.getGenres().stream()
                    .map(genreDto -> genreDto.getGenre())
                    .collect(Collectors.toList());
            film.setGenres(genres);

            return filmRepository.save(film);
        }

    public Film deleteFilmById(Long filmid) {
        if (!filmRepository.existsById(filmid)) {
            throw new RuntimeException("Film not found with id: " + filmid);
        }
        return filmRepository.deleteFilmByFilmId(filmid);
    }

//    public Page<Film> searchFilm(FilmDto filmDTO, int page, int size) {
//        Specification<Film> spec = filmSpecification.filterFilms(filmDTO);
//        Pageable pageable = PageRequest.of(page, size);
//        return filmRepository.findAll(spec, pageable);
//    }

    public Optional<Film> findFilmName(String filmname) {
            return filmRepository.findFilmsByFilmName(filmname);
    }

    public Optional<Film> findFilmsByRating(Double rating) {return filmRepository.findFilmsByRating(rating);}

    public Optional<Film> findFilmsById(Long id) {
            return filmRepository.findFilmsById(id);
    }

    public Page<Film> findFilms(Double minRating, Double maxRating, Integer startYear, Integer endYear, Pageable pageable) {
        Specification<Film> spec = Specification.where(FilmSpecification.ratingBetween(minRating, maxRating))
                .and(FilmSpecification.yearBetween(startYear, endYear));
        return filmRepository.findAll(spec, pageable);
    }


}
