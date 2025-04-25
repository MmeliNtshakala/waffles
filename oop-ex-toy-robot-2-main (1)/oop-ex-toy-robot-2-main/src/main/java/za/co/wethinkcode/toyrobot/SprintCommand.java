package za.co.wethinkcode.toyrobot;

public class SprintCommand extends Command {
    public SprintCommand(String argument) {
        super("sprint", argument);
    }

    @Override
    public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());
        for (int  x = nrSteps; x > 0; x--) {
            if (!target.updatePosition(x)) {
                target.setStatus("Sorry, I cannot go outside my safe zone.");
                return false;
            }
        }
        target.setStatus("Moved forward by " + nrSteps + " steps.");
        return true;
    }
}
