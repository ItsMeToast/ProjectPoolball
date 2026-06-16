package playertypes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PlayerTest {
    Player player;
    @BeforeEach
    public void setupPlayerTests() {
        player = new Player("Liam", "Nefeli", Nationality.CAN, 27, Playstyle.ATTACKER, Trait.CLUTCH, new Statline(99,96,93,90,87,84,81,78,2.5), 10);
    }

    @Test
    public void testParameterizedGetFirstName() {
        assertEquals("Liam", player.getFirstName());
    }

    @Test
    public void testParameterizedGetLastName() {
        assertEquals("Nefeli", player.getLastName());
    }

    @Test
    public void testParameterizedGetAge() {
        assertEquals(27, player.getAge());
    }

    @Test
    public void testParameterizedGetPlaystyle() {
        assertEquals(Playstyle.ATTACKER, player.getStyle());
    }

    @Test
    public void testParameterizedGetTrait() {
        assertEquals(Trait.CLUTCH, player.getTrait());
    }

    @Test
    public void testParameterizedGetOSS() {
        assertEquals(90, player.getOSS());
    }

    @Test
    public void testParameterizedGetPotential() {
        assertEquals(10, player.getPotential());
    }

    @Test
    public void testParameterizedInvalidTraitStyle() {
        try {
            Player p1 = new Player("Liam", "Nefeli", Nationality.CAN, 27, Playstyle.ATTACKER, Trait.SNEAKY, new Statline(99,96,93,90,87,84,81,78,2.5), 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void testParameterizedInvalidTraitPotential() {
        try {
            Player p1 = new Player("Liam", "Nefeli", Nationality.CAN, 27, Playstyle.ATTACKER, Trait.GOON, new Statline(99,96,93,90,87,84,81,78,2.5), 10);
            fail();
        } catch (IllegalArgumentException ignored) {}
    }
}
