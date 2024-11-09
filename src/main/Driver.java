package src.main;

import java.io.FileNotFoundException;

public class Driver {

    public static void main(String[] args) throws FileNotFoundException {

        Model model = new Model();
        Controller controller = new Controller(model);

        controller.start();
    }
}