# JavaQuest — instructions for Claude Code

JavaQuest is a terminal dungeon game that teaches Java. The PLAYER (Sammy) writes the code;
JUnit tests are the door locks. Full spec: `docs/PLAN.md`. Read it before any work.

## Who you're working with
- Sammy: Data Science undergrad, Windows + Git, rebuilding Java skills for ICT261 (Advanced Java,
  Gradle required by the course). Finals 7–18 Dec 2026 — keep scope tight.
- Explain what you build in short notes so he can defend the design in an interview.

## Hard rules
1. **Never implement player solutions** in `src/main/java/quest/levels/**`. Those files are stubs
   with TODOs. If Sammy asks you to fix or finish a level, give a hint (escalating: nudge → concept →
   pseudo-code), never the code. This is the whole point of the project.
2. Every stub **must compile** as shipped so one unfinished level never breaks the build. Every method
   body is exactly `throw new UnsupportedOperationException("TODO");`. The engine relies on that
   placeholder to tell "not started" (show the room's story) from "wrong" (show the failures).
3. Reference solutions live only in `src/reference/java/**` and exist to prove the tests are solvable.
4. Test failure messages are the teaching surface. Every assertion message says what was expected and
   gives a nudge, e.g. `"takeDamage(150) should clamp HP at 0, not go negative — check your if."`
5. Every level test class uses `@Timeout(2)` so infinite loops fail cleanly instead of hanging.
6. Windows-first: document commands as `gradlew.bat ...`. No bash-only scripts.
7. No network access at runtime, no external services, minimal dependencies (JUnit only for MVP,
   plus `org.openjfx:javafx-base` for optional bonus room 16 alone; no other JavaFX modules before Phase 3).
8. If `docs/ICT261_TOPICS.md` has been filled in, align level topics to it; otherwise follow PLAN.md §4.
9. Rooms based on an assessed ICT261 lab keep its concepts but use their own story, data, IDs and numbers.
   Never copy lab text, scaffolds or test data (PLAN.md §4): the repo, including `src/reference`, is public.

## Workflow
- Work **one phase at a time** (PLAN.md §5). Stop at the end of each phase, show the acceptance
  checklist with pass/fail, and wait for Sammy before starting the next phase.
- Git: init the repo in Phase 0. One branch per phase (`phase-0-skeleton`, `phase-1-engine`, ...),
  small commits with clear messages, merge to `main` when the phase is accepted.
- Before writing a big chunk of code, give a 5-line design note: components, data flow, why.
- Run `gradlew.bat testReference` (or `./gradlew testReference` if you're on a non-Windows shell)
  before declaring any phase done.
- Verify current stable versions of Gradle, JUnit 5 and the JavaFX plugin before pinning them.

## Commands (once Phase 0 exists)
- `gradlew.bat quest` — play / test the current level
- `gradlew.bat quest --args="--hint"` / `--args="--map"` / `--args="--level 5"`
- `gradlew.bat testReference` — prove every level is solvable (CI runs this)
