package interfazGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Tablero extends JFrame {
    public Tablero(){
        setTitle("Tablero Chessio");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 8));
        createContents();
        setVisible(true);
    }
    
    public void createContents(){
        for(int i = 0; i < 64; i++){
            final int a = i;
            JButton btn =  new JButton();
            if((a%8 + a/8)%2==0){
                btn.setBackground(Color.WHITE);
            }else{
                btn.setBackground(Color.BLACK);
            }
            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Has presionado el boton " + a/8 + ", " + a%8);
                }
                
            });
            add(btn);
        }
    }

    public static void main(String args[]){
        Tablero xd = new Tablero();

    }
}
