package za.co.wethinkcode.robots.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientBookKeeper {

  private static volatile ClientBookKeeper instance = null;
  private static volatile CopyOnWriteArrayList<ClientHandler> clientHandlers;
  private static volatile ConcurrentHashMap<String, CopyOnWriteArrayList<ClientHandler>> teams; 



  private ClientBookKeeper(){
    clientHandlers = new CopyOnWriteArrayList<>();
  }

  public static ClientBookKeeper getInstance() {
    ClientBookKeeper localInstance = instance;
    if (localInstance == null) {
      synchronized (ClientBookKeeper.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new ClientBookKeeper();
        }
      }
    }
    return localInstance;
  }

  public static synchronized CopyOnWriteArrayList<ClientHandler> getClientHandlers() {
    return clientHandlers;
  }

  public synchronized void addClient(ClientHandler clientHandler) {
    clientHandlers.add(clientHandler);
  }
  public synchronized void removeClient(ClientHandler clientHandler) {
    clientHandlers.remove(clientHandler);
  }

  public static synchronized  boolean handlerPresent(String name){
    if(clientHandlers != null){
    for (ClientHandler client : clientHandlers) {
        if (client.getClientName().equals(name)) {
            return true;
        }
    }
  }
    return false;
  }

  public synchronized  boolean isTeamAvailable(String teamName) {
    return teams != null && teams.containsKey(teamName);
  }

  public synchronized void addOnTeam(String teamName, ClientHandler clientHandler) {
    if (teams == null) {
      teams = new ConcurrentHashMap<>();
    }
    teams.computeIfAbsent(teamName, k -> new CopyOnWriteArrayList<>()).add(clientHandler);
  }

  public synchronized void removeFromTeam(String teamName, ClientHandler clientHandler) {
    if (teams != null && teams.containsKey(teamName)) {
      CopyOnWriteArrayList<ClientHandler> teamMembers = teams.get(teamName);
      if (teamMembers != null) {
        teamMembers.remove(clientHandler);
        if (teamMembers.isEmpty()) {
          teams.remove(teamName);
        }
      }
    }
  }

  public synchronized CopyOnWriteArrayList<ClientHandler> getTeamMembers(String teamName) {
    if (teams != null) {
      return teams.get(teamName);
    }
    return null;
  }

  private synchronized  void sendMessage(ClientHandler client, String message) throws IOException {
    BufferedWriter bufferedWriter = client.getBufferedWriter();
    bufferedWriter.write(message);
    bufferedWriter.newLine();
    bufferedWriter.flush();
  }

  public synchronized  void messageTeamMembers(ClientHandler sender, String message){
    String teamName = sender.getTeamName();
    if (teamName != null && teams != null && teams.containsKey(teamName)) {
      CopyOnWriteArrayList<ClientHandler> teamMembers = teams.get(teamName);
      for (ClientHandler member : teamMembers) {
        if (!member.equals(sender)) {
          try{
            sendMessage(member, message);
            }catch(IOException e){
              System.err.println("ERROR SENDING MESSAGE: " + e.getMessage());
            }
        }
      }
    }
  }

  public synchronized  void broadcastMessage(ClientHandler sender, String message){
    for (ClientHandler client : clientHandlers) {
      try{
        if (!client.equals(sender)) {
          sendMessage(client, message);
        }
      }catch(IOException e){
        System.err.println("ERROR SENDING MESSAGE: " + e.getMessage());
      }
    }
  }

  public synchronized void responseMessage(ClientHandler toSender, String message){
    BufferedWriter bufferedWriter = toSender.getBufferedWriter();
    try {
      bufferedWriter.write(message);
      bufferedWriter.newLine();
      bufferedWriter.flush();
    } catch (IOException e) {
      System.err.println("ERROR SENDING MESSAGE: " + e.getMessage());
    }
  }

  public static synchronized CopyOnWriteArrayList<ClientHandler> getAllHandlers() {
    return clientHandlers;
  }
}
