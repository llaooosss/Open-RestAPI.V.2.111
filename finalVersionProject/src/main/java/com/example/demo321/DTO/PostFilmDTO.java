package com.example.demo321.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Getter
@Setter
public class PostFilmDTO {


    Integer kinopoiskId;
    String nameRu;
    BigDecimal ratingKinopoiskTo;
    BigDecimal ratingKinopoiskFrom;
    Integer yearFrom;
    Integer yearTo;

}