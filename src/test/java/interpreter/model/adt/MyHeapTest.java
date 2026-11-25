package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyHeapTest {
    private MyHeap heap;

    @BeforeEach
    void setup() {
        heap = new MyHeap();
    }

    @Test
    void allocateAssignsSequentialAddressesStartingAtOne() throws ADTException {
        int a1 = heap.allocate(new IntValue(10));
        int a2 = heap.allocate(new IntValue(20));
        int a3 = heap.allocate(new IntValue(30));

        assertEquals(1, a1);
        assertEquals(2, a2);
        assertEquals(3, a3);
        assertEquals(new IntValue(20), heap.get(2));
    }

    @Test
    void getAndUpdateMissingAddressThrowADTException() {
        assertThrows(ADTException.class, () -> heap.get(0));
        assertThrows(ADTException.class, () -> heap.get(1));
        assertThrows(ADTException.class, () -> heap.update(1, new IntValue(99)));
    }

    @Test
    void updateAndSetContentModifyHeapState() throws ADTException {
        int addr = heap.allocate(new IntValue(10));
        heap.update(addr, new IntValue(42));
        assertEquals(new IntValue(42), heap.get(addr));

        heap.setContent(Map.of(5, new IntValue(100)));
        assertFalse(heap.isDefined(addr));
        assertEquals(new IntValue(100), heap.get(5));
    }
}