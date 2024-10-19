package com.example.demo321.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
public class PostFilmDTO {

    int kinopoiskId;
    String nameRu;
    double ratingKinopoisk;
    String genre;

}