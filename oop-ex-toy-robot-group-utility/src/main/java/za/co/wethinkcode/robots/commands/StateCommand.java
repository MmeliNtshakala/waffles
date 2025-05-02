package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.client.*;

public class StateCommand extends Commands {
    public StateCommand() {
        super("state");
    }

    @Override
    public boolean execute(ClientHandler target) {
        Robot robot = target.getRobot();
        ClientBookKeeper keeper = target.getKeeper();
        robot.setStatus(String.format(
                "Name: %s\nAlive: %s\nPosition: (%d,%d)\nDirection: %s\nType: %s",
                robot.getName(),
                robot.getState(),
                robot.getPosition().getX(),
                robot.getPosition().getY(),
                robot.getDirection().toString(),
                robot.getProperties()
        ));
        keeper.responseMessage(target, robot.getStatus());
        return true;
    }
}
