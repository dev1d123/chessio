/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

import java.util.ArrayList;

import interfazGrafica.TexturesPath;

/**
 *
 * @author Windows
 */
public class Reina extends Pieza implements PiezaInterfaz{
        //Inicia el juego y cuando se corona
    //x, y, signo
    public Reina(int x, int y, Player player, int textureID) {
        super(x, y, 'Q', player, textureID);
        imgPath1 = TexturesPath.getPath(false, textureID, 4);
        imgPath2 = TexturesPath.getPath(true, textureID, 4);
    }

    @Override
    public ArrayList<Pair> getMovimientos(Tablero tabla, ArrayList<Movimiento> movJ1, ArrayList<Movimiento> movJ2) {
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
    public String getM(Tablero tabla, ArrayList<Movimiento> movJ1, ArrayList<Movimiento> movJ2){
        ArrayList<Pair> res = getMovimientos(tabla, movJ1, movJ2);
        String ans = "";
        for(Pair p: res){
            ans+= "("+p.X+", "+p.Y+")\n";
        }
        return ans;
    }
    
}
