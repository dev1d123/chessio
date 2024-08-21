/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

/**
 *
 * @author Windows
 */
public class Player{
    private String nombre;
    private boolean color;
    //color == true -> blancas
    //color == false -> negras

    public Player(String nombre, boolean color) {
        this.nombre = nombre;
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isWhite() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }
    
}
