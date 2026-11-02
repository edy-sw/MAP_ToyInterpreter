package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicExpTest {
    private IDictionary<String, IValue> symTable;

    private static IExp boolExp(boolean b) {
        return new ValueExp(new BoolValue(b));
    }

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void logicalOperations() throws MyException {
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.AND, boolExp(true), boolExp(true)).eval(symTable));
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.AND, boolExp(true), boolExp(false)).eval(symTable));
        assertEquals(new BoolValue(true), new LogicExp(LogicOp.OR, boolExp(false), boolExp(true)).eval(symTable));
        assertEquals(new BoolValue(false), new LogicExp(LogicOp.OR, boolExp(false), boolExp(false)).eval(symTable));
    }

    @Test
    void nonBooleanOperandThrows() {
        IExp invalid = new LogicExp(LogicOp.AND, new ValueExp(new IntValue(1)), boolExp(true));
        var ex = assertThrows(ExpressionException.class, () -> invalid.eval(symTable));
        assertEquals("first operand is not a boolean", ex.getMessage());
    }
}