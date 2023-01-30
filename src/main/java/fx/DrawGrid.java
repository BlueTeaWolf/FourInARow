package fx;

import client.Client;
import game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class DrawGrid {
    private JFrame frame;
    private MultiDraw multiDraw;
    private static JLabel turnLabel = new JLabel();
    private static JLabel winAndLose = new JLabel();

    public DrawGrid(int rows, int columns, Client client, int myWins, int losses, int chipsNeededWin) {
        frame = new JFrame("Four-In-A-Row");
        frame.setSize(750, 720);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(frame.getSize());
        frame.add(multiDraw = new MultiDraw(frame.getSize(), rows, columns,client));
        frame.add(turnLabel, BorderLayout.SOUTH);
        turnLabel.setText("Not your turn");
        frame.add(winAndLose, BorderLayout.NORTH);
        winAndLose.setText("Wins: " + myWins + "           Losses: " + losses + "                      chipsNeededWin: " + chipsNeededWin);
        frame.pack();
        frame.setVisible(true);
    }

    public void setTurn(boolean turn) {
        multiDraw.setTurn(turn);
        turnLabel.setText(turn ? "Your turn" : "Not your turn");
        if (turn) {
            turnLabel.setForeground(Color.MAGENTA);
        } else {
            turnLabel.setForeground(Color.DARK_GRAY);
        }
    }

    public void setChip(Player player, int row, int column) {
        multiDraw.setChip(player,row,column);
    }
    public void disposeUI() {
        frame.dispose();
    }

    public static class MultiDraw extends JPanel implements MouseListener {
        private static final int CELL_WIDTH = 100; // 40
        private Color[][] grid;
        private boolean turn;
        private Client client;

        public MultiDraw(Dimension dimension, int rows, int cols, Client client) {
            setSize(dimension);
            setPreferredSize(dimension);
            grid = new Color[rows][cols];
            addMouseListener(this);
            initializeGrid();
            this.client = client;
        }

        private void initializeGrid() {
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                    grid[row][col] = Color.WHITE;
                }
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            Dimension d = getSize();
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, d.width, d.height);
            int startX = 0;
            int startY = 0;

            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                    g2.setColor(grid[row][col]);
                    g2.fillOval(startX, startY, CELL_WIDTH, CELL_WIDTH);
                    startX = startX + CELL_WIDTH;

                }
                startX = 0;
                startY += CELL_WIDTH;
            }

            g2.setColor(new Color(255, 255, 255));

//            g2.drawString("Red's Turn", 400, 20);

        }

        private void setChip(Player player, int row, int column) {

            if (player.getPlayer() == Player.RED.getPlayer()) {
                grid[row][column] = Color.RED;
            } else {
                grid[row][column] = Color.YELLOW;
            }
            repaint();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (turn) {
                int x = e.getX() / CELL_WIDTH;
                try {
                    client.makeMove(x);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            turn = false;
            turnLabel.setText("Not your turn");
            turnLabel.setForeground(Color.DARK_GRAY);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        public void setTurn(boolean turn) {
            this.turn = turn;
        }
    }
}