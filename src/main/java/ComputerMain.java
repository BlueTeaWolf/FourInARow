import game.Computer;
import game.Player;
import game.Protocol;
import socketio.Socket;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author BlueTeaWolf (Ole)
 */
public class ComputerMain implements Protocol {

    private Socket socket;
    private Computer computer;
    private boolean turn = false;
    private int lastTurn = -1;

    public ComputerMain() {
        socket = new Socket("localhost", Protocol.PORT);
        socket.connect();
    }

    public void test() {
        String message;
        boolean listening = true;
        Scanner scanner = new Scanner(System.in);
        while (listening) {
            try {
                System.out.println("Reading line");
                message = socket.readLine();
                System.out.println("recieved message");
                System.out.println(message);
                if (message.equals(NEXT_MOVE)) {
                    System.out.println("NEXT MOVE");
                    if (turn) {
                        System.out.println("Testing");
                        socket.write(computer.makeNextMove(-1));
                        System.out.println("SEND");
                        continue;
                    }
                    System.out.println("SEND");
                    socket.write(computer.makeNextMove(lastTurn));
                    continue;
                } else if (message.startsWith(END)) {
                    computer = null;
                    continue;
                } else if (message.startsWith(OK)) {
                    System.out.println("OK");
                    String[] move = message.split(":");
                    char player = message.charAt(3);
                    int y = Integer.parseInt(move[3]);

                    lastTurn = y;
                    System.out.printf("Move y: %d", y);
                    continue;
                } else if (message.equals(CONNECTED)) {
                    System.out.println(" Connected");
                } else if (message.equals(IMPOSSIBLE_MOVE)) {
                    System.out.println(IMPOSSIBLE_MOVE);
                    socket.write(computer.makeNextMove(-1));
                    continue;
                } else if (message.startsWith(START)) {
                    computer = null;
                    System.out.println("START");
                    String[] grid = message.split(":");
                    int row = Integer.parseInt(grid[1]);
                    int column = Integer.parseInt(grid[2]);

                    int chipsNeededWin = Integer.parseInt(grid[4]);

                    if(message.endsWith("RED")) {
                        computer = new Computer(Player.YELLOW, Player.RED, column, row, chipsNeededWin);
                    } else {
                        computer = new Computer(Player.RED, Player.YELLOW, column, row, chipsNeededWin);
                    }
                    turn = false;
                    continue;
                } else if (message.startsWith(SETTINGS)) {
                    System.out.println("Settings");
                    socket.write("0" + 6 + ":" + 7 + ":" + 4 + ":" + false + "\n");
                    if(message.endsWith("RED")) {
                        computer = new Computer(Player.YELLOW, Player.RED, 6, 7, 4);
                    } else {
                        computer = new Computer(Player.RED, Player.YELLOW, 6, 7, 4);
                    }

                    turn = true;
                    continue;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("End");
        }
    }

    public static void main(String[] args) {
        ComputerMain test = new ComputerMain();
        test.test();
    }
}
