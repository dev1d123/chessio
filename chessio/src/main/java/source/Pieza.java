/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

import java.util.ArrayList;

/**
 *
 * @author Windows
 */
public class Pieza{
    //private Jugador j
    //private boolean valido
    private char signo;
    public int textureID;
    private Player player;
            
    private boolean estaClavado;
    
    protected String imgPath1;
    protected String imgPath2;

    protected ArrayList<String> movimientos;

    private int x;
    private int y;


    //new Pieza('-');
    
    public Pieza(char signo){
        this.signo = signo;
    }
    
    public void setPath(String v1, String v2){
        imgPath1 = v1;
        imgPath2 = v2;
    }    
    public String getPath1(int textureID){
        return imgPath1;
    }
    public String getPath2(int textureID){
        return imgPath2;
    }
    public Pieza(int x, int y, char signo, Player player, int textureID){
        estaClavado = false;
        this.x = x;
        this.y = y;
        this.signo = signo;
        this.player = player;
        this.textureID = textureID;

    }

    public void setSigno(char c){
        signo = c;
    }
    public char getSigno(){
        return signo;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public ArrayList<Pair> getMovimientos(Tablero tabla, ArrayList<Movimiento> movJ1, ArrayList<Movimiento> movJ2) {
        return null;
    }
    public Pieza(Pieza otra) {
        this.signo = otra.signo;
        this.textureID = otra.textureID;
        this.player = otra.player; // Si Player necesita una copia profunda, crea un constructor de copia para Player
        this.estaClavado = otra.estaClavado;
        this.imgPath1 = otra.imgPath1;  
        this.imgPath2 = otra.imgPath2;
    
        // Copiar la lista de movimientos (si es mutable, usa una nueva instancia)
        this.movimientos = (otra.movimientos != null) ? new ArrayList<>(otra.movimientos) : null;
    
        this.x = otra.x;
        this.y = otra.y;
    }
    public Pieza clonar() {
        try {
            return (Pieza) this.clone(); // Clonación superficial
        } catch (CloneNotSupportedException e) {
            return new Pieza(this); // En caso de error, usa el constructor de copia
        }
    }

    public String obtenerNombreClase() {
        return this.getClass().getSimpleName(); 
    }
    
}
