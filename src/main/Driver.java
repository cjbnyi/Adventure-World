package src.main;

import java.io.FileNotFoundException;

public class Driver {

    public static void main(String[] args) throws FileNotFoundException {

        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);

        controller.start();
    }
}