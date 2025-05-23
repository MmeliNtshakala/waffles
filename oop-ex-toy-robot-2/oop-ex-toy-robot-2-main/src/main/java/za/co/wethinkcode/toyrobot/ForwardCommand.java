package za.co.wethinkcode.toyrobot;

public class ForwardCommand extends Command {
    public ForwardCommand(String argument){
        super("forward", argument);
    }
    @Override
    public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());
        if (target.updatePosition(nrSteps)){
            target.setStatus("Moved forward by "+nrSteps+" steps.");
        } else {
            target.setStatus("Sorry, I cannot go outside my safe zone.");
        }
        return true;
    }

    public static class offCommand extends Command {
        public offCommand(){
            super("off");
        }

        @Override
        public boolean execute(Robot target){
            target.setStatus("Shutting down...");
            return false;
        }
    }
}