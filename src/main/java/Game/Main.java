package Game;

import java.util.Scanner;

/**
 * @author BlueTeaWolf (Ole)
 */
public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board board = new Board(5,6, 5);
        Player player = Player.RED;
        Player player2 = Player.YELLOW;
//        board.nextMove(player,-1);
//        board.nextMove(player,1);
//        board.nextMove(player,1);
//        board.nextMove(player,1);
//        board.nextMove(player,1);
//        board.nextMove(player,1);
//        board.nextMove(player,6);
        board.nextMove(player2, 4);
        int move;
        while(true) {
            move = scanner.nextInt();
            board.nextMove(player,move);
        }
    }

}
