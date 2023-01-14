package Game;

/**
 * @author BlueTeaWolf (Ole)
 */
public class Board {
    //Board is 6 * 7
    private final String[][] board;
    private final int maxRow;
    private final int maxColumn;
    private final int chipsNeededWin;

    public Board(int x, int y, int chipsNeededWin) {
        board = new String[x][y];
        maxRow = y - 1;
        maxColumn = x - 1;
        this.chipsNeededWin = chipsNeededWin;
    }

    public boolean nextMove(Player player, int rowMove) {
        if (rowMove < 0 || rowMove > maxRow || !(board[0][rowMove] == null)) {
            System.out.println("Not a possible move!");
            return false;
        }

        int column = 0;
        while (board[column][rowMove] == null && column < maxRow - 1) {
            column++;
        }
        if (board[column][rowMove] != null) {
            column--;
        }
        board[column][rowMove] = player.getPlayer();
        System.out.println("moved " + column + " " + rowMove);
        if (checkIfWon(player, column, rowMove)) {
            System.out.println(player + " won the game!");
            System.exit(1);
        }

        return true;
    }

    public boolean checkIfWon(Player player, int column, int row) {

        int check = 0;

        //horizontal
        for (int i = 0; i < chipsNeededWin * 2; i++) {
            if ((row - chipsNeededWin + i) > maxRow || (row - chipsNeededWin + i) < 0) {
                continue;
            }

            if (board[column][row - chipsNeededWin + i] == player.getPlayer()) {
                check++;
                if (check >= chipsNeededWin) {
                    System.out.println("Horizontal win");
                    return true;
                }
            } else {
                check = 0;
            }
        }

        //verticle
        check = 0;
        for (int i = 0; i < chipsNeededWin * 2; i++) {
            if ((column - chipsNeededWin + i) > maxColumn || (column - chipsNeededWin + i) < 0) {
                continue;
            }

            if (board[column - chipsNeededWin + i][row] == player.getPlayer()) {
                check++;
                if (check >= chipsNeededWin) {
                    System.out.println("Verticle win");
                    return true;
                }
            } else {
                check = 0;
            }
        }

//        top left to bottom right
        check = 0;
        int columnMove = 0;
        for (int rowMove = 0; rowMove < chipsNeededWin * 2; rowMove++) {
            if (rowMove - chipsNeededWin > maxRow || columnMove - chipsNeededWin > maxColumn ||
                    rowMove - chipsNeededWin < 0 || columnMove - chipsNeededWin < 0) {
                columnMove++;
                continue;
            }
            if (board[columnMove - chipsNeededWin][rowMove - chipsNeededWin] == player.getPlayer()) {
                check++;
                if (check >= chipsNeededWin) {
                    System.out.println("Left to bottom win");
                    return true;
                }
            } else {
                check = 0;
            }
            columnMove++;
        }

        //top right to bottom left
        check = 0;
        int rowMove = row + chipsNeededWin;
        for (int columnMove1 = 0; columnMove1 < chipsNeededWin * 2; columnMove1++) {
            if (columnMove1 - chipsNeededWin > maxColumn || rowMove > maxColumn ||
                    columnMove1 - chipsNeededWin < 0 || rowMove < 0) {
                rowMove--;
                continue;
            }
            if (board[rowMove][columnMove1 - chipsNeededWin] == player.getPlayer()) {
                check++;
                if (check >= chipsNeededWin) {
                    System.out.println("Right to bottom win");
                    return true;
                }
            } else {
                check = 0;
            }
            rowMove--;
        }

        return false;
    }

    public String[][] getBoard() {
        int column = 0;
        for (int j = 0; j <= maxColumn; j++) {
            for (int i = 0; i <= maxRow-1; i++) {
                System.out.print(board[column][i] + " ");
            }
            System.out.print(board[column][maxRow] + "\n");
            column++;
        }
        return board;
    }
}
