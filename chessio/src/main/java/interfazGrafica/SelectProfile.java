package interfazGrafica;

import javax.swing.*;

import unsa.assets.ProfileC;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SelectProfile  extends JFrame{

    private JFrame frame;
    private JPanel panel;
    private ButtonGroup profileGroup;
    private JTextField passwordField;

    public SelectProfile() {
        frame = new JFrame("Select Profile");
        frame.setSize(600, 500);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel title = new JLabel("Select Your Profile", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);

        JPanel profilesPanel = new JPanel();
        profilesPanel.setLayout(new BoxLayout(profilesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(profilesPanel);

        profileGroup = new ButtonGroup();

        File dataFolder = new File("chessio/src/main/resources/data");
        if (dataFolder.exists() && dataFolder.isDirectory()) {
            File[] files = dataFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String profileName = file.getName();
                        profilesPanel.add(createProfileCard(profileName));
                    }
                }
            }
        } else {
            JLabel errorLabel = new JLabel("No profiles found or data folder missing.", SwingConstants.CENTER);
            errorLabel.setForeground(Color.RED);
            profilesPanel.add(errorLabel);
        }

        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        bottomPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        bottomPanel.add(passwordField, gbc);

        JButton accessButton = new JButton("Access");
        accessButton.addActionListener(e -> handleAccess());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        bottomPanel.add(accessButton, gbc);

        JButton deleteButton = new JButton("Delete Profile");
        deleteButton.addActionListener(e -> handleDelete());
        gbc.gridx = 1;
        gbc.gridy = 1;
        bottomPanel.add(deleteButton, gbc);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private JPanel createProfileCard(String profileName) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(400, 120)); 
        card.setMaximumSize(new Dimension(200, 120));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(new Color(245, 247, 255)); 
    
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(100, 150, 255), 3, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                card.setBackground(new Color(230, 240, 255)); 
            }
    
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                card.setBackground(new Color(245, 247, 255)); 
            }
    
        });
    
        ImageIcon originalIcon = new ImageIcon("chessio/src/main/resources/icons/profile-icon.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Escalar la imagen
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel iconLabel = new JLabel(scaledIcon); 

        card.add(iconLabel, BorderLayout.NORTH);

        JLabel profileLabel = new JLabel(profileName, SwingConstants.CENTER);
        profileLabel.setFont(new Font("Sans", Font.BOLD, 16));
        profileLabel.setForeground(new Color(50, 50, 50));
        card.add(profileLabel, BorderLayout.CENTER);
    
        JRadioButton profileButton = new JRadioButton();
        profileButton.setActionCommand(profileName);
        profileGroup.add(profileButton);
        profileButton.setOpaque(false);
        card.add(profileButton, BorderLayout.SOUTH);
    
        return card;
    }
    

    private void handleAccess() {
        String selectedProfile = getSelectedProfile();
        String password = passwordField.getText();

        if (selectedProfile == null) {
            JOptionPane.showMessageDialog(frame, "Please select a profile.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter your password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File dataFolder = new File("chessio/src/main/resources/data");
        File profileFile = new File(dataFolder, selectedProfile);

        if (profileFile.exists() && profileFile.isFile()) {
            try{
                ProfileC tempP;
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(profileFile))) {
                    tempP = (ProfileC) ois.readObject();
                }
                System.out.println("Access granted for profile: " + tempP.getName());
                System.out.println("Access granted for pass: " + tempP.getPassword());

                if(password.equals(tempP.getPassword())){
                    JOptionPane.showMessageDialog(frame, "Access granted for profile: " + tempP.getName());
                }else{
                    JOptionPane.showMessageDialog(frame, "Incorrect password. Access denied.", "Error", JOptionPane.ERROR_MESSAGE);
                }
    

            }catch(IOException | ClassNotFoundException e){
                JOptionPane.showMessageDialog(frame, "Failed to read the profile file.", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

        }else{
            JOptionPane.showMessageDialog(frame, "Profile file not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void handleDelete() {
        String selectedProfile = getSelectedProfile();
        String password = passwordField.getText();
    
        if (selectedProfile == null) {
            JOptionPane.showMessageDialog(frame, "Please select a profile to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter your password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        File dataFolder = new File("chessio/src/main/resources/data");
        File profileFile = new File(dataFolder, selectedProfile);
    
        if (profileFile.exists() && profileFile.isFile()) {
            try {
                // Deserializar el archivo para obtener el objeto ProfileC
                ProfileC tempP;
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(profileFile))) {
                    tempP = (ProfileC) ois.readObject();
                }
    
                // Validar la contraseña
                if (password.equals(tempP.getPassword())) {
                    // Confirmar eliminación
                    int confirmation = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to delete the profile: " + tempP.getName() + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                    );
    
                    if (confirmation == JOptionPane.YES_OPTION) {
                        if (profileFile.delete()) {
                            JOptionPane.showMessageDialog(frame, "Profile " + tempP.getName() + " deleted successfully.");
                            frame.dispose();
                            SwingUtilities.invokeLater(SelectProfile::new); // Reiniciar la ventana para reflejar cambios
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to delete the profile.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect password. Access denied.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(frame, "Failed to read the profile file.", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Profile file not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    public String getSelectedProfile() {
        if (profileGroup.getSelection() != null) {
            return profileGroup.getSelection().getActionCommand();
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SelectProfile::new);
    }
}
