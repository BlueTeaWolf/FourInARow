package client;

import fx.DrawGrid;
import fx.SettingsUI;
import game.Player;
import game.Protocol;
import socketio.Socket;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author BlueTeaWolf (Ole)
 */
public class Client {

    private Socket socket;
    private int wins = 0;
    private int losses = 0;

    public Client(String host, int port) {
        socket = new Socket(host, port);
        connect();
    }

    public void connect() {
        if (socket.connect()) {
            GameClientListener gameClientListener = new GameClientListener(socket, this);
            gameClientListener.start();
        }
    }

    public void makeMove(int move) throws IOException {
        socket.write(move);
        System.out.println("Move: " + move);
    }


    private class GameClientListener extends Thread implements Protocol {

        private Socket socket;
        private boolean listening;
        private DrawGrid drawGrid;
        private Client client;
        private boolean existUI;

        public GameClientListener(Socket socket, Client client) {
            this.socket = socket;
            this.client = client;
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
                        drawGrid.setTurn(true);
                        //maybe saying that it is your turn
                        System.out.println("made move");
                        continue;
                    } else if (message.startsWith(END)) {
                        String[] endResult = message.split(":");
                        char player;
                        if(message.endsWith(WON)) {
                            wins++;
                            player = message.charAt(message.length()-5);
                        } else {
                            losses++;
                            player = message.charAt(message.length()-6);
                        }
                        System.out.println(message);
                        int x = Integer.parseInt(endResult[2]);
                        int y = Integer.parseInt(endResult[1]);
                        if (player == 'R') {
                            drawGrid.setChip(Player.RED, x, y);
                        } else {
                            drawGrid.setChip(Player.YELLOW, x, y);
                        }
                    } else if (message.startsWith(OK)) {
                        String[] move = message.split(":");
                        char player = message.charAt(3);
                        int x = Integer.parseInt(move[2]);
                        int y = Integer.parseInt(move[3]);

                        if (player == 'R') {
                            drawGrid.setChip(Player.RED, x, y);
                        } else {
                            drawGrid.setChip(Player.YELLOW, x, y);
                        }

                        System.out.printf("Move x: %d%n Move y: %d", x, y);
                    } else if (message.equals(CONNECTED)) {
                        System.out.println(" Connected");
                    } else if (message.equals(IMPOSSIBLE_MOVE)) {
                        System.out.println(IMPOSSIBLE_MOVE);
                        drawGrid.setTurn(true);
                        System.out.println("made move");
                        continue;
                    } else if (message.startsWith(START)) {
                        makeUI(message);
                    } else if (message.equals(SETTINGS)) {
                        SettingsUI settingsUI = new SettingsUI();
                        settingsUI.setVisible(true);
                        while (settingsUI.isVisible()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {

                            }
                        }
                        String settings = settingsUI.getGameSettings();
                        System.out.println(settings);
                        socket.write("0" + settings + "\n");
                        System.out.println("Settings complete");
                        System.out.println(settings + " <---");
                        continue;
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
        private void makeUI(String message) {
            String[] grid = message.split(":");
            int row = Integer.parseInt(grid[1]);
            int column = Integer.parseInt(grid[2]);

            int chipsNeededWin = Integer.parseInt(grid[4]);

            if(grid[3].equals("true")) {
                wins = 0;
                losses = 0;
            }

            if(existUI) {
                drawGrid.disposeUI();
            }
            drawGrid = new DrawGrid(row, column, client, wins, losses, chipsNeededWin);
            existUI = true;
        }
        public void setListening(boolean listening) {
            this.listening = listening;
        }
    }

}
