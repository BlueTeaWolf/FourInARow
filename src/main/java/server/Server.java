package server;

import game.Board;
import game.Player;
import game.Protocol;
import socketio.ServerSocket;
import socketio.Socket;

import java.io.IOException;
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

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        //TEST
        initializeBoard(5, 6, 4);
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
                Connection connection = new Connection(socket);
                connection.start();
                players.put(socket, test.pop());
                sendMessage(socket, NEXT_MOVE);
            } else {

                isListening = false;
                socket.write(NEXT_MOVE + "\n");
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
            sender.write(IMPOSSIBLE_MOVE + "\n");
        } else if (message.startsWith(OK)) {
            for (Socket socket : players.keySet()) {
                if (socket != sender) {
                    socket.write(NEXT_MOVE + "\n");
                }
                socket.write(message + "\n");
            }
        } else if (message.startsWith(START)) {
            for (Socket socket : players.keySet()) {
                socket.write(message + "\n");
            }
        }
    }


        private class Connection extends Thread {

            private Socket socket;
            private Board board;

            public Connection(Socket socket) {
                this.socket = socket;
            }

            public void run() {
                while (true) {
                    try {
                        int nextMoveRow = socket.read();
                        makeNextMove(this.socket, nextMoveRow);
                        System.out.println("Hellooooo");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

