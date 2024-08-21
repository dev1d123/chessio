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
    }

    @Override
    public ArrayList<Pair> getMovimientos() {
        ArrayList<Pair> res = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int distancia = distanciaLineal(i, j);
                if (i == this.getX()) {
                    if (distancia == 2) {
                        if(posValida( i - 1, j)) { //Pos 1
                            Pair p = new Pair(i - 1, j);
                            res.add(p);
                        } 
                        if (posValida(i + 1, j)) {//Pos 2
                            Pair p = new Pair(i + 1, j);
                            res.add(p);
                        }
                    }
                }
                if (j == this.getY()) {
                    if (distancia == 2) {
                        if (posValida(i, j - 1)) { //Pos 1
                            Pair p = new Pair(i, j - 1);
                            res.add(p);
                        }
                        if (posValida(i, j + 1)) { //Pos 2
                            Pair p = new Pair(i, j + 1);
                            res.add(p);
                        }
                    }
                
                }
            }
        }
        return res;

    }
    
    private int distanciaLineal(int fila, int columna) {
        int rpta = 0;
        rpta += Math.abs(this.getX() - fila);
        rpta += Math.abs(this.getY() - columna);
        return rpta;
    }
    
    private boolean posValida(int fila, int columna) {
        return (fila >= 0 && columna >= 0 && fila <= 7 && columna <= 7);
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

