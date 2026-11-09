package quest.engine;

import java.util.List;

/** Every test outcome from one try of a room's lock. */
public record RunResult(List<TestOutcome> outcomes) {

    public RunResult {
        outcomes = List.copyOf(outcomes);
    }

    public int total() {
        return outcomes.size();
    }

    public int passed() {
        return (int) outcomes.stream().filter(o -> o.status() == TestOutcome.Status.PASSED).count();
    }

    /** True when there were tests and every one passed: the door opens. */
    public boolean allPassed() {
        return total() > 0 && passed() == total();
    }

    /** True when the player hasn't touched the room yet: every test hit the TODO placeholder. */
    public boolean notStarted() {
        return total() > 0
                && outcomes.stream().allMatch(o -> o.status() == TestOutcome.Status.NOT_STARTED);
    }
}
