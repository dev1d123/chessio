package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PromocionDialog extends JDialog {
    Random rn = new Random();
    
    private int opcionSeleccionada = rn.nextInt(4) + 1;

    public PromocionDialog(Frame parent) {
        super(parent, "Selecciona una pieza para la promoción", true);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240)); 

        
        JLabel mensaje = new JLabel("Elige una pieza para promocionar", SwingConstants.CENTER);
        mensaje.setFont(new Font("Arial", Font.BOLD, 18));
        
        add(mensaje, BorderLayout.NORTH);

        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.add(crearBoton("Torre", 1));
        panelBotones.add(crearBoton("Caballo", 2));
        panelBotones.add(crearBoton("Alfil", 3));
        panelBotones.add(crearBoton("Dama", 4));

        add(panelBotones, BorderLayout.CENTER);

        setSize(450, 180);
        setLocationRelativeTo(parent);
        
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2)); 
    }

    private JButton crearBoton(String nombre, int opcion) {
        JButton boton = new JButton(nombre);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(new Color(50, 50, 50)); 
        boton.setForeground(Color.WHITE); 
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1), 
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        boton.addActionListener(e -> seleccionarPieza(opcion));
        return boton;
    }

    private void seleccionarPieza(int opcion) {
        opcionSeleccionada = opcion;
        dispose();
    }

    public int getOpcionSeleccionada() {
        return opcionSeleccionada;
    }

    public static int mostrarDialogo(Frame parent) {
        PromocionDialog dialog = new PromocionDialog(parent);
        dialog.setVisible(true);
        return dialog.getOpcionSeleccionada();
    }
}
