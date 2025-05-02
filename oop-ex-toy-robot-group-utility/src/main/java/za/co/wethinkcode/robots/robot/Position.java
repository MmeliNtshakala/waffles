package za.co.wethinkcode.robots.robot;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Position moveForward(Direction direction){
        switch(direction){
            case NORTH:
                return new Position(x,y+1);
            case SOUTH:
                return new Position(x, y-1);
            case EAST:
                return new Position(x+1, y);
            case WEST:
                return new Position(x-1,y);
            default:
                return this;
        }
    }

    public Position moveBack(Direction direction){
        switch(direction){
            case NORTH:
                return new Position(x,y-1);
            case SOUTH:
                return new Position(x, y+1);
            case EAST:
                return new Position(x-1, y);
            case WEST:
                return new Position(x+1,y);
            default:
                return this;
        }
    }



    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public String toString(){
        return "(" + x +"," + y + ")";
    }
}
