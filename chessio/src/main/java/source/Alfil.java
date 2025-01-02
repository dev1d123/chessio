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
public class Alfil extends Pieza implements PiezaInterfaz{
    //Inicia el juego y cuando se corona
    //x, y, signo
    
    public Alfil(int x, int y, Player player, int textureID) {
        super(x, y, 'A', player, textureID);
        
        imgPath1 = TexturesPath.getPath(false, textureID, 3);
        imgPath2 = TexturesPath.getPath(true, textureID, 3);
    }

    @Override
    public ArrayList<Pair> getMovimientos(Tablero tabla) {
        ArrayList<Pair> res = new ArrayList<>();
        Juego.agregarMovimientos(tabla, res, this.getX(), this.getY(), 1, 1, this.getPlayer());  // ⬈
        Juego.agregarMovimientos(tabla, res, this.getX(), this.getY(), 1, -1, this.getPlayer()); // ⬉
        Juego.agregarMovimientos(tabla, res, this.getX(), this.getY(), -1, 1, this.getPlayer());  // ⬊
        Juego.agregarMovimientos(tabla, res, this.getX(), this.getY(), -1, -1, this.getPlayer()); // ⬋

        return res;
    }
    public String getM(Tablero tabla){
        ArrayList<Pair> res = this.getMovimientos(tabla);
        String ans = "";
        for(Pair p: res){
            ans+= "("+p.X+", "+p.Y+")\n";
        }
        return ans;
    }

    
}
