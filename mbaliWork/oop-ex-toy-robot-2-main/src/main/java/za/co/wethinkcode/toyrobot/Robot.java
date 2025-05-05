package za.co.wethinkcode.toyrobot;

import java.util.ArrayList;
import java.util.List;

public class Robot {
    private final Position TOP_LEFT = new Position(-200, 100);
    private final Position BOTTOM_RIGHT = new Position(100, -200);
    public static final Position CENTRE = new Position(0, 0);

    private Position position;
    private Direction currentDirection;
    private String status;
    private String name;


    private List<Command> commandHistory = new ArrayList<>();


    public Robot(String name) {
        this.name = name;
        this.position = CENTRE;  // Set default position
        this.currentDirection = Direction.NORTH; // Default starting direction
        this.status = "Ready";  // Default status
    }


    public Robot() {
        this("Unnamed Robot");
    }

    public void addCommandToHistory(Command cmd) {
        commandHistory.add(cmd);
    }

    public List<Command> getCommandHistory() {
        return commandHistory;
    }

    // Execute command and store it properly
    public boolean executeCommand(Command command) {
        addCommandToHistory(command);
        return command.execute(this);
    }

    public String getStatus() {
        return this.status;

    }

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    public boolean handleCommand(Command command) {
        return executeCommand(command); // Now ensures history tracking
    }

    public boolean updatePosition(int nrSteps) {
        int newX = this.position.getX();
        int newY = this.position.getY();

        switch (this.currentDirection) {
            case NORTH -> newY += nrSteps;
            case EAST -> newX += nrSteps;
            case SOUTH -> newY -= nrSteps;
            case WEST -> newX -= nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        if (newPosition.isIn(TOP_LEFT, BOTTOM_RIGHT)) {
            this.position = newPosition;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[" + this.position.getX() + "," + this.position.getY() + "] "
                + this.name + "> " + this.status;
    }

    public Position getPosition() {

        return this.position;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void turnLeft() {
        switch (this.currentDirection) {
            case NORTH -> this.currentDirection = Direction.WEST;
            case WEST -> this.currentDirection = Direction.SOUTH;
            case SOUTH -> this.currentDirection = Direction.EAST;
            case EAST -> this.currentDirection = Direction.NORTH;
        }
    }

    public void turnRight() {
        switch (this.currentDirection) {
            case NORTH -> this.currentDirection = Direction.EAST;
            case EAST -> this.currentDirection = Direction.SOUTH;
            case SOUTH -> this.currentDirection = Direction.WEST;
            case WEST -> this.currentDirection = Direction.NORTH;
        }
    }
}