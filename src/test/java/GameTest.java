import com.codeclan.models.Day;
import com.codeclan.models.Game;
import com.codeclan.models.Player;
import com.codeclan.models.Venue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {

    Game game;
    Player player;
    List<Day> availability;
    Venue venue;

    @Before
    public void setUp() throws Exception {
        availability = new ArrayList<Day>();
        availability.add(Day.TUESDAY);
        availability.add(Day.WEDNESDAY);
        venue = new Venue("Powerleague Townhead", "Glasgow");
        player = new Player("bob10", "Bob", availability, "Glasgow");
        game = new Game("Bob's kickabout", venue, player, 2, Day.TUESDAY, "20:00");
    }

    @Test
    public void canGetTitle() {
        assertEquals("Bob's kickabout", game.getTitle());
    }

    @Test
    public void canGetVenue() {
        assertEquals("Powerleague Townhead", game.getVenue().getName());
    }

    @Test
    public void canGetOrganiser() {
        assertEquals("Bob", player.getName());
    }

    @Test
    public void canGetDay() {
        assertEquals("tuesday", game.getDay());
    }

    @Test
    public void canGetTime() {
        assertEquals("20:00", game.getTime());
    }

    @Test
    public void canGetNumberOfRequiredPlayers() {
        assertEquals(2, game.getNumberOfRequiredPlayer() );
    }

    @Test
    public void canGetUpdatedRequiredPlayers() {
        Player player1 = new Player("bob10", "Bob", availability, "Glasgow");
        game.addPlayers(player1);
        assertEquals(1, game.playerCount());
        assertEquals(1, game.updatedRequiredPlayers());
    }

    @Test
    public void cannotAddPlayersIfOverRequiredPlayers() {
        assertEquals(0, game.playerCount());
        Player player1 = new Player("bob10", "Bob", availability, "Glasgow");
        game.addPlayers(player1);
        assertEquals(1, game.playerCount());
        Player player2 = new Player("Robbo", "Robbie", availability, "Glasgow");
        game.addPlayers(player2);
        assertEquals(2, game.playerCount());
        Player player3 = new Player("Sior", "Dave", availability, "Glasgow");
        game.addPlayers(player3);
        assertEquals(2, game.playerCount());
    }

    @Test
    public void canAddPlayerToInvitedPlayerList() {
        Game game1 = new Game("Bob's kickabout", venue, player, 2, Day.TUESDAY, "20:00");
        Player player1 = new Player("bob10", "Bob", availability, "Glasgow");
        game.invitePlayer(player1, game1);
        assertEquals(1, game.invitedPlayerCount());
    }

    @Test
    public void cannotAddUnavailablePlayerToGame() {
        Game game1 = new Game("Bob's kickabout", venue, player, 2, Day.THURSDAY, "20:00");
        Player player1 = new Player("bob10", "Bob", availability, "Glasgow");
        game.invitePlayer(player1, game1);
        assertEquals(0, game.invitedPlayerCount());
    }
}
