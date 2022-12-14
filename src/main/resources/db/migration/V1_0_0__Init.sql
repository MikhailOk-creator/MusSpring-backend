CREATE TABLE IF NOT EXISTS user_t (
    id int8 not null generated always as identity primary key,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS user_role_t (
    user_id INT NOT NULL,
    roles VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS artist_t (
    id int8 not null generated always as identity primary key,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    image VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS album_t (
    id int8 not null generated always as identity primary key,
    title VARCHAR(255) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    release_year VARCHAR(255) NOT NULL,
    image VARCHAR(255),
    duration VARCHAR(255),
    genre VARCHAR(255),
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
    genre VARCHAR(255) NOT NULL,
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

alter table user_role_t add constraint user_role_t_id_fk foreign key (user_id) references user_t;

alter table artist_album_t add constraint artist_album_t_artist_id_fk foreign key (artist_id) references artist_t;
alter table artist_album_t add constraint artist_album_t_album_id_fk foreign key (album_id) references album_t;
alter table album_song_t add constraint album_song_t_album_id_fk foreign key (album_id) references album_t;
alter table album_song_t add constraint album_song_t_song_id_fk foreign key (song_id) references song_t;