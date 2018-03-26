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
    private int numberOfRequiredPlayer;
    private List<Player> players;
    private List<Player> invitedPlayers;
    private Day day;
    private String time;

    public Game() {
    }

    public Game(String title, Venue venue, Player organiser, int numberOfRequiredPlayer, Day day, String time) {
        this.title = title;
        this.venue = venue;
        this.organiser = organiser;
        this.numberOfRequiredPlayer = numberOfRequiredPlayer;
        this.players = new ArrayList<>();
        this.day = day;
        this.time = time;
        this.invitedPlayers = new ArrayList<>();
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
    public String getDay() {
        return this.day.getDay();
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


    public int updatedRequiredPlayers() {
       return this.numberOfRequiredPlayer - this.players.size();
    }

    public Boolean addPlayers(Player player) {
        if(this.numberOfRequiredPlayer > this.players.size()){
                this.players.add(player);
                return true;
        }
        else return false;
    }

    public int playerCount() {
        return this.players.size();
    }


    public void invitePlayer(Player player, Game game) {
        if (player.playerAvailable(game) == true) {
            this.invitedPlayers.add(player);
        }
    }

    public int invitedPlayerCount() {
        return this.invitedPlayers.size();
    }
}
