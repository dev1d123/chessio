package source;

import java.util.ArrayList;

import interfazGrafica.TexturesPath;

public class Rey extends Pieza implements PiezaInterfaz {
    
    public Rey(int x, int y, Player player, int textureID) {
        super(x, y, 'R', player, textureID);
        imgPath1 = TexturesPath.getPath(false, textureID, 5);
        imgPath2 = TexturesPath.getPath(true, textureID, 5);
    }

    @Override
    public ArrayList<Pair> getMovimientos(Tablero tabla, ArrayList<Movimiento> movJ1, ArrayList<Movimiento> movJ2) {
        //si la torre 
        ArrayList<Pair> res = new ArrayList<>();
        
        int[][] movimientos = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {-1, -1}, {1, -1}, {-1, 1}
        };

        for (int[] mov : movimientos) {
            int newX = this.getX() + mov[0];
            int newY = this.getY() + mov[1];
            if (posValida(newX, newY)) {
                Casilla casilla = tabla.tabla[newX][newY];
                if (!casilla.tienePieza() || casilla.getPieza().getPlayer() != this.getPlayer()) {
                    res.add(new Pair(newX, newY));
                }
            }
        }
        Player j1 = this.getPlayer();

        Player j2;
        
        if(j1 != tabla.p1){
            j2 = tabla.p1;
        }else{
            j2 = tabla.p2;
        }

        if (puedeHacerEnroque(movJ1)) {
            //
            if (puedeEnrocarCorto(tabla, movJ1)) {
                if (!Juego.amenazaCasilla(j2, j1, tabla, this.getX(), this.getY() + 1, movJ2, movJ1) &&
                    !Juego.amenazaCasilla(j2, j1, tabla, this.getX(), this.getY() + 2, movJ2, movJ1)) {
                    res.add(new Pair(this.getX(), this.getY() + 2));
                }           
            }
            if (puedeEnrocarLargo(tabla, movJ1)) {
                if (!Juego.amenazaCasilla(j2, j1, tabla, this.getX(), this.getY() - 1, movJ2, movJ1) &&
                    !Juego.amenazaCasilla(j2, j1, tabla, this.getX(), this.getY() - 2, movJ2, movJ1)) {
                    res.add(new Pair(this.getX(), this.getY() - 2));
                }         
            }
        }
        
        return res;
    }


    private boolean puedeHacerEnroque(ArrayList<Movimiento> movJ1){
        //si nunca aparece torre!
        for(Movimiento mov: movJ1){
            if(mov.getPieza().equals("Rey")) return false;
        }
        return true;
    }

    private boolean puedeEnrocarCorto(Tablero tablero, ArrayList<Movimiento> movJ1) {
        for (Movimiento mov : movJ1) {
            if (mov.getPieza().equals("Torre")) {
                if (mov.getInicioColumna() == this.getY() + 3) {
                    return false;
                }
            }
        }
        return !tablero.tabla[this.getX()][this.getY() + 1].tienePieza() &&
            !tablero.tabla[this.getX()][this.getY() + 2].tienePieza();
    }

    private boolean puedeEnrocarLargo(Tablero tablero, ArrayList<Movimiento> movJ1) {
        for (Movimiento mov : movJ1) {
            if (mov.getPieza().equals("Torre")) {
                if (mov.getInicioColumna() == this.getY() - 4) {
                    return false;
                }
            }
        }
        return !tablero.tabla[this.getX()][this.getY() - 1].tienePieza() &&
            !tablero.tabla[this.getX()][this.getY() - 2].tienePieza() &&
            !tablero.tabla[this.getX()][this.getY() - 3].tienePieza();
    }


    private boolean posValida(int fila, int columna) {
        return (fila >= 0 && columna >= 0 && fila <= 7 && columna <= 7);
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

/*
 * Enroque, Coronacion (Julio.....Interfaz)
 * Imagenes (Eduardo)
 * 
 *  peon al paso, piezas clavadas, jaque, jaquemate, tabla 
 * 
 */