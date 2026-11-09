package quest.engine;

import java.util.List;

/**
 * One room of the dungeon. Pure data: the engine never hard-codes anything about a specific room.
 *
 * @param id        position in the dungeon (0 = tutorial); rooms unlock in id order
 * @param world     world name shown on the map, e.g. "World 1 - Rust Removal"
 * @param title     room title
 * @param story     story text (at most 120 words)
 * @param task      short summary of what the player must implement
 * @param stubPath  the file the player edits, relative to the project folder
 * @param testClass fully-qualified name of the JUnit class that is this room's lock
 * @param hints     three hints, in order: nudge, concept, pseudo-code
 * @param concept   what to read when stuck
 * @param xp        XP for clearing the room, before hint penalties
 */
public record Level(int id, String world, String title, String story, String task, String stubPath,
                    String testClass, List<String> hints, String concept, int xp) {

    public Level {
        hints = List.copyOf(hints);
    }
}
