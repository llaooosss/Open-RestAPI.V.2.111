package com.example.demo321.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class FilmDTO {

    Long id;
    Integer kinopoiskId;
    String nameRu;
    Integer yearFrom;
    Integer yearTo;
    BigDecimal ratingKinopoiskTo;
    BigDecimal ratingKinopoiskFrom;
    String description;
    String genre;

}
