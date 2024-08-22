/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

import java.util.ArrayList;
import java.util.Scanner;

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
    
    public Juego(){
        j1 = new Player("Julio", false);
        j2 = new Player("Julian", true);
        tabla = new Tablero();     
        
        tabla.agregarPieza(new Torre(0, 0, j2));
        tabla.agregarPieza(new Caballo(0, 1, j2));
        tabla.agregarPieza(new Alfil(0, 2, j2));
        tabla.agregarPieza(new Reina(0, 3, j2));
        tabla.agregarPieza(new Rey(0, 4, j2));
        tabla.agregarPieza(new Alfil(0, 5, j2));
        tabla.agregarPieza(new Caballo(0, 6, j2));
        tabla.agregarPieza(new Torre(0, 7, j2));

        for (int i = 0; i < 8; i++) {
            tabla.agregarPieza(new Peon(1, i, j2));
        }

        // Piezas del jugador j1 (inferior)
        tabla.agregarPieza(new Torre(7, 0, j1));
        tabla.agregarPieza(new Caballo(7, 1, j1));
        tabla.agregarPieza(new Alfil(7, 2, j1));
        tabla.agregarPieza(new Reina(7, 3, j1));
        tabla.agregarPieza(new Rey(7, 4, j1));
        tabla.agregarPieza(new Alfil(7, 5, j1));
        tabla.agregarPieza(new Caballo(7, 6, j1));
        tabla.agregarPieza(new Torre(7, 7, j1));

        for (int i = 0; i < 8; i++) {
            tabla.agregarPieza(new Peon(6, i, j1));
        }

        for (int row = 2; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                tabla.agregarCasilla(row, col);
            }
        }
        
        turno = 0;
    }
    
    public void iniciarJuego(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Iniciando juego");
        tabla.imprimirTabla();
        Player white = (j1.isWhite()) ? j1 : j2;
        Player black = (!j1.isWhite()) ? j1 : j2;
        boolean end = true;
        do{
            if(turno%2 == 0){
                //trabajamos con white    
                
                System.out.println("Turno del jugador blancas");
                //if(casilla != null)
                Casilla selec = null;
                do{
                    System.out.println("Seleccione una pieza");
                    int xSelect = sc.nextInt();
                    int ySelect = sc.nextInt();
                    selec = seleccionarPieza(white, xSelect, ySelect); 
                    
                }while(selec == null);
                System.out.println("La pieza seleccionada es " + selec);
                System.out.println("Los movimiento son");
                //
                ArrayList<Pair> mov = selec.getPieza().getMovimientos();
                for(Pair parsito: mov){
                    System.out.println(parsito.X + ", " + parsito.Y);
                }
                //mover la pieza
                boolean mover = false;
                do{
                    System.out.println("Seleccione una posicion");
                    int xSelect = sc.nextInt();
                    int ySelect = sc.nextInt();
                    mover = mover(selec, xSelect, ySelect, mov);
                 
                }while(!mover);
                System.out.println("Se movio");

            }else{
                System.out.println("Turno del jugador negras");
                Casilla selec = null;
                do{
                    System.out.println("Seleccione una pieza");
                    int xSelect = sc.nextInt();
                    int ySelect = sc.nextInt();
                    selec = seleccionarPieza(black, xSelect, ySelect); 
                }while(selec == null);
                System.out.println("La pieza seleccionada es " + selec);
                System.out.println("Los movimiento son");
                //
                ArrayList<Pair> mov = selec.getPieza().getMovimientos();
                for(Pair parsito: mov){
                    System.out.println(parsito.X + ", " + parsito.Y);
                }                
                boolean mover = false;
                do{
                    System.out.println("Seleccione una posicion");
                    int xSelect = sc.nextInt();
                    int ySelect = sc.nextInt();
                    mover = mover(selec, xSelect, ySelect, mov);
                 
                }while(!mover);
                System.out.println("Se movio");
            }
            tabla.imprimirTabla();
            turno++;
        }while(end);
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
        //recorrer todos los pares y con x y y, verificar que sea valido
        if(!validarMovimientoPieza(x, y, movimientosDisponibles)){
            System.out.println("Esa pieza no puede moverse ahi!!!");
            return false;
        }
        if(tabla.tabla[x][y].getPieza().getSigno() != '-'){
            System.out.println("Hay una pieza!!");
            return false;
        }
        //clavada, jaque
       
        Casilla objetivo = tabla.tabla[x][y];
        Pieza mover = pieza.getPieza();
        pieza.setPieza(new Pieza('-'));
        objetivo.setPieza(mover);
        return true;
        
        //80
        
        
        //verificar si esta clavado...fuerza bruta...
        
        //si hay jaque (todas las piezas estasn clavadas exceto las que puedo mover)
        
       
        //fuerza
    }
    public boolean validarMovimientoPieza(int x, int y, ArrayList<Pair> movimientosDisponibles){
        for(Pair parsito: movimientosDisponibles){
            if(parsito.X == x && parsito.Y == y){
                return true;
            }
        }
        return false;
    }
}
/*
DRY -> Do not repeat yourself
KISS -> Keep it short and simple
*/