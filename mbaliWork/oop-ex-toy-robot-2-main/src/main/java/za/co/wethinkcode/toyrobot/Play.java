package za.co.wethinkcode.toyrobot;

import java.util.Scanner;

public class Play {
    static Scanner scanner;

    public static void main(String[] args) {
        Command.emptyCommands();
        scanner = new Scanner(System.in);
        Robot robot;

        String name = getInput("What do you want to name your robot?");
        robot = new Robot(name);
        System.out.println("Hello Kiddo!");

        System.out.println(robot.toString());

        Command command;
        boolean shouldContinue = true;
        do {
            String instruction = getInput(robot.getName() + "> What must I do next?").strip().toLowerCase();
            try {
                command = Command.create(instruction);
                shouldContinue = robot.handleCommand(command);
            } catch (IllegalArgumentException e) {
                robot.setStatus("Sorry, I did not understand '" + instruction + "'.");
            }
            if(!instruction.startsWith("sprint") && !instruction.contains("replay")){
                System.out.println(robot);
            }
            if(instruction.contains("replay")){
                System.out.println(ReplayCommand.commandString);
            }
        } while (shouldContinue);

    }

    private static String getInput(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();

        while (input.isBlank()) {
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }
}
