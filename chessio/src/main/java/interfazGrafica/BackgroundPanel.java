package interfazGrafica;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class BackgroundPanel extends JPanel{
    private Image backgroundImage;

    public BackgroundPanel(){
        backgroundImage = Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("menu/background.jpg"));
        //agregar todos los botones y titulos 
        
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("menu/logo.png"));
        Image originalImage = icon.getImage();
        Image resizedImage = originalImage.getScaledInstance(500, 200, Image.SCALE_SMOOTH); 
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel title = new JLabel(resizedIcon);

        Border lineBorder = BorderFactory.createLineBorder(Color.YELLOW, 3);
        title.setBorder(BorderFactory.createCompoundBorder(lineBorder, null));

        add(title);


        ImageIcon icon2 = new ImageIcon(getClass().getClassLoader().getResource("menu/play.png"));
        JButton btn = new JButton(icon2);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        add(btn);                                                                                                  

    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

}