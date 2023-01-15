package server;

import java.io.IOException;

import game.Board;
import game.Player;
import game.Protocol;
import socketio.ServerSocket;
import socketio.Socket;

import java.util.*;

/**
 * @author BlueTeaWolf (Ole)
 */
public class Server implements Protocol {

    private ServerSocket serverSocket;
    private static final int MAX_PLAYERS = 2;
    private final Map<Socket, Player> players = new HashMap<>();
    private final Stack<Player> test = new Stack<>();
    private Board board;
    private final Player player1 = Player.RED;
    private final Player player2 = Player.YELLOW;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        //TEST
        initializeBoard(5, 6, 5);
        test.push(player1);
        test.push(player2);
    }

    public void start() throws IOException {
        Socket socket;

        while (true) {
            if ((players.size() < MAX_PLAYERS)) {
                socket = serverSocket.accept();
                socket.write(CONNECTED + "\n");
                System.out.println("CONNECTED");

                Connection connection = new Connection(socket);
                players.put(socket, test.pop());
                sendMessage(socket, NEXT_MOVE);
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
        if (message.equals(IMPOSSIBLE_MOVE)) {
            sender.write(IMPOSSIBLE_MOVE);
            return;
        }
        System.out.println("Test");
        for (Socket socket : players.keySet()) {
            sender.write(message + "\n");
            System.out.println(message.split(OK)[0]);
            socket.write(message.split(OK)[0] + "\n");
            System.out.println(message + " " + socket);
        }
    }


    private class Connection extends Thread {

        private Socket socket;
        private Board board;

        public Connection(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                int nextMoveRow = socket.read();
                makeNextMove(this.socket, nextMoveRow);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

