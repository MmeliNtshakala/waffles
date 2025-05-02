package za.co.wethinkcode.robots.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import za.co.wethinkcode.robots.client.ClientBookKeeper;
import za.co.wethinkcode.robots.client.ClientHandler;
import za.co.wethinkcode.robots.utilities.LOG;
import za.co.wethinkcode.robots.world.World;

public class Server{
    private final ServerSocket serverSocket;
    private volatile boolean isRunning = false;

    protected Server(int PORT) throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        this.serverSocket.setReuseAddress(true);
    }

    protected void startServer(){
        LOG.addToServerLog("SERVER STARTED ON PORT : " + serverSocket.getLocalPort());
        World world = new World();  // This is the part where the world is created and initialized.
        ClientHandler.setWorld(world);  // This part gives every handler access to the world.
        LOG.addToServerLog("WORLD CREATED : " + world.toString());
        Thread thread = null;
        try{
            this.isRunning = true;
            while(isRunning && !serverSocket.isClosed()){
                Socket socket= serverSocket.accept();  // Accepts the connection from the client.
                ClientHandler clientHandler = new ClientHandler(socket);  // Assigns a handler to the client.
                thread = new Thread(clientHandler);
                thread.start();   // This part tells the handler to start its job, which launches the clients robot to the world.
            }
        }catch(IOException e){
            if (isRunning) {
                LOG.addToServerLog("ERROR WHILE RUNNING THE SERVER : " + e.getMessage());
            }
        }finally {
            ClientHandler.setWorld(null);
            closeServer();  // This part closes the server when it is done.
            try {
                if (thread != null)thread.join();
            } catch (InterruptedException e) {}
        }
    }

    protected void closeServer(){
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                LOG.addToServerLog("SERVER HAS BEEN CLOSED.");
                this.isRunning = false;
                if (isThereAnyClientConnected()) {
                    LOG.addToServerLog("WARNING? SOME CLIENTS WERE STILL CONNECTED TO THE SERVER.");
                    LOG.addToClientLog("ISSUE? SERVER HAS DISCONNECTED.");
                }
            }
        } catch (IOException e) {
            LOG.addToServerLog("ERROR CLOSING THE SERVER : " + e.getMessage());
        }
    }

    protected boolean isServerRunning() {
        return isRunning;
    }

    protected boolean isThereAnyClientConnected() {
        return !ClientBookKeeper.getAllHandlers().isEmpty();
    }

    protected static int getOpenPort() {
        // This method finds a free port for the server to use.
        // We will use this method to find a free port for the server to use. when launching the server on a different computer.
        // for now we will use port 9999.
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException("UNABLE TO FIND A FREE PORT : ", e);
        }
    }

    public static void main(String[] args){
        try {
            Server server = new Server(9999);
            Runtime.getRuntime().addShutdownHook(new Thread(server::closeServer));
            server.startServer();
        } catch (IOException e) {
            LOG.addToServerLog("ERROR? STARTING THE SERVER: " + e.getMessage());
        }
    }
}