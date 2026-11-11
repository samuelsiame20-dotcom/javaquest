package quest.engine;

import static quest.engine.LevelRegistry.room;

import java.util.List;

/** The tutorial and World 1 - Rust Removal (fundamentals refresher). */
final class World1Rooms {

    private static final String TUTORIAL = "Tutorial";
    private static final String WORLD_1 = "World 1 - Rust Removal";

    private World1Rooms() {
    }

    static List<Level> rooms() {
        return List.of(
                room(0, TUTORIAL, "w0", "Level00_Doorbell", "The Doorbell",
                        """
                        A rusty speaker crackles beside the first door: "State your name, traveller."
                        The door only opens for a proper greeting.

                        This is how every room works: open the file below in your editor, replace the
                        TODO with your code, save, and run the game again. The door's lock is a set of
                        tests. Pass them all and it opens.
                        """,
                        "greet(name) must return \"Hello, <name>!\", e.g. \"Hello, Sammy!\"",
                        List.of(
                                "The exact text matters: capital H, a comma, a space, the name, then '!'.",
                                "Strings join with +, so \"Hi \" + \"there\" makes \"Hi there\". A method gives "
                                        + "back its answer with return.",
                                """
                                return "Hello, " + <the name parameter> + "!";
                                """),
                        "String concatenation with +, and the return statement.",
                        20),

                room(1, WORLD_1, "w1", "Level01_Gatekeeper", "The Gatekeeper",
                        """
                        Past the doorbell stands the Gatekeeper, a rack-mounted guardian with a red
                        status LED for an eye. It decides who may enter the Kernel Dungeon and what each
                        traveller pays. A power surge corrupted its rulebook, and now it waves everyone
                        through. Rewrite its rules before something nasty wanders in.
                        """,
                        "canEnter(...) applies the entry rules; toll(age) returns the gold to charge. "
                                + "The exact rules are in the comments in the file.",
                        List.of(
                                "Take canEnter's rules one at a time. Which rule beats every other rule? "
                                        + "Check that one first.",
                                "Combine conditions with && (and), || (or) and ! (not). For toll, an "
                                        + "if / else if / else chain picks exactly one branch. Watch the "
                                        + "boundaries: is 12 a child or an adult? Is 65?",
                                """
                                canEnter:  if banned, return false
                                           return (age >= 16) AND (hasKey OR knowsPassword)
                                toll:      if age < 12        -> 0
                                           else if age < 65   -> 10
                                           else               -> 5
                                """),
                        "Relational operators (<, >=), logical operators (&&, ||, !) and if / else-if "
                                + "chains. Oracle Java Tutorial: \"Equality, Relational, and Conditional "
                                + "Operators\".",
                        100),

                room(2, WORLD_1, "w1", "Level02_TorchCount", "Torch Count",
                        """
                        The Torch Gallery is a long hall of rooms, and the lamps follow a pattern: room 1
                        holds one torch, room 2 holds two, room 3 holds three, and so on. The Lamplighter
                        Daemon wants exact counts before it lights the way, and it has no patience for
                        guesses. Count carefully. The dark beyond the gallery is listening.
                        """,
                        "totalTorches(rooms), countBrightRooms(rooms) and daysOfLight(oil). Each one is "
                                + "a loop; examples are in the file.",
                        List.of(
                                "Each method keeps a running answer in a variable declared BEFORE the loop, "
                                        + "updates it inside the loop, and returns it after.",
                                "A for loop suits a known range like 1 to rooms (include the last room: <=). "
                                        + "% gives the remainder, so n % 3 == 0 means n divides by 3. "
                                        + "daysOfLight doesn't know how many days ahead of time, so a while "
                                        + "loop fits: keep going while there's enough oil for the next day.",
                                """
                                total = 0;  for room from 1 to rooms: total += room
                                count = 0;  for room from 1 to rooms: if room%3==0 OR room%5==0: count++
                                days = 0;   while oil >= days + 1:  days++;  oil -= days
                                """),
                        "for vs while loops, accumulator variables, and the % (remainder) operator. "
                                + "Oracle Java Tutorial: \"The for Statement\" and \"The while and do-while "
                                + "Statements\".",
                        100),

                room(3, WORLD_1, "w1", "Level03_RuneArray", "Rune Array",
                        """
                        Glowing runes line the walls of the Rune Vault, each carved with a power number.
                        Some numbers are negative: cursed runes that drain whoever reads them. The Vault
                        Keeper wants three answers: the strongest power, where the weakest rune sits,
                        and where a given power first appears. The Keeper has set traps for lazy readers.
                        """,
                        "strongest(runes), weakestIndex(runes) and indexOf(runes, target). "
                                + "Details and examples are in the file.",
                        List.of(
                                "Visit every element with a loop. Keep the 'best so far' in a variable and "
                                        + "replace it when you find something better.",
                                "Start 'best so far' at runes[0], not at 0: what if every rune is negative? "
                                        + "For weakestIndex, remember the INDEX and replace it only on strictly "
                                        + "smaller (<). For indexOf, return as soon as you find a match; "
                                        + "-1 only after the loop ends.",
                                """
                                strongest:     best = runes[0]; for each r: if r > best: best = r
                                weakestIndex:  idx = 0; for i from 1 to length-1:
                                                   if runes[i] < runes[idx]: idx = i
                                indexOf:       for i from 0 to length-1: if runes[i] == target: return i
                                               after the loop: return -1
                                """),
                        "Arrays: indexing from 0, .length, for and for-each loops, linear search. "
                                + "Oracle Java Tutorial: \"Arrays\".",
                        100),

                room(4, WORLD_1, "w1", "Level04_ScrollDecoder", "Scroll Decoder",
                        """
                        The Scriptorium is buried in scrolls the server daemons wrote backwards, padded
                        with stray spaces, and stuffed with repeated runes to fill the page. The Archivist,
                        a librarian made of punch cards, will let you pass once its four decoding spells
                        work. Words, vowels, runs and mirrors: strings are slippery here. Don't trust ==.
                        """,
                        "reverseWords, countVowels, compress and isPalindrome. Examples are in the file.",
                        List.of(
                                "Strings can't be changed (they're immutable). When you build a result piece by "
                                        + "piece in a loop, use a StringBuilder and call toString() at the end.",
                                "trim() removes outer spaces; split(\"\\\\s+\") splits on runs of spaces. "
                                        + "charAt(i) reads one char; Character.isLetter and Character.toLowerCase "
                                        + "help with palindromes; StringBuilder has reverse(). Compare strings "
                                        + "with equals(), never ==.",
                                """
                                reverseWords: words = trim().split("\\\\s+"); append from last to first with spaces
                                compress:     current = first char, run = 1
                                              for each next char: same -> run++ ; different -> append current+run, reset
                                              after loop: append the last current+run
                                isPalindrome: keep letters, lower-case; compare with its reverse using equals
                                """),
                        "String methods (trim, split, charAt, toLowerCase, equals) and StringBuilder (append, "
                                + "reverse, toString). Why == on Strings compares references, not text. "
                                + "Oracle Java Tutorial: \"Strings\" and \"The StringBuilder Class\".",
                        100),

                room(5, WORLD_1, "w1", "Level05_VaultLock", "BOSS: The Vault Lock",
                        """
                        At the end of the rust-caked corridor waits the Vault, its door ringed with brass
                        dials that each show a digit. The Vault Lock speaks in a grinding voice: it will
                        only open for someone who can check a code, turn the dials the short way round,
                        and write a guide for the next poor soul. Everything World 1 taught you is needed.
                        """,
                        "isValidCode, toDigits, turnsToOpen and guide. The dial rules are in the file.",
                        List.of(
                                "Split the boss into small helper ideas: a loop over characters, a loop over two "
                                        + "arrays in step (same index i), and a distance for one dial.",
                                "For one dial, the plain distance is Math.abs(d - c), and going the other way "
                                        + "round is 10 - distance. Turning UP from d to c takes (c - d + 10) % 10 "
                                        + "turns; the + 10 stops % giving a negative answer. A char digit becomes "
                                        + "a number with c - '0'.",
                                """
                                isValidCode: null or length outside 4..8 -> false; any non-digit -> false
                                turnsToOpen: total += min(dist, 10 - dist) for every dial
                                guide:       up = (c - d + 10) % 10;  down = (d - c + 10) % 10
                                             up == 0 -> '='  else up <= down -> '+'  else '-'
                                """),
                        "Methods that each do one job, plus everything from World 1: conditionals, loops, "
                                + "arrays, Strings, the % operator and Math.abs / Math.min.",
                        250));
    }
}
