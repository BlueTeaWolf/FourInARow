package client;

import game.Protocol;
import socketio.Socket;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author BlueTeaWolf (Ole)
 */
public class Client {

    private Socket socket;

    public Client(String host, int port) {
        socket = new Socket(host, port);
        connect();
    }

    public void connect() {
        if (socket.connect()) {
            GameClientListener gameClientListener = new GameClientListener(socket);
            gameClientListener.start();
        }
    }

    public void makeMove(int move) throws IOException {
        socket.write(move);
    }


    private class GameClientListener extends Thread implements Protocol {

        private Socket socket;
        private boolean listening;

        public GameClientListener(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            listening = true;
            String message;
            Scanner scanner = new Scanner(System.in);
            while (listening) {
                try {
                    System.out.println("Reading line");
                    message = socket.readLine();
                    System.out.println("recieved message");
                    if (message.equals(NEXT_MOVE)) {
                        System.out.println("Make next move: ");
                        makeMove(scanner.nextInt());
                        System.out.println("made move");
                        continue;
                    } else if (message.startsWith(END)) {
                        String endResult = message.split(":")[1];
                        if (endResult.equals(WON)) {
                            System.out.println("You won");
                        } else {
                            System.out.println("You lost");
                        }
                    } else if (message.startsWith(NEXT_MOVE)) {
                        String[] move = message.split(":");
                        int x = Integer.parseInt(move[1]);
                        int y = Integer.parseInt(move[2]);
                    } else if (message.startsWith(OK)) {
                        String[] move = message.split(":");
                        int x = Integer.parseInt(move[1]);
                        int y = Integer.parseInt(move[2]);
                        System.out.printf("Move x: %d%n Move y: %d", x, y);
                    } else if (message.equals(CONNECTED)) {
                        System.out.println(" Connected");
                    } else if (message.equals(IMPOSSIBLE_MOVE)) {
                        System.out.println(IMPOSSIBLE_MOVE);
                        makeMove(scanner.nextInt());
                        continue;
                    } else if (message.startsWith(START)) {

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("End");
            }
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void setListening(boolean listening) {
            this.listening = listening;
        }
    }

}
