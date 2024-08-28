package source;

import java.util.ArrayList;

public class Rey extends Pieza implements PiezaInterfaz {
    
    public Rey(int x, int y, Player player) {
        super(x, y, 'R', player);
        imgPath1 = "white-king.png";
        imgPath2 = "black-king.png";
    }

    @Override
    public ArrayList<Pair> getMovimientos(Tablero tabla) {
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

/*
 * Enroque, Coronacion (Julio.....Interfaz)
 * Imagenes (Eduardo)
 * 
 *  peon al paso, piezas clavadas, jaque, jaquemate, tabla 
 * 
 */