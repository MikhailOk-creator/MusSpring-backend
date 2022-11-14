INSERT INTO public.user_t (id,active,email,"password",real_name,surname,username) VALUES
    (1,true,'mikhail.okhapkin@yandex.ru','$2a$12$9k9lBDNPsI1WC0lrVw/M/eaxwbhcL6qTnECAvXuBDX5qm8zVCL4r6','Mikhail','Okhapkin','MikhailOk'),
    -- password: 123456 --
    (2,true,'user@test.com','$2a$12$6yxiw7O4XPvMN8ywRNL6Iez5IZZXrRbLVWdCgEM3WnvrVaV.uCNzm',NULL,NULL,'User1'),
    -- password: 123 --
    (3,true,'admin@test.com','$2a$12$KWbw.bKYLhj6AeEeqUvMIe76vfPBnIP7wuSkWv.M/jMupK3.3VLdK',NULL,NULL,'Admin1');
    -- password: 1234 --

INSERT INTO public.user_role_t (user_id,roles) VALUES
    (1,'SUPER_ADMIN'),
    (2,'USER'),
    (3,'ADMIN');

INSERT INTO public.artist_t (id,country,description,image,"name") VALUES
    (1,'Germany',NULL,NULL,'Rammstein');

INSERT INTO public.album_t (id,artist,duration,genre,image,"label","path",release_year,title,tracks) VALUES
    (1,'Rammstein','45:02','Industrial Metal',NULL,'Universal',NULL,'2001','Mutter',11);

INSERT INTO public.artist_album_t (artist_id,album_id) VALUES
    (1,1);

INSERT INTO public.song_t (id,title,album,artist,duration,genre,"path",release_year,track_number) VALUES
    (1,'Mein Herz Brennt','Mutter','Rammstein','04:39','Industrial Metal',NULL,'2001',1),
    (2,'Links 2 3 4','Mutter','Rammstein','03:36','Industrial Metal',NULL,'2001',2),
    (3,'Sonne','Mutter','Rammstein','04:33','Industrial Metal',NULL,'2001',3),
    (4,'Ich Will','Mutter','Rammstein','03:37','Industrial Metal',NULL,'2001',4),
    (5,'Feuer Frie!','Mutter','Rammstein','03:11','Industrial Metal',NULL,'2001',5),
    (6,'Mutter','Mutter','Rammstein','04:32','Industrial Metal',NULL,'2001',6),
    (7,'Spieluhr','Mutter','Rammstein','04:46','Industrial Metal',NULL,'2001',7),
    (8,'Zwitter','Muter','Rammstein','04:17','Industrial Metal',NULL,'2001',8),
    (9,'Rein Raus','Mutter','Rammstein','03:10','Industrial Metal',NULL,'2001',9),
    (10,'Adios','Mutter','Rammstein','03:50','Industrial Metal',NULL,'2001',10);
INSERT INTO public.song_t (id,title,album,artist,duration,genre,"path",release_year,track_number) VALUES
    (11,'Nebel','Mutter','Rammstein','04:50','Industrial Metal',NULL,'2001',11);

INSERT INTO public.album_song_t (album_id,song_id) VALUES
    (1,1),
    (1,2),
    (1,3),
    (1,4),
    (1,5),
    (1,6),
    (1,7),
    (1,8),
    (1,9),
    (1,10);
INSERT INTO public.album_song_t (album_id,song_id) VALUES
    (1,11);

