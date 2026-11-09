# JavaQuest

A terminal dungeon game that teaches Java. **You write the code.** Each room is a Java class
with `TODO`s; a JUnit test class is the door's lock. Make the tests pass and the door opens.

> Status: **Phase 1**. The engine (rooms, hints, XP, streaks, map, saved progress) and rooms 0-3
> are playable. Worlds 1-3 (15 rooms) arrive in Phase 2.

## Install (Windows)

1. JDK 21 or newer (`java -version`). The code is compiled for Java 21, so JDK 21 and JDK 25 both work.
2. Git.
3. That's it. You don't install Gradle: `gradlew.bat` downloads the pinned version (9.8.0) the first time.

## Play

```bat
gradlew.bat quest
```

That one command does everything:

- **Room not started** (every method still throws the `TODO` placeholder): shows the story,
  your task and the file to edit.
- **Some tests fail**: shows each test as `[PASS]`, `[FAIL]`, `[TODO]`, `[TIME]` (took over 2 s,
  usually an infinite loop) or `[CRASH]` (your code threw an exception), with a message that says
  what was expected.
- **Every test passes**: the door opens, you get XP, and the next room's story appears.

| Command | What it does |
|---|---|
| `gradlew.bat quest` | Enter / test your current room |
| `gradlew.bat quest --args="--hint"` | Reveal the next hint (3 per room: nudge, concept, pseudo-code) |
| `gradlew.bat quest --args="--map"` | Show the dungeon map |
| `gradlew.bat quest --args="--level 1"` | Replay a room you've cleared |
| `gradlew.bat quest --args="--no-color"` | Turn colours off |

**Streak**: rooms cleared in a row with no failed tries. **Hints** may lower a room's XP
(see `XpRules`).

**Progress** is saved in `%USERPROFILE%\.javaquest\progress.properties`. Delete it to start over.
Set the `JAVAQUEST_HOME` environment variable to keep it somewhere else.

**Colours** are switched on automatically in Windows Terminal, VS Code and IntelliJ. Force them with
`set JAVAQUEST_COLOR=always`, or turn them off with `never`.

### Troubleshooting

- `error: illegal character: '﻿'`: your editor saved the file with a byte-order mark (BOM).
  Save it as **UTF-8 without BOM** (in VS Code: click "UTF-8 with BOM" in the status bar, then "Save with Encoding").
- `compileJava FAILED`: a syntax error in your code. The compiler message gives the file and line.

## How the build works

| Source set | Folder | Contents |
|---|---|---|
| `main` | `src/main/java` | Engine (`quest.engine`, `quest.ui`) and **your** room stubs (`quest.levels`) |
| `test` | `src/test/java` | The locks (`quest.levels`) and engine tests (`quest.engine`) |
| `reference` | `src/reference/java` | Solutions with the same class names. **Don't peek.** |

| Task | What it does |
|---|---|
| `gradlew.bat quest` | Compiles your code and the tests, then starts the game |
| `gradlew.bat testEngine` | Engine tests only. Should **always** pass |
| `gradlew.bat test` | Tests **your** stubs. Unfinished rooms fail; that's normal |
| `gradlew.bat testReference` | Tests the **reference** solutions, proving every room can be passed. CI runs this |

`gradlew.bat build` also runs `test`, so it fails while you still have unfinished rooms.
Use `gradlew.bat assemble testReference` to check that the project itself is healthy.

`testReference` puts `reference` classes on the classpath, plus a copy of `main` with
`quest/levels/**` removed, so a stub can never shadow its solution.

### Engine design (short version)

- `LevelRegistry` holds every room as a `Level` record (story, task, hints, XP). The engine has no
  room-specific code.
- `LevelRunner` runs a room's test class inside the game via the JUnit Platform Launcher and sorts
  each result into PASS / FAIL / TODO / TIME / CRASH.
- `ProgressStore` reads and writes the `.properties` file. It writes a temp file and then renames
  it, so a crash can't corrupt your save.
- `Game` decides what happens; the `Renderer` interface decides how it looks. `ConsoleRenderer` is
  the terminal version; the Phase 3 JavaFX UI will be a second `Renderer`.

## Add a room

1. **Stub** in `src/main/java/quest/levels/wN/LevelNN_Name.java`: rules and examples in the
   Javadoc, and every method body is `throw new UnsupportedOperationException("TODO");`. The game
   uses that exact placeholder to tell "not started" from "wrong".
2. **Lock** in `src/test/java/quest/levels/wN/LevelNN_NameTest.java`: at most 5 tests, `@Timeout(2)`
   on the class, a `@DisplayName` on each test, and every assertion message says what was expected
   plus a nudge.
3. **Reference** in `src/reference/java/quest/levels/wN/LevelNN_Name.java`: same package and class name.
4. **Registry**: one `room(...)` entry in `LevelRegistry` (story of at most 120 words, task, 3 hints,
   concept note, XP).
5. Run `gradlew.bat testReference`. It must pass, and `LevelRegistryTest` checks the entry follows
   the rules.

## Rooms

| # | Room | Topic |
|---|---|---|
| 0 | The Doorbell (tutorial) | String concatenation, `return` |
| 1 | The Gatekeeper | Variables, conditionals, `&&` / `\|\|` / `!`, boundaries |
| 2 | Torch Count | `for` and `while` loops, accumulators, `%` |
| 3 | Rune Array | Arrays, min / max, linear search |

The rest of Worlds 1-3 (15 rooms) is planned in [docs/PLAN.md](docs/PLAN.md).
