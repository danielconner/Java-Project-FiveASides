package com.codeclan.models;

import java.util.List;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Day> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Day> availability) {
        this.availability = availability;
    }

    public List<Game> getInvitedGames() {
        return invitedGames;
    }

    public void setInvitedGames(List<Game> invitedGames) {
        this.invitedGames = invitedGames;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
