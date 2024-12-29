package interfazGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JDialog {
    private boolean hintsEnabled = true; 
    private String selectedTexture = "Clásico"; 
    private boolean timerEnabled = false; 

    public Settings(Frame parent) {
        super(parent, "Chess Settings", true);
        setSize(600, 500);
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

        JComboBox<String> textureComboBox = new JComboBox<>(new String[]{"a", "b", "c"});
        textureComboBox.setSelectedItem(selectedTexture);
        JLabel texturePreview = new JLabel();
        texturePreview.setPreferredSize(new Dimension(100, 100));
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
        switch (texture) {
            case "a":
                previewLabel.setIcon(new ImageIcon("path_to_classic_texture_image.jpg"));
                break;
            case "b":
                previewLabel.setIcon(new ImageIcon("path_to_wood_texture_image.jpg"));
                break;
            case "c":
                previewLabel.setIcon(new ImageIcon("path_to_metal_texture_image.jpg"));
                break;
            default:
                previewLabel.setIcon(null);
        }
    }

    public boolean isHintsEnabled() {
        return hintsEnabled;
    }

    public String getSelectedTexture() {
        return selectedTexture;
    }

    public boolean isTimerEnabled() {
        return timerEnabled;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Settings settings = new Settings(null);
            System.out.println("Ayudas activadas: " + settings.isHintsEnabled());
            System.out.println("Textura seleccionada: " + settings.getSelectedTexture());
            System.out.println("Temporizador habilitado: " + settings.isTimerEnabled());
        });
    }
}
