package controller;

import controller.server.Room;
import model.TileBag;
import view.CommandInterpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 */
public class Peer implements Runnable, Comparable<Peer> {
    private Socket sock;
    private BufferedReader in;
    private PrintWriter out;
    private volatile boolean running;
    private CommandInterpreter reader;
    private Thread streamInputHandler;
    private String name;
    private boolean chatEnabled;
    private ClientOrServer parent;
    private int preferredNrOfPlayers;
    private Room currentRoom;
    private TileBag tileBag;
    private int score;

    /**
     * Constructor. creates a peer object based in the given parameters.
     *
     * @param sockArg Socket of the controller.Peer-proces
     */
    public Peer(Socket sockArg, ClientOrServer.Type type, ClientOrServer parentInput) {
        try {
            parent = parentInput;

            sock = sockArg;
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(sock.getOutputStream(), true);
            this.running = true;
            reader = new CommandInterpreter(parent);


            streamInputHandler = new Thread(this);
            streamInputHandler.start();

            if (type == ClientOrServer.Type.CLIENT) {
                name = "Server";
                chatEnabled = true; // Client leaves all communication with its "Peer", the server, enabled by default
            } else {
                name = "Unknown client";
                chatEnabled = false;
                preferredNrOfPlayers = 0;
            }

        } catch (IOException e) {
            parent.getPrinter().println("Error in starting peer");
            e.printStackTrace();
        }
    }

    /**
     * Gets running
     *
     * @return value of running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Sets running to running
     *
     * @param running new value of running
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Gets name
     *
     * @return value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name to name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets chatEnabled
     *
     * @return value of chatEnabled
     */
    public boolean isChatEnabled() {
        return chatEnabled;
    }

    /**
     * Sets chatEnabled to chatEnabled
     *
     * @param chatEnabled new value of chatEnabled
     */
    public void setChatEnabled(boolean chatEnabled) {
        this.chatEnabled = chatEnabled;
    }

    /**
     * Gets preferredNrOfPlayers
     *
     * @return value of preferredNrOfPlayers
     */
    public int getPreferredNrOfPlayers() {
        return preferredNrOfPlayers;
    }

    /**
     * Sets preferredNrOfPlayers to preferredNrOfPlayers
     *
     * @param preferredNrOfPlayers new value of preferredNrOfPlayers
     */
    public void setPreferredNrOfPlayers(int preferredNrOfPlayers) {
        this.preferredNrOfPlayers = preferredNrOfPlayers;
    }

    /**
     * Gets currentRoom
     *
     * @return value of currentRoom
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Gets tileBag
     *
     * @return value of tileBag
     */
    public TileBag getTileBag() {
        return tileBag;
    }

    /**
     * Sets tileBag to tileBag
     *
     * @param tileBag new value of tileBag
     */
    public void setTileBag(TileBag tileBag) {
        this.tileBag = tileBag;
    }

    /**
     * Gets score
     *
     * @return value of score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets score to score
     *
     * @param score new value of score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     *
     * @param i
     */
    public void incScore(int i) {
        this.score += i;
    }

    /**
     *
     */
    public void run() {
        while (running && parent.getRunning()) {
            try {
                String s1 = in.readLine();
                if (s1 == null || s1.equals("")) {
                    close();
                } else {
                    reader.read(s1, this);
                }

            } catch (IOException e) {
                if (running && parent.getRunning()) {
                    parent.getPrinter().println("Error in reading data from " + name);
                    e.printStackTrace();
                }

            }
        }
    }


    /**
     *
     * @param s
     */
    public void sendMessage(String s) {
        out.println(s);
    }

    /**
     *
     * @param room
     */
    public void moveToRoom(Room room) {
        if (this.currentRoom != null) {
            currentRoom.removePeer(this);
        }
        room.addPeer(this);
        currentRoom = room;
    }


    /**
     * Closes the controller, the sockets will be terminated
     */
    public void close() {
        this.running = false;

        streamInputHandler.interrupt();
        parent.getPrinter().println("Connection reading thread for " + name + " closed");
        try {
            sock.close();
            parent.getPrinter().println("Socket for controller " + name + " closed");
        } catch (IOException e) {
            parent.getPrinter().println("Error in closing socket for " + name);

        }
        // TODO if server, remove peer from lists, rooms and such. Close any model rooms it is in

    }

    /**
     *
     * @param p
     * @return
     */
    // used for sorting reasons in model logic
    @Override
    public int compareTo(Peer p) {
        return this.name.compareTo(p.getName());
    }
}

