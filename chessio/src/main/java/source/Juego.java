/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import interfazGrafica.TableroGUI;

/**
 *
 * @author Windows
 * Gestionar la interaccion de los usuarios con el programa
 */
public class Juego {
    private Tablero tabla;
    private int turno;
    private Player j1;
    private Player j2;
    //Clase jugador

    //paths -> texturas....


    public Juego(int t){ //La posición puede ser una constante (final)
        j1 = new Player("Julio", false, false); //negras....abajo, jugador 1 siempre abajo -> posicion = false
        j2 = new Player("Julian", true, true); //blancas....arriba, jugador 2 siempre arriba -> posicion = true
        tabla = new Tablero();     

        // Piezas del jugador j2 (superior)
        tabla.agregarPieza(new Torre(0, 0, j2, t)); 
        tabla.agregarPieza(new Caballo(0, 1, j2, t));
        tabla.agregarPieza(new Alfil(0, 2, j2, t));
        tabla.agregarPieza(new Reina(0, 3, j2, t));
        tabla.agregarPieza(new Rey(0, 4, j2, t));
        tabla.agregarPieza(new Alfil(0, 5, j2, t));
        tabla.agregarPieza(new Caballo(0, 6, j2,t ));
        tabla.agregarPieza(new Torre(0, 7, j2,t ));

        for (int i = 0; i < 8; i++) {
            tabla.agregarPieza(new Peon(1, i, j2, t));
        }

        // Piezas del jugador j1 (inferior)
        tabla.agregarPieza(new Torre(7, 0, j1, t));
        tabla.agregarPieza(new Caballo(7, 1, j1, t));
        tabla.agregarPieza(new Alfil(7, 2, j1,t));
        tabla.agregarPieza(new Reina(7, 3, j1,t));
        tabla.agregarPieza(new Rey(7, 4, j1,t));
        tabla.agregarPieza(new Alfil(7, 5, j1,t));
        tabla.agregarPieza(new Caballo(7, 6, j1,t));
        tabla.agregarPieza(new Torre(7, 7, j1,t));

        for (int i = 0; i < 8; i++) {
            tabla.agregarPieza(new Peon(6, i, j1,t));
        }

        for (int row = 2; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                tabla.agregarCasilla(row, col);
            }
        }
        
