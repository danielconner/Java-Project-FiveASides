package com.codeclan.db;

import com.codeclan.models.Day;
import com.codeclan.models.Game;
import com.codeclan.models.Player;

import java.util.ArrayList;
import java.util.List;

public class Seeds {

    public static void seedData(){
        DBHelper.deleteAll(Game.class);
        DBHelper.deleteAll(Player.class);


        Player player1 = new Player("DaveBoi", "David Graham", Day.THURSDAY, 23);
        DBHelper.save(player1);

        Player player2 = new Player("Stevey", "Steven Davis",Day.TUESDAY , 4);
        DBHelper.save(player2);

        Game game1 =  new Game("Daveys Game", "Townhead", player1, Day.THURSDAY, "19:00");
        DBHelper.save(game1);

        Game game2 =  new Game("Steves Game", "Townhead", player2, Day.TUESDAY, "20:30");
        DBHelper.save(game2);

        List<Player> players = DBHelper.getAll(Player.class);
        List<Game> games = DBHelper.getAll(Game.class);




    }
}
