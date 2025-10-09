package source;

import java.util.ArrayList;

import interfazGrafica.TexturesPath;

public class Rey extends Pieza implements PiezaInterfaz {

    public boolean jaque = false;
    
    public Rey(Rey otro) {
        super(otro.getX(), otro.getY(), otro.getSigno(), otro.getPlayer(), otro.textureID);
        this.jaque = otro.jaque;
        this.imgPath1 = otro.imgPath1;
        this.imgPath2 = otro.imgPath2;
    }
    public Rey(int x, int y, Player player, int textureID) {
        super(x, y, 'R', player, textureID);
        imgPath1 = TexturesPath.getPath(false, textureID, 5);
        imgPath2 = TexturesPath.getPath(true, textureID, 5);
    }

    @Override
    public ArrayList<Pair> getMovimientos(Tablero tabla, ArrayList<Movimiento> movJ1, ArrayList<Movimiento> movJ2) {
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
                    // King cannot move to attacked squares
                    Player opponent = (this.getPlayer() == tabla.p1) ? tabla.p2 : tabla.p1;
                    if (!Juego.isSquareAttacked(tabla, opponent, newX, newY)) {
                        res.add(new Pair(newX, newY));
                    }
                }
            }
        }

        // Castling
        Player self = this.getPlayer();
        Player opp = (self == tabla.p1) ? tabla.p2 : tabla.p1;
        ArrayList<Movimiento> ownMoves = movJ1; // by contract of caller: own move list

        if (!this.jaque && puedeHacerEnroque(ownMoves)) {
            // Short castle
            if (puedeEnrocar(tabla, ownMoves, true, opp)) {
                res.add(new Pair(this.getX(), this.getY() + 2));
            }
            // Long castle
            if (puedeEnrocar(tabla, ownMoves, false, opp)) {
                res.add(new Pair(this.getX(), this.getY() - 2));
            }
        }

        return res;
    }

    private boolean puedeHacerEnroque(ArrayList<Movimiento> ownMoves){
        for(Movimiento mov: ownMoves){
            if(mov.getPieza().equals("Rey")) return false;
        }
        return true;
    }

    private boolean puedeEnrocar(Tablero tablero, ArrayList<Movimiento> ownMoves, boolean corto, Player opp) {
        int row = this.getX();
        int kingCol = this.getY();
        int rookCol = corto ? 7 : 0;

        // Rook exists and same color
        Casilla rookSquare = tablero.tabla[row][rookCol];
        if (!rookSquare.tienePieza() || !(rookSquare.getPieza() instanceof Torre) || rookSquare.getPieza().getPlayer() != this.getPlayer()) {
            return false;
        }
        // Rook hasn't moved
        for (Movimiento mov : ownMoves) {
            if (mov.getPieza().equals("Torre") && mov.getInicioFila()==row && mov.getInicioColumna()==rookCol) return false;
        }
        // Empty path between king and rook
        int step = corto ? 1 : -1;
        for (int c = kingCol + step; c != rookCol; c += step) {
            if (tablero.tabla[row][c].tienePieza()) return false;
        }
        // Squares not attacked: current, intermediate, destination
        int destCol = kingCol + (corto ? 2 : -2);
        int midCol = kingCol + (corto ? 1 : -1);
        if (Juego.isSquareAttacked(tablero, opp, row, kingCol)) return false;
        if (Juego.isSquareAttacked(tablero, opp, row, midCol)) return false;
        if (Juego.isSquareAttacked(tablero, opp, row, destCol)) return false;

        return true;
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
    public Rey clonar() {
        return new Rey(this);
    }
}

/*
 * Enroque, Coronacion (Julio.....Interfaz)
 * Imagenes (Eduardo)
 * 
 *  peon al paso, piezas clavadas, jaque, jaquemate, tabla 
 * 
 */