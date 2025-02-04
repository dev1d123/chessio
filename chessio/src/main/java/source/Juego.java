/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import interfazGrafica.PromocionDialog;
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
    ArrayList<Movimiento> movJ1 = new ArrayList<Movimiento>();
    ArrayList<Movimiento> movJ2 = new ArrayList<Movimiento>();
    private int text;
    //Clase jugador

    //paths -> texturas....


    public Juego(int t){ //La posición puede ser una constante (final)
        this.text = t;
        j1 = new Player("Julio", false, false); //negras....abajo, jugador 1 siempre abajo -> posicion = false
        j2 = new Player("Julian", true, true); //blancas....arriba, jugador 2 siempre arriba -> posicion = true
        tabla = new Tablero(j1, j2);     

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
            Pair initialPos = new Pair();
            while (true) {
                if (selectedPiece == null) {
                    //JOptionPane.showMessageDialog(null, "Seleccione una pieza!");
                    //bro ._.
                    //boolean asd = hayJaque(j1, j2, tab, tabla);
                    //boolean tra = hayJaque(j2, j1, tab, tabla);
                    Pair selection = tab.seleccionarElemento();
                    selectedPiece = seleccionarPieza(currentPlayer, selection.X, selection.Y);

                    if (selectedPiece != null) {
                        initialPos.X = selection.X;
                        initialPos.Y = selection.Y;
                        System.out.println("La pieza seleccionada es " + selectedPiece);
                        
                        if(currentPlayer == j1){
                            availableMoves = selectedPiece.getPieza().getMovimientos(tabla, movJ1, movJ2);
                        }else{
                            availableMoves = selectedPiece.getPieza().getMovimientos(tabla, movJ2, movJ1);
                        }

                        tab.paintMovements(availableMoves);

                        
                        //boolean useless1 = hayJaque(j1, j2, tab, tabla);
                        //boolean useless2 = hayJaque(j2, j1, tab, tabla);
                        for (Pair move : availableMoves) {
                            //System.out.println("Movimiento posible: " + move.X + ", " + move.Y);
                        }
                    }
                } else {
                    //JOptionPane.showMessageDialog(null, "Seleccione una posición para mover o seleccione otra pieza.");
                    //boolean asd = hayJaque(j1, j2, tab, tabla);
                    //boolean tra = hayJaque(j2, j1, tab, tabla);
                    Pair selection = tab.seleccionarElemento();

                    Casilla newSelection = seleccionarPieza(currentPlayer, selection.X, selection.Y);
                    if (newSelection != null && newSelection != selectedPiece) {
                        selectedPiece = newSelection;

                        if(currentPlayer == j1){
                            availableMoves = selectedPiece.getPieza().getMovimientos(tabla, movJ1, movJ2);
                        }else{
                            availableMoves = selectedPiece.getPieza().getMovimientos(tabla, movJ2, movJ1);
                        }

                        tab.paintMovements(availableMoves);
                        /*
                        boolean useless1 = hayJaque(j1, j2, tab, tabla);
                        boolean useless2 = hayJaque(j2, j1, tab, tabla);
                        */
                        System.out.println("Nueva pieza seleccionada: " + selectedPiece);
                        continue;
                    }
                    Pieza piezaMov = selectedPiece.getPieza();
                    boolean moved;
                    if(currentPlayer == j1){
                        moved = mover(selectedPiece, selection.X, selection.Y, availableMoves, this.tabla, movJ1, movJ2);
                    }else{
                        moved = mover(selectedPiece, selection.X, selection.Y, availableMoves, this.tabla, movJ2, movJ2 );
                    }

                    if (moved) {
                        System.out.println("$$$$$$$$$$$$$$Pieza movida -> " + piezaMov.obtenerNombreClase());
                        System.err.println("Posicion inicial -> (" + initialPos.X + ", " + initialPos.Y + ")");
                        System.err.println("Posicion final -> (" + selection.X + ", " + selection.Y + ")");

                        Movimiento m = new Movimiento(piezaMov.obtenerNombreClase(), initialPos.X, initialPos.Y, selection.X, selection.Y);
                        if(currentPlayer == j1){
                            movJ1.add(m);
                        }else{
                            movJ2.add(m);
                        }
                        

                        /*
                        if(turno%2 == 0){
                            if(hayJaque(white, black, tab, tabla)){
                                JOptionPane.showMessageDialog(null, "Hay un jaque", "Título del Mensaje", JOptionPane.INFORMATION_MESSAGE);
                            }
                            
                            if(hayJaqueMate(white, black, tab, tabla, turno)){
                                JOptionPane.showMessageDialog(null, "Hay un jaque MATE", "Título del Mensaje", JOptionPane.INFORMATION_MESSAGE);
                            }
                            
                        }else{
                            //turno de black.
                            if(hayJaque(black, white, tab, tabla)){
                                JOptionPane.showMessageDialog(null,"Hay un jaque","Título del Mensaje", JOptionPane.INFORMATION_MESSAGE);
                            }
                             
                            if(hayJaqueMate(black, white, tab, tabla, turno)){
                                JOptionPane.showMessageDialog(null, "Hay un jaque MATE", "Título del Mensaje", JOptionPane.INFORMATION_MESSAGE);
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
    
    public boolean hayJaque(Player p1, Player p2, TableroGUI tab, Tablero esteTablero){
        //System.out.println("p1: " + p1);
        //System.out.println("p2: " + p2);

        //System.out.println("ptab1: " + esteTablero.p1);
        //System.out.println("ptab2: " + esteTablero.p2);
        //comprobar si hay jaque de p1 a p2
        //obtener la posicion del rey!
        Rey rey = null;
        int reyX = -1;
        int reyY = -1;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Casilla casilla = esteTablero.tabla[i][j];
                if(casilla.tienePieza() == false) continue;
                /*
                System.out.println("Imprimiendo: " + casilla.toString() + " -> " + (casilla.getPieza() instanceof Pieza));
                System.out.println("Imprimiendo xd: " + casilla.getPieza().getClass().getName());

                System.out.println("Imprimiendo dueño: " + casilla.getPieza().getPlayer());
                */
                if(casilla.getPieza() instanceof Rey && casilla.getPieza().getPlayer() == p2){
                    rey = (Rey)casilla.getPieza();
                    reyX = casilla.getX();
                    reyY = casilla.getY();
                }
            }
        }
        //System.out.println("REY pos: " + reyX + ", " + reyY);
        //obtener todas las coordenadas de ataque de p1!!!
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Casilla casilla = esteTablero.tabla[i][j];
                if(casilla.tienePieza() == false) continue;
                if(casilla.getPieza().getPlayer() == p1){
                    //de p1 a p2
                    ArrayList<Pair> mov;
                    if(p1 == j1){
                        mov = casilla.getPieza().getMovimientos(esteTablero, movJ1, movJ2);
                    }else{
                        mov = casilla.getPieza().getMovimientos(esteTablero, movJ2, movJ1);
                    }
                    
                    
                    for(Pair p: mov){
                        if(reyX == p.X && reyY == p.Y){
                            if(tab != null){
                                tab.paintSquare(reyX, reyY, Color.BLUE);

                            }
                            rey.jaque = true;
                            return true;

                        }
                    }
                }
            }
        }
        rey.jaque = false;
        return false;
    }
   

    public boolean hayJaqueMate(Player p1, Player p2, TableroGUI tab, Tablero originalTab, int turno) {
        //System.out.println("TESTEANDO MATE");
        
        if (!hayJaque(p1, p2, tab, originalTab)) {
            return false;
        }
        //System.out.println("paso1");
    
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casilla casilla = tabla.tabla[i][j];
    
                if (!casilla.tienePieza()) continue;
    
                Pieza pieza = casilla.getPieza();
    
                if (pieza.getPlayer() != p2) continue;
                
                ArrayList<Pair> movimientosDisponibles;
                if(p1 == j1){
                    movimientosDisponibles = pieza.getMovimientos(tabla, movJ1, movJ2);
                }else{
                    movimientosDisponibles = pieza.getMovimientos(tabla, movJ2, movJ1);
                }
    
                for (Pair mov : movimientosDisponibles) {
                    Tablero copiaTablero = new Tablero(this.tabla);
    
                    Casilla copiaCasillaPieza = copiaTablero.tabla[casilla.getX()][casilla.getY()];
                    boolean movimientoExitoso;
                    if(p1 == j1){
                        movimientoExitoso = mover(copiaCasillaPieza, mov.getX(), mov.getY(), movimientosDisponibles, copiaTablero, movJ1, movJ2);
                    }else{
                        movimientoExitoso = mover(copiaCasillaPieza, mov.getX(), mov.getY(), movimientosDisponibles, copiaTablero, movJ2, movJ1);
                    }

                    
                    
                    //System.out.println("SIMULAR MOVIMIENTO!!!");
                    copiaTablero.imprimirTabla();
                    
                    if (!hayJaque(p1, p2, null, copiaTablero)) {

                        //System.out.println("ya no hay jaque bro");
                        return false; 
                    }else{
                        //System.out.println("sigue habiendo jaque!");
                    }


                    
                }
            }
        }
    
        // Si ningún movimiento elimina el jaque, es jaque mate
        System.out.println("JAQUE MATE");
        return true;
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
    
    
    public boolean mover(Casilla pieza, int x, int y, ArrayList<Pair> movimientosDisponibles, Tablero tab, ArrayList<Movimiento> m1, ArrayList<Movimiento> m2){ 
        if(x>=8 || x < 0 || y>=8 || y < 0 ){
            System.out.println("Limites excedidos");
            return false;
        }

        if(pieza.getX() == x && pieza.getY() == y){
            System.out.println("No puedes seleccionar la misma casilla GAAA");
            return false;
        }
        if(!validarMovimientoPieza(x, y, movimientosDisponibles)){
            System.out.println("Esa pieza no puede moverse ahi!!!");
            return false;
        }

        Pieza mover = pieza.getPieza();
        Player jugador = mover.getPlayer();
        Casilla objetivo = tab.tabla[x][y];
        
        if(mover.obtenerNombreClase().equals("Peon")){
            int filaFinal = (jugador == j1) ? 0 : 7; 
            if (x == filaFinal) {
                System.out.println("¡Peón ha llegado a la fila de promoción!");

                String[] opciones = {"Torre", "Caballo", "Alfil", "Dama"};
                int opcion = JOptionPane.showOptionDialog(
                    null, 
                    "Selecciona la pieza para la promoción", 
                    "Promoción de Peón",
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    opciones, 
                    opciones[3] // Selección por defecto: Dama
                );

                Pieza nuevaPieza;
                switch (opcion) {
                    case 0 -> nuevaPieza = new Torre(x, y, jugador, text);
                    case 1 -> nuevaPieza = new Caballo(x, y, jugador, text);
                    case 2 -> nuevaPieza = new Alfil(x, y, jugador, text);
                    default -> nuevaPieza = new Reina(x, y, jugador, text); 
                }
                pieza.setPieza(new Pieza('-'));
                pieza.quitarPieza();
                objetivo.setPieza(nuevaPieza);
                System.out.println("Peón promovido a " + nuevaPieza.obtenerNombreClase());


                return true;
            }
        }


        ArrayList<Movimiento> movimientosOponente;
        if(jugador == j1){
            movimientosOponente = movJ2;
        }else{
            movimientosOponente = movJ1;
        }
        //captura al paso
        
        if (!movimientosOponente.isEmpty()) {

            Movimiento lastMove = movimientosOponente.get(movimientosOponente.size() - 1);
            if(lastMove.getPieza().equals("Peon") && Math.abs(lastMove.getInicioFila() - lastMove.getFinFila()) == 2){

                int enemigoFila = lastMove.getFinFila();
                int enemigoColumna = lastMove.getFinColumna();
                if (pieza.getX() == enemigoFila && Math.abs(pieza.getY() - enemigoColumna) == 1 && x == (jugador.getPosicion() ? enemigoFila + 1 : enemigoFila - 1)) {
                    tab.tabla[enemigoFila][enemigoColumna].quitarPieza();
                }
            }
        }
        

        
        //Enroque, todas las contidiciones ya estan validadas, solo verificar si el rey se ha movido a una casilla de posible enroque....e intercambiar
        if (pieza.getPieza().obtenerNombreClase().equals("Rey")) {
            if (Math.abs(pieza.getY() - y) == 2) { // Enroque detectado
                int torreColumna = (y > pieza.getY()) ? 7 : 0; // Determina si es enroque corto o largo
                int nuevaTorreColumna = (y > pieza.getY()) ? y - 1 : y + 1; 
                Casilla torreCasilla = tab.tabla[pieza.getX()][torreColumna];
                
                if (torreCasilla.tienePieza() && torreCasilla.getPieza().obtenerNombreClase().equals("Torre")) {
                    Pieza torre = torreCasilla.getPieza();
                    Casilla nuevaTorreCasilla = tab.tabla[pieza.getX()][nuevaTorreColumna];
                    
                    // Mover la torre
                    torreCasilla.quitarPieza();
                    torreCasilla.setPieza(new Pieza('-'));
                    nuevaTorreCasilla.setPieza(torre);
                    torre.setX(pieza.getX());
                    torre.setY(nuevaTorreColumna);
                }
            }
        }


        if(tab.tabla[x][y].tienePieza()){
            if(tab.tabla[x][y].getPieza().getPlayer() == jugador){
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
    public static boolean amenazaCasilla(Player p1, Player p2, Tablero esteTablero, int x, int y, ArrayList<Movimiento> movJ1, ArrayList<Movimiento> movJ2) {
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Casilla casilla = esteTablero.tabla[i][j];
                if(casilla.tienePieza() == false) continue;
                if(casilla.getPieza().getPlayer() == p1){
                    //de p1 a p2
                    ArrayList<Pair> mov = casilla.getPieza().getMovimientos(esteTablero, movJ1, movJ2);
            
                    
                    for(Pair p: mov){
                        if(x == p.X && y == p.Y){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
