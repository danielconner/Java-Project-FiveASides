package com.codeclan.models;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private String title;
    private String venue;
    private Player organiser;
    private List<Player> players;
    private Day day;
    private String time;
    private int id;

    public Game(String title, String venue, Player organiser, Day day, String time) {
        this.title = title;
        this.venue = venue;
        this.organiser = organiser;
        this.players = new ArrayList<Player>();
        this.day = day;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Player getOrganiser() {
        return organiser;
    }

    public void setOrganiser(Player organiser) {
        this.organiser = organiser;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
