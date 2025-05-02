package za.co.wethinkcode.robots.robot;

import za.co.wethinkcode.robots.world.World;


public class Robot{
  private final String name;
  private RobotProperties properties;
  private boolean alive = false;
  private Position position;
  private Direction direction;
  private String status;
  private World world;


  public Position getPosition() {
    return position;
  }

  public Direction getOrientation(){
    return direction;
  }

  private RobotProperties setProperty(String type){
    return RobotProperties.valueOf(type.toUpperCase());
  }

  public boolean getState(){
    return alive;
  }

  public void kill(){
    this.alive = false;
  }

  public String getStatus(){
    return status;
  }

  public String getName(){
    return name;
  }


  public Direction getDirection(){
    return direction;
  }

  public void setPosition(Position position){
    this.position = position;
  }

  public void setDirection(Direction direction){
    this.direction = direction;
  }

  public RobotProperties getProperties(){
    return properties;
  }

  public void moveForward() {
    position = position.moveForward(direction);
  }

  public void turnLeft() {
    direction = direction.turnRight();
  }

  public void turnRight(){
    direction = direction.turnLeft();
  }

  public void sprint(int steps){
    for (int i = steps; i > 0; i--);{
      position = position.moveForward(direction);
    }
  }

  public void moveBack(){
    position = position.moveBack(direction);
  }


  public void revive(String type) {
    this.properties  = setProperty(type);
    this.alive = true;
    this.position = new Position(0,0);
    this.direction = Direction. NORTH;
  }

  public void repair(){
    this.alive = true;
  }

  public Robot(String name, String type){
    this.name = name;
    this.properties = setProperty(type);
    this.position = new Position(0, 0);
    this.alive = true;
    this.status = "Ready";
    this.direction = Direction.NORTH;
  }

  public boolean updatePosition(int nrSteps) {
    Position newPosition = position;
    for(int i = 0; i< nrSteps; i++){
      newPosition = newPosition.moveForward(direction);
    }

    if (world != null && world.isPositionAllowed(newPosition)){
      this.position = newPosition;
      return true;
    }
    return false;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setCurrentDirection(Direction currentDirection) {
    this.direction = currentDirection;
  }

  public Direction getCurrentDirection() {
    return direction;
  }

  public World getWorld() {
    return this.world;
  }

  public void setWorld(World world){
    this.world = world;
  }
}