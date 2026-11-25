package interpreter.model.stmt;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.RelationalExp;
import interpreter.model.exp.RelOp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WhileStmtTest {

    @TempDir
    Path tempDir;

    private PrgState runProgram(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        IRepository repo = new Repository(state, tempDir.resolve("test.log").toString());
        Controller controller = new Controller(repo);
        controller.allStep();
        return state;
    }

    @Test
    void loopRunsZeroTimesWhenConditionInitiallyFalse() throws MyException {
        // int v; v = 0; while (v > 0) { print(v); v = v - 1; }
        IStmt program = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(0))),
                        new WhileStmt(
                                new RelationalExp(RelOp.GT, new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(
                                        new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp(ArithOp.SUB, new VarExp("v"), new ValueExp(new IntValue(1))))
                                )
                        )
                )
        );

        PrgState state = runProgram(program);
        assertEquals(List.of(), state.getOut().toList());
    }

    @Test
    void loopRunsOnceAndMultipleTimes() throws MyException {
        // int v; v = 1; while (v > 0) { print(v); v = v - 1; }
        IStmt onceProgram = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(1))),
                        new WhileStmt(
                                new RelationalExp(RelOp.GT, new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(
                                        new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp(ArithOp.SUB, new VarExp("v"), new ValueExp(new IntValue(1))))
                                )
                        )
                )
        );
        assertEquals(List.of(new IntValue(1)), runProgram(onceProgram).getOut().toList());

        // int v; v = 3; while (v > 0) { print(v); v = v - 1; }
        IStmt multiProgram = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(3))),
                        new WhileStmt(
                                new RelationalExp(RelOp.GT, new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(
                                        new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp(ArithOp.SUB, new VarExp("v"), new ValueExp(new IntValue(1))))
                                )
                        )
                )
        );
        assertEquals(
                List.of(new IntValue(3), new IntValue(2), new IntValue(1)),
                runProgram(multiProgram).getOut().toList()
        );
    }

    @Test
    void nonBooleanConditionThrowsStatementException() {
        PrgState state = PrgState.of(new NopStmt());
        IStmt loop = new WhileStmt(new ValueExp(new IntValue(1)), new NopStmt());
        assertThrows(StatementException.class, () -> loop.execute(state));
    }
}