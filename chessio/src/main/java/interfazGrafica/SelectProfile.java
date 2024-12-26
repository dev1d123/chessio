package interfazGrafica;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SelectProfile {

    private JFrame frame;
    private JPanel panel;

    public SelectProfile() {
        // Configuración inicial de la ventana
        frame = new JFrame("Select Profile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel title = new JLabel("Select Your Profile", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        JPanel profilesPanel = new JPanel();
        profilesPanel.setLayout(new GridLayout(0, 3, 10, 10)); // Diseño en cuadrícula
        JScrollPane scrollPane = new JScrollPane(profilesPanel);

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
            panel.add(errorLabel, BorderLayout.CENTER);
        }

        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addProfileButton = new JButton("Add New Profile");
        addProfileButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Feature not implemented yet!"));
        panel.add(addProfileButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private JPanel createProfileCard(String profileName) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        JLabel profileLabel = new JLabel(profileName, SwingConstants.CENTER);
        profileLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Selected profile: " + profileName));

        card.add(profileLabel, BorderLayout.CENTER);
        card.add(selectButton, BorderLayout.SOUTH);

        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SelectProfile::new);
    }
}
