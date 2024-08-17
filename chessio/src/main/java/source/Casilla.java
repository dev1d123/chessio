
package source;

/**
 *
 * @author Windows
 */
public class Casilla {
    private Pieza pieza;
    private int x,y;
    
    public Casilla(int x, int y, Pieza pieza){
        this.x = x; 
        this.y = y;
        this.pieza = pieza;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
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
