package unsa.assets;
import java.io.Serializable;

import java.util.ArrayList;

public class ProfileC implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String password;

    private ArrayList<Integer> challenge;
    //private Settings config;
    private ArrayList<Partida> historial;

    public ProfileC(){
        this.name = "Unknown";
        this.challenge = new ArrayList<>();
        this.password = "ñññ";

       //this.config = new Settings();   
        this.historial = new ArrayList<>();

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }
    public ArrayList<Integer> getChallenge() {
        return challenge;
    }

    public void setChallenge(ArrayList<Integer> challenge) {
        this.challenge = challenge;
    }
    /*
    public Settings getConfig() {
        return config;
    }

    public void setConfig(Settings config) {
        this.config = config;
    }
 */
    public ArrayList<Partida> getHistorial() {
        return historial;
    }

    public void setHistorial(ArrayList<Partida> historial) {
        this.historial = historial;
    }


}
