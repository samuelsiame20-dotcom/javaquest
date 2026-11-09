package quest.levels.w1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/** The lock for Room 1. */
@Timeout(2)
@DisplayName("Room 1 - The Gatekeeper")
class Level01_GatekeeperTest {

    @Test
    @DisplayName("lets in an adult with a key, or with the password")
    void keyOrPasswordIsEnough() {
        assertTrue(Level01_Gatekeeper.canEnter(30, true, false, false),
                "canEnter(30, key=true, password=false, banned=false) should be true: a key alone is enough.");
        assertTrue(Level01_Gatekeeper.canEnter(30, false, true, false),
                "canEnter(30, key=false, password=true, banned=false) should be true: the password alone is "
                        + "enough. Did you use && where you needed ||?");
    }

    @Test
    @DisplayName("turns away a traveller with no key and no password")
    void needsKeyOrPassword() {
        assertFalse(Level01_Gatekeeper.canEnter(30, false, false, false),
                "canEnter(30, key=false, password=false, banned=false) should be false: without a key or "
                        + "the password, nobody passes.");
    }

    @Test
    @DisplayName("never lets a banned traveller in")
    void bannedNeverEnters() {
        assertFalse(Level01_Gatekeeper.canEnter(30, true, true, true),
                "canEnter(30, key=true, password=true, banned=true) should be false: banned beats every "
                        + "other rule. Check banned before anything else.");
    }

    @Test
    @DisplayName("enforces the minimum age of 16")
    void ageLimit() {
        assertFalse(Level01_Gatekeeper.canEnter(15, true, true, false),
                "canEnter(15, ...) should be false: 15 is too young, even with a key and the password.");
        assertTrue(Level01_Gatekeeper.canEnter(16, true, false, false),
                "canEnter(16, key=true, ...) should be true: 16 is old enough. Check >= versus >.");
    }

    @Test
    @DisplayName("charges the right toll at every age boundary")
    void tollBoundaries() {
        assertEquals(0, Level01_Gatekeeper.toll(5), "toll(5) should be 0: children under 12 pass free.");
        assertEquals(0, Level01_Gatekeeper.toll(11), "toll(11) should be 0: 11 is still under 12.");
        assertEquals(10, Level01_Gatekeeper.toll(12),
                "toll(12) should be 10: 12 counts as an adult. Check < versus <= on your first boundary.");
        assertEquals(10, Level01_Gatekeeper.toll(64), "toll(64) should be 10: 64 is still under 65.");
        assertEquals(5, Level01_Gatekeeper.toll(65),
                "toll(65) should be 5: elders 65 and over pay 5. Check your second boundary.");
    }
}
