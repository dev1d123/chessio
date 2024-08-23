/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

/**
 *
 * @author Windows
 */
public class Main {
    public static void main(String args[]){
        
        //recibir instrucciones....jugador1, jugador2 -> opciones(skins, efectos de sonido y musica)
        Juego j = new Juego();
        j.iniciarJuego();

    }
}
//captura al paso, enroque...
//Cada pieza debe tener un arreglo de movimientos....

/*
//negras mueven, se calcula el estado de las blancas...

//tablas luego de cada movimiento...(insuficiencia de material)(ahogado)

public boolean jaque(Player jugador, Player rival, Tablero tablero){
              //retorna true si jugador esta en jaque
              //
}
public boolean tablas (Player jugador, Player rival, Tablero tablero){
              //retorna true si la partida esta en tablas.....hay muchos casos de tablas :decepcionado: este es el mas complicadito
}
public boolean jaqueMate(Player jugador, Player rival, Tablero tablero){
              //retorna true si jugador esta en jaque mate
}


public boolean clavada(Pieza piezaMovida, Player jugador, Player rival, Tablero tablero){
    // retorna true si al cambiar la posición de piezaMovida el rey se encuentra en jaque….(pueden reciclar el método de jaque)
}

*/