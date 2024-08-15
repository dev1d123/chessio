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
    
    public Pieza(char s){
        signo = s;
    }
    public void setSigno(char c){
        signo = c;
    }
    public char getSigno(){
        return signo;
    }
}
