import com.codeclan.models.Day;
import com.codeclan.models.Game;
import com.codeclan.models.Player;
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

    @Before
    public void setUp() throws Exception {
        availability = new ArrayList<Day>();
        availability.add(Day.TUESDAY);
        availability.add(Day.WEDNESDAY);
        player = new Player("bob10", "Bob", availability, 0, "Glasgow");
        game = new Game("Bob's kickabout", "Townhead", player, Day.TUESDAY, "20:00");
    }

    @Test
    public void canGetTitle() {
        assertEquals("Bob's kickabout", game.getTitle());
    }

    @Test
    public void canGetVenue() {
        assertEquals("Townhead", game.getVenue());
    }

    @Test
    public void canGetOrganiser() {
        assertEquals("Bob", player.getName());
    }

    @Test
    public void canGetDay() {
        assertEquals(Day.TUESDAY, game.getDay());
    }

    @Test
    public void canGetTime() {
        assertEquals("20:00", game.getTime());
    }


}
