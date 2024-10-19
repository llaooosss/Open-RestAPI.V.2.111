package com.example.demo321.mapstruct;

import com.example.demo321.DTO.FilmDTO;
import com.example.demo321.DTO.PostFilmDTO;
import com.example.demo321.entity.Film;
import com.example.demo321.repository.FilmRepository;
import com.example.demo321.util.ResourNoFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
public class Converter {

    private final FilmRepository filmRepository;
    private final ModelMapper mapper;

    public FilmDTO searchId(@PathVariable Long id) {
        var post = filmRepository.findById(id)
                .orElseThrow(() -> new ResourNoFoundException("Not found:" + id));
        var postFilm = toDTO(post);
        return postFilm;
    }

    public FilmDTO create(@RequestBody PostFilmDTO postData) {
        var post = toEntity(postData); // Сначала в Entity
        filmRepository.save(post);
        var postDTO = toDTO(post); // Потом в DTO
        return postDTO;
    }

    public FilmDTO toDTO(Film film) {
        var dto = new FilmDTO();
        dto.setId(film.getId());
        dto.setKinopoiskId(film.getKinopoiskId());
        dto.setNameRu(film.getNameRu());
        dto.setYear(film.getYear());
        dto.setRatingKinopoisk(film.getRatingKinopoisk());
        dto.setDescription(film.getDescription());
        dto.setGenre(film.getGenre());
        return dto;
    }

    public Film toEntity(PostFilmDTO postFilmDTO) {
        var film = new Film();
        film.setKinopoiskId(postFilmDTO.getKinopoiskId());
        film.setNameRu(postFilmDTO.getNameRu());
        film.setRatingKinopoisk(postFilmDTO.getRatingKinopoisk());
        film.setGenre(postFilmDTO.getGenre());
        return film;
    }

}
