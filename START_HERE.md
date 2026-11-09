# START HERE

## What you need installed (Windows)
1. **JDK 21** (Temurin from adoptium.net is fine). Check: `java -version`
2. **Git**. Check: `git --version`
3. **Claude Code**. Check: `claude --version`
4. An editor for playing: IntelliJ IDEA Community or VS Code with the Java extension.

You do NOT need to install Gradle — Claude Code will add the Gradle wrapper (`gradlew.bat`).

## How to kick it off
Open a terminal in this folder (`Desktop\JavaQuest`) and run `claude`, then paste the prompts
below **one at a time**. Review each phase before moving on.

### Prompt 1 — Phase 0 (skeleton)
```
Read CLAUDE.md and docs/PLAN.md fully. Then do Phase 0 only: Gradle wrapper project,
JUnit 5, the `reference` source set, `testReference` and `quest` tasks, GitHub Actions CI,
README and .gitignore. Init git on branch phase-0-skeleton. Give me a 5-line design note
before you write code. Stop when Phase 0's acceptance checklist passes and show it to me.
```

### Prompt 2 — Phase 1 (engine + first 3 levels)
```
Phase 0 is accepted — merge it. Do Phase 1 from docs/PLAN.md: engine (LevelRunner,
ProgressStore, ConsoleRenderer, hints, XP, map) and World 1 levels 1–3 as stubs + tests +
reference solutions. Do NOT solve the stubs. Stop at the acceptance checklist.
```

### Then: play levels 1–3 yourself
Before asking for Phase 2, clear levels 1–3. If something's too easy/hard or a failure
message confused you, tell Claude Code — that's the most valuable feedback the game gets.

### Prompt 3 — Phase 2 (MVP, 15 levels)
```
Phase 1 is accepted — merge it. Here's my feedback from playing levels 1–3: <your notes>.
Do Phase 2: complete Worlds 1–3 (15 levels). Stubs only in main, all tests pass in
testReference. Stop at the acceptance checklist.
```

**Stop after Phase 2 until finals are over (18 Dec).** JavaFX (Phase 3) comes after.

## Optional but recommended
Paste your ICT261 course outline into `docs/ICT261_TOPICS.md` before Prompt 2, so the levels
match what's actually on your exam.

## If you get stuck on a level
`gradlew.bat quest --args="--hint"` first. If you ask Claude Code, it will give hints, not
answers — that's on purpose (see CLAUDE.md rule 1).
