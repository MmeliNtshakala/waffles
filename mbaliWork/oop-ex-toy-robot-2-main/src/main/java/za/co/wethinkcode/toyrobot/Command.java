package za.co.wethinkcode.toyrobot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Command {
    private final String name;
    private String argument;
    private String flag;
    private static ArrayList<String> commands = new ArrayList<>();

    public abstract boolean execute(Robot target);

    public Command(String name){
        this.name = name.trim().toLowerCase();
        this.argument = "";
        this.flag = "";
    }

    public Command(String name, String argument) {
        this(name);
        this.argument = argument.trim();
        this.flag = "";
    }

    public Command(String name,String argument, String flag){
        this(name);
        this.argument = argument.trim();
        this.flag = flag.trim();
    }

    public String getName() {                                                                           //<2>

        return name;
    }

    public String getArgument() {
        return this.argument;
    }

    public String getFlag(){
        return this.flag;
    }

    private static void setCommands(String instruction){
        if(!instruction.startsWith("replay")) commands.add(instruction);
    }
    public static ArrayList<String> getCommands(){
        return commands;
    }
    public static void emptyCommands(){
        commands.clear();
    }



    public static Command create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ");
        setCommands(instruction);

        switch (args[0]) {
            case "shutdown":
            case "off":
                return new ShutdownCommand();
            case "help":
                return new HelpCommand();
            case "forward":
                return new ForwardCommand(args.length > 1 ? args[1] : "0");
            case "back":
                return new BackCommand(args.length > 1 ? args[1] : "0");
            case "left":
                return new LeftCommand();
            case "right":
                return new RightCommand();
            case "sprint":
                return new SprintCommand(args.length > 1 ? args[1] : "0");
            case "replay":
                if(args.length == 1) return new ReplayCommand();
                if(args.length == 2) return new ReplayCommand(args[1]);
                if(args.length == 3) return new ReplayCommand(args[1],args[2]);

            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }

}


