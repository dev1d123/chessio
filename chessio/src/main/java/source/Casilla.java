
package source;

/**
 *
 * @author Windows
 */
public class Casilla {
    private Pieza pieza;
    //Crear una pieza...que represente a una casilla vacia
    private int x,y;
    private boolean tienePieza = false;
    public Casilla(int x, int y, Pieza pieza){
        this.x = x; 
        this.y = y;
        this.pieza = pieza;
        this.tienePieza = true;
    }
    public Casilla(int x, int y){
        this.x = x; 
        this.y = y;
    }

    public boolean tienePieza() {
        return tienePieza;
    }


    public Pieza getPieza() {
        return pieza;
    }
    public void quitarPieza(){
        this.tienePieza = false;
    }
    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
        pieza.setX(this.x);
        pieza.setY(this.y);

        if (pieza.getSigno() != '-') this.tienePieza = true;
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

//sabado (interfaz grafica del ajedrez...)
//lunes (debugear el juego .... errores minimos)....Julio juege con un bot ()

//miercoles (ajedrez 100%, comenzar con la comunicacion entre ventanas)

//jueves (perfil, edicion)

//sabado (Terminar con lo demas)

//lunes...100% (1 septiembre)