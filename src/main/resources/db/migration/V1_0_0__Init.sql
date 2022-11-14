CREATE TABLE IF NOT EXISTS user_t (
    id INT NOT NULL,
    active BOOLEAN NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_role_t (
    user_id INT NOT NULL,
    roles VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS artist_t (
    id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    image_url VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS album_t (
    id INT NOT NULL,
    artist VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    release_year VARCHAR(255) NOT NULL,
    image VARCHAR(255),
    duration VARCHAR(255),
    genre VARCHAR(255),
    label VARCHAR(255) NOT NULL,
    tracks INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS song_t (
    id INT NOT NULL,
    album VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    duration VARCHAR(255) NOT NULL,
    genre VARCHAR(255) NOT NULL,
    path VARCHAR(255),
    release_year VARCHAR(255) NOT NULL,
    track_number INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS artist_album_t (
    artist_id INT NOT NULL,
    album_id INT NOT NULL,
    PRIMARY KEY (artist_id, album_id)
);

CREATE TABLE IF NOT EXISTS album_song_t (
    album_id INT NOT NULL,
    song_id INT NOT NULL,
    PRIMARY KEY (album_id, song_id)
);
