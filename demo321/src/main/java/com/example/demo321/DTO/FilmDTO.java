package com.example.demo321.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FilmDTO {

    Long id;
    int kinopoiskId;
    String nameRu;
    int year;
    double ratingKinopoisk;
    String description;
    String genre;

}
