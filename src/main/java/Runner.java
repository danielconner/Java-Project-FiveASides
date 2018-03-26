import com.codeclan.db.DBHelper;
import com.codeclan.models.Day;
import com.codeclan.models.Game;
import com.codeclan.models.Player;
import com.codeclan.models.Venue;

import java.util.ArrayList;
import java.util.List;

public class Runner {

    public static void main(String[] args) {

        DBHelper.deleteAll(Game.class);
        DBHelper.deleteAll(Venue.class);
        DBHelper.deleteAll(Player.class);

        List<Day> davesAvailabilty = new ArrayList<>();
        davesAvailabilty.add(Day.THURSDAY);
        davesAvailabilty.add(Day.FRIDAY);
        Player player1 = new Player("DaveBoi", "David Graham", davesAvailabilty, 23, "Glasgow");
        DBHelper.save(player1);

        List<Day> stevesAvailabilty = new ArrayList<>();
        stevesAvailabilty.add(Day.WEDNESDAY);
        stevesAvailabilty.add(Day.TUESDAY);
        Player player2 = new Player("Stevey", "Steven Davis", stevesAvailabilty , 41, "Glasgow");
        DBHelper.save(player2);
        Venue venue1 = new Venue("Powerleague Edinburgh", "Edinburgh");
        Venue venue2 = new Venue("Powerleague Townhead", "Glasgow");
        DBHelper.save(venue1);
        DBHelper.save(venue2);

        Game game1 =  new Game("Daveys Game", venue1, player1, Day.THURSDAY, "19:00");
        DBHelper.save(game1);

        Game game2 =  new Game("Steves Game", venue2, player2, Day.TUESDAY, "20:30");
        DBHelper.save(game2);

        List<Player> players = DBHelper.getAll(Player.class);
        Player player = DBHelper.find(player1.getId(), Player.class);
        List<Game> games = DBHelper.getAll(Game.class);

        List<Player> sortedByGamesPlayed = DBHelper.sortPlayersByMostGamesPlayed();
        List<Game> gamesBasedByVenue = DBHelper.gamesBasedOnLocation("Glasgow");
        List<Game> upDatedGames = DBHelper.getAll(Game.class);



    }
}

