package src.main;

import java.io.FileNotFoundException;
import src.gui.GUIController;

public class Driver {

    public static void main(String[] args) throws FileNotFoundException {

        Model model = new Model();
        Controller controller = new Controller(model);
        
        controller.start();
        GUIController guiController = new GUIController();
    }
}