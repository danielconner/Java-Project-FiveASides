import com.codeclan.models.Day;
import com.codeclan.models.Game;
import com.codeclan.models.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PlayerTest {

    Player player;
    Game game;
    Game game2;
    List<Day> availability;

    @Before
    public void setUp() throws Exception {
        availability = new ArrayList<Day>();
        availability.add(Day.TUESDAY);
        availability.add(Day.WEDNESDAY);
        player = new Player("bob10", "Bob", availability, 0);
        game = new Game("Bob's kickabout", "Townhead", player, Day.TUESDAY, "20:00");
        game2 = new Game("Bob's kickabout", "Townhead", player, Day.FRIDAY, "20:00");
    }

    @Test
    public void testIsAvailable() {
        assertTrue(player.playerAvailable(game));
    }

    @Test
    public void testNotAvailable() {
        assertFalse(player.playerAvailable(game2));
    }

    @Test
    public void testCanSignUpToGame() {
        assertEquals(0, player.getSignedUpForGames().size());
        player.signUpForGame(game);
        assertEquals(1, player.getSignedUpForGames().size());

    }
}
