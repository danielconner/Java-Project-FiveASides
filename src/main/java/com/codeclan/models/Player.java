package com.codeclan.models;

import javax.persistence.*;
import java.util.ArrayList;
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
    private String location;
    private List<Game> organisedGames;


    public Player() {
    }

    public Player(String username, String name, List<Day> availability, String location) {
        this.username = username;
        this.name = name;
        this.availability = availability;
        this.gamesPlayed = 0;
        this.signedUpForGames = new ArrayList<>();
        this.invitedGames = new ArrayList<>();
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

    @ElementCollection(targetClass = Day.class)
    @CollectionTable(
            name = "player_availability", joinColumns = {@JoinColumn(name = "player_id", nullable = false, updatable = false)}
    )
    @Column(name = "enum_id")
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

    public boolean playerAvailable(Game game) {
        for (Day day : availability) {
            if (day == game.getDay()) {
                return true;
            }
        }
        return false;
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
