package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    private IList<String> list;

    @BeforeEach
    void setUp() {
        list = new MyList<>();
    }

    @Test
    void addAndGet() throws ADTException {
        list.add("val1");
        list.add("val2");
        assertEquals(2, list.size());
        assertEquals("val1", list.get(0));
        assertEquals("val2", list.get(1));
    }

    @Test
    void indexOutOfBoundsThrows() {
        assertThrows(ADTException.class, () -> list.get(0));
    }
}