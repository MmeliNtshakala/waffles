package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.robot.Direction;
import za.co.wethinkcode.robots.client.ClientHandler;

public class TurnCommands extends Commands {

    public TurnCommands(String turnDirection) {
        super("turn", turnDirection);
    }

    @Override
    public boolean execute(ClientHandler target) {
        Robot robot = target.getRobot();
        Direction currentDirection = robot.getCurrentDirection();
        String turnDirection = getArgument();

        if ("left".equalsIgnoreCase(turnDirection)) {
            currentDirection = currentDirection.turnLeft();
        } else if ("right".equalsIgnoreCase(turnDirection)) {
            currentDirection = currentDirection.turnRight();
        } else {
            robot.setStatus("Invalid turn direction.");
            return false;
        }

        robot.setCurrentDirection(currentDirection);
        robot.setStatus("Turned " + turnDirection + ".");
        return true;
    }
}
