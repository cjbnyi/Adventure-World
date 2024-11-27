package src.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.main.Agent;
import src.main.Coordinates;
import src.main.Map;
import src.main.PrologInterface;

public class GUIController {
    private GUI gameView;
    private Agent agent;
    private Coordinates playerPos;
    private PrologInterface prologInterface = new PrologInterface();
    private int numRows = Map.getInstance().getNumRows();
    private int numCols = Map.getInstance().getNumColumns();

    public GUIController() {
        this.agent = Agent.getInstance();
        this.playerPos = agent.getPlayerCoordinates();
        this.gameView = new GUI();
        setListeners();
    }

    private void setListeners() {
        this.agent = Agent.getInstance();
        this.playerPos = agent.getPlayerCoordinates();

        gameView.setUpListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                if (playerPos.x() > 0) {

                    playerPos = new Coordinates(playerPos.x() - 1, playerPos.y());
                    agent.setPlayerCoordinates(playerPos);

                    prologInterface.updateAgent();
    
                    gameView.setStatus("You moved up.");
                    gameView.updateTile();
                } else {
                    gameView.setStatus("Invalid move.");
                }
            }
        });

        gameView.setDownListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerPos.x() < numRows - 1) {

                    playerPos = new Coordinates(playerPos.x() + 1, playerPos.y());
                    agent.setPlayerCoordinates(playerPos);

                    prologInterface.updateAgent();
                    
                    gameView.setStatus("You moved down.");
                    gameView.updateTile();
                } else {
                    gameView.setStatus("Invalid move.");
                }
            }
        });

        gameView.setLeftListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerPos.y() > 0) {
                    
                    playerPos = new Coordinates(playerPos.x(), playerPos.y() - 1);
                    agent.setPlayerCoordinates(playerPos);

                    prologInterface.updateAgent();

                    gameView.setStatus("You moved left.");
                    gameView.updateTile();
                } else {
                    gameView.setStatus(" Invalid move.");
                }
            }
        });

        gameView.setRightListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerPos.y() < numCols - 1) {
                    
                    playerPos = new Coordinates(playerPos.x(), playerPos.y() + 1);
                    agent.setPlayerCoordinates(playerPos);

                    prologInterface.updateAgent();

                    gameView.setStatus("You moved right.");
                    gameView.updateTile();
                } else {
                    gameView.setStatus("Invalid move.");
                }
            }
        });

        gameView.setGrabGoldListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                prologInterface.assertGrabGold();
                gameView.getGrabGold().setEnabled(false);

                gameView.setGold(prologInterface.getNumOfGold());
                gameView.setStatus("You grabbed the gold.");
            }
        });

        gameView.setLeaveListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameView.setStatus("You left home.");

                int numGold = prologInterface.getNumOfGold();

                if (prologInterface.isWinner()) {
                    gameView.gameOver("Mission accomplished! " + numGold + " coins collected.");

                } else {
                    gameView.gameOver("Mission failed! Only " + numGold + " coins collected.");
                }
            }
        });
    }
}
