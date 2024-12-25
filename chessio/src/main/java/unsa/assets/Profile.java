package unsa.assets;
import java.io.Serializable;

import java.util.ArrayList;

public class Profile implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private ArrayList<Integer> challenge;
    private Settings config;
    private ArrayList<Partida> historial;

    public Profile(){
        this.name = "Unknown";
        this.challenge = new ArrayList<>();
        this.config = new Settings();   
        this.historial = new ArrayList<>();

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getChallenge() {
        return challenge;
    }

    public void setChallenge(ArrayList<Integer> challenge) {
        this.challenge = challenge;
    }

    public Settings getConfig() {
        return config;
    }

    public void setConfig(Settings config) {
        this.config = config;
    }

    public ArrayList<Partida> getHistorial() {
        return historial;
    }

    public void setHistorial(ArrayList<Partida> historial) {
        this.historial = historial;
    }


}
