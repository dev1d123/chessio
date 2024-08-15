/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

/**
 *
 * @author Windows
 */
public class Main {
    public static void main(String args[]){
        Tablero t = new Tablero();
        t.agregarAst(7,7);
        t.agregarAst(3,2);
        t.agregarAst(5,4);
        t.imprimirTabla();
    }
}
