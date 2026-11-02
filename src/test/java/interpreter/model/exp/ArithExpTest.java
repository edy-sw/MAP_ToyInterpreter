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

class ArithExpTest {
    private IDictionary<String, IValue> symTable;

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void arithmeticOperations() throws MyException {
        assertEquals(new IntValue(5), new ArithExp(ArithOp.ADD, num(2), num(3)).eval(symTable));
        assertEquals(new IntValue(6), new ArithExp(ArithOp.SUB, num(10), num(4)).eval(symTable));
        assertEquals(new IntValue(15), new ArithExp(ArithOp.MUL, num(3), num(5)).eval(symTable));
        assertEquals(new IntValue(4), new ArithExp(ArithOp.DIV, num(8), num(2)).eval(symTable));
    }

    @Test
    void divisionByZeroThrows() {
        IExp div = new ArithExp(ArithOp.DIV, num(5), num(0));
        var ex = assertThrows(ExpressionException.class, () -> div.eval(symTable));
        assertEquals("division by zero", ex.getMessage());
    }

    @Test
    void nonIntegerOperandThrows() {
        IExp invalid = new ArithExp(ArithOp.ADD, new ValueExp(new BoolValue(true)), num(2));
        var ex = assertThrows(ExpressionException.class, () -> invalid.eval(symTable));
        assertEquals("first operand is not an integer", ex.getMessage());
    }

    @Test
    void undefinedVariableThrows() {
        IExp varExp = new VarExp("x");
        var ex = assertThrows(ExpressionException.class, () -> varExp.eval(symTable));
        assertEquals("variable 'x' is not defined", ex.getMessage());
    }
}