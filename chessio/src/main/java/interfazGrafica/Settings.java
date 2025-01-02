package interfazGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JDialog {
    private boolean hintsEnabled = true; 
    private String selectedTexture = "Clásico"; 
    private boolean timerEnabled = false; 

    public Settings(GameMenu parent) {
        super(parent, "Chess Settings", true);
        setSize(800, 500);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 220)); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Game Settings");
        title.setFont(new Font("Serif", Font.BOLD, 24));
        title.setForeground(new Color(50, 50, 150));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(title, gbc);

        JLabel hintsLabel = new JLabel("Enable help:");
        hintsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(hintsLabel, gbc);

        JCheckBox hintsCheckBox = new JCheckBox();
        hintsCheckBox.setSelected(hintsEnabled);
        hintsCheckBox.addActionListener(e -> hintsEnabled = hintsCheckBox.isSelected());
        gbc.gridx = 1;
        mainPanel.add(hintsCheckBox, gbc);

        JLabel textureLabel = new JLabel("Change textures:");
        textureLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(textureLabel, gbc);

        JComboBox<String> textureComboBox = new JComboBox<>(new String[]{
            TexturesPath.textureToInt(0),
            TexturesPath.textureToInt(1),
            TexturesPath.textureToInt(2),
            TexturesPath.textureToInt(3),
            TexturesPath.textureToInt(4),
            TexturesPath.textureToInt(5),
            TexturesPath.textureToInt(6),
            TexturesPath.textureToInt(7),
            TexturesPath.textureToInt(8),
            TexturesPath.textureToInt(9)
        });
        textureComboBox.setSelectedItem(selectedTexture);
        
        JLabel texturePreview = new JLabel();
        texturePreview.setPreferredSize(new Dimension(600, 100));
        texturePreview.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        updateTexturePreview(textureComboBox.getSelectedItem().toString(), texturePreview);
        textureComboBox.addActionListener(e -> {
            selectedTexture = (String) textureComboBox.getSelectedItem();
            updateTexturePreview(selectedTexture, texturePreview);
        });
        JPanel texturePanel = new JPanel(new BorderLayout());
        texturePanel.add(textureComboBox, BorderLayout.NORTH);
        texturePanel.add(texturePreview, BorderLayout.CENTER);
        gbc.gridx = 1;
        mainPanel.add(texturePanel, gbc);

        JLabel timerLabel = new JLabel("Enable Timer:");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(timerLabel, gbc);

        JCheckBox timerCheckBox = new JCheckBox();
        timerCheckBox.setSelected(timerEnabled);
        timerCheckBox.addActionListener(e -> timerEnabled = timerCheckBox.isSelected());
        gbc.gridx = 1;
        mainPanel.add(timerCheckBox, gbc);

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setBackground(new Color(50, 150, 250));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.helpSettings = hintsEnabled;
                parent.texturesSettings = selectedTexture;
                parent.timeSettings = timerEnabled;

                JOptionPane.showMessageDialog(Settings.this, "Successfully saved configurations.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(saveButton, gbc);

        add(mainPanel);
        setVisible(true);
    }
    private void updateTexturePreview(String texture, JLabel previewLabel) {
        String imagePath = null;
    
        switch (texture) {
            case "Classic":
                imagePath = "chessio/src/main/resources/piecesPreview/classic.png";
                break;
            case "Medieval":
                imagePath = "chessio/src/main/resources/piecesPreview/medieval.png";
                break;
            case "Fantasy":
                imagePath = "chessio/src/main/resources/piecesPreview/fantasy.png";
                break;
            case "Terror":
                imagePath = "chessio/src/main/resources/piecesPreview/terror.png";
                break;
            case "Animals":
                imagePath = "chessio/src/main/resources/piecesPreview/animals.png";
                break;
            case "Reptiles":
                imagePath = "chessio/src/main/resources/piecesPreview/reptiles.png";
                break;
            case "Dino":
                imagePath = "chessio/src/main/resources/piecesPreview/dino.png";
                break;
            case "Aquatic 1":
                imagePath = "chessio/src/main/resources/piecesPreview/aquatic1.png";
                break;
            case "Aquatic 2":
                imagePath = "chessio/src/main/resources/piecesPreview/aquatic2.png";
                break;
            case "Insects":
                imagePath = "chessio/src/main/resources/piecesPreview/insects.png";
                break;
            default:
                previewLabel.setIcon(null);
                return;
        }
    
        if (imagePath != null) {
            // Cargar la imagen
            ImageIcon originalIcon = new ImageIcon(imagePath);
            Image originalImage = originalIcon.getImage();
            
            // Redimensionar la imagen
            int newWidth = 600; // Ancho deseado
            int newHeight = 100; // Altura deseada
            Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    
            // Establecer la imagen redimensionada en el JLabel
            previewLabel.setIcon(new ImageIcon(resizedImage));
        }
    }
    
    



}
