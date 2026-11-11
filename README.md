# JavaQuest

A terminal dungeon game that teaches Java. **You write the code.** Each room is a Java class
with `TODO`s; a JUnit test class is the door's lock. Make the tests pass and the door opens.

> Status: **Phase 2 (MVP)**. The engine and all 15 rooms of Worlds 1-3 (plus the tutorial) are
> playable. The rooms follow the ICT261 Advanced Java outline (see the table at the end).

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

### Architecture: the engine as a layered design

The engine follows the layered architecture from ICT261 Unit 1: each layer only talks to the one
below it, and the UI can be swapped without touching the rest.

| Layer (Unit 1) | In JavaQuest | Job |
|---|---|---|
| UI | `quest.ui.Renderer`, `ConsoleRenderer` | Everything the player sees. No game rules |
| Controller | `Game`, `Options` | Reads the command, decides what happens, calls the layers below |
| Service | `LevelRunner`, `XpRules`, `LevelRegistry` | The rules: run a lock, score it, find the current room |
| Data | `ProgressStore` | Reads and writes the save file |
| Network | none | The game is offline by design |

It's also MVC: the **model** is `Level` and `Progress`, the **view** is `Renderer`, and the
**controller** is `Game`. Because `Game` depends on the `Renderer` interface and not on the console,
the Phase 3 JavaFX screen is a second view with no engine changes.

### Engine design (short version)

- `LevelRegistry` holds every room as a `Level` record (story, task, hints, XP). The room content
  lives in one class per world (`World1Rooms`, `World2Rooms`, `World3Rooms`). The engine has no
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
   uses that exact placeholder to tell "not started" from "wrong". A room with several classes keeps
   them as nested types in that one file, so the player has one file to edit. Only unavoidable
   boilerplate is real code (records, enum constructors, a `super(...)` line needed to compile).
2. **Lock** in `src/test/java/quest/levels/wN/LevelNN_NameTest.java`: at most 5 tests, `@Timeout(2)`
   on the class, a `@DisplayName` on each test, and every assertion message says what was expected
   plus a nudge.
3. **Reference** in `src/reference/java/quest/levels/wN/LevelNN_Name.java`: same package and class name.
4. **Registry**: one `room(...)` entry in that world's `WorldNRooms` class (story of at most 120
   words, task, 3 hints, concept note, XP).
5. Run `gradlew.bat testReference`. It must pass, and `LevelRegistryTest` checks the entry follows
   the rules.
6. Check the fresh stub: every test must report `[TODO]`, so the room opens with its story.

## Rooms

Each room has at most 5 tests. Bosses are worth 250 XP, other rooms 100, the tutorial 20.

| # | Room | Topics | ICT261 |
|---|---|---|---|
| 0 | The Doorbell (tutorial) | String concatenation, `return` | |
| | **World 1 - Rust Removal** | | assumed knowledge |
| 1 | The Gatekeeper | Variables, conditionals, `&&` / `\|\|` / `!`, boundaries | |
| 2 | Torch Count | `for` and `while` loops, accumulators, `%` | |
| 3 | Rune Array | Arrays, min / max, linear search | |
| 4 | Scroll Decoder | String methods, `StringBuilder`, `equals` vs `==` | |
| 5 | BOSS: The Vault Lock | Methods, plus all of World 1 | |
| | **World 2 - The Guild Hall** | | Unit 1 (OOP revision) |
| 6 | Forge a Hero | Constructors, overloading, encapsulation, static vs instance | Unit 1 |
| 7 | Monster Bestiary | Inheritance, `super()`, overriding vs accidental overloading | Unit 1 |
| 8 | Spellbook | Interface vs abstract class, multiple interfaces, default and static interface methods | Unit 1 |
| 9 | The Enchanter's Forge | Composition over inheritance, enums with fields | Unit 1 |
| 10 | BOSS: The Battle Simulator | Polymorphism through an interface, plus all of World 2 | Unit 1 |
| | **World 3 - The Armory** | | Units 4 and 5 |
| 11 | Cursed Gold | Checked vs unchecked, custom exceptions, multi-catch, try-with-resources, exception translation | Unit 4 |
| 12 | Bag of Holding | Generic classes and methods, bounded types, PECS wildcards, multiple bounds | Unit 5 |
| 13 | The Merchant's Ledger | Choosing List / Set / Map, `equals` / `hashCode`, streams (`filter`, `map`, `groupingBy`) | Unit 5 |
| 14 | The Leaderboard | `Comparable`, `Comparator`, sorting a copy | Unit 5 |
| 15 | BOSS: The Shop System | Exceptions + generics + collections + stream reports | Units 4 and 5 |

Room 11 practises the same skills as the Unit 4 lab, but on its own story and data, so this repo
never contains a lab answer. JavaFX (Units 2-3) is planned as an optional bonus room and
World 5; see [docs/PLAN.md](docs/PLAN.md).
