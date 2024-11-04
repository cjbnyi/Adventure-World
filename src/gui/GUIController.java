import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIController {
    private GUI gameView;
    private Driver driver;

    public GUIController(GUI gameView) {
        this.driver = new Driver();
        this.gameView = gameView;
        setListeners();
    }

    private void setListeners() {
        gameView.setUpListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (driver.getRow() > 0) {
                    driver.setRow(driver.getRow() - 1);
    
                    gameView.setStatus("You moved up.");
                    updateScreen();
                } else {
                    gameView.setStatus("Invalid move.");
                }
            }
        });

        gameView.setDownListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (driver.getRow() < 4) {
                    driver.setRow(driver.getRow() + 1);
                    
                    gameView.setStatus("You moved down.");
                    updateScreen();
                } else {
                    gameView.setStatus("Invalid move.");
                }
            }
        });

        gameView.setLeftListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (driver.getCol() > 0) {
                    driver.setCol(driver.getCol() - 1);
                    
                    gameView.setStatus("You moved left.");
                    updateScreen();
                } else {
                    gameView.setStatus(" Invalid move.");
                }
            }
        });

        gameView.setRightListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (driver.getCol() < 4) {
                    driver.setCol(driver.getCol() + 1);
                    
                    gameView.setStatus("You moved right.");
                    updateScreen();
                } else {
                    gameView.setStatus("Invalid move.");
                }
            }
        });

        gameView.setGrabGoldListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                driver.addGold();  

                gameView.setGold(driver.getGold());
                gameView.setStatus("You grabbed the gold.");
            }
        });

        gameView.setLeaveListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameView.setStatus("You left home.");
            }
        });
    }

    private void updateScreen() {
        gameView.updateTile(driver.getRow(), driver.getCol());
    }
}
