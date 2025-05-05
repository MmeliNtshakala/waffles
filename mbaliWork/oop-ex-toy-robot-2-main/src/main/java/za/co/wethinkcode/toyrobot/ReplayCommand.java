package za.co.wethinkcode.toyrobot;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class ReplayCommand extends Command {
    public ReplayCommand(){
        super("replay");
    }
    public ReplayCommand(String argument){
        super ("replay", argument);
    }
    public ReplayCommand(String argument,String flag){
        super("replay", argument, flag);
    }
    ArrayList<String> command = getCommands();
    static ArrayList<String> commandList = new ArrayList<>();
    public static String commandString;
    public static void runCommands(Robot target, int i){
        Command command = Command.create(getCommands().get(i));
        target.handleCommand(command);
        commandList.add(target.toString());
    }
    @Override
    public boolean execute(Robot target) {

        String steps = getArgument();
        if(!steps.contains("reversed")){
            String[] nrsteps = getArgument().split("-");
            int length = command.size();
            if(nrsteps[0] != "" && nrsteps.length == 1){
            int stepsie = Integer.parseInt(nrsteps[0]);
            for(int i = (length - stepsie); i < length; i++){
                runCommands(target, i );

            }

            }
            else if(nrsteps[0] != "" && nrsteps.length == 2){
                int stepsie1 = Integer.parseInt(nrsteps[1]);
                int stepsie2 = Integer.parseInt(nrsteps[0]);

                for(int i = stepsie1 - 2; i < stepsie2 - 2 ; i++){
                    runCommands(target, i);

                }
            }
            else{
                int Length = command.size();
                for(int i = 0; i < Length; i++){
                    runCommands(target, i );
                }
            }
        }
        else{
            String[] nrsteps = getFlag().split("-");
            int length = command.size();
            if(nrsteps[0] != "" && nrsteps.length == 1){
                int stepsie = Integer.parseInt(nrsteps[0]);
                for(int i = length - 1 ; i >= (length - stepsie); i--){
                    runCommands(target, i );

                }

            }
            else if(nrsteps[0] != "" && nrsteps.length == 2){
                int stepsie1 = Integer.parseInt(nrsteps[1]);
                int stepsie2 = Integer.parseInt(nrsteps[0]);

                for(int i = stepsie2 - 3; i >= stepsie1 - 2 ; i--){
                    runCommands(target, i);

                }
            }
            else{
                int Length = command.size();
                for(int i = Length - 1; i >= 0 ; i--){
                    runCommands(target, i );
                }
            }

        }

        target.setStatus("replayed " + commandList.size()+" commands.");
        commandList.add(target.toString());
        commandString = String.join("\n",commandList);
        commandList.clear();
        return true;


    }
}