package ua.kpi.comsys.dop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NotificationProcessorTest {

    private NotificationProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new NotificationProcessor();
    }

    @Test
    @DisplayName("TextMsg форматується за шаблоном TEXT")
    void processTextMsg() {
        String expected = """
                Notification: TEXT
                From: alice
                Message: Hello, world!
                """;

        assertEquals(expected, processor.process(new TextMsg("alice", "Hello, world!")));
    }

    @Test
    @DisplayName("ImageMsg форматується за шаблоном IMAGE")
    void processImageMsg() {
        String expected = """
                Notification: IMAGE
                From: bob
                URL: https://example.com/cat.png (512 KB)
                """;

        assertEquals(expected,
                processor.process(new ImageMsg("bob", "https://example.com/cat.png", 512)));
    }

    @Test
    @DisplayName("AudioMsg форматується за шаблоном AUDIO")
    void processAudioMsg() {
        String expected = """
                Notification: AUDIO
                From: carol
                Duration: 42 seconds
                """;

        assertEquals(expected, processor.process(new AudioMsg("carol", 42)));
    }

    @Test
    @DisplayName("Граничні допустимі значення (1 KB, 1 секунда)")
    void processMinimalPositiveValues() {
        assertEquals("""
                Notification: IMAGE
                From: dan
                URL: img (1 KB)
                """, processor.process(new ImageMsg("dan", "img", 1)));
        assertEquals("""
                Notification: AUDIO
                From: dan
                Duration: 1 seconds
                """, processor.process(new AudioMsg("dan", 1)));
    }

    @Test
    @DisplayName("null замість повідомлення -> IllegalArgumentException з точним текстом")
    void processNullThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> processor.process(null));

        assertEquals("Notification cannot be null", ex.getMessage());
    }
}
