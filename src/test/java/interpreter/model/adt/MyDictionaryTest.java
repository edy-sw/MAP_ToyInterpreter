package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    private IDictionary<String, Integer> dict;

    @BeforeEach
    void setUp() {
        dict = new MyDictionary<>();
    }

    @Test
    void putAndLookup() throws ADTException {
        dict.put("a", 100);
        assertTrue(dict.isDefined("a"));
        assertEquals(100, dict.lookup("a"));
    }

    @Test
    void updateExistingKey() throws ADTException {
        dict.put("a", 100);
        dict.update("a", 200);
        assertEquals(200, dict.lookup("a"));
    }

    @Test
    void lookupMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.lookup("nonexistent"));
    }

    @Test
    void updateMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.update("nonexistent", 50));
    }
}