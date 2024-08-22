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
    
    private Player player;
            
    private boolean estaClavado;
    
    private int x;
    private int y;
    
    //new Pieza('-');
    
    public Pieza(char signo){
        this.signo = signo;
    }
    
    
    public Pieza(int x, int y, char signo, Player player){
        estaClavado = false;
        this.x = x;
        this.y = y;
        this.signo = signo;
        this.player = player;
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
    
    public ArrayList<Pair> getMovimientos() {
        return null;
    }

    
}
