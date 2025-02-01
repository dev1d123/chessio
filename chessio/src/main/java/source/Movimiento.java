package source;

public class Movimiento {
    private String pieza;  
    private int inicioFila, inicioColumna; 
    private int finFila, finColumna;
    
    public Movimiento(String pieza, int inicioFila, int inicioColumna, int finFila, int finColumna) {
        this.pieza = pieza;
        this.inicioFila = inicioFila;
        this.inicioColumna = inicioColumna;
        this.finFila = finFila;
        this.finColumna = finColumna;
    }

    
    public String getPieza() {
        return pieza;
    }

    public int getInicioFila() {
        return inicioFila;
    }

    public int getInicioColumna() {
        return inicioColumna;
    }

    public int getFinFila() {
        return finFila;
    }

    public int getFinColumna() {
        return finColumna;
    }

    
    @Override
    public String toString() {
        char columnaInicial = (char) ('a' + inicioColumna);
        char columnaFinal = (char) ('a' + finColumna);
        int filaInicial = 8 - inicioFila;
        int filaFinal = 8 - finFila;

        return pieza + ": " + columnaInicial + filaInicial + " -> " + columnaFinal + filaFinal;
    }
}
