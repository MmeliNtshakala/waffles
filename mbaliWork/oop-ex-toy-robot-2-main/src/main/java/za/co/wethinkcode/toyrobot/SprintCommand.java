package za.co.wethinkcode.toyrobot;

public class SprintCommand extends Command {
    public SprintCommand(String argument) {
        super("sprint", argument);
    }

    @Override

    public boolean execute(Robot target) {
        int steps = Integer.parseInt(getArgument());

        for (int i = steps; i > 0; i--) {

            if (target.updatePosition(i)) {
                target.setStatus("Moved forward by " + i + " steps.");
                System.out.println(target.toString());
            } else {
                target.setStatus("Sorry, I cannot go outside my safe zone.");
            }
        }


        return true; // Don't print the status again inside the method
    }
}








