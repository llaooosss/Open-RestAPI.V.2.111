package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "filmes")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long filmId;
    private String filmName;
    private Integer year;
    private Double rating;
    @Column(length = 30_000)
    private String description;

    @ElementCollection
    private List<String> genres;

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", filmId=" + filmId +
                ", filmName='" + filmName + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", genres=" + genres +
                '}';
    }
}


