package com.codeclan.db;

import com.codeclan.models.Day;
import com.codeclan.models.Game;
import com.codeclan.models.Player;
import com.codeclan.models.Venue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Seeds {

    public static void seedData(){
        DBHelper.deleteAll(Player.class);
        DBHelper.deleteAll(Game.class);
        DBHelper.deleteAll(Venue.class);

        Player player1 = new Player("DaveBoi", "David Graham", "Glasgow");
        DBHelper.save(player1);

        Player player2 = new Player("Stevey", "Steven Davis",  "Glasgow");
        DBHelper.save(player2);

        Venue venue1 = new Venue("Powerleague Edinburgh", "Edinburgh");
        DBHelper.save(venue1);
        Venue venue2 = new Venue("Powerleague Townhead", "Glasgow");
        DBHelper.save(venue2);
        Venue venue3 = new Venue("Science Park", "Maryhill");
        DBHelper.save(venue3);
        Venue venue4 = new Venue("Broadwood", "Cumbernauld");
        DBHelper.save(venue4);

        Game game1 =  new Game("Daveys Game", venue1, player1, 2, Day.THURSDAY, "19:00");
        DBHelper.save(game1);

        Game game2 =  new Game("Steves Game", venue2, player2, 2, Day.TUESDAY, "20:30");
        DBHelper.save(game2);

        DBHelper.addPlayerToGame(player1, game1);

    }
}
