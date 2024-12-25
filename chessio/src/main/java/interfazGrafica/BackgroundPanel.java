package interfazGrafica;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 

        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("menu/logo.png"));
        Image originalImage = icon.getImage();
        Image resizedImage = originalImage.getScaledInstance(500, 200, Image.SCALE_SMOOTH); 
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel title = new JLabel(resizedIcon);

        Border lineBorder = BorderFactory.createLineBorder(Color.YELLOW, 3);
        title.setBorder(BorderFactory.createCompoundBorder(lineBorder, null));


        title.setAlignmentX(CENTER_ALIGNMENT);
        add(title);
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
        buttonContainer.setOpaque(false); 

        buttonContainer.add(createButton("menu/play.png"));
        buttonContainer.add(createButton("menu/aboutus.png"));
        buttonContainer.add(createButton("menu/challenge.png"));
        buttonContainer.add(createButton("menu/settings.png"));



        add(buttonContainer);
    }
    private JButton createButton(String imagePath) {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
        Image originalImage = icon.getImage();
        Image resizedImage = originalImage.getScaledInstance(300, 120, Image.SCALE_SMOOTH); // Tamaño fijo para los botones
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JButton button = new JButton(resizedIcon);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setSize(100, 100); 
        button.setAlignmentX(CENTER_ALIGNMENT);

        return button;
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

}