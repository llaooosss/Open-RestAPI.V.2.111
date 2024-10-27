package com.example.demo.mapstruct;

import com.example.demo.DTO.FilmDto;
import com.example.demo.entity.Film;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    FilmMapper INSTANCE = Mappers.getMapper( FilmMapper.class);
    FilmDto toDTO(Film film);

    FilmMapper UNINSTANCE = Mappers.getMapper( FilmMapper.class);
    Film toEntity(FilmDto filmDTO);

}
