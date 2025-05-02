package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.client.ClientHandler;

public class ShutdownCommand extends Commands {
    public ShutdownCommand() {
        super("off");
    }

    @Override
    public boolean execute(ClientHandler target) {
        Robot robot = target.getRobot();
        robot.setStatus("Shutting down...");
        return false;
    }
}
