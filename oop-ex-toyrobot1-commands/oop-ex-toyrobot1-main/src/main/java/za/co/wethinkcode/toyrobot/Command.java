package za.co.wethinkcode.toyrobot;
import java.util.HashMap;
import java.util.Map;

public interface Command {
    boolean execute(Robot robot);

    String getName();

    class Factory {
        private static final Map<String, Command> COMMAND_MAP = new HashMap<>();

        static {
            COMMAND_MAP.put("off", new OffCommand());
            COMMAND_MAP.put("help", new HelpCommand());
        }

        public static  Command getCommand(String commandName, String[] args) {
            Command command = COMMAND_MAP.get(commandName.toLowerCase());
            if ("forward".equals(commandName.toLowerCase()) && args.length > 1) {
                int steps = Integer.parseInt(args[1]);
                return new ForwardCommand(steps);
            }
            return command;
        }
    }

    class  mOffCommand implements Command{
        @Override
        public boolean execute(Robot robot) {
            robot.setStatus("Shutting down");
            return true;
        }
        @Override
        public String getName()
    }

}
