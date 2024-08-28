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
    public Caballo(int x, int y, Player player) {
        super(x, y, 'C', player);
        imgPath1 = "white-knight.png";
        imgPath2 = "black-knight.png";
    }

    @Override
    public ArrayList<Pair> getMovimientos(Tablero tabla) {
        ArrayList<Pair> res = new ArrayList<>();
        //A partir de la posicion, los posibles movimientos son los siguientes.
        int[][] movimientos = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
        //Por cada moviemiento, verificar si esa posicion es valida
        for (int[] mov : movimientos) {
            int newX = this.getX() + mov[0];
            int newY = this.getY() + mov[1];
            if (posValida(newX, newY)) {
                //si es valida verificar que sea mi enemigo  o una casilla vacia
                Casilla casilla = tabla.tabla[newX][newY];
                if (!casilla.tienePieza() || casilla.getPieza().getPlayer() != this.getPlayer()) {
                    res.add(new Pair(newX, newY));
                }
            }
        }
        return res;

    }
   
            

    private boolean posValida(int fila, int columna) {
        return (fila >= 0 && columna >= 0 && fila <= 7 && columna <= 7);
    }
    
    public String getM(Tablero tabla){
        ArrayList<Pair> res = getMovimientos(tabla);
        String ans = "";
        for(Pair p: res){
            ans+= "("+p.X+", "+p.Y+")\n";
        }
        return ans;
    }

}

