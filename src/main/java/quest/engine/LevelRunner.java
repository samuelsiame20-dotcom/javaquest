package quest.engine;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import quest.engine.TestOutcome.Status;

/**
 * Tries a room's lock: runs its JUnit test class in this JVM via the JUnit Platform Launcher
 * and turns each result into a {@link TestOutcome} the player can act on.
 */
public final class LevelRunner {

    public RunResult run(Level level) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(level.testClass()))
                .build();
        Collector collector = new Collector();
        try {
            LauncherFactory.create().execute(request, collector);
        } catch (RuntimeException e) {
            // e.g. the test class can't be found; an empty result tells the caller something is wrong.
            return new RunResult(List.of());
        }
        return new RunResult(collector.outcomes);
    }

    /** Collects one outcome per test, plus any test class that failed before its tests could run. */
    private static final class Collector implements TestExecutionListener {
        private final List<TestOutcome> outcomes = new ArrayList<>();

        @Override
        public void executionFinished(TestIdentifier id, TestExecutionResult result) {
            boolean failed = result.getStatus() != TestExecutionResult.Status.SUCCESSFUL;
            if (id.isTest() || failed && id.isContainer() && id.getSource().isPresent()) {
                outcomes.add(classify(id.getDisplayName(), failed ? result.getThrowable().orElse(null) : null));
            }
        }
    }

    /**
     * Turns what a test threw into a status and a player-friendly message.
     *
     * @param name   the test's display name
     * @param thrown what the test threw, or {@code null} if it passed
     */
    static TestOutcome classify(String name, Throwable thrown) {
        if (thrown == null) {
            return new TestOutcome(name, Status.PASSED, "");
        }
        for (Throwable t = thrown; t != null; t = t.getCause()) {
            if (t instanceof UnsupportedOperationException && "TODO".equals(t.getMessage())) {
                return new TestOutcome(name, Status.NOT_STARTED,
                        "Not written yet: this method still throws the TODO placeholder" + where(t) + ".");
            }
            if (t instanceof TimeoutException) {
                return new TestOutcome(name, Status.TIMED_OUT,
                        "Took longer than 2 seconds. Is there a loop whose condition never becomes false?");
            }
        }
        if (thrown instanceof AssertionError) {
            String message = thrown.getMessage();
            return new TestOutcome(name, Status.FAILED, message == null ? "Assertion failed." : message);
        }
        String message = thrown.getMessage();
        return new TestOutcome(name, Status.CRASHED, "Your code threw " + thrown.getClass().getSimpleName()
                + (message == null ? "" : ": " + message) + where(thrown) + ".");
    }

    /** Points at the line in the player's level file where the exception started, if it did. */
    private static String where(Throwable t) {
        for (StackTraceElement frame : t.getStackTrace()) {
            String cls = frame.getClassName();
            if (cls.startsWith("quest.levels.") && !cls.endsWith("Test") && frame.getLineNumber() > 0) {
                return " (at " + frame.getFileName() + " line " + frame.getLineNumber() + ")";
            }
        }
        return "";
    }
}
