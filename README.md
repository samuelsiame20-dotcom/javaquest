# JavaQuest

A terminal dungeon game that teaches Java. **You write the code.** Each room is a Java class
with a `TODO`; a JUnit test class is the door's lock. Make the tests pass and the door opens.

> Status: **Phase 0 (skeleton)**. The build, the lock mechanism and a tutorial room exist.
> The game engine (map, hints, XP, saved progress) arrives in Phase 1.

## Install (Windows)

1. JDK 21 or newer (`java -version`). The code is compiled for Java 21, so JDK 21 and JDK 25 both work.
2. Git.
3. That's it. You don't install Gradle: `gradlew.bat` downloads the pinned version (9.8.0) the first time.

## Play

```bat
gradlew.bat quest
```

Phase 0 prints the welcome screen. Until the engine lands, try the tutorial lock directly:

1. Open `src/main/java/quest/levels/w0/Level00_Doorbell.java` and replace the `TODO`.
2. Run `gradlew.bat test` and read the failure messages. They tell you what's wrong.

Coming in Phase 1:

| Command | What it does |
|---|---|
| `gradlew.bat quest` | Show your current room, or test your code for it |
| `gradlew.bat quest --args="--hint"` | Reveal the next hint (costs XP) |
| `gradlew.bat quest --args="--map"` | Show the dungeon map |
| `gradlew.bat quest --args="--level 5"` | Replay an unlocked room |

## How the build works

| Source set | Folder | Contents |
|---|---|---|
| `main` | `src/main/java` | Engine (`quest.engine`) and **your** level stubs (`quest.levels`) |
| `test` | `src/test/java` | The locks: one test class per level |
| `reference` | `src/reference/java` | Solutions with the same class names. **Don't peek.** |

| Task | What it does |
|---|---|
| `gradlew.bat quest` | Compiles your code and the tests, then starts the game |
| `gradlew.bat test` | Tests **your** stubs. Unfinished levels fail; that's normal |
| `gradlew.bat testReference` | Tests the **reference** solutions, proving every level can be passed. CI runs this |

`gradlew.bat build` also runs `test`, so it fails while you still have unfinished levels.
Use `gradlew.bat assemble testReference` to check that the project itself is healthy.

`testReference` puts `reference` classes on the classpath, plus a copy of `main` with
`quest/levels/**` removed, so a stub can never shadow its solution.

## Add a level

1. **Stub** in `src/main/java/quest/levels/wN/LevelNN_Name.java`: story in the Javadoc, a `TODO`,
   and a body that compiles (`throw new UnsupportedOperationException("TODO")` or a default return).
2. **Lock** in `src/test/java/quest/levels/wN/LevelNN_NameTest.java`: at most 5 tests, `@Timeout(2)`
   on the class, and every assertion message says what was expected plus a nudge.
3. **Reference** in `src/reference/java/quest/levels/wN/LevelNN_Name.java`: same package and class name.
4. Run `gradlew.bat testReference`; it must pass.
5. (Phase 1+) Add one entry to `LevelRegistry`.

## Levels

| # | Room | Topic |
|---|---|---|
| 0 | The Doorbell (tutorial) | Strings, return values |

Worlds 1-3 (15 levels) are planned in [docs/PLAN.md](docs/PLAN.md).
