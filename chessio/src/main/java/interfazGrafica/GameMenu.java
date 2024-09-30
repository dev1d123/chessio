package interfazGrafica;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameMenu extends JFrame{
    public GameMenu(){
        setTitle("Chesio");
        setSize(1200, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        createContents();
        setVisible(true);
    }
    private void createContents(){
        GridBagConstraints c = new GridBagConstraints();

        ImageIcon icono = new ImageIcon(getClass().getClassLoader().getResource("menu/test.gif"));
        JPanel image = new JPanel();
        JLabel picLabel = new JLabel(icono);
        
        image.add(picLabel);

        JPanel space = new JPanel();
        space.setBackground(Color.red);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.0; 
        c.weighty = 0.0;  
        c.insets = new Insets(10, 10, 10, 0);

        c.fill = GridBagConstraints.NONE;  
        add(image, c);

        c.gridx = 1;  
        c.gridy = 0;  
        c.weightx = 1.0;  
        c.weighty = 1.0;  
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.BOTH;  

        
        space.add(new JButton("boton"));
        add(space, c);


    }
    public static void main(String args[]){
        GameMenu g = new GameMenu();
    }
    
}
