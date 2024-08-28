package game;
import javax.swing.JOptionPane;

import interfazGrafica.*;
import source.*;

public class Game {
    public static void main(String args[]){
        System.out.println("Iniciando el juego, creando el juego!");    
        
        Menu menu = new Menu();
        menu.setVisible(true);
        //Juego j = new Juego();

        System.out.println("Dandole la informacion al tablero");
        //TableroGUI tablero = new TableroGUI(j);
        JOptionPane.showMessageDialog(null, "El tablero ha sido creado");
        //j.iniciarJuego(tablero);
        
    }  
}
