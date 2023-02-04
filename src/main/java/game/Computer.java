package game;

import java.util.Random;

/**
 * @author BlueTeaWolf (Ole)
 */
public class Computer {

    private final Player ai;
    private final Player enemy;
    private final Board board;

    public Computer(Player player, Player playingPlayer, int column, int row, int neededChipsToWin) {
        this.ai = player;
        this.enemy = playingPlayer;
        board = new Board(column,row,neededChipsToWin);
    }

    public int makeNextMove(int lastRow) {
        Random randm = new Random();

        if(!(lastRow <= -1)) {
            return board.getMaxRow() / 2;
        } else {
            board.nextMove(enemy, lastRow);
        }

        String result;
        for (int j = 0; j < board.getMaxRow() +1; j++) {
            result = board.nextMove(ai, j);
            if (result.startsWith(Protocol.END)) {
                return j;
            }
            board.removeMove(j);
        }

        for (int i = 0; i < board.getMaxRow() +1; i++) {
            result = board.nextMove(enemy,i);
            board.removeMove(i);
            if(result.startsWith(Protocol.END)) {
                board.nextMove(ai,i);
                return i;
            }
        }

        int random = randm.nextInt(board.getMaxRow());
        while(board.nextMove(ai,random).equals(Protocol.IMPOSSIBLE_MOVE)) {
            board.removeMove(random);
            random = randm.nextInt(board.getMaxRow());
        }
        return random;
    }
}
