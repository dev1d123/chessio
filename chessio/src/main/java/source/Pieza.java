/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

/**
 *
 * @author Windows
 */
public class Pieza {
    //private Jugador j
    //private boolean valido
    private char signo;
    private String color;
    
    private int x;
    private int y;
    
    public Pieza(char s){
        signo = s;
    }
        
    public Pieza(int x, int y, char signo){
        this.x = x;
        this.y = y;
        this.signo = signo;
    }
    public Pieza(char s, String color){
        signo = s;
        this.color = color;
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
    
}
