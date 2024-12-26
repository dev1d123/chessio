package interfazGrafica;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

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
        image.setLayout(new BoxLayout(image, BoxLayout.Y_AXIS));

        JLabel picLabel = new JLabel(icono);
        picLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);


        JPanel profilePanel = new JPanel(new GridBagLayout());
        GridBagConstraints pc = new GridBagConstraints();

        JLabel profileLabel = new JLabel("Profile:");
        pc.gridx = 0;
        pc.gridy = 0;
        pc.insets = new Insets(5, 5, 5, 5);
        profilePanel.add(profileLabel, pc);

        JTextField profileTextField = new JTextField(20);
        profileTextField.setEditable(false);
        pc.gridx = 1;
        pc.gridy = 0;
        profilePanel.add(profileTextField, pc);

        JButton selectProfileButton = new JButton("Select profile");
        pc.gridx = 0;
        pc.gridy = 1;
        pc.fill = GridBagConstraints.HORIZONTAL;
        profilePanel.add(selectProfileButton, pc);

        selectProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Profile secundaria = new Profile();
                secundaria.setVisible(true);

            }
        });


        JButton createProfileButton = new JButton("Create profile");
        pc.gridx = 1;
        pc.gridy = 1;
        profilePanel.add(createProfileButton, pc);

        createProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                CreateProfile secundaria = new CreateProfile();
                secundaria.setVisible(true);

            }
        });

        image.add(picLabel);
        image.add(profilePanel);

        JPanel space = new BackgroundPanel();

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

        


        
        add(space, c);


        

    }
    public static void main(String args[]){
        SwingUtilities.invokeLater(() -> {
            GameMenu ventana = new GameMenu();
            ventana.setVisible(true);
        });
    }
    
}
