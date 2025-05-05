package za.co.wethinkcode.toyrobot;

public class RightCommand extends Command {
    public RightCommand() {
        super("right");
    }

    @Override
    public boolean execute(Robot target) {
        if (target == null) {
            return false;
        }
        target.turnRight();
        target.setStatus("Turned right.");
        return true;
    }
}

