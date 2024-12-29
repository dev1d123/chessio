package interfazGrafica;

import javax.swing.*;
import java.awt.*;

public class AboutUs extends JDialog {

    public AboutUs(Frame parent) {
        super(parent, "About Us", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 30)); 

        JLabel title = new JLabel("About Us", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(new Color(255, 215, 0)); 
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(45, 45, 45));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel description = new JLabel("Chessio - Your gateway to the world of chess.");
        description.setFont(new Font("Arial", Font.PLAIN, 16));
        description.setForeground(Color.WHITE);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(description);

        JLabel team = new JLabel("Team: Dev1d (All_free_god)");
        team.setFont(new Font("Arial", Font.PLAIN, 14));
        team.setForeground(Color.LIGHT_GRAY);
        team.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(team);

        JLabel mission = new JLabel("Our mission: Create unforgettable chess experiences.");
        mission.setFont(new Font("Arial", Font.PLAIN, 14));
        mission.setForeground(Color.LIGHT_GRAY);
        mission.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(mission);

        JLabel contact = new JLabel("Contact: support@chessio.com");
        contact.setFont(new Font("Arial", Font.PLAIN, 14));
        contact.setForeground(Color.LIGHT_GRAY);
        contact.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(contact);


        ImageIcon icon = new ImageIcon(new ImageIcon("chessio/src/main/resources/icons/icon.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        JLabel logo = new JLabel(icon);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(logo);


        mainPanel.add(contentPanel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(255, 69, 0)); 
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

}
