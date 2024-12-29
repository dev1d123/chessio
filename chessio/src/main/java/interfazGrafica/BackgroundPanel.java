package interfazGrafica;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.concurrent.CountDownLatch;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.Border;

import game.Game;
import source.Juego;

public class BackgroundPanel extends JPanel{
    private Image backgroundImage;

    public BackgroundPanel(GameMenu parent){
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

        JButton playButton = createButton("menu/play.png");
        JButton aboutButton = createButton("menu/aboutus.png");
        JButton challengeButton = createButton("menu/challenge.png");
        JButton settingsButton = createButton("menu/settings.png");

        buttonContainer.add(playButton);
        buttonContainer.add(aboutButton);
        buttonContainer.add(challengeButton);
        buttonContainer.add(settingsButton);

        playButton.addActionListener(e -> {

                setVisible(false);
                parent.setVisible(false);

                SelectGame sg = new SelectGame(parent);

                boolean esJuegoLocal = sg.isLocalGame();

            
                if (esJuegoLocal) {
                    System.out.println("El usuario seleccionó Juego Local.");
                    
                
                } else {
                    System.out.println("El usuario seleccionó Juego Online.");
                    if (parent.getUserSelected() == null) {
                        JOptionPane.showMessageDialog(null, "You must be registered to play.", "Error", JOptionPane.ERROR_MESSAGE); 
                    }

                }

                /* 
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        Juego j = new Juego();
                        TableroGUI tablero = new TableroGUI(j);
                        SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(null, "El tablero ha sido creado")
                        );

                        j.iniciarJuego(tablero);
                        return null;
                    }

                    @Override
                    protected void done() {
                        setVisible(true);
                        parent.setVisible(true);
                    }
                };

                worker.execute();
                */
           


        });


        aboutButton.addActionListener(e -> {
            System.out.println("Mostrando información sobre nosotros...");
        });

        challengeButton.addActionListener(e -> {
            if (parent.getUserSelected() == null) {
                JOptionPane.showMessageDialog(null, "You must be registered to play.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("Iniciando un desafío...");
            }
        });

        settingsButton.addActionListener(e -> {
            Settings settings = new Settings(parent);


        });


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