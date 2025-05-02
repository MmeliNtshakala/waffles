package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.client.ClientBookKeeper;
import za.co.wethinkcode.robots.client.ClientHandler;
import za.co.wethinkcode.robots.robot.Robot;

public class BackwardCommand extends Commands {
    public BackwardCommand(String argument) {
        super("back");
    }

    @Override
    public boolean execute(ClientHandler target) {
        Robot robot = target.getRobot();
        ClientBookKeeper keeper = target.getKeeper();
        int steps = Integer.parseInt(getArgument());
        for (int i = 0; i < steps; i++) {
            robot.moveBack();
        }
        
        keeper.responseMessage(target, robot.getStatus());
        return true;
    }
}