package za.co.wethinkcode.robots.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.fasterxml.jackson.databind.JsonNode;

import za.co.wethinkcode.robots.commands.Commands;
import za.co.wethinkcode.robots.robot.Robot;
import za.co.wethinkcode.robots.utilities.DataManangement;
import za.co.wethinkcode.robots.utilities.LOG;
import za.co.wethinkcode.robots.world.World;

public class ClientHandler implements Runnable{

  private final Socket socket;
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;
  private String clientName;
  private static World world;
  private Robot robot;
  private ClientBookKeeper keeper = ClientBookKeeper.getInstance();
  private String TEAM_NAME = "";
  

  @Override
  public void run() {
    JsonNode messageFromClient;
    while (socket.isConnected()) {
      try {
        messageFromClient = DataManangement.readJSON(socket);
        try{
          Commands command = Commands.create(messageFromClient);
          command.execute(this);
        }catch (IllegalArgumentException e){
          LOG.addToClientLog("UNKNOWN?" + clientName + " -> " +e.getMessage());
          keeper.responseMessage(this, "Invalid Command!!\n");
        }
      } catch (Exception e) {
        closeEverything(socket, bufferedReader, bufferedWriter);
        break;
      }
    }
  }

  public Robot getRobot(){
    return robot;
  }

  public ClientBookKeeper getKeeper(){
    return keeper;
  }

  public String getClientName(){
    return clientName;
  }


  public void launchRobot(String robotType){

    // this method will be responsible for launching the robot in the world
    // this method will be implemented on the GUI for launching the robot.
    // will edit this code for private team launching and communication

    if(this.robot != null && this.robot.getState()){
      keeper.responseMessage(this, "YOU HAVE A ROBOT PRESENT IN THE WORLD");
      return;
    }

    this.robot = new Robot(clientName, robotType);
    this.robot.setWorld( ClientHandler.world);
    keeper.broadcastMessage(this,  "\n" + clientName + " HAS LAUNCHED THEIR ROBOT TO THE WORLD!\n");
    LOG.addToServerLog(clientName + " HAS LAUNCHED THEIR ROBOT TO THE WORLD!");
  }
  
  public final void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferWriter){
    keeper.removeClient(this);
    keeper.removeFromTeam(TEAM_NAME, this);
    keeper.broadcastMessage(this, clientName + " HAS LEFT THE WORLD!");
    LOG.addToServerLog(clientName + " HAS LEFT THE SERVER!");
    LOG.addToClientLog(clientName + " HAS LEFT THE WORLD!");
    try {
      if (bufferedReader != null) {
        bufferedReader.close();
      }
      if (bufferWriter != null) {
        bufferWriter.close();
      }
      if (socket != null) {
        socket.close();
      }
    } catch (IOException e) {
      System.err.println("ERROR CLOSING SOCKET : " + e.getMessage());
    }
  }

  public static void setWorld(World world) {
    ClientHandler.world = world;
  }


  public String getTeamName() {
    return TEAM_NAME;
  }

  public void setTeamName(String teamName) {
    TEAM_NAME = teamName;
  }

  public BufferedWriter getBufferedWriter() {
    return bufferedWriter;
  }

  public ClientHandler(Socket socket) throws IOException {
    this.socket = socket;
    try {
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.clientName = bufferedReader.readLine();
        while(ClientBookKeeper.handlerPresent(clientName)){
          bufferedWriter.write("\nNAME ALREADY TAKEN! PLEASE CHOOSE ANOTHER NAME: ");
          bufferedWriter.newLine();
          bufferedWriter.flush();
          String message = bufferedReader.readLine();
          String[] messageComponents = message.split(":");
          this.clientName = messageComponents[messageComponents.length - 1].trim();
          bufferedWriter.write("set name to : " + clientName);
          bufferedWriter.newLine();
          bufferedWriter.flush();
        }

        keeper.addClient(this);
        keeper.broadcastMessage(this, clientName + " HAS ENTERED THE WORLD! \n");
        LOG.addToServerLog(clientName + " HAS CONNECTED TO THE SERVER!");
        LOG.addToClientLog(clientName + " HAS ENTERED THE WORLD!");

    } catch (IOException e) {
        closeEverything(socket, bufferedReader, bufferedWriter);
    }
  }
}
