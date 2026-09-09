package ru.abrosimov.kinopoiskservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KinopoiskFilmDto {

    @JsonProperty("kinopoiskId")
    private Long filmId;

    @JsonProperty("nameRu")
    private String nameRu;

    @JsonProperty("nameOriginal")
    private String nameOriginal;

    @JsonProperty("year")
    private Integer year;

    @JsonProperty("ratingKinopoisk")
    private Double rating;

    @JsonProperty("description")
    private String description;
}