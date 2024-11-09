package src.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an agent with a set of known environment information at any given point.
 * Employs the Singleton design pattern.
 */
public class Agent {

    private static Agent instance = null;

    private Coordinates playerCoordinates;
    private final Coordinates homeCoordinates;
    private final ArrayList<Coordinates> knownPitCoordinates;
    private final ArrayList<Coordinates> knownBreezeCoordinates;
    private final ArrayList<Coordinates> knownGoldCoordinates;
    private final ArrayList<Coordinates> grabbedGoldCoordinates;

    private Agent(Coordinates homeCoordinates) {
        this.playerCoordinates = homeCoordinates;
        this.homeCoordinates = homeCoordinates;
        this.knownPitCoordinates = new ArrayList<>();
        this.knownBreezeCoordinates = new ArrayList<>();
        this.knownGoldCoordinates = new ArrayList<>();
        this.grabbedGoldCoordinates = new ArrayList<>();
    }

    public static void initializeInstance(Coordinates homeCoordinates) {
        if (instance != null) {
            throw new IllegalStateException("Agent has already been initialized." +
                    "Retrieve it through getInstance().");
        }
        instance = new Agent(homeCoordinates);
    }

    public static Agent getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Agent has not yet been initialized." +
                    "Initialize it through initializeInstance().");
        }
        return instance;
    }

    // GETTERS
    public Coordinates getPlayerCoordinates() {
        return playerCoordinates;
    }

    public Coordinates getHomeCoordinates() {
        return homeCoordinates;
    }

    public List<Coordinates> getKnownPitCoordinates() {
        return Collections.unmodifiableList(knownPitCoordinates);
    }

    public List<Coordinates> getKnownBreezeCoordinates() {
        return Collections.unmodifiableList(knownBreezeCoordinates);
    }

    public List<Coordinates> getKnownGoldCoordinates() {
        return Collections.unmodifiableList(knownGoldCoordinates);
    }

    public int getNumOfGold() {
        return this.grabbedGoldCoordinates.size();
    }

    public List<Coordinates> getGrabbedGoldCoordinates() {
        return this.grabbedGoldCoordinates;
    }

    // MODIFIERS
    public void setPlayerCoordinates(Coordinates playerCoordinates) {
        this.playerCoordinates = playerCoordinates;
    }

    public void addKnownPitCoordinates(Coordinates pitCoordinates) {
        this.knownPitCoordinates.add(pitCoordinates);
    }

    public void addKnownBreezeCoordinates(Coordinates breezeCoordinates) {
        this.knownBreezeCoordinates.add(breezeCoordinates);
    }

    public void addKnownGoldCoordinates(Coordinates goldCoordinates) {
        this.knownGoldCoordinates.add(goldCoordinates);
    }

    public void addGrabbedGoldCoordinates(Coordinates grabbedGoldCoordinates) {
        this.grabbedGoldCoordinates.add(grabbedGoldCoordinates);
    }

    // UTILITY FUNCTIONS
    // TODO: Implement the method, printing necessary agent information for debugging purposes.
    public static void printAgentInformation() {
        System.out.println("\n[Printing agent information]");
        System.out.println("printAgentInformation() to be implemented!");
    }
}