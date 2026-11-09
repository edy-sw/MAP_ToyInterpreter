package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarDeclStmtTest {
    @Test
    void duplicateVariableDeclarationThrows() throws Exception {
        var state = PrgState.of(new NopStmt());
        var decl = new VarDeclStmt("a", new IntType());
        decl.execute(state);

        assertThrows(StatementException.class, () -> decl.execute(state));
    }
}