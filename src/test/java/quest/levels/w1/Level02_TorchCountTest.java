package quest.levels.w1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/** The lock for Room 2. */
@Timeout(2)
@DisplayName("Room 2 - Torch Count")
class Level02_TorchCountTest {

    @Test
    @DisplayName("adds up the torches in every room")
    void totalTorches() {
        assertEquals(1, Level02_TorchCount.totalTorches(1), "totalTorches(1) should be 1: one room, one torch.");
        assertEquals(15, Level02_TorchCount.totalTorches(5),
                "totalTorches(5) should be 1+2+3+4+5 = 15. If you got 10, your loop stops one room early: "
                        + "use <= rooms, not < rooms.");
        assertEquals(5050, Level02_TorchCount.totalTorches(100), "totalTorches(100) should be 5050.");
    }

    @Test
    @DisplayName("counts zero torches when there are no rooms")
    void noRooms() {
        assertEquals(0, Level02_TorchCount.totalTorches(0), "totalTorches(0) should be 0: no rooms, no torches.");
        assertEquals(0, Level02_TorchCount.totalTorches(-3),
                "totalTorches(-3) should be 0: a negative number of rooms has no torches.");
    }

    @Test
    @DisplayName("counts the bright rooms")
    void brightRooms() {
        assertEquals(0, Level02_TorchCount.countBrightRooms(2), "countBrightRooms(2) should be 0: rooms 1 and 2 "
                + "aren't divisible by 3 or 5.");
        assertEquals(7, Level02_TorchCount.countBrightRooms(15),
                "countBrightRooms(15) should be 7 (rooms 3, 5, 6, 9, 10, 12, 15). Room 15 is divisible by both "
                        + "3 and 5 but counts once: one if with ||, not two separate ifs.");
    }

    @Test
    @DisplayName("knows how many full days the oil lasts")
    void daysOfLightExact() {
        assertEquals(0, Level02_TorchCount.daysOfLight(0), "daysOfLight(0) should be 0: no oil, no light.");
        assertEquals(1, Level02_TorchCount.daysOfLight(1), "daysOfLight(1) should be 1: day 1 burns exactly 1.");
        assertEquals(4, Level02_TorchCount.daysOfLight(10), "daysOfLight(10) should be 4: 1+2+3+4 = 10 exactly.");
    }

    @Test
    @DisplayName("stops before a day the oil can't finish")
    void daysOfLightLeftover() {
        assertEquals(3, Level02_TorchCount.daysOfLight(9),
                "daysOfLight(9) should be 3: days 1-3 use 6 oil, leaving 3, but day 4 needs 4. Check there's "
                        + "enough oil for the next day BEFORE counting it.");
        assertEquals(5, Level02_TorchCount.daysOfLight(20), "daysOfLight(20) should be 5: 1+2+3+4+5 = 15, and "
                + "day 6 would need 6 more but only 5 is left.");
    }
}
