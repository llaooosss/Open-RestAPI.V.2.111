package com.example.demo321.util;

public class FilmNotFoundException extends RuntimeException{
    public FilmNotFoundException(String message) {
        super(message);
    }
}
