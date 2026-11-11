package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;
import interpreter.model.exp.RelOp;

import java.util.Scanner;

public class View {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Please enter your option: ");
            String option = scanner.nextLine().trim();

            if (option.equals("0")) {
                System.out.println("Exiting interpreter.");
                break;
            }

            switch (option) {
                case "1" -> runProgram(Examples.EX1);
                case "2" -> runProgram(Examples.EX2);
                case "3" -> runProgram(Examples.EX3);
                default -> System.out.println("Invalid option. Please choose 1, 2, 3, or 0.");
            }
        }
    }

    private void printMenu() {
        System.out.println("1. Run Example 1: " + Examples.EX1);
        System.out.println("2. Run Example 2: " + Examples.EX2);
        System.out.println("3. Run Example 3: " + Examples.EX3);
        System.out.println("0. Exit");
    }

    private void runProgram(IStmt program) {
        PrgState state = PrgState.of(program);
        Controller controller = new Controller(new Repository(state, "log.txt"), true);

        try {
            controller.allStep();
            System.out.println("Program finished successfully.");
            System.out.println("Final Output: " + state.getOut());
        } catch (MyException e) {
            System.err.println("Execution Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected System Error: " + e.getMessage());
        }
    }
}