CREATE TABLE IF NOT EXISTS films (
    id BIGSERIAL PRIMARY KEY,
    film_id BIGINT NOT NULL UNIQUE,
    film_name VARCHAR(255) NOT NULL,
    release_year INT,
    rating DOUBLE PRECISION,
    description TEXT
    );