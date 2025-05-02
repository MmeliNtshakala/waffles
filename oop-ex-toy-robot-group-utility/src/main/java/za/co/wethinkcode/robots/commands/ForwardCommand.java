package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.client.ClientBookKeeper;
import za.co.wethinkcode.robots.client.ClientHandler;

public class ForwardCommand extends Commands {
    @Override
    public boolean execute(ClientHandler target) {
        Robot robot = target.getRobot();
        ClientBookKeeper keeper = target.getKeeper();
        System.out.println(getArgument());
        int nrSteps = Integer.parseInt(getArgument());
        if (robot.updatePosition(nrSteps)){
            robot.setStatus("Moved forward by "+ nrSteps +" steps.");
        } else {
            robot.setStatus("Sorry, I cannot go outside my safe zone.");
        }
        keeper.responseMessage(target, robot.getStatus());
        return true;
    }

    public ForwardCommand(String argument) {
        super("forward", argument);
    }
}

