package com.example.demo321.Service;


import com.example.demo321.config.Config;
import com.example.demo321.entity.FilmResponseEntityFilter;
import com.example.demo321.entity.FilmSpecification;
import com.example.demo321.mapstruct.Converter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import com.example.demo321.DTO.PostFilmDTO;
import com.example.demo321.entity.Film;
import com.example.demo321.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FilmService {


    @Autowired
    private Config config;


    private final FilmSpecification filmSpecification;


    private final Converter converter;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FilmRepository filmRepository;


    public PostFilmDTO findFilmByKinopoiskId(Integer kinopoiskId) {
        return filmRepository.findFilmByKinopoiskId(kinopoiskId);
    }

    public PostFilmDTO findFilmByNameRu(String nameRu) {
        return filmRepository.findFilmByNameRu(nameRu);
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

            // DTO в энтити
            converter.toEntity(new PostFilmDTO(dto.getKinopoiskId(),
                    dto.getNameRu(),
                    dto.getRatingKinopoiskTo(),
                    dto.getRatingKinopoiskFrom(),
                    dto.getYearTo(),
                    dto.getYearFrom()));


            // Преобразование Entity в DTO
            Film film = new Film();
            converter.toDTO(film);

            return config.getHttpEntity();
        } else {
            throw new IllegalAccessException("Duplicate entry found");
        }
    }

    public ResponseEntity<String> getAllInfo(@PathVariable("id") int id) {
        return config.enviroment(id);
    }


    public Page<Film> filterFilms(String nameRu,
                                  BigDecimal ratingKinopoiskTo,
                                  BigDecimal ratingKinopoiskFrom,
                                  Integer yearFrom,
                                  Integer yearTo,
                                  int page,
                                  int size) {
        Specification<Film> spec = filmSpecification.filterFilm(nameRu, ratingKinopoiskTo, ratingKinopoiskFrom, yearFrom, yearTo);
        Pageable pageable = PageRequest.of(page, size);
        return filmRepository.findAll(spec, pageable);
    }

}
