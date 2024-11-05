package src.main;

import java.util.Scanner;

public class Model {

    private PrologInterface prologInterface;

    public Model() {
        this.prologInterface = new PrologInterface();
    }

    public void encodeFullMapInformation(Scanner scanner) {
        prologInterface.encodeFullMapInformation(scanner);
    }

    public void getFullMapInformation() {

    }

    public void getAgentKnowledge() {

    }
}