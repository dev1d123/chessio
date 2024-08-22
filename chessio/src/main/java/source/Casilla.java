
package source;

/**
 *
 * @author Windows
 */
public class Casilla {
    private Pieza pieza;
    //Crear una pieza...que represente a una casilla vacia
    private int x,y;
    private boolean tienePieza;
    public Casilla(int x, int y, Pieza pieza){
        this.x = x; 
        this.y = y;
        this.pieza = pieza;
        this.tienePieza = false;
    }
    public Casilla(int x, int y){
        this.x = x; 
        this.y = y;
        this.tienePieza = false;
    }

    public boolean tienePieza() {
        return tienePieza;
    }


    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
        this.tienePieza = true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public String toString(){
        return pieza.getSigno() + "";
    }

}
