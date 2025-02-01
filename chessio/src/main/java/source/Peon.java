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
public class Peon extends Pieza implements PiezaInterfaz{
    
    //color 
    private boolean primerizo = true;
    
    public Peon(int x, int y, Player player, int textureID) {
        super(x, y, 'P', player, textureID);

        imgPath1 = TexturesPath.getPath(false, textureID, 0);
        imgPath2 = TexturesPath.getPath(true, textureID, 0);
    }

    @Override
    public ArrayList<Pair> getMovimientos(Tablero t, ArrayList<Movimiento> movJ1, ArrayList<Movimiento> movJ2) {
        determinarSiEsPrimerizo(); //Se determina si el peón esta dando su primer movimiento o no
        ArrayList<Pair> res = new ArrayList<>();
        int[][] movimientos = {
          // Peon  |  Peon  
          //Arriba |  Abajo  
            {1, 0} , {-1, 0},   //Movimientos predeterminados de una casilla
            {2, 0} , {-2, 0},   //Movimientos de dos casillas (solo posibles al primer movimiento), aplica para captura al paso
            {1, -1}, {-1, 1},   //Movimientos para captura, ⬋ y ⬈ respectivamente
            {1, 1} , {-1, -1},  //Movimientos para captura, ⬊ y ⬉ respectivamente
        };
        
        Player p = this.getPlayer();
        
        //k = 0, el bucle recorrerá movimientos factibles para peón de arriba
        //k = 1, el bucle recorrerá movimientos factibles para peón de abajo
        int k = (p.getPosicion()) ? 0 : 1; 
        for (int i = k; i < movimientos.length; i += 2) {
            int newX = this.getX() + movimientos[i][0];
            int newY = this.getY() + movimientos[i][1];
            
            if(!posValida(newX, newY)){
                continue;
            }

            Casilla cas = t.tabla[newX][newY];  
            //System.out.println(newX + "" + newY);
            //System.out.println("Se pregunta tiene pieza?: " + cas.tienePieza());
            if (!cas.tienePieza()) { //No hay una pieza en la nueva posición
                if (i < 2) { 
                    res.add(new Pair(newX, newY)); //Se añade el salto de 1
                }
                if (i >=2 && i <= 3 && this.esPrimerizo() && res.size() == 1) { //Se verifica si es factible dar salto de 2
                    res.add(new Pair(newX, newY)); //res de tamaño 1 indica que antes se pudo dar salto de 1, por tanto ahora se puede dar salto de 2
                } 
            }
            if (cas.tienePieza() && i >= 4 && cas.getPieza().getPlayer() != p) {
                res.add(new Pair(newX, newY));
            }
        }
        // Captura al paso
        if (!movJ2.isEmpty()) {
            Movimiento lastMove = movJ2.get(movJ2.size() - 1);
            if (lastMove.getPieza().equals("Peon") && Math.abs(lastMove.getInicioFila() - lastMove.getFinFila()) == 2) {
                int enemigoFila = lastMove.getFinFila();
                int enemigoColumna = lastMove.getFinColumna();
                if (this.getX() == enemigoFila && Math.abs(this.getY() - enemigoColumna) == 1) {
                    int capturaX = p.getPosicion() ? enemigoFila + 1 : enemigoFila - 1;
                    if (posValida(capturaX, enemigoColumna)) {
                        res.add(new Pair(capturaX, enemigoColumna));
                    }
                }
            }
        }
        
        

        return res;
    }
    
    private void determinarSiEsPrimerizo() {
        if (this.getX() != 1 && this.getX() != 6) this.primerizo = false;
    }
     
    private boolean esPrimerizo() {
        return this.primerizo;
    }

    private boolean posValida(int fila, int columna) {
        return (fila >= 0 && columna >= 0 && fila <= 7 && columna <= 7);
    }
    
}
