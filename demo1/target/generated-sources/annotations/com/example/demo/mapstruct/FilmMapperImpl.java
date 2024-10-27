package com.example.demo.mapstruct;

import com.example.demo.DTO.FilmDto;
import com.example.demo.entity.Film;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-27T20:59:17+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class FilmMapperImpl implements FilmMapper {

    @Override
    public FilmDto toDTO(Film film) {
        if ( film == null ) {
            return null;
        }

        FilmDto filmDto = new FilmDto();

        return filmDto;
    }

    @Override
    public Film toEntity(FilmDto filmDTO) {
        if ( filmDTO == null ) {
            return null;
        }

        Film film = new Film();

        return film;
    }
}
