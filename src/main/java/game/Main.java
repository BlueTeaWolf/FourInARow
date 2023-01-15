package game;

import server.Server;

import java.io.IOException;

/**
 * @author BlueTeaWolf (Ole)
 */
public class Main implements Protocol{


    public static void main(String[] args) throws IOException {
        Server server = new Server(PORT);
        server.start();

//        board.nextMove(player,-1);
//        board.nextMove(player,1);
//        board.nextMove(player,1);
//        board.nextMove(player,1);
//        board.nextMove(player,1);
//        board.nextMove(player,1);
//        board.nextMove(player,6);
//        board.nextMove(player2, 4);
//        int move;
//        while(true) {
//            move = scanner.nextInt();
//            board.nextMove(player,move);
//        }


    }
}
