package ru.abrosimov.kinopoiskservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "films")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "film_id", nullable = false, unique = true)
    private Long filmId;

    @Column(name = "film_name", nullable = false)
    private String filmName;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}