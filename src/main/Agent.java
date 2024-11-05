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

    private Coordinate playerCoordinates;
    private final Coordinate homeCoordinates;
    private final ArrayList<Coordinate> knownPitCoordinates;
    private final ArrayList<Coordinate> knownBreezeCoordinates;
    private final ArrayList<Coordinate> knownGoldCoordinates;

    private Agent(Coordinate homeCoordinates) {
        this.playerCoordinates = homeCoordinates;
        this.homeCoordinates = homeCoordinates;
        this.knownPitCoordinates = new ArrayList<>();
        this.knownBreezeCoordinates = new ArrayList<>();
        this.knownGoldCoordinates = new ArrayList<>();
    }

    public static Agent initializeInstance(Coordinate homeCoordinates) {
        if (instance != null) {
            throw new IllegalStateException("Agent has already been initialized." +
                    "Retrieve it through getInstance().");
        }
        instance = new Agent(homeCoordinates);
        return instance;
    }

    public static Agent getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Agent has not yet been initialized." +
                    "Initialize it through initializeInstance().");
        }
        return instance;
    }

    public Coordinate getPlayerCoordinates() {
        return playerCoordinates;
    }

    public Coordinate getHomeCoordinates() {
        return homeCoordinates;
    }

    public List<Coordinate> getKnownPitCoordinates() {
        return Collections.unmodifiableList(knownPitCoordinates);
    }

    public List<Coordinate> getKnownBreezeCoordinates() {
        return Collections.unmodifiableList(knownBreezeCoordinates);
    }

    public List<Coordinate> getKnownGoldCoordinates() {
        return Collections.unmodifiableList(knownGoldCoordinates);
    }

    public void setPlayerCoordinates(Coordinate playerCoordinates) {
        this.playerCoordinates = playerCoordinates;
    }

    public void addKnownPitCoordinates(Coordinate pitCoordinates) {
        this.knownPitCoordinates.add(pitCoordinates);
    }

    public void addKnownBreezeCoordinates(Coordinate breezeCoordinates) {
        this.knownBreezeCoordinates.add(breezeCoordinates);
    }

    public void addKnownGoldCoordinates(Coordinate goldCoordinates) {
        this.knownGoldCoordinates.add(goldCoordinates);
    }
}