# JavaQuest — Build Plan for Claude Code

A terminal (later JavaFX) dungeon game that teaches Java by making the **player write the code**.
Each level is a story room with a Java class stub. The player implements it; JUnit tests act as the
"door lock". Pass the tests → door opens → XP + next room.

Player: Sammy (Windows, Git, beginner-to-intermediate Java, revising for ICT261 Advanced Java).
Purpose: learning tool + ICT261 exam revision. Not a production product.

---

## 0. Rules for Claude Code (read first, put this section in CLAUDE.md)

1. **Never implement player solutions** in `src/main/java/quest/levels/**`. Those files are stubs
   with TODOs. Writing them defeats the entire project. If asked to "fix level N", give a hint, not code.
2. Every stub **must compile** as shipped (return defaults or `throw new UnsupportedOperationException("TODO")`),
   so one unfinished level never breaks the build for the others.
3. Reference solutions live only in the `reference` source set (see §3) and exist to prove the tests are
   solvable. The player is on the honor system not to open them.
4. Test failure messages are the teaching surface: each assertion message must explain *what* was expected
   and give a nudge, e.g. `"Hero.takeDamage(150) should clamp HP at 0, not go negative — check your if."`
5. Every level test uses a timeout (`@Timeout(2)`) so infinite loops fail cleanly instead of hanging.
6. Windows-first: document commands as `gradlew.bat ...`. No bash-only scripts.
7. No network access, no external services, minimal dependencies (JUnit only, plus `org.openjfx:javafx-base`
   for optional bonus room 16 alone; no other JavaFX modules before Phase 3). Small commits with clear
   messages, one phase per branch merged to `main`.

---

## 1. Stack

| Piece | Choice | Why |
|---|---|---|
| Language | Java 21 (LTS) | Records, switch patterns, modern course material |
| Build | Gradle wrapper (Groovy DSL) | ICT261 requires Gradle |
| Tests | JUnit 5 + JUnit Platform Launcher | Tests double as the game's lock mechanism |
| UI (MVP) | Terminal, ANSI colors (fallback to plain on old consoles) | Fast to build, zero UI debt before finals |
| UI (Phase 3) | JavaFX via the `org.openjfx.javafxplugin` | Matches ICT261 labs |
| Progress | `progress.properties` in user home `.javaquest/` | No JSON library needed |
| CI | GitHub Actions: build + `testReference` | Guarantees every level is solvable |

Verify current stable versions of the Gradle wrapper, JUnit, and JavaFX plugin before pinning them.

---

## 2. Core game loop

