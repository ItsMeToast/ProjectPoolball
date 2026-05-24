package playertypes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatlineTest {
    Statline statline;

    @BeforeEach
    public void setupStatline() {
        statline = new Statline(99,84,89,93,96,98,91,99,4.26);
    }

    @Test
    public void testGetGS() {
        assertEquals(96, statline.getGS());
    }

    @Test
    public void testGetPM() {
        assertEquals(93, statline.getPM());
    }

    @Test
    public void testGetSW() {
        assertEquals(93, statline.getSW());
    }

    @Test
    public void testGetDF() {
        assertEquals(88, statline.getDF());
    }

    @Test
    public void testGetInjury() {
        assertEquals(4.3, statline.getInjury());
    }
}
