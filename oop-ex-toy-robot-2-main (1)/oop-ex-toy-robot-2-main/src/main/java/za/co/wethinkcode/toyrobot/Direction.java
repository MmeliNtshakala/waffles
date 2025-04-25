package za.co.wethinkcode.toyrobot;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public Direction turnLeft(){
        return  values()[(ordinal() + 3) % 4];
    }
    public Direction turnRight(){
        return values()[(ordinal() + 1) % 4];
    }
}
