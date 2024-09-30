package interfazGrafica;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
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

        Image img = null;
        try {
            img = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tower.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image newImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);

        ImageIcon icono = new ImageIcon(newImg);

        JPanel image = new JPanel();
        JLabel picLabel = new JLabel(icono);
        image.add(picLabel);
        add(image);
    }
    public static void main(String args[]){
        GameMenu g = new GameMenu();
    }
    
}
