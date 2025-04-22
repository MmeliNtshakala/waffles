package za.co.wethinkcode.toyrobot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReplayCommand extends Command {
    private static final List<Command> history = new ArrayList<>();

    public ReplayCommand(String argument) {
        super("replay", argument);
    }

    public static void addCommandToHistory(Command command) {

        if (!(command instanceof ReplayCommand)) {
            history.add(command);
        }
    }

    public static void clearHistory() {
        history.clear();
    }

    @Override
    public boolean execute(Robot target) {
        String argument = getArgument();
        List<Command> commandsToReplay = new ArrayList<>(history);

        if (!argument.isEmpty()) {
            if (argument.contains("-")) {

                String[] range = argument.split("-");
                try {
                    int start = commandsToReplay.size() - Integer.parseInt(range[0]);
                    int end = commandsToReplay.size() - Integer.parseInt(range[1]);
                    commandsToReplay = commandsToReplay.subList(end, start);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    target.setStatus("Invalid replay range.");
                    return true;
                }
            } else if (argument.equalsIgnoreCase("reversed")) {
                Collections.reverse(commandsToReplay);
            } else {
                try {
                    int lastN = Integer.parseInt(argument);
                    commandsToReplay = commandsToReplay.subList(commandsToReplay.size() - lastN, commandsToReplay.size());
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    target.setStatus("Invalid replay argument.");
                    return true;
                }
            }
        }

        for (Command command : commandsToReplay) {
            command.execute(target);
        }

        target.setStatus("replayed " + commandsToReplay.size() + " commands.");
        return true;
    }
}