        turno = 0;
    }
    public Tablero getTablero(){
        return tabla;
    }
    public void iniciarJuego(TableroGUI tab) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Iniciando juego");
        tabla.imprimirTabla();
    
        Player white = (j1.isWhite()) ? j1 : j2;
        Player black = (!j1.isWhite()) ? j1 : j2;
        boolean end = true;
    
        do {

            Player currentPlayer = (turno % 2 == 0) ? white : black;
            /* 
            if(turno%2 == 0){
                if(hayJaque(white, black, tab)){
                    JOptionPane.showMessageDialog(null, "Hay un jaque", "Título del Mensaje", JOptionPane.INFORMATION_MESSAGE);

                }

            }else{
                if(hayJaque(black, white, tab)){
                    JOptionPane.showMessageDialog(null, "Hay un jaque", "Título del Mensaje", JOptionPane.INFORMATION_MESSAGE);
                }

            }
            */
            System.out.println("Turno del jugador " + (currentPlayer.isWhite() ? "blancas" : "negras"));
    
            Casilla selectedPiece = null;
            ArrayList<Pair> availableMoves = new ArrayList<>();
    
            while (true) {
                if (selectedPiece == null) {
                    JOptionPane.showMessageDialog(null, "Seleccione una pieza!");
                    Pair selection = tab.seleccionarElemento();
                    selectedPiece = seleccionarPieza(currentPlayer, selection.X, selection.Y);
    
                    if (selectedPiece != null) {
                        System.out.println("La pieza seleccionada es " + selectedPiece);
                        availableMoves = selectedPiece.getPieza().getMovimientos(tabla);
                        tab.paintMovements(availableMoves);
                        for (Pair move : availableMoves) {
                            System.out.println("Movimiento posible: " + move.X + ", " + move.Y);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una posición para mover o seleccione otra pieza.");
                    Pair selection = tab.seleccionarElemento();
    
                    Casilla newSelection = seleccionarPieza(currentPlayer, selection.X, selection.Y);
                    if (newSelection != null && newSelection != selectedPiece) {
                        selectedPiece = newSelection;
                        availableMoves = selectedPiece.getPieza().getMovimientos(tabla);
                        tab.paintMovements(availableMoves);
                        System.out.println("Nueva pieza seleccionada: " + selectedPiece);
                        continue;
                    }
    
                    boolean moved = mover(selectedPiece, selection.X, selection.Y, availableMoves);

                    if (moved) {
                        System.out.println("Pieza movida.");
                        /* 
                        if(turno%2 == 0){
                            if(hayJaque(white, black, tab)){
                                JOptionPane.showMessageDialog(null, "Hay un jaque", "Título del Mensaje", JOptionPane.INFORMATION_MESSAGE);

                            }
                            //turno de white
                        }else{
                            //turno de black.
                            if(hayJaque(black, white, tab)){
                                JOptionPane.showMessageDialog(null,"Hay un jaque","Título del Mensaje", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        */
                        break;
                        
                    }
                }
            }
    
            tab.reload();
            tabla.imprimirTabla();
            turno++;
        } while (end);
        tab.dispose();
    }
    
    public boolean hayJaque(Player p1, Player p2, TableroGUI tab){
        //comprobar si hay jaque de p1 a p2
        //obtener la posicion del rey!
        int reyX = -1;
        int reyY = -1;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Casilla casilla = tabla.tabla[i][j];
                if(casilla.tienePieza() == false) continue;
                if(casilla.getPieza() instanceof Rey && casilla.getPieza().getPlayer() == p2){
                    reyX = casilla.getX();
                    reyY = casilla.getY();
                }
            }
        }
        //obtener todas las coordenadas de ataque de p1!!!
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Casilla casilla = tabla.tabla[i][j];
                if(casilla.tienePieza() == false) continue;
                if(casilla.getPieza().getPlayer() == p1){
                    ArrayList<Pair> mov = casilla.getPieza().getMovimientos(tabla);
                    for(Pair p: mov){
                        if(reyX == p.X && reyY == p.Y){
                            tab.paintSquare(reyX, reyY, Color.BLUE);
                            return true;

                        }
                    }
                }
            }
        }
        



        return false;
    }


    public Casilla seleccionarPieza(Player p, int x, int y){ //jugador

        if(x>=8 || x < 0 || y>=8 || y < 0 ){
            System.out.println("Limites excedidos");
            return null;
        }
        
        Casilla res = tabla.tabla[x][y];
        
        if(res.getPieza().getPlayer() != p){
            System.out.println("Esa no es tu pieza!");
            return null;
        }
        /*
         * Si la res.tienePieza()
         * if(res.tienePieza(), )
         * 
         */
        if(res.getPieza().getSigno() != '-'){
            return res;
        }
        System.out.println("No hay una pieza");
        return null;
    }
    
    
    public boolean mover(Casilla pieza, int x, int y, ArrayList<Pair> movimientosDisponibles){ 
        if(x>=8 || x < 0 || y>=8 || y < 0 ){
            System.out.println("Limites excedidos");
            return false;
        }

        if(pieza.getX() == x && pieza.getY() == y){
            System.out.println("No puedes seleccionar la misma casilla");
            return false;
        }

        /*
         * if(pieza te corresponde){
         *      
         * }
         */



        //recorrer todos los pares y con "x" e "y", verificar que sea valido
        if(!validarMovimientoPieza(x, y, movimientosDisponibles)){
            System.out.println("Esa pieza no puede moverse ahi!!!");
            return false;
        }


        Pieza mover = pieza.getPieza();
        


        Player jugador = mover.getPlayer();
        Casilla objetivo = tabla.tabla[x][y];

        if(tabla.tabla[x][y].tienePieza()){
            if(tabla.tabla[x][y].getPieza().getPlayer() == jugador){
                System.out.println("nunca vas a ver este mensaje");
                return false;
            }else{
                //comer
                objetivo.setPieza(new Pieza('-'));
                objetivo.quitarPieza();                
            }
        }
        //clavada, jaque
       

        pieza.setPieza(new Pieza('-'));
        pieza.quitarPieza();
        objetivo.setPieza(mover);
        
        return true;
        

    }
    public boolean validarMovimientoPieza(int x, int y, ArrayList<Pair> movimientosDisponibles){
        for(Pair parsito: movimientosDisponibles){
            if(parsito.X == x && parsito.Y == y){
                return true;
            }
        }
        return false;
    }
    
    
    
    // Método estatico para obtener los movimientos de una pieza que se mueve vertical o diagonalmente.
    public static void agregarMovimientos(Tablero tabla, ArrayList<Pair> res, int startX, int startY, int dx, int dy, Player player) {
        int x = startX;
        int y = startY;
        
        while (true) {
            x += dx;
            y += dy;
            
            if (x < 0 || x >= 8 || y < 0 || y >= 8) {
                break;
            }

            Casilla casilla = tabla.tabla[x][y];
            if (!casilla.tienePieza()) {
                res.add(new Pair(x, y));
            } else {
                if (casilla.getPieza().getPlayer() != player) {
                    res.add(new Pair(x, y)); 
                }
                break;
            }
        }
    }
}
/*
DRY -> Do not repeat yourself
KISS -> Keep it short and simple 
*/

//JFrame -> constructor -> tamaño, titulo, setVisible(), createContens....

//Programacion con hilos -> animaciones (fotogramas)