package src.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import src.main.Agent;
import src.main.Coordinates;

public class GUI {
    private JFrame frame;
    private JPanel[][] gridPanels; // Store grid panels to update colors later
    private Coordinates homePos, playerPos;
    private JButton upArrow, downArrow, leftArrow, rightArrow, grabGold, leave;
    private JLabel character = new JLabel(new ImageIcon("resources/graphics/leprechaun.png"));
    private JLabel home = new JLabel(new ImageIcon("resources/graphics/home.png"));
    private JTextField totalGold = new JTextField();
    private JTextField status = new JTextField();
    private Agent agent = Agent.getInstance();

     public GUI() {
        // initializing positions
        this.playerPos = agent.getHomeCoordinates();
        this.homePos = agent.getHomeCoordinates();

        // layout adjustments
        this.character.setBounds(5, 5, 76, 76);
        this.home.setBounds(5, 5, 76, 76);

        frame = new JFrame();
        frame.setSize(850, 700);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Center panel for grid
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 5, 10, 10));
        panel.setBounds(20, 20, 500, 500);
        frame.add(panel);

        // Other panels
        JPanel titlePanel = createColoredPanel(Color.GRAY);
        titlePanel.setLayout(null);
        titlePanel.setBounds(540, 20, 275, 120);

        JPanel arrowPanel = createColoredPanel(Color.GRAY);
        arrowPanel.setLayout(null);
        arrowPanel.setBounds(540, 270, 275, 250);

        JPanel menuPanel = createColoredPanel(Color.GRAY);
        menuPanel.setLayout(null);
        menuPanel.setBounds(20, 540, 795, 100);

        grabGold = new JButton("Grab Gold");
        grabGold.setLayout(null);
        grabGold.setBounds(540, 160, 275, 40);
        grabGold.setEnabled(false);

        leave = new JButton("Leave");
        leave.setLayout(null);
        leave.setBounds(540, 210, 275, 40);

        // Arrow buttons
        upArrow = new JButton();
        upArrow.setIcon(new ImageIcon("resources/graphics/uparrow.png"));
        upArrow.setBounds(115, 20, 45, 77);

        downArrow = new JButton();
        downArrow.setBounds(115, 153, 45, 77);
        downArrow.setIcon(new ImageIcon("resources/graphics/downarrow.png"));

        leftArrow = new JButton();
        leftArrow.setBounds(20, 100, 77, 45);
        leftArrow.setIcon(new ImageIcon("resources/graphics/leftarrow.png"));

        rightArrow = new JButton();
        rightArrow.setBounds(178, 100, 77, 45);
        rightArrow.setIcon(new ImageIcon("resources/graphics/rightarrow.png"));

        // Initialize a 5x5 grid
        gridPanels = new JPanel[5][5];
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                Coordinates newTile = new Coordinates(r, c);

                JPanel gridPanel = new JPanel();
                gridPanel.setBackground((newTile == playerPos) ? Color.PINK : Color.GRAY); // Sets the color of the grid depending on player position
                gridPanel.setLayout(null);

                if (newTile.x() == playerPos.x() && newTile.y() == playerPos.y()) {
                    gridPanel.add(home);
                    gridPanel.add(character);
                    gridPanel.setComponentZOrder(character, 0);
                } 

                panel.add(gridPanel);
                gridPanels[r][c] = gridPanel;
            }
        }

        JLabel goldLabel = new JLabel("Gold Collected: ");
        goldLabel.setBounds(50, 35, 100, 30);

        // Extra space is added just so the placement of the text will not look awkward
        totalGold.setText("  0");
        totalGold.setBounds(150, 35, 70, 30);
        totalGold.setFocusable(false);

        JLabel statusLabel = new JLabel("Status: ");
        statusLabel.setBounds(370, 35, 70, 30);

        status.setText(" You are at home.");
        status.setBounds(420, 35, 320, 30);
        status.setFocusable(false);

        menuPanel.add(statusLabel);
        menuPanel.add(status);
        menuPanel.add(goldLabel);
        menuPanel.add(totalGold);
        frame.add(arrowPanel);
        frame.add(titlePanel);
        frame.add(menuPanel);
        frame.add(panel);
        frame.add(grabGold);
        frame.add(leave);
        arrowPanel.add(upArrow);
        arrowPanel.add(downArrow);
        arrowPanel.add(leftArrow);
        arrowPanel.add(rightArrow);
        frame.setVisible(true);
    }

    // Method that simplifies the creation of panels
    public JPanel createColoredPanel(Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setOpaque(true); 
        return panel;
    }

    public void updateTile(Agent agent) {
        ArrayList<Coordinates> breezePos = new ArrayList<>(agent.getKnownBreezeCoordinates());
        ArrayList<Coordinates> goldPos = new ArrayList<>(agent.getKnownGoldCoordinates());
        ArrayList<Coordinates> pitPos = new ArrayList<>(agent.getKnownPitCoordinates());
        ArrayList<Coordinates> grabbedGoldPos = new ArrayList<>(agent.getGrabbedGoldCoordinates());

        Coordinates newPlayerPos = agent.getPlayerCoordinates();
        int row = newPlayerPos.x();
        int col = newPlayerPos.y();

        int previousRow = playerPos.x();
        int previousCol = playerPos.y();

        // Reset previous tile color
        gridPanels[previousRow][previousCol].setBackground(Color.DARK_GRAY);
        gridPanels[previousRow][previousCol].remove(character);

        // Update new tile color
        gridPanels[row][col].setBackground(Color.PINK);
        gridPanels[row][col].add(character);
        gridPanels[row][col].setComponentZOrder(character, 0);

        // Player can leave once it is at home
        if (this.homePos.x() == newPlayerPos.x() && this.homePos.y() == newPlayerPos.y()) {
            leave.setEnabled(true);
            status.setText(" You are at home.");
        } else {
            leave.setEnabled(false);
        }

        // If the new position falls on a tile that is either the gold, breeze, or pit
        if (breezePos.contains(newPlayerPos) && goldPos.contains(newPlayerPos) && !grabbedGoldPos.contains(newPlayerPos)) {
            JLabel breeze = new JLabel(new ImageIcon("resources/graphics/breeze.png"));
            JLabel gold = new JLabel(new ImageIcon("resources/graphics/gold.png"));
            gridPanels[row][col].setLayout(null);
            breeze.setBounds(5, 45, 80, 40);
            gold.setBounds(5, 5, 80, 40);
            gridPanels[row][col].add(breeze);
            gridPanels[row][col].add(gold);
            status.setText(" You perceive a glitter and a breeze.");
            grabGold.setEnabled(true);
        } else if (breezePos.contains(newPlayerPos)) {
            JLabel breeze = new JLabel(new ImageIcon("resources/graphics/breeze.png"));
            breeze.setBounds(5, 5, 76, 76);
            status.setText(" You perceive a breeze.");
            gridPanels[row][col].setLayout(null);
            gridPanels[row][col].add(breeze);
            breeze.setBounds(5, 5, 76, 76);
            grabGold.setEnabled(false);
        } else if (goldPos.contains(newPlayerPos) && !grabbedGoldPos.contains(newPlayerPos)) {
            JLabel gold = new JLabel(new ImageIcon("resources/graphics/gold.png"));
            status.setText(" You perceive a  glitter.");
            gridPanels[row][col].setLayout(null);
            gridPanels[row][col].add(gold);
            gold.setBounds(5, 5, 76, 76);
            grabGold.setEnabled(true);
        } else if (pitPos.contains(newPlayerPos)) {
            JLabel pit = new JLabel(new ImageIcon("resources/graphics/pit.png"));
            pit.setBounds(5, 5, 76, 76);
            gridPanels[row][col].setLayout(null);
            gridPanels[row][col].add(pit);
            status.setText(" You fell into a pit.");
            grabGold.setEnabled(false);
        }  

        // Update current tile with the new position
        playerPos = newPlayerPos; 
    }


    // Listeners
    public void setUpListener(ActionListener actionListener) {
		this.upArrow.addActionListener(actionListener);
	}

    public void setDownListener(ActionListener actionListener) {
		this.downArrow.addActionListener(actionListener);
	}

    public void setLeftListener(ActionListener actionListener) {
		this.leftArrow.addActionListener(actionListener);
	}

    public void setRightListener(ActionListener actionListener) {
		this.rightArrow.addActionListener(actionListener);
	}

    public void setGrabGoldListener(ActionListener actionListener) {
		this.grabGold.addActionListener(actionListener);
	}

    public void setLeaveListener(ActionListener actionListener) {
		this.leave.addActionListener(actionListener);
	}

    // Setters and getters
    public void setGold(int num) {
        totalGold.setText(" " + num);
    }

    public void setStatus(String text) {
        status.setText(" " + text);
    }

    public JFrame getFrame() {
        return this.frame;
    }
}
