package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignStmtTest {
    @Test
    void undeclaredVariableAssignmentThrows() {
        var state = PrgState.of(new NopStmt());
        var assign = new AssignStmt("x", new ValueExp(new IntValue(10)));
        assertThrows(StatementException.class, () -> assign.execute(state));
    }

    @Test
    void typeMismatchAssignmentThrows() {
        var state = PrgState.of(new NopStmt());
        state.getSymTable().put("x", new IntType().defaultValue()); // declared as int
        var assign = new AssignStmt("x", new ValueExp(new BoolValue(true))); // assigning bool
        assertThrows(StatementException.class, () -> assign.execute(state));
    }
}