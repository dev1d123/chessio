package interfazGrafica;

import source.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class TableroGUI extends JFrame {
    private Pair seleccion;
    private CountDownLatch latch;
    private Juego juego;
    private JButton[][] buttons;

    public TableroGUI(Juego juego){
        this.juego = juego;
        buttons = new JButton[8][8];
        setTitle("Tablero Chessio");
        setSize(640, 640);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 8));


        createContents(juego.getTablero());
        setVisible(true);
    }
    public void paintMovements(ArrayList<Pair> mov){
        for(Pair p: mov){
            if(juego.getTablero().tabla[p.getX()][p.getY()].tienePieza()){
                buttons[p.getY()][p.getX()].setBackground(Color.RED);
            }else{
                buttons[p.getY()][p.getX()].setBackground(Color.YELLOW);
            }
        }
        revalidate(); 
        repaint();   
    }

    public void createContents(Tablero tabla){
        getContentPane().removeAll(); 

        for(int i = 0; i < 64; i++){
            final int a = i;

            buttons[i%8][i/8] = new JButton();
            
            JButton btn = buttons[i%8][i/8];

            if((a%8 + a/8)%2==0){
                btn.setBackground(Color.WHITE);
            }else{
                btn.setBackground(Color.GRAY);
            }

            if((tabla.tabla[i/8][i%8].tienePieza())){
                if(tabla.tabla[i/8][i%8].getPieza().getPlayer().isWhite()){
                    try{

                        Image img = ImageIO.read(getClass().getClassLoader().getResourceAsStream(tabla.tabla[i/8][i%8].getPieza().getPath1()));
                        Image newImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);

                        ImageIcon icono = new ImageIcon(newImg);
                        btn.setIcon(icono);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    try{
                        Image img = ImageIO.read(getClass().getClassLoader().getResourceAsStream(tabla.tabla[i/8][i%8].getPieza().getPath2()));
                        Image newImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);

                        ImageIcon icono = new ImageIcon(newImg);
                        btn.setIcon(icono);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
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
    //Esperar a que haga click -> CountDownLatch
    
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
