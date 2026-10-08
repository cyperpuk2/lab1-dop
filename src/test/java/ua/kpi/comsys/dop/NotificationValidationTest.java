package ua.kpi.comsys.dop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NotificationValidationTest {

    @Nested
    @DisplayName("TextMsg")
    class TextMsgValidation {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "\t", "\n"})
        void invalidSender(String sender) {
            assertThrows(IllegalArgumentException.class, () -> new TextMsg(sender, "hi"));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "\t", "\n"})
        void invalidText(String text) {
            assertThrows(IllegalArgumentException.class, () -> new TextMsg("alice", text));
        }

        @Test
        void validValuesAreStored() {
            TextMsg msg = new TextMsg("alice", "hi");
            assertEquals("alice", msg.sender());
            assertEquals("hi", msg.text());
        }
    }

    @Nested
    @DisplayName("ImageMsg")
    class ImageMsgValidation {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "\t"})
        void invalidSender(String sender) {
            assertThrows(IllegalArgumentException.class, () -> new ImageMsg(sender, "url", 10));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "\t"})
        void invalidUrl(String url) {
            assertThrows(IllegalArgumentException.class, () -> new ImageMsg("bob", url, 10));
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -1, Integer.MIN_VALUE})
        void nonPositiveSize(int sizeKb) {
            assertThrows(IllegalArgumentException.class, () -> new ImageMsg("bob", "url", sizeKb));
        }

        @Test
        void validValuesAreStored() {
            ImageMsg msg = assertDoesNotThrow(() -> new ImageMsg("bob", "url", 1));
            assertEquals("bob", msg.sender());
            assertEquals("url", msg.url());
            assertEquals(1, msg.sizeKb());
        }
    }

    @Nested
    @DisplayName("AudioMsg")
    class AudioMsgValidation {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "\t"})
        void invalidSender(String sender) {
            assertThrows(IllegalArgumentException.class, () -> new AudioMsg(sender, 5));
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -1, Integer.MIN_VALUE})
        void nonPositiveDuration(int duration) {
            assertThrows(IllegalArgumentException.class, () -> new AudioMsg("carol", duration));
        }

        @Test
        void validValuesAreStored() {
            AudioMsg msg = assertDoesNotThrow(() -> new AudioMsg("carol", 1));
            assertEquals("carol", msg.sender());
            assertEquals(1, msg.durationSeconds());
        }
    }

    @Test
    @DisplayName("Records є незмінними value-об'єктами (equals/hashCode)")
    void recordsHaveValueSemantics() {
        assertEquals(new TextMsg("a", "b"), new TextMsg("a", "b"));
        assertEquals(new ImageMsg("a", "u", 2).hashCode(), new ImageMsg("a", "u", 2).hashCode());
        assertEquals(new AudioMsg("a", 3), new AudioMsg("a", 3));
    }
}
