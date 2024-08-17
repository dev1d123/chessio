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
public class Caballo extends Pieza implements PiezaInterfaz{
        //Inicia el juego y cuando se corona
    //x, y, signo
    public Caballo(int x, int y) {
        super(x, y, 'C');
    }

    @Override
    public ArrayList<Pair> getMovimientos() {
        ArrayList<Pair> res = new ArrayList<>();
        
        return res;
    }
}
