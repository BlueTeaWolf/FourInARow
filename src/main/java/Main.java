import game.Computer;
import game.Board;
import game.Player;
import game.Protocol;
import server.Server;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author BlueTeaWolf (Ole)
 */
public class Main implements Protocol {


    public static void main(String[] args) throws IOException {
        Server server = new Server(PORT);
        server.start();

//        Board board = new Board(10, 10, 4);
//        Scanner scanner = new Scanner(System.in);
//
//        Player player = Player.RED;
//        Player aiPlayer = Player.YELLOW;
//
//        Computer computer = new Computer(aiPlayer,player,10,10,4);
//
//        while(true) {
//            int playerMove = scanner.nextInt();
//            String message = board.nextMove(player,playerMove);
//            if(message.startsWith(Protocol.END)) {
//                return;
//            } else if(message.startsWith(Protocol.IMPOSSIBLE_MOVE)) {
//                continue;
//            }
//            message = board.nextMove(aiPlayer, computer.makeNextMove(playerMove));
//            if(message.startsWith(Protocol.END)) {
//                return;
//            }
//            System.out.println(board.getBoard());
//        }

//        while (true) {
//            int playerMove = scanner.nextInt();
//            String message = board.nextMove(player, playerMove);
//            if (message.startsWith(Protocol.END)) {
//                return;
//            } else if (message.startsWith(Protocol.IMPOSSIBLE_MOVE)) {
//                System.out.println(IMPOSSIBLE_MOVE + " try another one");
//                continue;
//            }
//            System.out.println(board.getBoard());
//            playerMove = scanner.nextInt();
//            message = board.nextMove(aiPlayer, playerMove);
//            if (message.startsWith(Protocol.END)) {
//                return;
//            } else if (message.startsWith(Protocol.IMPOSSIBLE_MOVE)) {
//                System.out.println(IMPOSSIBLE_MOVE + " try another one");
//                continue;
//            }
//            System.out.println(board.getBoard());
//        }
    }
}
