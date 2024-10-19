package com.example.demo321.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "filmes")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "filmid")
    int kinopoiskId;

    @Column(name = "filmname")
    String nameRu;

    @Column(name = "year")
    @Min(value = 0, message = "Age should be greated thah 0")
    int year;

    @Column(name = "rating")
    @Min(0)
    @Max(10)
    double ratingKinopoisk;

    @Column(name = "description")
    String description;

    @Column(name = "genre")
    String genre;


    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", kinopoiskId=" + kinopoiskId +
                ", nameRu='" + nameRu + '\'' +
                ", year=" + year +
                ", ratingKinopoisk=" + ratingKinopoisk +
                ", description='" + description + '\'' +
                '}';
    }
}
