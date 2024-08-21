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
public class Peon extends Pieza implements PiezaInterfaz{
    
    //color 
    
    public Peon(int x, int y, Player player) {
        super(x, y, 'P', player);
    }
    
    @Override
    public ArrayList<Pair> getMovimientos() {
        return null;
    }

    
}
