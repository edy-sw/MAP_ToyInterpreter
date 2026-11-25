package interpreter.model.stmt;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ReadHeapExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapOperationsTest {
    private PrgState state;

    @BeforeEach
    void setup() throws MyException {
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("v", new RefType(new IntType())).execute(state);
    }

    // --- NewStmt (S11) ---
    @Test
    void newAllocatesAtAddressOneAndUpdatesVariable() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }

    @Test
    void newThrowsOnUndeclaredNonRefOrWrongType() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);

        assertThrows(StatementException.class,
                () -> new NewStmt("missing", new ValueExp(new IntValue(1))).execute(state));
        assertThrows(StatementException.class,
                () -> new NewStmt("x", new ValueExp(new IntValue(1))).execute(state));
        assertThrows(StatementException.class,
                () -> new NewStmt("v", new ValueExp(new BoolValue(true))).execute(state));
    }

    // --- ReadHeapExp (E6) ---
    @Test
    void readHeapReturnsValueAndThrowsOnNonRefOrMissingAddress() throws MyException {
        // v is currently (0, int), address 0 is not in heap
        assertThrows(ExpressionException.class,
                () -> new ReadHeapExp(new VarExp("v")).eval(state.getSymTable(), state.getHeap()));
        assertThrows(ExpressionException.class,
                () -> new ReadHeapExp(new ValueExp(new IntValue(5))).eval(state.getSymTable(), state.getHeap()));

        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        assertEquals(new IntValue(20),
                new ReadHeapExp(new VarExp("v")).eval(state.getSymTable(), state.getHeap()));
    }

    // --- WriteHeapStmt (S12) ---
    @Test
    void writeHeapUpdatesLocationAndThrowsOnErrors() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);

        // v has address 0 (not allocated yet)
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("v", new ValueExp(new IntValue(30))).execute(state));
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("missing", new ValueExp(new IntValue(30))).execute(state));
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("x", new ValueExp(new IntValue(30))).execute(state));

        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("v", new ValueExp(new BoolValue(false))).execute(state));

        new WriteHeapStmt("v", new ValueExp(new IntValue(30))).execute(state);
        assertEquals(new IntValue(30), state.getHeap().get(1));
    }
}