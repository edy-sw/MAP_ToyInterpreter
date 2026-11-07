package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {

    private MyDictionary<String, interpreter.model.value.IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void testAllOperatorsTrueAndFalse() throws MyException {
        IExp five = new ValueExp(new IntValue(5));
        IExp seven = new ValueExp(new IntValue(7));

        // LT (<)
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LT, five, seven).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.LT, seven, five).eval(symTable));

        // LE (<=)
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LE, five, seven).eval(symTable));
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.LE, five, five).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.LE, seven, five).eval(symTable));

        // EQ (==)
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.EQ, five, five).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.EQ, five, seven).eval(symTable));

        // NE (!=)
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.NE, five, seven).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.NE, five, five).eval(symTable));

        // GT (>)
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GT, seven, five).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GT, five, seven).eval(symTable));

        // GE (>=)
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GE, seven, five).eval(symTable));
        assertEquals(new BoolValue(true), new RelationalExp(RelOp.GE, five, five).eval(symTable));
        assertEquals(new BoolValue(false), new RelationalExp(RelOp.GE, five, seven).eval(symTable));
    }

    @Test
    void testNonIntegerFirstOperandThrows() {
        IExp boolExp = new ValueExp(new BoolValue(true));
        IExp intExp = new ValueExp(new IntValue(5));

        var relExp = new RelationalExp(RelOp.LT, boolExp, intExp);
        ExpressionException ex = assertThrows(ExpressionException.class, () -> relExp.eval(symTable));
        assertTrue(ex.getMessage().contains("first operand is not an integer"));
    }

    @Test
    void testNonIntegerSecondOperandThrows() {
        IExp intExp = new ValueExp(new IntValue(5));
        IExp boolExp = new ValueExp(new BoolValue(false));

        var relExp = new RelationalExp(RelOp.GT, intExp, boolExp);
        ExpressionException ex = assertThrows(ExpressionException.class, () -> relExp.eval(symTable));
        assertTrue(ex.getMessage().contains("second operand is not an integer"));
    }

    @Test
    void testToStringFormatting() {
        IExp exp = new RelationalExp(RelOp.LE, new VarExp("a"), new ValueExp(new IntValue(10)));
        assertEquals("(a <= 10)", exp.toString());
    }
}