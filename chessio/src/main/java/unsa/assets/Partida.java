package unsa.assets;

import java.io.Serializable;
import java.util.List;

public class Partida implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<String> movimientos;
    public List<String> getMovimientos() {
        return movimientos;
    }

}   
