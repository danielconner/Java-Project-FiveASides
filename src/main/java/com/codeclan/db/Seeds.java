package com.codeclan.db;

import com.codeclan.models.Day;
import com.codeclan.models.Game;
import com.codeclan.models.Player;
import com.codeclan.models.Venue;

import java.util.ArrayList;
import java.util.List;

public class Seeds {

    public static void seedData(){
        DBHelper.deleteAll(Game.class);
        DBHelper.deleteAll(Player.class);

        List<Day> davesAvailabilty = new ArrayList<>();
        davesAvailabilty.add(Day.THURSDAY);
        davesAvailabilty.add(Day.FRIDAY);
        Player player1 = new Player("DaveBoi", "David Graham", davesAvailabilty, "Glasgow");
        DBHelper.save(player1);

        List<Day> stevesAvailabilty = new ArrayList<>();
        stevesAvailabilty.add(Day.WEDNESDAY);
        stevesAvailabilty.add(Day.TUESDAY);
        Player player2 = new Player("Stevey", "Steven Davis", stevesAvailabilty, "Glasgow");
        DBHelper.save(player2);

        Venue venue1 = new Venue("Powerleague Edinburgh", "Edinburgh");
        DBHelper.save(venue1);
        Venue venue2 = new Venue("Powerleague Townhead", "Glasgow");
        DBHelper.save(venue2);

        Game game1 =  new Game("Daveys Game", venue1, player1, 2, Day.THURSDAY, "19:00");
        DBHelper.save(game1);

        Game game2 =  new Game("Steves Game", venue2, player2, 2, Day.TUESDAY, "20:30");
        DBHelper.save(game2);





    }
}
