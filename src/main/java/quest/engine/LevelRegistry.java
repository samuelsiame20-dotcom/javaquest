package quest.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Every room in the dungeon, in the order they unlock. The room content (story, hints, ...) lives
 * in one class per world: {@link World1Rooms}, {@link World2Rooms}, {@link World3Rooms}.
 * Adding a room = one {@code room(...)} entry there (plus its stub, test class and reference solution).
 */
public final class LevelRegistry {

    private static final List<Level> LEVELS = build();

    private LevelRegistry() {
    }

    private static List<Level> build() {
        List<Level> all = new ArrayList<>();
        all.addAll(World1Rooms.rooms());
        all.addAll(World2Rooms.rooms());
        all.addAll(World3Rooms.rooms());
        return List.copyOf(all);
    }

    public static List<Level> all() {
        return LEVELS;
    }

    public static Optional<Level> byId(int id) {
        return LEVELS.stream().filter(level -> level.id() == id).findFirst();
    }

    /** The first room not yet cleared, or empty when every room is cleared. */
    public static Optional<Level> currentRoom(Progress progress) {
        return LEVELS.stream().filter(level -> !progress.isCleared(level.id())).findFirst();
    }

    /** A room can be played if it's cleared or it's the current room. */
    public static boolean isUnlocked(Level level, Progress progress) {
        return progress.isCleared(level.id())
                || currentRoom(progress).map(current -> current.id() == level.id()).orElse(false);
    }

    /** Builds a Level, deriving the stub path and test class name from the package and class. */
    static Level room(int id, String world, String pkg, String className, String title,
                      String story, String task, List<String> hints, String concept, int xp) {
        String fqcn = "quest.levels." + pkg + "." + className;
        String stubPath = "src/main/java/" + fqcn.replace('.', '/') + ".java";
        return new Level(id, world, title, story, task, stubPath, fqcn + "Test", hints, concept, xp);
    }
}
