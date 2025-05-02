package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.client.ClientBookKeeper;
import za.co.wethinkcode.robots.client.ClientHandler;
import za.co.wethinkcode.robots.robot.Robot;

public class HelpCommand extends Commands {

    public HelpCommand() {
        super("help");
    }

    @Override
    public boolean execute(ClientHandler target) {
        Robot robot = target.getRobot();
        ClientBookKeeper keeper = target.getKeeper();
        robot.setStatus("I can understand these commands:\n" +
                "OFF  - Shut down robot\n" +
                "HELP - provide information about commands\n" +
                "FORWARD - move forward by specified number of steps, e.g. 'FORWARD 10'");
        keeper.responseMessage(target, robot.getStatus());
        return true;
    }
}