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
public class Reina extends Pieza implements PiezaInterfaz{
        //Inicia el juego y cuando se corona
    //x, y, signo
    public Reina(int x, int y, Player player) {
        super(x, y, 'Q', player);
    }

    @Override
    public ArrayList<Pair> getMovimientos(Tablero tabla) {
        ArrayList<Pair> res = new ArrayList<>();
        Juego.agregarMovimientos(tabla, res, this.getX(), this.getY(), 1, 0, this.getPlayer());  // Derecha
        Juego.agregarMovimientos(tabla, res, this.getX(), this.getY(), -1, 0, this.getPlayer()); // Izquierda
        Juego.agregarMovimientos(tabla, res, this.getX(), this.getY(), 0, 1, this.getPlayer());  // Arriba
        Juego.agregarMovimientos(tabla, res, this.getX(), this.getY(), 0, -1, this.getPlayer()); // Abajo
        Juego.agregarMovimientos(tabla, res, this.getX(), this.getY(), 1, 1, this.getPlayer());  // ⬈
        Juego.agregarMovimientos(tabla, res, this.getX(), this.getY(), 1, -1, this.getPlayer()); // ⬉
        Juego.agregarMovimientos(tabla, res, this.getX(), this.getY(), -1, 1, this.getPlayer());  // ⬊
        Juego.agregarMovimientos(tabla, res, this.getX(), this.getY(), -1, -1, this.getPlayer()); // ⬋
        return res;
    }
    public String getM(){
        ArrayList<Pair> res = getMovimientos();
        String ans = "";
        for(Pair p: res){
            ans+= "("+p.X+", "+p.Y+")\n";
        }
        return ans;
    }
    
}
