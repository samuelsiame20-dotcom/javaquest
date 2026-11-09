package quest.levels.w0;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/** The lock for Room 0. Each message explains what was expected and gives a nudge. */
@Timeout(2)
@DisplayName("Room 0 - The Doorbell")
class Level00_DoorbellTest {

    @Test
    @DisplayName("greets Sammy")
    void greetsSammy() {
        assertEquals("Hello, Sammy!", Level00_Doorbell.greet("Sammy"),
                "greet(\"Sammy\") should return \"Hello, Sammy!\" - check the comma, the space and the '!'.");
    }

    @Test
    @DisplayName("greets any traveller, not just Sammy")
    void greetsAnyName() {
        assertEquals("Hello, Ada!", Level00_Doorbell.greet("Ada"),
                "greet(\"Ada\") should return \"Hello, Ada!\" - don't hard-code a name, use the name parameter.");
    }
}
