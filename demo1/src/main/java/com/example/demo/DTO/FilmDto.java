package com.example.demo.DTO;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;


@Setter
@Getter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmDto {

    Long kinopoiskId;
    String nameRu;
    Double ratingKinopoisk;
    Integer year;
    String genre;
    String description;
    List<GenreDto> genres;

}
