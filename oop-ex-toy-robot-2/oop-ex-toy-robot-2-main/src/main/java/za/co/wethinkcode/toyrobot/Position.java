package za.co.wethinkcode.toyrobot;

/**
 * Represents a position in a 2D plane with x and y coordinates.
 */
public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Updates the position based on the direction and steps provided.
     *
     * @param direction the direction to move (NORTH, SOUTH, EAST, WEST)
     * @param steps     the number of steps to move (can be negative for reverse movement)
     * @return a new Position object with the updated coordinates
     */
    public Position updatePosition(Direction direction, int steps) {
        int newX = this.x;
        int newY = this.y;

        switch (direction) {
            case NORTH -> newY += steps; // Move up
            case SOUTH -> newY -= steps; // Move down
            case EAST -> newX += steps;  // Move right
            case WEST -> newX -= steps;  // Move left
        }

        return new Position(newX, newY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }

    /**
     * Checks if the position is within a rectangular boundary defined by top-left and bottom-right corners.
     *
     * @param topLeft      the top-left corner of the boundary
     * @param bottomRight  the bottom-right corner of the boundary
     * @return true if the position is within the boundary, false otherwise
     */
    public boolean isIn(Position topLeft, Position bottomRight) {
        boolean withinTop = this.y <= topLeft.getY();
        boolean withinBottom = this.y >= bottomRight.getY();
        boolean withinLeft = this.x >= topLeft.getX();
        boolean withinRight = this.x <= bottomRight.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }

    /**
     * Calculates the Euclidean distance to another position.
     *
     * @param other the other position
     * @return the distance between the two positions
     */
    public double distanceTo(Position other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }
}