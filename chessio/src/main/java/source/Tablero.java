
package source;

/**
 *
 * @author Windows
 */

public class Tablero {
    Casilla tabla[][] = new Casilla[8][8];
    
    public Tablero(){
        for(int i = 0 ; i < tabla.length; i++){
            for(int j = 0; j <  tabla[i].length; j++){
                tabla[i][j] = new Casilla(i, j, new Pieza('-')); 
            }
        }
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



