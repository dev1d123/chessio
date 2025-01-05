
package source;

/**
 *
 * @author Windows
 */

public class Tablero {
    public Casilla tabla[][] = new Casilla[8][8];
    public Player p1;
    public Player p2;

    public Tablero(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        for (int i = 0; i < tabla.length; i++) {
            for (int j = 0; j < tabla[i].length; j++) {
                tabla[i][j] = new Casilla(i, j);
            }
        }
    }

    public Tablero(Tablero otro) {
        for (int i = 0; i < tabla.length; i++) {
            for (int j = 0; j < tabla[i].length; j++) {
                Casilla nuevaCasilla = new Casilla(otro.tabla[i][j]); 
                if(otro.tabla[i][j].tienePieza()){
                    nuevaCasilla.setPieza(otro.tabla[i][j].getPieza());
                }else{
                        //new Pieza('-');
                    nuevaCasilla.setPieza(new Pieza('-'));
                }
                this.tabla[i][j] =  nuevaCasilla;
            }
        }
        this.p1 = otro.p1; 
        this.p2 = otro.p2;
    }


    public void agregarCasilla(int i, int j){
       tabla[i][j].setPieza(new Pieza('-'));
    }
    public void agregarPieza(Pieza p){
        tabla[p.getX()][p.getY()].setPieza(p);
    }
    
    public void imprimirTabla(){
        for(int i = 0 ; i < tabla.length; i++){
            for(int j = 0; j <  tabla[i].length; j++){
                System.out.print(tabla[i][j].getPieza().getSigno());
            }
            System.out.println();
        }
    }
}





