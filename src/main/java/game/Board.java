package game;

/**
 * @author BlueTeaWolf (Ole)
 */
public class Board implements Protocol {
    //Board is 6 * 7
    private final char[][] board;
    private final int maxRow;
    private final int maxColumn;
    private final int chipsNeededWin;

    public Board(int x, int y, int chipsNeededWin) {
        board = new char[x][y];
        maxRow = y - 1;
        maxColumn = x - 1;
        this.chipsNeededWin = chipsNeededWin;
    }

    public String nextMove(Player player, int rowMove) {
        if (rowMove < 0 || rowMove > maxRow || !(board[0][rowMove] == 0)) {
            System.out.println("Not a possible move!");
            return IMPOSSIBLE_MOVE;
        }

        int column = 0;
        while (board[column][rowMove] == 0 && column < maxColumn) {
            column++;
        }
        if (board[column][rowMove] != 0) {
            column--;
        }
        board[column][rowMove] = player.getPlayer();
        if (checkIfWon(player, column, rowMove)) {
            return END + WON;
        }

        return OK + column + ":" + rowMove;
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
                    System.out.println("Vertical win");
                    return true;
                }
            } else {
                check = 0;
            }
        }

//        top left to down right
        check = 0;
        int columnMove = column - chipsNeededWin;
        for (int rowMove = row - chipsNeededWin; rowMove < chipsNeededWin * 2; rowMove++) {
            if (rowMove > maxRow || columnMove > maxColumn || rowMove < 0 || columnMove < 0) {
                columnMove++;
                continue;
            }
            if (board[columnMove][rowMove] == player.getPlayer()) {
                check++;
                if (check >= chipsNeededWin) {
                    System.out.println("Top left to bottom right win");
                    return true;
                }
            } else {
                check = 0;
            }
            columnMove++;
        }

        //top right to bottom left
        check = 0;
        int rowMove = row + chipsNeededWin-1;
        for (int i = 1; i < chipsNeededWin * 2; i++) {
            if (rowMove > maxRow || (column - chipsNeededWin + i) > maxColumn || rowMove < 0 || (column - chipsNeededWin + i) - chipsNeededWin < 0) {
                rowMove--;
                continue;
            }
            if (board[column - chipsNeededWin + i][rowMove] == player.getPlayer()) {
                check++;
                if (check >= chipsNeededWin) {
                    System.out.println("Top right to bottom left win");
                    return true;
                }
            } else {
                check = 0;
            }
            rowMove--;
        }

        return false;
    }

    public void removeMove(int row) {
        if (row > maxRow) {
            return;
        }
        int column = 0;
        while (board[column][row] == 0 && column < maxColumn) {
            column++;
        }
        board[column][row] = 0;
    }

    public int getMaxRow() {
        return maxRow;
    }

    public int getMaxColumn() {
        return maxColumn;
    }

    public char[][] getBoard() {
        int column = 0;
        for (int j = 0; j <= maxColumn; j++) {
            for (int i = 0; i <= maxRow - 1; i++) {
                System.out.print(board[column][i] + " ");
            }
            System.out.print(board[column][maxRow] + "\n");
            column++;
        }
        return board;
    }
}
