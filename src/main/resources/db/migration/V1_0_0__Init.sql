CREATE TABLE IF NOT EXISTS user_t (
    id int8 not null generated always as identity primary key,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(255),
    surname VARCHAR(255),
    role VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS artist_t (
    id int8 not null generated always as identity primary key,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    image VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS genre_t (
    id int8 not null generated always as identity primary key,
    name_of_genre VARCHAR(255) NOT NULL,
    description VARCHAR(505)
);

CREATE TABLE IF NOT EXISTS album_t (
    id int8 not null generated always as identity primary key,
    title VARCHAR(255) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    release_year VARCHAR(255) NOT NULL,
    image VARCHAR(255),
    duration VARCHAR(255),
    path VARCHAR(255),
    label VARCHAR(255) NOT NULL,
    tracks INT NOT NULL
);

CREATE TABLE IF NOT EXISTS song_t (
    id int8 not null generated always as identity primary key,
    title VARCHAR(255) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    album VARCHAR(255) NOT NULL,
    duration VARCHAR(255) NOT NULL,
    path VARCHAR(255),
    release_year VARCHAR(255) NOT NULL,
    track_number INT NOT NULL
    );

CREATE TABLE IF NOT EXISTS artist_album_t (
    artist_id INT NOT NULL,
    album_id INT NOT NULL,
    PRIMARY KEY (artist_id, album_id),
    foreign key (artist_id) references artist_t(id),
    foreign key (album_id) references album_t(id)
);

CREATE TABLE IF NOT EXISTS album_song_t (
    album_id INT NOT NULL,
    song_id INT NOT NULL,
    PRIMARY KEY (album_id, song_id),
    foreign key (album_id) references album_t(id),
    foreign key (song_id) references song_t(id)
);

CREATE TABLE IF NOT EXISTS user_song_t (
    user_id INT NOT NULL,
    song_id INT NOT NULL,
    PRIMARY KEY (user_id, song_id),
    foreign key (user_id) references user_t(id),
    foreign key (song_id) references song_t(id)
);

CREATE TABLE IF NOT EXISTS user_album_t (
    user_id INT NOT NULL,
    album_id INT NOT NULL,
    PRIMARY KEY (user_id, album_id),
    foreign key (user_id) references user_t(id),
    foreign key (album_id) references album_t(id)
);

CREATE TABLE IF NOT EXISTS user_artist_t (
    user_id INT NOT NULL,
    artist_id INT NOT NULL,
    PRIMARY KEY (user_id, artist_id),
    foreign key (user_id) references user_t(id),
    foreign key (artist_id) references artist_t(id)
);