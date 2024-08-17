/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

import java.util.Scanner;

/**
 *
 * @author Windows
 * Gestionar la interaccion de los usuarios con el programa
 */
public class Juego {
    private Tablero tabla;
    private int turno;
    //Clase jugador
    
    public Juego(){
        tabla = new Tablero();
        //agregar piezas
        tabla.agregarPieza(7,7, 'P');
        tabla.agregarPieza(0,0, 'C');
        tabla.agregarPieza(5,5, 'T');
        tabla.agregarPieza(3,1, 'R');
            
        turno = 0;
    }
    
    public void iniciarJuego(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Iniciando juego");
        tabla.imprimirTabla();
        boolean end = true;
        do{
            if(turno%2 == 0){
                System.out.println("Turno del jugador 1");
                //if(casilla != null)
                Casilla selec = null;
                do{
                    System.out.println("Seleccione una pieza");
                    int xSelect = sc.nextInt();
                    int ySelect = sc.nextInt();
                    selec = seleccionarPieza(xSelect, ySelect); 
                    
                }while(selec == null);
                System.out.println("La pieza seleccionada es " + selec);
                
                //mover la pieza
                boolean mover = false;
                do{
                    System.out.println("Seleccione una posicion");
                    int xSelect = sc.nextInt();
                    int ySelect = sc.nextInt();
                    mover = mover(selec, xSelect, ySelect);
                 
                }while(!mover);
                System.out.println("Se movio");

            }else{
                System.out.println("Turno del jugador 2");
                Casilla selec = null;
                do{
                    System.out.println("Seleccione una pieza");
                    int xSelect = sc.nextInt();
                    int ySelect = sc.nextInt();
                    selec = seleccionarPieza(xSelect, ySelect); 
                }while(selec == null);
                System.out.println("La pieza seleccionada es " + selec);
                
                boolean mover = false;
                do{
                    System.out.println("Seleccione una posicion");
                    int xSelect = sc.nextInt();
                    int ySelect = sc.nextInt();
                    mover = mover(selec, xSelect, ySelect);
                 
                }while(!mover);
                System.out.println("Se movio");
            }
            tabla.imprimirTabla();
            turno++;
        }while(end);
    }
    
    public Casilla seleccionarPieza(int x, int y){ //jugador
        if(x>=8 || x < 0 || y>=8 || y < 0 ){
            System.out.println("Limites excedidos");
            return null;
        }
        Casilla res = tabla.tabla[x][y];
        if(res.getPieza().getSigno() != '-'){
            return res;
        }
        System.out.println("No hay una pieza");
        return null;
    }
    
    public boolean mover(Casilla pieza, int x, int y){ 
        if(x>=8 || x < 0 || y>=8 || y < 0 ){
            System.out.println("Limites excedidos");
            return false;
        }
        if(pieza.getX() == x && pieza.getY() == y){
            System.out.println("No puedes seleccionar la misma casilla");
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
    }
    
}
/*
DRY -> Do not repeat yourself
KISS -> Keep it short and simple
*/