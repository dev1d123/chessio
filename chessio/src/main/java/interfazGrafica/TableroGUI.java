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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class TableroGUI extends JFrame {
    private Pair seleccion;
    private CountDownLatch latch;
    private Juego juego;
    private JButton[][] buttons;
    private int textureID;
    private TimerWindow timerWindow;

    public TableroGUI(Juego juego, boolean help, int textures, boolean time) {
        this.textureID = textures;
        this.juego = juego;

        buttons = new JButton[8][8];
        setTitle("Tablero Chessio");
        setSize(640, 640);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 8));

        createContents(juego.getTablero());

        if (time) {
            timerWindow = new TimerWindow(this);
        }

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (timerWindow != null) {
                    timerWindow.dispose();
                }
                dispose();
            }
        });
    }
    public void paintSquare(int x, int y, Color color) {
        buttons[y][x].setBackground(color);
    
        revalidate();
        repaint();
    }
    
    public void paintMovements(ArrayList<Pair> mov) {
        for (int i = 0; i < 64; i++) {
            if ((i % 8 + i / 8) % 2 == 0) {
                buttons[i % 8][i/8].setBackground(Color.WHITE);
            } else {
                buttons[i % 8][i/8].setBackground(Color.GRAY);
            }
        }
        for (Pair p : mov) {
            if (juego.getTablero().tabla[p.getX()][p.getY()].tienePieza()) {
                buttons[p.getY()][p.getX()].setBackground(Color.RED);
            } else {
                buttons[p.getY()][p.getX()].setBackground(Color.YELLOW);
            }
        }
        revalidate();
        repaint();
    }

    public void createContents(Tablero tabla) {
        getContentPane().removeAll();

        for (int i = 0; i < 64; i++) {
            final int a = i;

            buttons[i % 8][i / 8] = new JButton();

            JButton btn = buttons[i % 8][i / 8];

            if ((a % 8 + a / 8) % 2 == 0) {
                btn.setBackground(Color.WHITE);
            } else {
                btn.setBackground(Color.GRAY);
            }

            if ((tabla.tabla[i / 8][i % 8].tienePieza())) {
                try {
                    Image img;
                    if (tabla.tabla[i / 8][i % 8].getPieza().getPlayer().isWhite()) {
                        img = ImageIO.read(getClass().getClassLoader().getResourceAsStream(tabla.tabla[i / 8][i % 8].getPieza().getPath1(textureID)));
                    } else {
                        img = ImageIO.read(getClass().getClassLoader().getResourceAsStream(tabla.tabla[i / 8][i % 8].getPieza().getPath2(textureID)));
                    }
                    Image newImg = img.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                    btn.setIcon(new ImageIcon(newImg));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Has presionado el boton " + a / 8 + ", " + a % 8);
                    notificarSeleccion(new Pair(a / 8, a % 8));
                }
            });
            add(btn);
        }

        revalidate();
        repaint();
    }

    public synchronized void notificarSeleccion(Pair seleccion) {
        this.seleccion = seleccion;
        notify();
    }

    public synchronized Pair seleccionarElemento() {
        System.out.println("Esperando al usuario!!");

        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return seleccion;
    }

    public void reload() {
        createContents(juego.getTablero());
    }
}

class TimerWindow extends JFrame {
    private JLabel timerLabel;
    private Timer timer;
    private int secondsElapsed;

    public TimerWindow(JFrame parent) {
        setTitle("GAME TIME");
        setSize(200, 100);
        setResizable(false);
        setLocation(parent.getX(), parent.getY() + parent.getHeight());

        timerLabel = new JLabel("Time: 00:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(timerLabel);

        startTimer();
        setVisible(true);

        parent.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopTimer();
                dispose();
            }
        });
    }

    private void startTimer() {
        timer = new Timer();
        secondsElapsed = 0;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                secondsElapsed++;
                int minutes = secondsElapsed/60;
                int seconds = secondsElapsed%60;
                timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
            }
        }, 1000, 1000);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
