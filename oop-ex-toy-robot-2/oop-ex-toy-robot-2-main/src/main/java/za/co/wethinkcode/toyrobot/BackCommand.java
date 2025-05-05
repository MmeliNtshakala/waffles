package za.co.wethinkcode.toyrobot;

public class BackCommand extends Command {
    public BackCommand(String argument) {
        super("back", argument);
    }

    @Override
    public boolean execute(Robot target) {
        try {
            int nrSteps = Integer.parseInt(getArgument());
            if (target.updatePosition(-nrSteps)) {
                target.setStatus("Moved back by " + nrSteps + " steps.");
            } else {
                target.setStatus("Sorry, I cannot go outside my safe zone.");
            }
        } catch (NumberFormatException e) {
            target.setStatus("Invalid argument for back command. Please specify a valid number of steps.");
        }
        return true;
    }
}