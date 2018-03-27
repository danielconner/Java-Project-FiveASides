package com.codeclan.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "games")
public class Game {
    private int id;
    private String title;
    private Venue venue;
    private Player organiser;
    private int numberOfRequiredPlayer;
    private Set<Player> players;
    private Day day;
    private String time;

    public Game() {
    }

    public Game(String title, Venue venue, Player organiser, int numberOfRequiredPlayer, Day day, String time) {
        this.title = title;
        this.venue = venue;
        this.organiser = organiser;
        this.numberOfRequiredPlayer = numberOfRequiredPlayer;
        this.players = new HashSet<>();
        this.time = time;
        this.day = day;
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
    public Venue getVenue() { return venue;
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

    @Column(name = "required_players")
    public int getNumberOfRequiredPlayer() {
        return numberOfRequiredPlayer;
    }

    public void setNumberOfRequiredPlayer(int numberOfRequiredPlayer) {
        this.numberOfRequiredPlayer = numberOfRequiredPlayer;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "players_signed_up_games",
            joinColumns = {@JoinColumn(name = "game_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "player_id", nullable = false)})
    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
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

    public int updatedRequiredPlayers() {
       return this.numberOfRequiredPlayer - this.players.size();
    }

    public void addPlayers(Player player) {
        if(this.numberOfRequiredPlayer > this.players.size()){
                this.players.add(player);
                this.numberOfRequiredPlayer -= 1;
        }
    }

    public int playerCount() {
        return this.players.size();
    }


}
