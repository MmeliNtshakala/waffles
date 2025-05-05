package za.co.wethinkcode.toyrobot;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help");
    }

    @Override
    public boolean execute(Robot target) {
        target.setStatus(
                "I can understand these commands:\n" +
                        "OFF     - Shut down robot\n" +
                        "HELP    - Provide information about commands\n" +
                        "FORWARD - Move forward by specified number of steps, e.g. 'FORWARD 10'");

        return true;
    }
}

