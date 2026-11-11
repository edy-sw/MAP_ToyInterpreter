package interpreter.controller;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @TempDir
    Path tempDir;

    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        String logPath = tempDir.resolve("test-log.txt").toString();
        new Controller(new Repository(state, logPath), false).allStep();
        return state;
    }

    @Test
    void example1PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX1);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void example2PrintsEighteen() throws MyException {
        PrgState state = run(Examples.EX2);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    @Test
    void example3PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX3);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void example4ReadsNumbersCorrectly() throws Exception {
        Path testFile = Path.of("test.in");
        Files.writeString(testFile, "15\n50\n");

        try {
            PrgState state = run(Examples.EX4);
            assertTrue(state.getExeStack().isEmpty());
            assertEquals(new IntValue(15), state.getOut().get(0));
            assertEquals(new IntValue(50), state.getOut().get(1));
        } finally {
            Files.deleteIfExists(testFile);
        }
    }

    @Test
    void example5PrintsFive() throws MyException {
        PrgState state = run(Examples.EX5);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(5), state.getOut().get(0));
    }

    @Test
    void oneStepOnEmptyStackThrows() throws ADTException {
        PrgState emptyState = PrgState.of(new NopStmt());
        emptyState.getExeStack().pop(); // remove NopStmt, making stack empty

        String logPath = tempDir.resolve("empty-log.txt").toString();
        Controller controller = new Controller(new Repository(emptyState, logPath), false);
        assertThrows(StatementException.class, () -> controller.oneStep(emptyState));
    }

    @Test
    void logFileContainsTheFourSectionsInOrder() throws Exception {
        run(new NopStmt());
        String log = Files.readString(tempDir.resolve("test-log.txt"));
        int a = log.indexOf("ExeStack:");
        int b = log.indexOf("SymTable:");
        int c = log.indexOf("Out:");
        int d = log.indexOf("FileTable:");
        assertTrue(0 <= a && a < b && b < c && c < d);
    }
}