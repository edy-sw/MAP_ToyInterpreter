package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfStmtTest {
    @Test
    void nonBooleanConditionThrows() {
        var state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        var ifStmt = new IfStmt(new ValueExp(new IntValue(10)), new NopStmt(), new NopStmt());
        assertThrows(StatementException.class, () -> ifStmt.execute(state));
    }
}