package fx;

/**
 * @author BlueTeaWolf (Ole)
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SettingsUI extends JFrame implements ActionListener {

    private JTextField rowsField;
    private JTextField columnsField;
    private JTextField chipsForWinField;
    private JCheckBox resetPointsCheckBox;
    private JCheckBox playAgainstComputerCheckBox;
    private JButton startGameButton;
    private String gameSettings;

    public SettingsUI() {
        super("Four-In-A-Row-Settings");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel rowsLabel = new JLabel("Rows");
        mainPanel.add(rowsLabel);

        rowsField = new JTextField();
        rowsField.setPreferredSize(new Dimension(100, 20));
        mainPanel.add(rowsField);

        JLabel columnsLabel = new JLabel("Columns");
        mainPanel.add(columnsLabel);

        columnsField = new JTextField();
        columnsField.setPreferredSize(new Dimension(100, 20));
        mainPanel.add(columnsField);

        JLabel chipsForWinLabel = new JLabel("Chips For Win");
        mainPanel.add(chipsForWinLabel);

        chipsForWinField = new JTextField();
        chipsForWinField.setPreferredSize(new Dimension(100, 20));
        mainPanel.add(chipsForWinField);

        resetPointsCheckBox = new JCheckBox("Reset Points");
        mainPanel.add(resetPointsCheckBox);

        startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(this);
        mainPanel.add(startGameButton);

        add(mainPanel);
        setLayout(new FlowLayout());
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == startGameButton) {
            gameSettings = getGameSettings();
            dispose();
        }
    }

    public String getGameSettings() {
        int rows;
        int columns;
        int chipsForWin;

        try {
            rows = Integer.parseInt(rowsField.getText());
            columns = Integer.parseInt(columnsField.getText());
            chipsForWin = Integer.parseInt(chipsForWinField.getText());
        } catch (NumberFormatException exception) {
            rows = 6;
            columns = 7;
            chipsForWin = 4;
        }

        boolean resetPoints = resetPointsCheckBox.isSelected();
        return rows + ":" + columns + ":" + chipsForWin + ":" + resetPoints;
    }

}