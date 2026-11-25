package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerA4Test {

    @TempDir
    Path tempDir;

    private List<IValue> runAndGetOut(IStmt program, String logName) throws MyException {
        PrgState state = PrgState.of(program);
        Controller controller = new Controller(new Repository(state, tempDir.resolve(logName).toString()));
        controller.allStep();
        return state.getOut().toList();
    }

    @Test
    void ex6PrintsAllocatedReferences() throws MyException {
        assertEquals(
                List.of(new RefValue(1, new IntType()), new RefValue(2, new RefType(new IntType()))),
                runAndGetOut(Examples.EX6, "log6.txt")
        );
    }

    @Test
    void ex7DereferencesNestedHeapValues() throws MyException {
        assertEquals(
                List.of(new IntValue(20), new IntValue(25)),
                runAndGetOut(Examples.EX7, "log7.txt")
        );
    }

    @Test
    void ex8WritesAndReadsUpdatedHeapValue() throws MyException {
        assertEquals(
                List.of(new IntValue(20), new IntValue(35)),
                runAndGetOut(Examples.EX8, "log8.txt")
        );
    }

    @Test
    void ex9PreservesIndirectlyReachableAddressWithSafeGC() throws MyException {
        assertEquals(
                List.of(new IntValue(20)),
                runAndGetOut(Examples.EX9, "log9.txt")
        );
    }

    @Test
    void ex10CountsDownWithWhileLoop() throws MyException {
        assertEquals(
                List.of(new IntValue(4), new IntValue(3), new IntValue(2), new IntValue(1), new IntValue(0)),
                runAndGetOut(Examples.EX10, "log10.txt")
        );
    }
}