import com.codeclan.models.Day;
import controllers.PlayerController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerControllerTest {

    private PlayerController playerController;

    @Before
    public void setUp() throws Exception {
        playerController = new PlayerController();
    }

    @Test
    public void testDayFromString() {
        assertEquals(Day.WEDNESDAY, playerController.returnDayFromString("wednesday"));
    }
}
