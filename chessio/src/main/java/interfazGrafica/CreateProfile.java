package interfazGrafica;

import javax.swing.*;

import unsa.assets.ProfileC;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class CreateProfile extends JFrame {
    public CreateProfile() {
        setTitle("Inicio de Sesión");
        setSize(400, 300);
        setLocationRelativeTo(null);

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
        
        JLabel title = new JLabel("Bienvenido");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(title, gbc);
        
        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setForeground(Color.BLACK);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(userLabel, gbc);

        JTextField userField = new JTextField(15);
        userField.setFont(new Font("Arial", Font.PLAIN, 16));
        userField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        gbc.gridx = 1;
        mainPanel.add(userField, gbc);

        // Crear el campo Contraseña
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        // Botón de inicio de sesión
        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(50, 150, 250));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, gbc);

        // Evento del botón
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText();
                String password = new String(passwordField.getPassword());

                //Comprobar que el nombre no se repita!
                File dataFolder = new File("../data");
                if(!dataFolder.exists()){
                    dataFolder.mkdirs();
                }
                boolean userExists = false;
                if (dataFolder.isDirectory()) {
                    for (File file : Objects.requireNonNull(dataFolder.listFiles())) {
                        if (file.isFile() && file.getName().endsWith(".dat")) {
                            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                                ProfileC existingProfile = (ProfileC) ois.readObject();
                                if (existingProfile.getName().equals(user)) {
                                    userExists = true;
                                    break;
                                }
                            } catch (IOException | ClassNotFoundException ex) {
                                System.err.println("Error al leer el archivo: " + ex.getMessage());
                            }
                        }
                    }
                }

                if(userExists){
                    JOptionPane.showMessageDialog(CreateProfile.this,  "El nombre de usuario ya existe. Intente con otro.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Crear el nuevo perfil
                    ProfileC perfilCreado = new ProfileC();
                    perfilCreado.setName(user);
                    perfilCreado.setPassword(password);

                    // Guardar el perfil en un archivo
                    File profileFile = new File(dataFolder, user + ".dat");
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(profileFile))) {
                        oos.writeObject(perfilCreado);
                        JOptionPane.showMessageDialog(CreateProfile.this, "Creación de cuenta exitosa", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        CreateProfile.this.dispose();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                        JOptionPane.showMessageDialog(CreateProfile.this, "Error al guardar el perfil: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Agregar el panel a la ventana
        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CreateProfile());
    }
}
