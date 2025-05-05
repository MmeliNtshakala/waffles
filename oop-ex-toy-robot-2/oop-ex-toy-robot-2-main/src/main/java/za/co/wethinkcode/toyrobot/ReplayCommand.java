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
        boolean reverse = false;

        if (!argument.isEmpty()) {
            if (argument.contains("-")) {
                String[] range = argument.split("-");
                try {
                    int start = commandsToReplay.size() - Integer.parseInt(range[1]);
                    int end = commandsToReplay.size() - Integer.parseInt(range[0]) + 1;
                    commandsToReplay = new ArrayList<>(commandsToReplay.subList(start, end));
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    target.setStatus("Invalid replay range.");
                    return true;
                }
            } else if (argument.equalsIgnoreCase("reversed")) {
                reverse = true;
            } else {
                try {
                    int lastN = Integer.parseInt(argument);
                    commandsToReplay = new ArrayList<>(commandsToReplay.subList(
                            commandsToReplay.size() - lastN, commandsToReplay.size()));
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    target.setStatus("Invalid replay argument.");
                    return true;
                }
            }
        }

        if (reverse) {
            Collections.reverse(commandsToReplay);
        }

        int replayCount = 0;
        for (Command command : commandsToReplay) {
            command.execute(target);
            replayCount++;
        }

        target.setStatus("Replayed " + replayCount + " commands.");
        return true;
    }
}
