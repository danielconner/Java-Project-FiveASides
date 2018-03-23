package com.codeclan.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "players")

public class Player {
    private String username;
    private String name;
    private List<Day> availability;
    private List<Game> invitedGames;
    private List<Game> signedUpForGames;
    private int gamesPlayed;
    private int id;


    public Player() {
    }

    public Player(String username, String name, List<Day> availability, int gamesPlayed) {
        this.username = username;
        this.name = name;
        this.availability = availability;
        this.gamesPlayed = gamesPlayed;
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

    @Column(name = "availability")
    public List<Day> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Day> availability) {
        this.availability = availability;
    }

    @ManyToMany
    @JoinTable(name = "invited_players_invited_games",
        joinColumns = {@JoinColumn(name = "player_id", nullable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "game_id", nullable = false, updatable = false)})
    public List<Game> getInvitedGames() {
        return invitedGames;
    }

    public void setInvitedGames(List<Game> invitedGames) {
        this.invitedGames = invitedGames;
    }

    @ManyToMany
    @JoinTable(name = "players_signed_up_games",
            joinColumns = {@JoinColumn(name = "player_id", nullable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "game_id", nullable = false, updatable = false)})

    public List<Game> getSignedUpForGames() {
        return signedUpForGames;
    }

    public void setSignedUpForGames(List<Game> signedUpForGames) {
        this.signedUpForGames = signedUpForGames;
    }

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
}
