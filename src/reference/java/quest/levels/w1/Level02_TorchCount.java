package quest.levels.w1;

/** Reference solution for Room 2. Exists only to prove the lock can be opened. */
public class Level02_TorchCount {

    public static int totalTorches(int rooms) {
        int total = 0;
        for (int room = 1; room <= rooms; room++) {
            total += room;
        }
        return total;
    }

    public static int countBrightRooms(int rooms) {
        int count = 0;
        for (int room = 1; room <= rooms; room++) {
            if (room % 3 == 0 || room % 5 == 0) {
                count++;
            }
        }
        return count;
    }

    public static int daysOfLight(int oil) {
        int days = 0;
        while (oil >= days + 1) {
            days++;
            oil -= days;
        }
        return days;
    }
}
