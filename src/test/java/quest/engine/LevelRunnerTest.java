package quest.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.Test;
import quest.engine.TestOutcome.Status;

/** Checks how test results are translated for the player. */
class LevelRunnerTest {

    @Test
    void passedTestHasNoMessage() {
        TestOutcome outcome = LevelRunner.classify("t", null);

        assertEquals(Status.PASSED, outcome.status());
        assertEquals("", outcome.message());
    }

    @Test
    void todoPlaceholderMeansNotStarted() {
        TestOutcome outcome = LevelRunner.classify("t", new UnsupportedOperationException("TODO"));

        assertEquals(Status.NOT_STARTED, outcome.status());
    }

    @Test
    void otherUnsupportedOperationIsACrash() {
        TestOutcome outcome = LevelRunner.classify("t", new UnsupportedOperationException("nope"));

        assertEquals(Status.CRASHED, outcome.status());
    }

    @Test
    void timeoutIsReportedAsALoopProblem() {
        TestOutcome outcome = LevelRunner.classify("t", new TimeoutException("t() timed out after 2 seconds"));

        assertEquals(Status.TIMED_OUT, outcome.status());
        assertTrue(outcome.message().contains("loop"));
    }

    @Test
    void assertionFailureKeepsTheNudgeMessage() {
        TestOutcome outcome = LevelRunner.classify("t", new AssertionError("toll(12) should be 10"));

        assertEquals(Status.FAILED, outcome.status());
        assertEquals("toll(12) should be 10", outcome.message());
    }

    @Test
    void unexpectedExceptionNamesTheException() {
        TestOutcome outcome = LevelRunner.classify("t", new ArrayIndexOutOfBoundsException("Index 3 out of bounds"));

        assertEquals(Status.CRASHED, outcome.status());
        assertTrue(outcome.message().contains("ArrayIndexOutOfBoundsException"), outcome.message());
        assertTrue(outcome.message().contains("Index 3 out of bounds"), outcome.message());
    }

    @Test
    void runResultKnowsNotStartedFromAllPassed() {
        TestOutcome todo = new TestOutcome("a", Status.NOT_STARTED, "");
        TestOutcome pass = new TestOutcome("b", Status.PASSED, "");

        assertTrue(new RunResult(java.util.List.of(todo, todo)).notStarted());
        assertTrue(new RunResult(java.util.List.of(pass, pass)).allPassed());
        assertEquals(1, new RunResult(java.util.List.of(todo, pass)).passed());
    }
}
