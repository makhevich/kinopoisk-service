package ru.abrosimov.kinopoiskservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.abrosimov.kinopoiskservice.entity.Film;
import ru.abrosimov.kinopoiskservice.service.FilmService;

import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/v2/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public ResponseEntity<List<Film>> getAndImportFilms(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "countries", required = false) Integer countries,
            @RequestParam(name = "genres", required = false) Integer genres,
            @RequestParam(name = "order", required = false, defaultValue = "RATING") String order,
            @RequestParam(name = "type", required = false, defaultValue = "ALL") String type,
            @RequestParam(name = "ratingFrom", required = false) Double ratingFrom,
            @RequestParam(name = "ratingTo", required = false) Double ratingTo,
            @RequestParam(name = "yearFrom", required = false) Integer yearFrom,
            @RequestParam(name = "yearTo", required = false) Integer yearTo,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page
    ) {
        List<Film> importedFilms = filmService.fetchAndSaveNewFilms(
                keyword, countries, genres, order, type, ratingFrom, ratingTo, yearFrom, yearTo, page
        );
        return ResponseEntity.ok(importedFilms);
    }

    @GetMapping("/db")
    public ResponseEntity<Page<Film>> searchInDb(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "yearFrom", required = false) Integer yearFrom,
            @RequestParam(name = "yearTo", required = false) Integer yearTo,
            @RequestParam(name = "ratingFrom", required = false) Double ratingFrom,
            @RequestParam(name = "ratingTo", required = false) Double ratingTo,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "ASC") String direction
    ) {
        Page<Film> result = filmService.searchFilmsInDatabase(
                keyword, yearFrom, yearTo, ratingFrom, ratingTo, page, size, sortBy, direction
        );
        return ResponseEntity.ok(result);
    }
}