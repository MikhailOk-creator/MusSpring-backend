package ru.rtu_mirea.musspringbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user_t")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "realName")
    private String realName;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "active", nullable = false)
    private boolean active;

    /** User role in the system
     * @see Role **/
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role_t", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    /** List of user's liked songs
     * @see Song **/
    @ManyToMany
    @JoinTable(
            name = "user_song_t",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private Set<Song> likedSongs;

    /** List of user's liked album
     * @see Album **/
    @ManyToMany
    @JoinTable(
            name = "user_album_t",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id"))
    private Set<Album> likedAlbums;

    /** List of user's liked artists
     * @see Artist **/
    @ManyToMany
    @JoinTable(
            name = "user_artist_t",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private Set<Artist> likedArtists;
}
