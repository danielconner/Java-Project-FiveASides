import com.codeclan.models.Venue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VenueTest {

    private Venue venue;

    @Before
    public void setUp() throws Exception {
        venue = new Venue("Powerleague Townhead", "Glasgow");
    }

    @Test
    public void testCanGetVenueName() {
        assertEquals("Powerleague Townhead", venue.getName());
    }

    @Test
    public void testCanGetLocation() {
        assertEquals("Glasgow", venue.getLocation());
    }
}
