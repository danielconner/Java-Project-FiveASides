package com.codeclan.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "players")
public class Player {
    private String username;
    private String name;
    private Set<Game> signedUpForGames;
    private int gamesPlayed;
    private int id;
    private String location;
    private List<Game> organisedGames;


    public Player() {
    }

    public Player(String username, String name, String location) {
        this.username = username;
        this.name = name;
        this.gamesPlayed = 0;
        this.signedUpForGames = new HashSet<>();
        this.location = location;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name= "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "players_signed_up_games",
            joinColumns = {@JoinColumn(name = "player_id")},
            inverseJoinColumns = {@JoinColumn(name = "game_id")})
    public Set<Game> getSignedUpForGames() {
        return signedUpForGames;
    }

    public void setSignedUpForGames(Set<Game> signedUpForGames) {
        this.signedUpForGames = signedUpForGames;
    }

    @Column(name = "games_played")
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "players", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public List<Game> getOrganisedGames() {
        return organisedGames;
    }

    public void setOrganisedGames(List<Game> organisedGames) {
        this.organisedGames = organisedGames;
    }


    public void signUpForGame(Game game) {
            signedUpForGames.add(game);
    }


    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
