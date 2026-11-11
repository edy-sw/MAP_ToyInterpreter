package interpreter.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String, Command> commands = new HashMap<>();

    public void addCommand(Command c) {
        commands.put(c.getKey(), c);
    }

    private void printMenu() {
        System.out.println("\n================= MENU =================");
        for (Command c : commands.values()) {
            String line = String.format("%4s : %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
        System.out.println("========================================");
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Input option: ");
            String key = scanner.nextLine().trim();
            Command command = commands.get(key);
            if (command == null) {
                System.out.println("Invalid option! Try again.");
                continue;
            }
            command.execute();
        }
    }
}