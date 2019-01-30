package connection.client;

import connection.ClientOrServer;
import connection.Peer;
import connection.TerminalInputHandler;
import game.Board;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * connection.client.Client class for a simple client-server application
 *
 * @author Theo Ruys
 * @version 2005.02.21
 */
public class Client implements ClientOrServer {
    public static final Type type = ClientOrServer.Type.CLIENT;
    private TerminalInputHandler terminalInputHandler;
    private Thread terminalInputHandlerThread;
    private Peer clientPeer;
    private volatile boolean running;
    private String name;
    private Board board;

    /**
     * Starts a connection.client.Client application.
     */
    public Client(String ip, String arg) {
        if (arg.equals("singleplayer")){
            terminalInputHandler = new TerminalInputHandler(this, TerminalInputHandler.InputState.SINGLEPLAYER);
        } else {
            terminalInputHandler = new TerminalInputHandler(this);
        }
        connect(ip);
        ClientCommands.setClientObject(this);

    }

    public void connect(String ip) {
        String name = "name";
        InetAddress addr = null;
        int port = 4000;
        Socket sock = null;
        name="default";

        // check the IP-adress
        try {
            addr = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {

            System.out.println("ERROR: host " + ip + " unknown");
            System.exit(0);
        }


        // try to open a Socket to the server
        try {
            sock = new Socket(addr, port);
        } catch (IOException e) {
            System.out.println("ERROR: could not create a socket on " + addr
                + " and port " + port);
        }

        this.running = true;

        // create connection.Peer object and start the two-way communication
        clientPeer = new Peer(sock, type, this);
        terminalInputHandlerThread = new Thread(terminalInputHandler);
        terminalInputHandlerThread.start();
        System.out.println("Connected to server");
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Peer getPeer() {
        return clientPeer;
    }

    public void sendMessageToAll(String s) {
        clientPeer.sendMessage(s);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public synchronized boolean getRunning() {
        return this.running;
    }

    public Type getType(){
        return this.type;
    }

    // TODO Implement
    @Override
    public void shutDown() {
        this.running = false;
        clientPeer.setRunning(false);

        System.out.println("Trying to shut down");
        try {
            terminalInputHandlerThread.interrupt(); // TODO this is not a very neat way of doing it
            System.out.println("Closed terminal input handling thread");
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            System.out.println("Error in closing terminal input thread");
        }
        clientPeer.close();



    }
} // end of class connection.client.Client
