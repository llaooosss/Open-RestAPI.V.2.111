package com.example.demo.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExcentionsHendler {

    @ExceptionHandler(FilmIsFind.class)
    public ResponseEntity<String> handleIsFoundFilm(FilmIsFind e) {
        return ResponseEntity.status(HttpStatus.FOUND).body(e.getMessage());
    }
}
