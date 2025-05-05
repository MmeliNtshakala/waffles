package za.co.wethinkcode.toyrobot;

public class Robot {
    public static final Position CENTRE = new Position(0, 0);

    private Position position;
    private Direction currentDirection;
    private String status;
    private String name;

    public Robot(String name) {
        this.name = name;
        this.position = CENTRE;
        this.currentDirection = Direction.NORTH;
        this.status = "Ready";
    }

    public Position getPosition() {
        return position;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter for the Robot's name.
     *
     * @return the name of the robot
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the robot's position based on the number of steps and its current direction.
     *
     * @param steps the number of steps to move. Positive for forward, negative for backward.
     * @return true if the position was successfully updated, false otherwise.
     */
    public boolean updatePosition(int steps) {
        Position newPosition = this.position.updatePosition(this.currentDirection, steps);

        if (isInSafeZone(newPosition)) {
            this.position = newPosition;
            return true;
        } else {
            this.status = "Sorry, I cannot go outside my safe zone.";
            return false;
        }
    }

    /**
     * Checks if the position is within the robot's safe zone.
     *
     * @param position the position to check.
     * @return true if the position is within the safe zone, false otherwise.
     */
    private boolean isInSafeZone(Position position) {
        return position.isIn(new Position(-100, 100), new Position(100, -100));
    }

    /**
     * Sets the robot's current direction.
     *
     * @param direction the new direction to face.
     */
    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }

    /**
     * Handles a command issued to the robot.
     *
     * @param command the command to execute.
     * @return true if the command was successfully executed, false otherwise.
     */
    public boolean handleCommand(Command command) {
        boolean success = command.execute(this);
        if (!(command instanceof ReplayCommand)){
            ReplayCommand.addCommandToHistory(command);
        }
        return success;
    }

    @Override
    public String toString() {
        return "[" + position.getX() + "," + position.getY() + "] " + name + "> " + status;
    }
}