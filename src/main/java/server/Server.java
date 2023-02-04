package server;

import game.Board;
import game.Computer;
import game.Player;
import game.Protocol;
import socketio.ServerSocket;
import socketio.Socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author BlueTeaWolf (Ole)
 */
public class Server implements Protocol {

    private ServerSocket serverSocket;
    private final Map<Socket, Player> players = new HashMap<>();
    private final Stack<Player> test = new Stack<>();
    private Board board;
    private final Player player1 = Player.RED;
    private final Player player2 = Player.YELLOW;
    private boolean isListening = true;
//    private ArrayList<Connection> connections = new ArrayList<>();

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
//        initializeBoard(5, 6, 4);
        test.push(player1);
        test.push(player2);
    }

    public void start() throws IOException {
        Socket socket = null;

        while (isListening) {
            if ((players.size() < MAX_PLAYERS)) {
                socket = serverSocket.accept();
                socket.write(CONNECTED + "\n");
                System.out.println("CONNECTED");

                players.put(socket, test.pop());
                sendMessage(socket, NEXT_MOVE);
            } else {
                Socket receiver = (Socket) players.keySet().toArray()[0];
                receiver.write(SETTINGS + ":" + players.get(receiver) + "\n");
                System.out.println(players.get(receiver) + " <----");
                System.out.println("waiting...");
                String message = receiver.readLine();
                String[] settings = message.split(":");
                initializeBoard(Integer.parseInt(settings[0]), Integer.parseInt(settings[1]), Integer.parseInt(settings[2]));

                isListening = false;

                for (Socket connections : players.keySet()) {
                    Connection connection = new Connection(connections);
                    connection.start();
//                    this.connections.add(connection);
                    connections.write(START + settings[0] + ":" + settings[1] + ":" + settings[3] + ":" + settings[2] + "\n");
                    System.out.println("SEND");
                }
                receiver.write(NEXT_MOVE + "\n");
            }
        }
    }

    public void initializeBoard(int x, int y, int neededChipsToWin) {
        board = new Board(x, y, neededChipsToWin);
    }

    public void makeNextMove(Socket socket, int row) throws IOException {
        sendMessage(socket, board.nextMove(players.get(socket), row));
    }

    public void sendMessage(Socket sender, String message) throws IOException {
        System.out.println(message);
        if (message.equals(IMPOSSIBLE_MOVE)) {
            sender.write(IMPOSSIBLE_MOVE + "\n");
        } else if (message.startsWith(OK)) {
            for (Socket socket : players.keySet()) {
                if (socket != sender) {
                    socket.write(NEXT_MOVE + "\n");
                }
                socket.write(message + "\n");
            }
        } else if (message.startsWith(END)) {
            for (Socket socket : players.keySet()) {
                if(message.endsWith(TIE)) {
                    socket.write(message + "\n");
                    continue;
                }
                if (socket == sender) {
                    socket.write(message + ":" + WON + "\n");
                } else {
                    socket.write(message + ":" + LOSE + "\n");
                }
            }
            newGame();
        }
    }

    private void newGame() throws IOException {
        Socket receiver = (Socket) players.keySet().toArray()[0];
        System.out.println("send settings");

        receiver.write(SETTINGS + "\n");

        String newMessage = receiver.readLine();
        String[] settings = newMessage .split(":");
        System.out.println("Got settings");
        System.out.println(newMessage );

        if(newMessage.startsWith("0")) {
            newMessage = newMessage.substring(1);
        }

        initializeBoard(Integer.parseInt(settings[0]), Integer.parseInt(settings[1]), Integer.parseInt(settings[2]));
        System.out.println("initialized board");
        for (Socket socket: players.keySet()) {
            socket.write(START + settings[0] + ":" + settings[1] + ":" + settings[3] + ":" + settings[2] + "\n");
            System.out.println(START + settings[0] + ":" + settings[1] + ":" + settings[3] + settings[2] + "\n");
            System.out.println("Send settings to everyone");
        }
        receiver.write(NEXT_MOVE + "\n");
        System.out.println("send next move");
    }

    private class Connection extends Thread {

        private Socket socket;
        private Board board;
        private boolean running = true;

        public Connection(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            while (running) {
                try {
                    int nextMoveRow = socket.read();
                    makeNextMove(this.socket, nextMoveRow);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

