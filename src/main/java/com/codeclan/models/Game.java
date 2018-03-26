package com.codeclan.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {
    private int id;
    private String title;
    private Venue venue;
    private Player organiser;
    private List<Player> players;
    private List<Player> invitedPlayers;
    private Day day;
    private String time;

    public Game() {
    }

    public Game(String title, Venue venue, Player organiser, Day day, String time) {
        this.title = title;
        this.venue = venue;
        this.organiser = organiser;
        this.players = new ArrayList<Player>();
        this.day = day;
        this.time = time;
        this.invitedPlayers = new ArrayList<Player>();
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @ManyToOne
    @JoinColumn(name="player_id", nullable=false)
    public Player getOrganiser() {
        return organiser;
    }

    public void setOrganiser(Player organiser) {
        this.organiser = organiser;
    }

    @ManyToMany
    @JoinTable(name = "players_signed_up_games",
            joinColumns = {@JoinColumn(name = "game_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "player_id", nullable = false, updatable = false)})
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Column(name = "day")
    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    @Column(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    @ManyToMany
    @JoinTable(name = "invited_players_invited_games",
            joinColumns = {@JoinColumn(name = "game_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "player_id", nullable = false, updatable = false)})
    public List<Player> getInvitedPlayers() {
        return invitedPlayers;
    }

    public void setInvitedPlayers(List<Player> invitedPlayers) {
        this.invitedPlayers = invitedPlayers;
    }
}