```
gradlew.bat quest          -> shows map, current room, story text, file to edit
(player edits Level0X.java in their IDE)
gradlew.bat quest          -> runs that level's tests via JUnit Launcher
   FAIL -> prints failed assertion messages as "the lock rattles..." + hint cost
   PASS -> +XP, room unlocked, next story shown, progress saved
gradlew.bat quest --args="--hint"     -> reveals next hint tier (costs XP)
gradlew.bat quest --args="--map"      -> ASCII world map with cleared/locked rooms
gradlew.bat quest --args="--level 5"  -> replay any unlocked level
```
(Gradle JavaExec needs `--args`. Optionally add a `play.bat` wrapper so it's just `play hint`.)

Game feel (keep it light but real):
- Story: player is a junior engineer trapped in a haunted server room ("The Kernel Dungeon").
- XP per level; **3 hint tiers** per level (nudge → concept → pseudo-code), each costs XP.
- Each world ends in a **boss level** that combines the world's concepts.
- Streak counter for levels cleared on first attempt.

---

## 3. Architecture

```
javaquest/
├─ build.gradle, settings.gradle, gradlew(.bat)
├─ CLAUDE.md                         (§0 rules)
├─ README.md                         (how to play, how to add a level)
├─ src/main/java/quest/
│  ├─ engine/
│  │  ├─ Level.java                  (record: id, world, title, story, stubPath, testClass, hints, xp)
│  │  ├─ LevelRegistry.java          (ordered list of all levels)
│  │  ├─ LevelRunner.java            (runs one test class via JUnit Platform Launcher, returns result)
│  │  ├─ ProgressStore.java          (load/save progress.properties)
│  │  └─ Game.java                   (main loop, arg parsing)
│  ├─ ui/
│  │  ├─ ConsoleRenderer.java        (map, story, results, colors)
│  │  └─ Renderer.java               (interface — JavaFX implements it in Phase 3)
│  └─ levels/                        (PLAYER FILES — stubs only)
│     ├─ w1/Level01_Gatekeeper.java ...
│     └─ ...
├─ src/test/java/quest/levels/       (one test class per level = the lock)
├─ src/reference/java/quest/levels/  (reference solutions, same package/class names)
└─ .github/workflows/ci.yml
```

Gradle requirements:
- Custom source set `reference`.
- Task `testReference`: runs `src/test` against `reference` output instead of `main` level classes
  (configure its classpath accordingly). Must pass in CI.
- Task `quest` (JavaExec) wired so `gradlew.bat quest` compiles and launches the game; it must compile
  test classes too, since `LevelRunner` executes them.
- Normal `gradlew.bat test` is allowed to fail (unfinished levels) — CI runs `testReference` instead.

Design notes:
- `Renderer` interface keeps the engine UI-agnostic, so JavaFX is a swap, not a rewrite.
- Adding a level = stub + test + reference + one registry entry. README documents this.

---

## 4. Level content (aligned to ICT261 — see `docs/ICT261_TOPICS.md`)

Aligned to the ICT261 units on 2026-11-11. Topics the course adds are in **bold**. The MVP is still
15 rooms; course topics were folded into existing rooms rather than adding new ones. Room 0 (The
Doorbell) is the tutorial that comes before World 1.

**Assessed labs: same concepts, different surface.** Rooms that practise a graded lab (Unit 4 marks
importer, Lecture 3 Lodge Billing) use the lab's concepts, exceptions and rules of thumb, but their own
story, data, IDs, file format and numbers. Never copy lab text, scaffolds or test data. Reason: the repo
(including `src/reference`) is a public portfolio piece, and it must not publish model answers to graded
work or make Sammy's lab submissions match public code.

**World 1 — Rust Removal (fundamentals refresher)** — the course assumes these
1. Gatekeeper — variables, conditionals (access rules) ✅ built
2. Torch Count — loops, accumulation ✅ built
3. Rune Array — arrays, min/max/search ✅ built
4. Scroll Decoder — Strings, StringBuilder
5. BOSS: Vault Lock — methods + all of the above

**World 2 — The Guild Hall (OOP)** — Unit 1 OOP revision
6. Forge a Hero — class, constructor, encapsulation, getters/validation, **overloaded constructors**,
   **static counter vs instance fields**
7. Monster Bestiary — inheritance, `super()`, method overriding, plus a test that catches
   **overloading by accident instead of overriding** (call through a superclass reference)
8. Spellbook — interfaces vs abstract classes, polymorphism in a loop, **multiple interfaces**,
   **interface default and static methods**
9. Enchanter's Forge — **composition over inheritance** (a weapon *has an* enchantment); enums with
   fields/methods as the supporting type (enums aren't in the course outline, so they're no longer a room of their own)
10. BOSS: Battle Simulator — polymorphic turn-based fight built from composed parts

**World 3 — The Armory (robust Java)** — Units 4 and 5
11. Cursed Gold — a **loot-ledger importer** practising the Unit 4 lab's skills on a different surface:
    reading through a `Reader` with **try-with-resources**, field-count check, `NumberFormatException`,
    range check via `IllegalArgumentException`, a custom **unchecked** duplicate exception, a missing
    file handled as a **checked** exception. Fixed policy: **skip and report** a bad line, then keep
    importing (one test proves the import continues). Concept note covers checked vs unchecked,
    `throw` vs `throws`, exception translation, and friendly messages vs logged detail.
12. Bag of Holding — generics (`Inventory<T extends Item>`, bounded types), **one PECS method**
    `copyAll(List<? extends T> from, List<? super T> to)`; **type erasure** in the concept note
    and hints (a test can't observe it)
13. Merchant's Ledger — `List`, `Map`, `Set`, choosing the right one, **`equals()`/`hashCode()`
    with `HashSet`**, **a small stream task** (`filter`/`map`, `groupingBy` + `counting`)
14. Leaderboard — `Comparable`, `Comparator`, sorting
15. BOSS: Shop System — exceptions + generics + collections, plus a **stream sales summary**

**Optional bonus — Room 16: The Crystal Ledger (`javafx.base`, no window)** — Units 2-3
A dungeon inn's billing model with its own room types and prices, using the **same rule shapes as the
Lodge Billing lab** (a long stay gets a percentage off the room charge only; extras are never discounted;
balance = total − paid). Inputs are **properties**; total and balance are **bindings**; an
**`ObservableList`** of saved bills has a **listener** keeping a running total. Tests prove the balance
updates when "paid" changes (a binding, not a one-off calculation). Runs in plain JUnit:
`javafx.beans`/`javafx.collections` need no JavaFX runtime or display. It comes after room 15, so the
MVP is still complete at 15 and the engine needs no changes. Its only cost is the one
dependency allowed by §0 rule 7.

**World 4 — The Stream Caverns (modern Java)** — post-MVP, renumbered 17-21
17. Lambdas & functional interfaces · 18. Streams, deeper (sorted, reduce, flatMap) · 19. Records &
Optional · 20. File I/O: save/load game · 21. BOSS: Quest Log Analytics (streams over a CSV of quests).
Revisit when the course's later units arrive (JDBC/JPA, REST/JSON, background threads): World 4 may
re-point at those instead.

**World 5 — The Crystal Interface (JavaFX)** — post-finals
Layouts, FXML + controller, controls, validation, TableView, event handling, BOSS: build a working shop
screen. (Properties, bindings and ObservableList are already covered by bonus room 16.)

Each room: ≤ 5 focused tests, story text ≤ 120 words, 3 hints, a concept note pointing to what to read
(name the ICT261 unit where it applies).

---

## 5. Phases & acceptance criteria

**Phase 0 — Skeleton (Claude Code, ~1 session)**
- Gradle project, wrapper, JUnit 5, `reference` source set, `testReference`, `quest` task, CI, README, `.gitignore`.
- ✅ `gradlew.bat quest` prints a welcome screen; `gradlew.bat testReference` passes; CI green.

**Phase 1 — Engine + World 1 levels 1–3**
- LevelRunner, ProgressStore, ConsoleRenderer, hints, XP, map.
- ✅ Fresh clone → level 1 fails with helpful messages → paste reference solution locally → passes,
  progress saved, level 2 unlocked. Engine unit tests for ProgressStore and LevelRegistry.

**Phase 2 — Worlds 1–3 complete (MVP, 15 levels, + optional bonus room 16)**
- ✅ All 15 levels pass `testReference`; every failure message reviewed for clarity; README lists topics per level
  and the ICT261 unit each one covers.
- ✅ Rooms 11 and 16 vary the surface of their assessed labs (§4); no lab text, scaffold or data copied.
- ✅ README has a short "Architecture" section mapping the engine onto Unit 1's layers: UI = `Renderer`/
  `ConsoleRenderer`, controller = `Game`, service = `LevelRunner`/`XpRules`, data = `ProgressStore`;
  MVC: model = `Level`/`Progress`, view = `Renderer`, controller = `Game`.
- Room 16 is built last, and only if rooms 1–15 are done with time to spare before finals.
- **MVP stops here until after 18 Dec.**

**Phase 3 — JavaFX UI (after finals)**
- JavaFX `Renderer`: clickable world map, story panel, "Run tests" button, results panel.
- ✅ Same engine, both UIs work; `gradlew.bat questGui` launches it.

**Phase 4 — Worlds 4–5 content**

---

## 6. Definition of done (MVP)

- Clean clone on Windows → `gradlew.bat quest` works with only a JDK installed.
- 15 levels (+ optional bonus room 16), all proven solvable by CI, none solved in `main`.
- README: install, how to play, how to add a level, screenshot/GIF of the terminal.
- Sammy has personally cleared World 1 before Phase 2 content is expanded (validates the difficulty curve).
