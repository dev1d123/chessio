package interfazGrafica;

import source.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TableroGUI extends JFrame {
    private Pair seleccion;
    private CountDownLatch latch;
    private Juego juego;
    private JButton[][] buttons;
    private int textureID;

    private JLabel lblTurno = new JLabel("Turno: -");
    private JLabel lblWhiteClock = new JLabel("Blancas: 10:00");
    private JLabel lblBlackClock = new JLabel("Negras: 10:00");
    private JPanel panelCapturedWhite = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
    private JPanel panelCapturedBlack = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));

    private JPanel hudPanel = new JPanel(new GridLayout(2, 1, 4, 4));
    private JPanel topHud = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel bottomHud = new JPanel(new GridLayout(1, 2));

    private JPanel boardPanel;

    // Color palette (a8 top-left dark)
    private static final Color DARK_SQ = new Color(181, 136, 99);
    private static final Color LIGHT_SQ = new Color(240, 217, 181);

    // Use Swing timer explicitly to avoid clash with java.util.Timer
    private javax.swing.Timer swingClockTimer;

    public TableroGUI(Juego juego, boolean help, int textures, boolean time) {
        this.textureID = textures;
        this.juego = juego;

        buttons = new JButton[8][8];

        setTitle("Tablero Chessio");
        setSize(800, 900);
        setResizable(false);
        setLocationRelativeTo(null);

        // Use BorderLayout and place board+HUD in correct regions
        setLayout(new BorderLayout());

        // Board panel with 8x8 grid
        boardPanel = new JPanel(new GridLayout(8, 8));
        add(boardPanel, BorderLayout.CENTER);

        initHUD(); // SOUTH

        createContents(juego.getTablero());

        // Live clock updater on GUI only (Juego maintains official times)
        swingClockTimer = new javax.swing.Timer(200, e -> setClocks(juego.getWhiteTimeLeftLive(), juego.getBlackTimeLeftLive()));
        swingClockTimer.start();

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (swingClockTimer != null) swingClockTimer.stop();
                dispose();
            }
        });
    }

    public void paintSquare(int row, int col, Color color) {
        buttons[row][col].setBackground(color);
        revalidate();
        repaint();
    }

    public void paintMovements(ArrayList<Pair> mov, Tablero tabla) {
        // Reset board colors
        resetBoardColors();

        // Paint legal moves
        for (Pair p : mov) {
            int r = p.getX();
            int c = p.getY();
            if (juego.getTablero().tabla[r][c].tienePieza()) {
                buttons[r][c].setBackground(Color.RED);
            } else {
                buttons[r][c].setBackground(Color.YELLOW);
            }
        }

        // Highlight king in check
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (tabla.tabla[r][c].tienePieza()) {
                    Pieza p = tabla.tabla[r][c].getPieza();
                    if (p instanceof Rey rey && rey.jaque) {
                        buttons[r][c].setBackground(Color.BLUE);
                    }
                }
            }
        }

        revalidate();
        repaint();
    }

    public void createContents(Tablero tabla) {
        boardPanel.removeAll();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton btn = new JButton();
                buttons[row][col] = btn;

                // Top-left (a8) is dark
                if ((row + col) % 2 == 0) {
                    btn.setBackground(DARK_SQ);
                } else {
                    btn.setBackground(LIGHT_SQ);
                }

                if (tabla.tabla[row][col].tienePieza()) {
                    try {
                        Image img;
                        if (tabla.tabla[row][col].getPieza().getPlayer().isWhite()) {
                            img = ImageIO.read(getClass().getClassLoader().getResourceAsStream(
                                    tabla.tabla[row][col].getPieza().getPath1(textureID)));
                        } else {
                            img = ImageIO.read(getClass().getClassLoader().getResourceAsStream(
                                    tabla.tabla[row][col].getPieza().getPath2(textureID)));
                        }
                        Image newImg = img.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                        btn.setIcon(new ImageIcon(newImg));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                final int r = row;
                final int c = col;
                btn.addActionListener(e -> {
                    System.out.println("Has presionado el boton " + r + ", " + c);
                    notificarSeleccion(new Pair(r, c));
                });
                boardPanel.add(btn);
            }
        }

        revalidate();
        repaint();
    }

    public void reload() {
        createContents(juego.getTablero());
    }

    private void resetBoardColors() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                buttons[r][c].setBackground(((r + c) % 2 == 0) ? DARK_SQ : LIGHT_SQ);
            }
        }
    }

    private void initHUD() {
        lblTurno.setFont(lblTurno.getFont().deriveFont(Font.BOLD));
        topHud.add(lblTurno);
        topHud.add(Box.createHorizontalStrut(16));
        topHud.add(lblWhiteClock);
        topHud.add(Box.createHorizontalStrut(8));
        topHud.add(lblBlackClock);

        JPanel capWhiteWrapper = new JPanel(new BorderLayout());
        capWhiteWrapper.add(new JLabel("Blancas capturaron: "), BorderLayout.WEST);
        capWhiteWrapper.add(panelCapturedWhite, BorderLayout.CENTER);

        JPanel capBlackWrapper = new JPanel(new BorderLayout());
        capBlackWrapper.add(new JLabel("Negras capturaron: "), BorderLayout.WEST);
        capBlackWrapper.add(panelCapturedBlack, BorderLayout.CENTER);

        bottomHud.add(capWhiteWrapper);
        bottomHud.add(capBlackWrapper);

        hudPanel.add(topHud);
        hudPanel.add(bottomHud);

        add(hudPanel, BorderLayout.SOUTH);
    }

    public void setTurno(String texto) {
        lblTurno.setText(texto);
    }

    public void setClocks(long whiteMs, long blackMs) {
        lblWhiteClock.setText("Blancas: " + formatTime(whiteMs));
        lblBlackClock.setText("Negras: " + formatTime(blackMs));
    }

    public void setCapturadas(List<Pieza> captByWhite, List<Pieza> captByBlack) {
        renderCaptured(panelCapturedWhite, captByWhite);
        renderCaptured(panelCapturedBlack, captByBlack);
        panelCapturedWhite.revalidate();
        panelCapturedBlack.revalidate();
        panelCapturedWhite.repaint();
        panelCapturedBlack.repaint();
    }

    private void renderCaptured(JPanel panel, List<Pieza> piezas) {
        panel.removeAll();
        for (Pieza p : piezas) {
            JLabel l = new JLabel(String.valueOf(p.getSigno()));
            l.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            panel.add(l);
        }
    }

    private String formatTime(long ms) {
        if (ms < 0) ms = 0;
        long totalSec = ms / 1000;
        long m = totalSec / 60;
        long s = totalSec % 60;
        return String.format("%02d:%02d", m, s);
    }

    // Restore blocking selection API used by Juego
    public synchronized void notificarSeleccion(Pair seleccion) {
        this.seleccion = seleccion;
        notify();
    }

    public synchronized Pair seleccionarElemento() {
        try {
            wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return seleccion;
    }
}
