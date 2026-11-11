package interpreter;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.repository.Repository;
import interpreter.view.ExitCommand;
import interpreter.view.RunExampleCommand;
import interpreter.view.TextMenu;
import interpreter.view.Examples;

public class Main {
    public static void main(String[] args) {
        var menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        var c1 = new Controller(new Repository(PrgState.of(Examples.EX1), "log1.txt"), false);
        menu.addCommand(new RunExampleCommand("1", Examples.EX1.toString(), c1));

        var c2 = new Controller(new Repository(PrgState.of(Examples.EX2), "log2.txt"), false);
        menu.addCommand(new RunExampleCommand("2", Examples.EX2.toString(), c2));

        var c3 = new Controller(new Repository(PrgState.of(Examples.EX3), "log3.txt"), false);
        menu.addCommand(new RunExampleCommand("3", Examples.EX3.toString(), c3));

        var c4 = new Controller(new Repository(PrgState.of(Examples.EX4), "log4.txt"), false);
        menu.addCommand(new RunExampleCommand("4", Examples.EX4.toString(), c4));

        var c5 = new Controller(new Repository(PrgState.of(Examples.EX5), "log5.txt"), false);
        menu.addCommand(new RunExampleCommand("5", Examples.EX5.toString(), c5));

        menu.show();
    }
}