package za.co.wethinkcode.robots.world;

import java.util.ArrayList;
import java.util.List;

import za.co.wethinkcode.robots.robot.Position;
import za.co.wethinkcode.robots.robot.Robot;

public class World {
  private static int[] sizeOfWorld;
  final private int width;
  final private int height;
  final private int obstacleX;
  final private int obstacleY;
  final private List<Robot> robots;

  public World() {
    this.width = 100;
    this.height = 100;
    this.obstacleX = 10;
    this.obstacleY = 10;
    this.robots = new ArrayList<>();

    sizeOfWorld = new int[2];
    sizeOfWorld[0] = this.width;
    sizeOfWorld[1] = this.height;
  }

  public int[] getSizeOfWorld() {
    return sizeOfWorld;
  }

  public boolean isPositionAllowed(Position newPosition) {
    return true;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getObstacleX() {
    return obstacleX;
  }

  public int getObstacleY() {
    return obstacleY;
  }

  public void addRobot(Robot robot) {
    robots.add(robot);
  }

  public List<Robot> getRobots() {
    return robots;
  }

  // returns a string representation of the world's current state
  public String dumpWorld() {
    StringBuilder info = new StringBuilder();
    info.append("Robot World Created!\n");
    info.append("World Size: ").append(width).append("x").append(height).append("\n");
    info.append("Obstacle at: (").append(obstacleX).append(", ").append(obstacleY).append(")\n");
    info.append("Robots:\n");
    for (Robot robot : robots) {
      info.append("-- ").append(robot.getName()).append("\n");
    }
    return info.toString();
  }
}
