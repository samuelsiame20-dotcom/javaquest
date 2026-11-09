package quest.engine;

/**
 * The result of one test in a room's lock.
 *
 * @param name    the test's display name, e.g. "greets Sammy"
 * @param status  what happened
 * @param message the explanation shown to the player (empty when passed)
 */
public record TestOutcome(String name, Status status, String message) {

    public enum Status {
        /** The test passed. */
        PASSED,
        /** An assertion failed: the code runs but gives the wrong answer. */
        FAILED,
        /** The method still throws the TODO placeholder. */
        NOT_STARTED,
        /** The code ran past the 2-second limit (usually an infinite loop). */
        TIMED_OUT,
        /** The code threw an unexpected exception. */
        CRASHED
    }
}
