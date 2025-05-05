package za.co.wethinkcode.toyrobot;


public class BackCommand extends Command {
    public BackCommand(String argument) {
        super("back", argument);
    }

    @Override
    public boolean execute(Robot target) {
        int steps = Integer.parseInt(getArgument());
        boolean moved = target.updatePosition(-steps);

        if (moved) {
            target.setStatus("Moved back by " + steps + " steps.");
        } else {
            target.setStatus("Cannot move back by " + steps + " steps.");
        }

        return moved;
    }
}
