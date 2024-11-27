package src.main;

import java.io.FileReader;

public class Model {

    private final PrologInterface prologInterface;

    public Model() {
        this.prologInterface = new PrologInterface();
    }

    public void encodeFullMapInformation(FileReader fileReader) {
        prologInterface.encodeFullMapInformation(fileReader);
    }

    // GETTERS
    public Map getMap() {
        return Map.getInstance();
    }

    public Agent getAgent() {
        return Agent.getInstance();
    }
}