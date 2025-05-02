package za.co.wethinkcode.robots.commands;

import za.co.wethinkcode.robots.client.ClientBookKeeper;
import za.co.wethinkcode.robots.client.ClientHandler;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.world.World;

public class LookCommand extends Commands {
    public LookCommand() {
        super("look");
    }

    @Override
    public boolean execute(ClientHandler target) {
        Robot robot = target.getRobot();
        World world = robot.getWorld();
        int[] size = world.getSizeOfWorld(); // Assumes world provides width and height as [width, height]
        ClientBookKeeper keeper = target.getKeeper();

        String message = String.format(
                "Robot %s looking around: The World size is %dx%d, my position is %s.",
                robot.getName(),
                size[0], size[1],
                robot.getPosition().toString()
        );

        robot.setStatus(message);
        keeper.responseMessage(target, robot.getStatus());
        return true;
    }
}
