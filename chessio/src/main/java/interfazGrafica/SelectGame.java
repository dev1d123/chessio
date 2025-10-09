package interfazGrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectGame extends JDialog {
    private boolean isLocalGame; 
    private int difficultyDepth = 3; // default normal
    private boolean humanPlaysWhite = true; // default

    public SelectGame(Frame parent) {
        super(parent, "Seleccionar Tipo de Juego", true);
        setSize(400, 200);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, Color.GREEN, getWidth(), getHeight(), Color.YELLOW));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Seleccione el Tipo de Juego");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(title, gbc);

        JButton localGameButton = new JButton("Juego Local");
        localGameButton.setFont(new Font("Arial", Font.BOLD, 16));
        localGameButton.setBackground(new Color(50, 150, 250));
        localGameButton.setForeground(Color.WHITE);
        localGameButton.setFocusPainted(false);
        localGameButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        localGameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(localGameButton, gbc);

        JButton onlineGameButton = new JButton("Juego con bot");
        onlineGameButton.setFont(new Font("Arial", Font.BOLD, 16));
        onlineGameButton.setBackground(new Color(50, 150, 250));
        onlineGameButton.setForeground(Color.WHITE);
        onlineGameButton.setFocusPainted(false);
        onlineGameButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        onlineGameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 1;
        mainPanel.add(onlineGameButton, gbc);

        localGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isLocalGame = true; 
                dispose();
            }
        });

        onlineGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isLocalGame = false; 
                String[] opciones = {"Fácil", "Normal", "Difícil"};
                int sel = JOptionPane.showOptionDialog(
                    SelectGame.this,
                    "Selecciona dificultad del bot",
                    "Dificultad",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[1]
                );
                switch (sel) {
                    case 0 -> difficultyDepth = 2; // Fácil
                    case 2 -> difficultyDepth = 5; // Difícil
                    default -> difficultyDepth = 3; // Normal
                }

                // Preguntar color del humano
                String[] colores = {"Blancas", "Negras"};
                int colorSel = JOptionPane.showOptionDialog(
                    SelectGame.this,
                    "¿Con qué color deseas jugar?",
                    "Color",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    colores,
                    colores[0]
                );
                humanPlaysWhite = (colorSel != 1); // 0->Blancas, 1->Negras

                dispose();
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    public boolean isLocalGame() {
        return isLocalGame;
    }

    public int getDifficultyDepth() {
        return difficultyDepth;
    }

    public boolean isHumanPlaysWhite() {
        return humanPlaysWhite;
    }
}
