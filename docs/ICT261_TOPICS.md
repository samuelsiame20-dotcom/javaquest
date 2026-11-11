# ICT261 course topics

Built from the 9 course files in `docs/course/` (lecturer: Mr E Nyirenda). Claude Code: this file
is filled in, so per CLAUDE.md rule 8 align levels to it. Read the matching file in
`docs/course/text/` before writing a level's story, tests or concept note, and reuse the course's
vocabulary and lecture examples (Student, Customer) where they fit. **Exception: assessed labs** (marks
importer, Lodge Billing). Rooms practising them keep the concepts but use their own story, data, IDs and
numbers (CLAUDE.md rule 9).

Assessment (Unit 1 slides): Labs 15% · Test 20% · Quiz 5% · **Final exam 60%**.

## Units covered so far

| Unit | Source (`docs/course/text/`) | Topics |
|---|---|---|
| 1 — Orientation & architecture | `u1_lecture1_orientation_architecture.txt` | Layered architecture (UI → controller → service → data → network), MVC, separation of concerns, package structure, Git + Gradle skeleton, UML class/sequence diagrams |
| 1 — OOP revision | `u1_oop_revision_20_examples.md` | Class/object, constructors, methods, params/returns, **overloading**, encapsulation, **static vs instance**, inheritance, `super()`, overriding, polymorphism (incl. in a loop), abstract classes, interfaces, multiple interfaces, **default/static interface methods**, abstract vs interface, **composition over inheritance**, Student Result System case study |
| Lab 1 | `lab1_javafx_install_first_app.txt` | JDK 21, IntelliJ, Git, Gradle, first JavaFX window |
| 2 — JavaFX GUI fundamentals | `u2_javafx_gui_fundamentals.md` | Stage/Scene/Node, scene graph, JavaFX Application Thread, layouts (BorderPane, GridPane, VBox/HBox, StackPane), events, validation, FXML + controller (`fx:id`, `@FXML`), thin controller, **properties & bindings**, **ObservableList**, TableView |
| 3 — Controls & data views | `u3_lecture3_controls_validation_tableview.md` | TextField/TextArea/ComboBox/Button, validation before save, ListView/TableView, model class → table row, column ↔ getter, alerts, delete confirmation, menus/toolbars, accessibility, errors that preserve user input, Controller vs DAO |
| Lab (Lecture 3) | `lab_gui_lodge_billing_screen.txt` | Lodge Billing screen. Rules: Standard K350 / Deluxe K550 / Family K750 per night; **5+ nights → 5% off the room charge**; extras not discounted; no tax; balance = total − paid. Check: Deluxe × 5 + K200 extras = K2812.50, paid K1000 → balance K1812.50 |
| 4 — Exception handling | `u4_exception_handling.md` | Throwable hierarchy, Error vs Exception, checked vs unchecked, try/catch, multi-catch ordering, finally, **try-with-resources**, throw vs throws, fail fast, custom exceptions, **exception translation**, user vs developer messages, **logging** (`java.util.logging`), anti-patterns (swallowing, stack traces to users, catching `Exception` everywhere), reading a stack trace |
| Lab 4 | `u4_lab_exception_handling.txt` | Marks importer: `BufferedReader` over `marks.txt`, field-count check, `NumberFormatException`, range 0..100 via `IllegalArgumentException`, `DuplicateStudentException extends RuntimeException`, skip-vs-stop decision, `FileNotFoundException` before `IOException`, finally message, Logger |
| 5 — Generics & collections | `u5_generics_collections.md` | Raw vs generic types, generic methods, diamond, **bounded types & multiple bounds**, generic interfaces (Repository pattern), **type erasure** + its limits, why `List<Student>` isn't `List<Person>`, **PECS / wildcards**, Collections Framework, choosing a structure by performance, **`equals()`/`hashCode()`**, **streams (map/filter/grouping/counting)**, JavaFX ObservableList, immutable snapshots |

Units 1 slides also promise later topics not in these files yet: embedded database (JDBC/JPA), REST APIs/JSON,
background threads (keeping the UI responsive), testing and packaging. Add their slides here when they arrive.

## Gaps vs `docs/PLAN.md` §4 (Claude Code: propose changes, don't apply them without Sammy's OK)

> **Applied 2026-11-11** (Sammy approved): all six are now in PLAN.md §4, §5 and §6, with two adjustments:
> rooms 11 and 16 use their own surface instead of the lab's exact records and prices (gap 1, gap 5), and
> the README architecture note (gap 6) is a Phase 2 acceptance item.

1. **Unit 4 lab fits Level 11 almost exactly.** Make "Cursed Gold" the marks importer: same four
   records, same rules, `DuplicateStudentException`, try-with-resources, and a test for the
   skip-vs-stop choice.
2. **Streams are in Unit 5, so they're exam material.** PLAN puts them in World 4 (post-MVP). Suggest folding a
   small `filter/map/groupingBy` task into Level 13 or the World 3 boss.
3. **Missing from World 2:** method overloading, static vs instance, interface default/static methods,
   composition. Each fits as an extra test in Levels 6–9, not new levels.
4. **Missing from World 3:** `equals()/hashCode()` with `HashSet`, PECS wildcards, type erasure.
   `equals/hashCode` fits Level 13; one PECS method (`copyAll(List<? extends T>, List<? super T>)`) fits Level 12.
5. **JavaFX is 2 of the 5 units, but PLAN defers it to after finals.** The exam risk is real. A cheap fix
   inside the MVP: `javafx.base` (properties, bindings, `ObservableList`, `FXCollections`) runs in plain JUnit
   with no window and no display. One optional "World 3.5" level could cover a bound total property and an
   ObservableList listener, using the Lodge Billing rules above. Layouts, FXML and TableView stay in World 5.
6. **Unit 1 architecture:** the engine already follows it (`Renderer` interface = UI layer split). Say this in the
   README so it doubles as an exam example of layered design / MVC.
