package interfazGrafica;

import source.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;
public class TableroGUI extends JFrame {
    private Pair seleccion;
    private CountDownLatch latch;
    private Juego juego;

    public TableroGUI(Juego juego){
        this.juego = juego;
        setTitle("Tablero Chessio");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 8));
        createContents(juego.getTablero());
        setVisible(true);
    }
    
    public void createContents(Tablero tabla){
        getContentPane().removeAll(); 
        for(int i = 0; i < 64; i++){
            final int a = i;
            JButton btn =  new JButton();

            if((a%8 + a/8)%2==0){
                btn.setBackground(Color.WHITE);
            }else{
                btn.setBackground(Color.BLACK);
            }
            if((tabla.tabla[i/8][i%8].tienePieza())){
                btn.setText(tabla.tabla[i/8][i%8].getPieza().getSigno()+"");
            }
            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Has presionado el boton " + a/8 + ", " + a%8);

                    seleccion = new Pair(a / 8, a % 8);
                    latch.countDown();
                }
                
            });
            add(btn);
        }
        revalidate(); 
        repaint();     
    }
    public Pair seleccionarElemento(){
        latch = new CountDownLatch(1); 
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return seleccion;
    }
    public void reload(){
        createContents(juego.getTablero());
    }

}
