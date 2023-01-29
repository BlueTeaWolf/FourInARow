package fx;

import game.Player;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DrawGrid {
    private JFrame frame;

    public DrawGrid(int rows, int columns) {
        frame = new JFrame("Four-In-A-Row");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(frame.getSize());
        frame.add(new MultiDraw(frame.getSize(), rows, columns));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String... argv) {
        //TODO client start drawing grid after receiving the boards size
        new DrawGrid(6, 7);
    }

    public static class MultiDraw extends JPanel implements MouseListener {
        private static final int CELL_WIDTH = 100; // 40
        private final Color[][] grid;
        private boolean turn;


        public MultiDraw(Dimension dimension, int rows, int cols) {
            setSize(dimension);
            setPreferredSize(dimension);
            grid = new Color[rows][cols];
            addMouseListener(this);
            initializeGrid();
        }

        private void initializeGrid() {
            for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                    grid[row][col] = new Color(255, 255, 255);
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

            //2) draw grid here
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

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (turn) {
                int x = e.getX() / CELL_WIDTH;
                System.out.println(x);
            }
            turn = false;
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