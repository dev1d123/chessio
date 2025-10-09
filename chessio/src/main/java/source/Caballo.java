package source;

import java.util.ArrayList;

import interfazGrafica.TexturesPath;

/**
 *
 * @author Windows
 */
public class Caballo extends Pieza implements PiezaInterfaz{
        //Inicia el juego y cuando se corona
    //x, y, signo
    public Caballo(Caballo otro) {
        super(otro.getX(), otro.getY(), otro.getSigno(), otro.getPlayer(), otro.textureID);
        this.imgPath1 = otro.imgPath1;
        this.imgPath2 = otro.imgPath2;
    }
    public Caballo(int x, int y, Player player, int textureID) {
        super(x, y, 'C', player, textureID);
        imgPath1 = TexturesPath.getPath(false, textureID, 2);
        imgPath2 = TexturesPath.getPath(true, textureID, 2);
    }

    @Override
    public ArrayList<Pair> getMovimientos(Tablero tabla, ArrayList<Movimiento> movJ1, ArrayList<Movimiento> movJ2) {
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
    
    public String getM(Tablero tabla, ArrayList<Movimiento> movJ1, ArrayList<Movimiento> movJ2){
        ArrayList<Pair> res = getMovimientos(tabla, movJ1, movJ2);
        String ans = "";
        for(Pair p: res){
            ans+= "("+p.X+", "+p.Y+")\n";
        }
        return ans;
    }
    public Caballo clonar() {
        return new Caballo(this);
    }
}

