package ru.abrosimov.kinopoiskservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KinopoiskResponseDto {

    private int total;
    private int totalPages;
    private List<KinopoiskFilmDto> items;
}