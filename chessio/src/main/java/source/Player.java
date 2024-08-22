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
    private boolean posicion;
    //posicion = true -> arriba
    //posicion = false -> abajo
    public Player(String nombre, boolean color, boolean posicion) {
        this.nombre = nombre;
        this.color = color;
        this.posicion = posicion;
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
