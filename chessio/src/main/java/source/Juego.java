/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package source;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import interfazGrafica.PromocionDialog;
import interfazGrafica.TableroGUI;

/**
 *
 * @author Windows
 * Gestionar la interaccion de los usuarios con el programa
 */
public class Juego {
    private Tablero tabla;
    private int turno;
    private Player j1;
    private Player j2;
    ArrayList<Movimiento> movJ1 = new ArrayList<Movimiento>();
    ArrayList<Movimiento> movJ2 = new ArrayList<Movimiento>();
    private int text;
    private long initialTimeMs = 10 * 60 * 1000; // 10 minutes
    private long whiteTimeLeft;
    private long blackTimeLeft;
    private long turnStartMs;

    private ArrayList<Pieza> capturedByWhite = new ArrayList<>();
    private ArrayList<Pieza> capturedByBlack = new ArrayList<>();

    private int halfMoveClock = 0; // for fifty-move rule (plies)
    private final Map<String, Integer> repetition = new HashMap<>();
    private boolean lastMoveWasCapture = false;
    private boolean lastMoveWasPawnMove = false;
    private volatile boolean tablasPorAtras = false;

    private boolean vsBot = false;
    private Player botSide = null;
    private int botDepth = 3;
    private final BotEngine botEngine = new BotEngine();
    private boolean executingBotMove = false;

    //Clase jugador

    //paths -> texturas....


    public Juego(int t){ //La posición puede ser una constante (final)
        this.text = t;
        j1 = new Player("Julio", false, false); //negras....abajo, jugador 1 siempre abajo -> posicion = false
        j2 = new Player("Julian", true, true); //blancas....arriba, jugador 2 siempre arriba -> posicion = true
        tabla = new Tablero(j1, j2);     

        // Piezas del jugador j2 (superior)
        tabla.agregarPieza(new Torre(0, 0, j2, t)); 
        tabla.agregarPieza(new Caballo(0, 1, j2, t));
        tabla.agregarPieza(new Alfil(0, 2, j2, t));
        tabla.agregarPieza(new Reina(0, 3, j2, t));
        tabla.agregarPieza(new Rey(0, 4, j2, t));
        tabla.agregarPieza(new Alfil(0, 5, j2, t));
        tabla.agregarPieza(new Caballo(0, 6, j2,t ));
        tabla.agregarPieza(new Torre(0, 7, j2,t ));

        for (int i = 0; i < 8; i++) {
            tabla.agregarPieza(new Peon(1, i, j2, t));
        }

        // Piezas del jugador j1 (inferior)
        tabla.agregarPieza(new Torre(7, 0, j1, t));
        tabla.agregarPieza(new Caballo(7, 1, j1, t));
        tabla.agregarPieza(new Alfil(7, 2, j1,t));
        tabla.agregarPieza(new Reina(7, 3, j1,t));
        tabla.agregarPieza(new Rey(7, 4, j1,t));
        tabla.agregarPieza(new Alfil(7, 5, j1,t));
        tabla.agregarPieza(new Caballo(7, 6, j1,t));
        tabla.agregarPieza(new Torre(7, 7, j1,t));

        for (int i = 0; i < 8; i++) {
            tabla.agregarPieza(new Peon(6, i, j1,t));
        }

        for (int row = 2; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                tabla.agregarCasilla(row, col);
            }
        }
        
        turno = 0;
        // Init clocks
        Player white = (j1.isWhite()) ? j1 : j2;
        Player black = (!j1.isWhite()) ? j1 : j2;
        whiteTimeLeft = initialTimeMs;
        blackTimeLeft = initialTimeMs;

        // Initialize repetition table with initial position
        updateRepetitionKey(tabla, (turno % 2 == 0) ? white : black);
    }
    public Tablero getTablero(){
        return tabla;
    }
    public void iniciarJuego(TableroGUI tab) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Iniciando juego");
        tabla.imprimirTabla();
    
        Player white = (j1.isWhite()) ? j1 : j2;
        Player black = (!j1.isWhite()) ? j1 : j2;
        boolean end = false;
        updateHUD(tab, (turno % 2 == 0) ? white : black);
    
        do {

            Player currentPlayer = (turno % 2 == 0) ? white : black;
            long moveStart = System.currentTimeMillis();
            turnStartMs = moveStart;

            // BOT TURN
            if (vsBot && currentPlayer == botSide) {
                // compute in a thread (alpha-beta)
                final BotEngine.AIMove[] holder = new BotEngine.AIMove[1];
                Thread t = new Thread(() -> {
                    ArrayList<Movimiento> own = (currentPlayer == j1) ? movJ1 : movJ2;
                    ArrayList<Movimiento> opp = (currentPlayer == j1) ? movJ2 : movJ1;
                    holder[0] = botEngine.computeBestMove(tabla, currentPlayer, own, opp, botDepth, 0);
                });
                t.start();
                try { t.join(); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }

                BotEngine.AIMove mv = holder[0];
                if (mv == null) {
                    // No moves: mate or stalemate
                    boolean mate = hayJaqueMate((currentPlayer == white) ? black : white, currentPlayer, tab, tabla, turno);
                    if (mate) {
                        JOptionPane.showMessageDialog(null, "Jaque mate. Ganan " + ((currentPlayer == white) ? "negras" : "blancas"), "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Tablas.", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                    }
                    end = true;
                } else {
                    // Build available moves for validation
                    Casilla from = tabla.tabla[mv.fromX][mv.fromY];
                    ArrayList<Pair> pseudo = (currentPlayer == j1)
                        ? from.getPieza().getMovimientos(tabla, movJ1, movJ2)
                        : from.getPieza().getMovimientos(tabla, movJ2, movJ1);
                    ArrayList<Pair> legales = filtrarLegales(from, pseudo, tabla, currentPlayer, tab);

                    executingBotMove = true;
                    boolean moved = (currentPlayer == j1)
                        ? mover(from, mv.toX, mv.toY, legales, this.tabla, movJ1, movJ2, null)
                        : mover(from, mv.toX, mv.toY, legales, this.tabla, movJ2, movJ1, null);
                    executingBotMove = false;

                    if (moved) {
                        Movimiento m = new Movimiento(from.getPieza().obtenerNombreClase(), mv.fromX, mv.fromY, mv.toX, mv.toY);
                        if (currentPlayer == j1) movJ1.add(m); else movJ2.add(m);

                        long now = System.currentTimeMillis();
                        long elapsed = now - moveStart;
                        if (currentPlayer == white) whiteTimeLeft -= elapsed; else blackTimeLeft -= elapsed;

                        if (whiteTimeLeft <= 0 || blackTimeLeft <= 0) {
                            JOptionPane.showMessageDialog(null, "Tiempo agotado. Ganan " + (currentPlayer == white ? "negras" : "blancas"), "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                            end = true;
                        }

                        if (lastMoveWasCapture || lastMoveWasPawnMove) halfMoveClock = 0; else halfMoveClock++;
                        Player nextToMove = (currentPlayer == white) ? black : white;
                        updateRepetitionKey(tabla, nextToMove);

                        boolean mate = hayJaqueMate(currentPlayer, (currentPlayer == white) ? black : white, tab, tabla, turno);
                        boolean tablas = hayTablas(nextToMove, currentPlayer, tab, tabla);
                        if (mate) {
                            JOptionPane.showMessageDialog(null, "Jaque mate. Ganan " + (currentPlayer == white ? "blancas" : "negras"), "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                            end = true;
                        } else if (tablas) {
                            JOptionPane.showMessageDialog(null, "Tablas.", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                            end = true;
                        }

                        updateHUD(tab, nextToMove);
                        tab.reload();
                        tabla.imprimirTabla();
                        turno++;
                    } else {
                        // If move failed, end with tablas to avoid deadlock
                        JOptionPane.showMessageDialog(null, "Tablas.", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                        end = true;
                    }
                }
                continue; // go to next outer loop iteration
            }

            // HUMAN TURN
            Casilla selectedPiece = null;
            ArrayList<Pair> availableMoves = new ArrayList<>();
            Pair initialPos = new Pair();
            boolean abortedLocal = false; // back -> tablas

            while (true) {
                if (selectedPiece == null) {
                    Pair selection = tab.seleccionarElemento();
                    // Back button sentinel or flag
                    if (tablasPorAtras || (selection != null && selection.X == -1 && selection.Y == -1)) {
                        JOptionPane.showMessageDialog(null, "Tablas (regresar).", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                        abortedLocal = true;
                        break;
                    }
                    selectedPiece = seleccionarPieza(currentPlayer, selection.X, selection.Y);

                    if (selectedPiece != null) {
                        initialPos.X = selection.X;
                        initialPos.Y = selection.Y;
                        ArrayList<Pair> pseudo;
                        if (currentPlayer == j1) {
                            pseudo = selectedPiece.getPieza().getMovimientos(tabla, movJ1, movJ2);
                        } else {
                            pseudo = selectedPiece.getPieza().getMovimientos(tabla, movJ2, movJ1);
                        }
                        availableMoves = filtrarLegales(selectedPiece, pseudo, tabla, currentPlayer, tab);
                        tab.paintMovements(availableMoves, tabla);
                    }
                } else {
                    Pair selection = tab.seleccionarElemento();
                    // Back button sentinel or flag
                    if (tablasPorAtras || (selection != null && selection.X == -1 && selection.Y == -1)) {
                        JOptionPane.showMessageDialog(null, "Tablas (regresar).", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                        abortedLocal = true;
                        break;
                    }

                    Casilla newSelection = seleccionarPieza(currentPlayer, selection.X, selection.Y);
                    if (newSelection != null && newSelection != selectedPiece) {
                        selectedPiece = newSelection;
                        ArrayList<Pair> pseudo;
                        if (currentPlayer == j1) {
                            pseudo = selectedPiece.getPieza().getMovimientos(tabla, movJ1, movJ2);
                        } else {
                            pseudo = selectedPiece.getPieza().getMovimientos(tabla, movJ2, movJ1);
                        }
                        availableMoves = filtrarLegales(selectedPiece, pseudo, tabla, currentPlayer, tab);
                        tab.paintMovements(availableMoves, tabla);
                        continue;
                    }
                    Pieza piezaMov = selectedPiece.getPieza();
                    boolean moved;
                    if (currentPlayer == j1) {
                        moved = mover(selectedPiece, selection.X, selection.Y, availableMoves, this.tabla, movJ1, movJ2, tab);
                    } else {
                        // FIX: pass opponent list properly
                        moved = mover(selectedPiece, selection.X, selection.Y, availableMoves, this.tabla, movJ2, movJ1, tab);
                    }

                    if (moved) {
                        Movimiento m = new Movimiento(piezaMov.obtenerNombreClase(), initialPos.X, initialPos.Y, selection.X, selection.Y);
                        if (currentPlayer == j1) {
                            movJ1.add(m);
                        } else {
                            movJ2.add(m);
                        }

                        // Update clocks
                        long now = System.currentTimeMillis();
                        long elapsed = now - moveStart;
                        if (currentPlayer == white) {
                            whiteTimeLeft -= elapsed;
                        } else {
                            blackTimeLeft -= elapsed;
                        }
                        if (whiteTimeLeft <= 0 || blackTimeLeft <= 0) {
                            JOptionPane.showMessageDialog(null, "Tiempo agotado. Ganan " + (currentPlayer == white ? "negras" : "blancas"), "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                            end = true;
                        }

                        // Update 50-move clock
                        if (lastMoveWasCapture || lastMoveWasPawnMove) halfMoveClock = 0;
                        else halfMoveClock++;

                        // Update repetition table (side to move after increment)
                        Player nextToMove = (currentPlayer == white) ? black : white;
                        updateRepetitionKey(tabla, nextToMove);

                        // Check end conditions
                        boolean mate, tablas, jaque;
                        Player enemy = (currentPlayer == white) ? black : white;

                        jaque = hayJaque(currentPlayer, enemy, tab, tabla);
                        mate = hayJaqueMate(currentPlayer, enemy, tab, tabla, turno);
                        tablas = hayTablas(enemy, currentPlayer, tab, tabla);

                        if (mate) {
                            JOptionPane.showMessageDialog(null, "Jaque mate. Ganan " + (currentPlayer == white ? "blancas" : "negras"), "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                            end = true;
                        } else if (tablas) {
                            JOptionPane.showMessageDialog(null, "Tablas.", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
                            end = true;
                        }

                        updateHUD(tab, nextToMove);
                        break;
                    }
                }
            }

            if (abortedLocal) {
                end = true;
            } else {
                tab.reload();
                tabla.imprimirTabla();
                turno++;
            }
        } while (!end);
        tab.dispose();
    }

    // Enable bot mode: which side and depth (by difficulty)
    public void setBotMode(Player botSide, int depth) {
        this.vsBot = true;
        this.botSide = botSide;
        this.botDepth = Math.max(1, depth);
    }

    // Called by GUI Back button: mark draw and wake selection wait
    public void solicitarTablasPorAtras(TableroGUI tab) {
        this.tablasPorAtras = true;
        if (tab != null) {
            tab.notificarSeleccion(new Pair(-1, -1));
        }
    }

    private void updateHUD(TableroGUI tab, Player toMove) {
        if (tab == null) return;
        try {
            String turnoText = toMove.isWhite() ? "Turno: Blancas" : "Turno: Negras";
            tab.setTurno(turnoText);
            tab.setClocks(getWhiteTimeLeftLive(), getBlackTimeLeftLive());
            tab.setCapturadas(capturedByWhite, capturedByBlack);

            // Modo
            tab.setMode(vsBot ? "Bot" : "Local");

            // Mostrar lado del bot en el HUD solo si aplica
            if (vsBot && botSide != null) {
                String botTxt = botSide.isWhite() ? "blancas(bot)" : "negas(bot)";
                tab.setBotLabel(botTxt);
            } else {
                tab.setBotLabel("");
            }
        } catch (Throwable ignore) {
            // compatibility
        }
    }

    public boolean isWhiteToMove() {
        Player white = (j1.isWhite()) ? j1 : j2;
        Player currentPlayer = (turno % 2 == 0) ? white : ((!j1.isWhite()) ? j1 : j2);
        return currentPlayer == white;
    }

    public long getWhiteTimeLeftLive() {
        Player white = (j1.isWhite()) ? j1 : j2;
        if (isWhiteToMove()) {
            long elapsed = Math.max(0, System.currentTimeMillis() - turnStartMs);
            return Math.max(0, whiteTimeLeft - elapsed);
        } else {
            return Math.max(0, whiteTimeLeft);
        }
    }

    public long getBlackTimeLeftLive() {
        Player white = (j1.isWhite()) ? j1 : j2;
        Player black = (!j1.isWhite()) ? j1 : j2;
        if (!isWhiteToMove()) {
            long elapsed = Math.max(0, System.currentTimeMillis() - turnStartMs);
            return Math.max(0, blackTimeLeft - elapsed);
        } else {
            return Math.max(0, blackTimeLeft);
        }
    }

    private ArrayList<Pair> filtrarLegales(Casilla origen, ArrayList<Pair> pseudo, Tablero tab, Player jugador, TableroGUI tabGUI) {
        ArrayList<Pair> legales = new ArrayList<>();
        for (Pair mov : pseudo) {
            Tablero copia = new Tablero(tab);
            Casilla from = copia.tabla[origen.getX()][origen.getY()];
            Casilla to = copia.tabla[mov.getX()][mov.getY()];
            Pieza p = from.getPieza();

            from.setPieza(new Pieza('-'));
            from.quitarPieza();
            to.setPieza(p);

            Player white = (j1.isWhite()) ? j1 : j2;
            Player black = (!j1.isWhite()) ? j1 : j2;
            boolean sigueJaque;
            if (jugador == white) {
                sigueJaque = hayJaque(black, white, null, copia);
            } else {
                sigueJaque = hayJaque(white, black, null, copia);
            }
            if (!sigueJaque) {
                legales.add(new Pair(mov.getX(), mov.getY()));
            }
        }
        return legales;
    }

    // Overload without GUI for convenience
    private ArrayList<Pair> filtrarLegales(Casilla origen, ArrayList<Pair> pseudo, Tablero tab, Player jugador, Object ignored) {
        return filtrarLegales(origen, pseudo, tab, jugador, (TableroGUI) null);
    }

    public boolean hayJaque(Player p1, Player p2, TableroGUI tab, Tablero esteTablero){
        Rey rey = null;
        int reyX = -1, reyY = -1;

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Casilla casilla = esteTablero.tabla[i][j];
                if(!casilla.tienePieza()) continue;
                if(casilla.getPieza() instanceof Rey && casilla.getPieza().getPlayer() == p2){
                    rey = (Rey)casilla.getPieza();
                    reyX = casilla.getX();
                    reyY = casilla.getY();
                }
            }
        }
        if (rey == null) return false;

        boolean attacked = isSquareAttacked(esteTablero, p1, reyX, reyY);
        if (attacked && tab != null) {
            tab.paintSquare(reyX, reyY, Color.BLUE);
        }
        rey.jaque = attacked;
        return attacked;
    }

    public boolean hayJaqueMate(Player atacante, Player defensor, TableroGUI tab, Tablero tableroActual, int turno) {
        if (!hayJaque(atacante, defensor, null, tableroActual)) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casilla casilla = tableroActual.tabla[i][j];
                if (!casilla.tienePieza()) continue;
                if (casilla.getPieza().getPlayer() != defensor) continue;

                ArrayList<Pair> pseudo;
                if (defensor == j1) pseudo = casilla.getPieza().getMovimientos(tableroActual, movJ1, movJ2);
                else pseudo = casilla.getPieza().getMovimientos(tableroActual, movJ2, movJ1);

                ArrayList<Pair> legales = filtrarLegales(casilla, pseudo, tableroActual, defensor, (TableroGUI) null);
                if (!legales.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean hayTablas(Player toMove, Player rival, TableroGUI tab, Tablero tableroActual) {
        // Stalemate
        if (!hayJaque(rival, toMove, null, tableroActual) && noHayMovimientosLegales(toMove, tableroActual)) {
            return true;
        }
        // 50-move rule
        if (halfMoveClock >= 100) {
            return true;
        }
        // Threefold repetition
        if (isThreefoldRepetition()) {
            return true;
        }
        // Insufficient material
        if (insuficienciaMaterial(tableroActual)) {
            return true;
        }
        return false;
    }

    private boolean noHayMovimientosLegales(Player toMove, Tablero tableroActual) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casilla c = tableroActual.tabla[i][j];
                if (!c.tienePieza()) continue;
                if (c.getPieza().getPlayer() != toMove) continue;

                ArrayList<Pair> pseudo;
                if (toMove == j1) pseudo = c.getPieza().getMovimientos(tableroActual, movJ1, movJ2);
                else pseudo = c.getPieza().getMovimientos(tableroActual, movJ2, movJ1);

                ArrayList<Pair> legales = filtrarLegales(c, pseudo, tableroActual, toMove, (TableroGUI) null);
                if (!legales.isEmpty()) return false;
            }
        }
        return true;
    }

    private boolean insuficienciaMaterial(Tablero t) {
        ArrayList<Pieza> whitePieces = new ArrayList<>();
        ArrayList<Pieza> blackPieces = new ArrayList<>();
        Player white = (j1.isWhite()) ? j1 : j2;
        Player black = (!j1.isWhite()) ? j1 : j2;

        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                Casilla c = t.tabla[i][j];
                if (!c.tienePieza()) continue;
                Pieza p = c.getPieza();
                if (p.getSigno()=='-') continue;
                if (p.getPlayer()==white) whitePieces.add(p);
                else blackPieces.add(p);
            }
        }
        // Count material except kings
        return insufMaterialGrupo(whitePieces) && insufMaterialGrupo(blackPieces);
    }

    private boolean insufMaterialGrupo(ArrayList<Pieza> ps) {
        int bishops = 0;
        int knights = 0;
        int others = 0;
        // Track bishop color squares
        Set<Integer> bishopColors = new HashSet<>();
        for (Pieza p: ps) {
            if (p instanceof Rey) continue;
            if (p instanceof Peon || p instanceof Torre || p instanceof Reina) {
                others++;
            } else if (p instanceof Alfil) {
                bishops++;
            } else if (p instanceof Caballo) {
                knights++;
            }
        }
        if (others > 0) return false;
        if (bishops==0 && knights==0) return true; // K vs K
        if (bishops==1 && knights==0) return true; // K+B vs K
        if (bishops==0 && knights==1) return true; // K+N vs K
        // For simplicity, allow K+B vs K+B on same color bishops only if both sides just have single bishop
        // The cross-check of colors is performed by entire-board function; this local check suffices.
        return false;
    }

    private boolean isThreefoldRepetition() {
        for (int v : repetition.values()) {
            if (v >= 3) return true;
        }
        return false;
    }

    private void updateRepetitionKey(Tablero t, Player sideToMove) {
        String key = computePositionKey(t, sideToMove);
        repetition.put(key, repetition.getOrDefault(key, 0) + 1);
    }

    private String computePositionKey(Tablero t, Player sideToMove) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                Casilla c = t.tabla[i][j];
                if (!c.tienePieza() || c.getPieza().getSigno()=='-') {
                    sb.append('.');
                } else {
                    Pieza p = c.getPieza();
                    char s = p.getSigno();
                    boolean isWhite = p.getPlayer().isWhite();
                    sb.append(isWhite ? Character.toUpperCase(s) : Character.toLowerCase(s));
                }
            }
            sb.append('/');
        }
        sb.append('|').append(sideToMove.isWhite() ? 'w' : 'b');
        // Castling rights
        sb.append('|').append(castlingRightsString());
        // En passant square: from last move if pawn double
        sb.append('|').append(enPassantTarget());
        return sb.toString();
    }

    private String castlingRightsString() {
        StringBuilder r = new StringBuilder();
        // Determine from move history and current board row e-file
        // White at row 0 (in this project)
        if (canStillCastle(true, true)) r.append('K');
        if (canStillCastle(true, false)) r.append('Q');
        if (canStillCastle(false, true)) r.append('k');
        if (canStillCastle(false, false)) r.append('q');
        if (r.length()==0) r.append('-');
        return r.toString();
    }

    private boolean canStillCastle(boolean white, boolean shortSide) {
        Player side = white ? ((j1.isWhite()) ? j1 : j2) : ((!j1.isWhite()) ? j1 : j2);
        ArrayList<Movimiento> own = (side == j1) ? movJ1 : movJ2;
        int row = white ? 0 : 7;
        int rookCol = shortSide ? 7 : 0;
        int kingCol = 4;
        // King or rook moved?
        for (Movimiento m : own) {
            if (m.getPieza().equals("Rey")) return false;
            if (m.getPieza().equals("Torre") && m.getInicioFila()==row && m.getInicioColumna()==rookCol) return false;
        }
        return true;
    }

    private String enPassantTarget() {
        ArrayList<Movimiento> last = (!movJ1.isEmpty() || !movJ2.isEmpty())
            ? ((turno % 2 == 0) ? movJ1 : movJ2) // last move was by previous player
            : null;
        if (last == null || last.isEmpty()) return "-";
        Movimiento lm = last.get(last.size()-1);
        if (lm.getPieza().equals("Peon") && Math.abs(lm.getInicioFila()-lm.getFinFila())==2) {
            int file = lm.getFinColumna();
            int rank = (lm.getInicioFila()+lm.getFinFila())/2;
            char fileChar = (char) ('a' + file);
            char rankChar = (char) ('1' + rank); // ranks as 0..7 -> '1'..'8'
            return ""+fileChar+rankChar;
        }
        return "-";
    }

    public static boolean isSquareAttacked(Tablero t, Player attacker, int x, int y) {
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                Casilla c = t.tabla[i][j];
                if (!c.tienePieza()) continue;
                Pieza p = c.getPieza();
                if (p.getPlayer() != attacker) continue;

                if (p instanceof Peon) {
                    int dir = attacker.getPosicion() ? 1 : -1;
                    if (x == c.getX()+dir && (y == c.getY()+1 || y == c.getY()-1)) return true;
                } else if (p instanceof Caballo) {
                    int[][] d = {{2,1},{2,-1},{-2,1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2}};
                    for (int[] mv: d) if (x == c.getX()+mv[0] && y == c.getY()+mv[1]) return true;
                } else if (p instanceof Rey) {
                    int[][] d = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
                    for (int[] mv: d) if (x == c.getX()+mv[0] && y == c.getY()+mv[1]) return true;
                } else if (p instanceof Alfil || p instanceof Reina) {
                    int[][] dirs = {{1,1},{1,-1},{-1,1},{-1,-1}};
                    for (int[] dir: dirs) {
                        int xi=c.getX(), yi=c.getY();
                        while (true) {
                            xi += dir[0]; yi += dir[1];
                            if (xi<0||xi>=8||yi<0||yi>=8) break;
                            if (xi==x && yi==y) return true;
                            if (t.tabla[xi][yi].tienePieza()) break;
                        }
                    }
                }
                if (p instanceof Torre || p instanceof Reina) {
                    int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
                    for (int[] dir: dirs) {
                        int xi=c.getX(), yi=c.getY();
                        while (true) {
                            xi += dir[0]; yi += dir[1];
                            if (xi<0||xi>=8||yi<0||yi>=8) break;
                            if (xi==x && yi==y) return true;
                            if (t.tabla[xi][yi].tienePieza()) break;
                        }
                    }
                }
            }
        }
        return false;
    }

    public Casilla seleccionarPieza(Player p, int x, int y){ //jugador

        if(x>=8 || x < 0 || y>=8 || y < 0 ){
            System.out.println("Limites excedidos");
            return null;
        }
        
        Casilla res = tabla.tabla[x][y];
        
        if(res.getPieza().getPlayer() != p){
            System.out.println("Esa no es tu pieza!");
            return null;
        }
        /*
         * Si la res.tienePieza()
         * if(res.tienePieza(), )
         * 
         */
        if(res.getPieza().getSigno() != '-'){
            return res;
        }
        System.out.println("No hay una pieza");
        return null;
    }

    public boolean mover(Casilla pieza, int x, int y, ArrayList<Pair> movimientosDisponibles, Tablero tab, ArrayList<Movimiento> m1, ArrayList<Movimiento> m2, TableroGUI tabGUI){
        if(x>=8 || x < 0 || y>=8 || y < 0 ) return false;
        if(pieza.getX() == x && pieza.getY() == y) return false;
        if(!validarMovimientoPieza(x, y, movimientosDisponibles)) return false;

        Pieza mover = pieza.getPieza();
        Player jugador = mover.getPlayer();
        Casilla objetivo = tab.tabla[x][y];

        lastMoveWasCapture = false;
        lastMoveWasPawnMove = (mover instanceof Peon);

        // PROMOTION
        if (mover instanceof Peon) {
            int filaFinal = (jugador.getPosicion()) ? 7 : 0;
            if (x == filaFinal) {
                // Auto-queen for bot moves
                if (executingBotMove) {
                    Pieza nuevaPieza = new Reina(x, y, jugador, text);

                    Tablero copiaTablero = new Tablero(this.tabla);
                    Casilla antes = copiaTablero.tabla[pieza.getX()][pieza.getY()];
                    Casilla objetivoDespues = copiaTablero.tabla[x][y];
                    Pieza captured = objetivoDespues.tienePieza() ? objetivoDespues.getPieza() : null;

                    antes.setPieza(new Pieza('-')); antes.quitarPieza();
                    objetivoDespues.setPieza(nuevaPieza);

                    boolean sigueJaque = (jugador == j2) ? hayJaque(j1, j2, null, copiaTablero) : hayJaque(j2, j1, null, copiaTablero);
                    if (sigueJaque) return false;

                    if (captured != null) { lastMoveWasCapture = true; addCaptured(captured, jugador); }
                    pieza.setPieza(new Pieza('-')); pieza.quitarPieza();
                    objetivo.setPieza(nuevaPieza);
                    return true;
                }

                // Human: show dialog as before
                int opcion = PromocionDialog.mostrarDialogo(tabGUI);
                Pieza nuevaPieza;
                switch (opcion) {
                    case 1 -> nuevaPieza = new Torre(x, y, jugador, text);
                    case 2 -> nuevaPieza = new Caballo(x, y, jugador, text);
                    case 3 -> nuevaPieza = new Alfil(x, y, jugador, text);
                    default -> nuevaPieza = new Reina(x, y, jugador, text);
                }

                // ...existing copy simulation, self-check, captured update and set...
                Tablero copiaTablero = new Tablero(this.tabla);
                Casilla antes = copiaTablero.tabla[pieza.getX()][pieza.getY()];
                Casilla objetivoDespues = copiaTablero.tabla[x][y];
                Pieza captured = objetivoDespues.tienePieza() ? objetivoDespues.getPieza() : null;

                antes.setPieza(new Pieza('-')); antes.quitarPieza();
                objetivoDespues.setPieza(nuevaPieza);

                boolean sigueJaque = (jugador == j2) ? hayJaque(j1, j2, null, copiaTablero) : hayJaque(j2, j1, null, copiaTablero);
                if (sigueJaque) return false;

                if (captured != null) { lastMoveWasCapture = true; addCaptured(captured, jugador); }
                pieza.setPieza(new Pieza('-')); pieza.quitarPieza();
                objetivo.setPieza(nuevaPieza);
                return true;
            }
        }

        // En passant
        ArrayList<Movimiento> movimientosOponente = (jugador == j1) ? movJ2 : movJ1;
        if (!movimientosOponente.isEmpty()) {
            Movimiento lastMove = movimientosOponente.get(movimientosOponente.size() - 1);
            if(lastMove.getPieza().equals("Peon") && Math.abs(lastMove.getInicioFila() - lastMove.getFinFila()) == 2){
                int enemigoFila = lastMove.getFinFila();
                int enemigoColumna = lastMove.getFinColumna();
                if (pieza.getX() == enemigoFila && Math.abs(pieza.getY() - enemigoColumna) == 1 && x == (jugador.getPosicion() ? enemigoFila + 1 : enemigoFila - 1)) {
                    Tablero copiaTablero = new Tablero(this.tabla);
                    Casilla antes = copiaTablero.tabla[pieza.getX()][pieza.getY()];
                    Casilla objetivoDespues = copiaTablero.tabla[x][y];
                    Casilla peonEliminado = copiaTablero.tabla[enemigoFila][enemigoColumna];

                    Pieza moverCopia = antes.getPieza();
                    Pieza capturedCopy = peonEliminado.getPieza();

                    antes.setPieza(new Pieza('-')); antes.quitarPieza();
                    objetivoDespues.setPieza(moverCopia);
                    peonEliminado.setPieza(new Pieza('-')); peonEliminado.quitarPieza();

                    boolean sigueJaque = (jugador == j2) ? hayJaque(j1, j2, null, copiaTablero) : hayJaque(j2, j1, null, copiaTablero);
                    if(sigueJaque) return false;

                    pieza.setPieza(new Pieza('-')); pieza.quitarPieza();
                    objetivo.setPieza(mover);

                    Pieza captured = tab.tabla[enemigoFila][enemigoColumna].getPieza();
                    tab.tabla[enemigoFila][enemigoColumna].setPieza(new Pieza('-'));
                    tab.tabla[enemigoFila][enemigoColumna].quitarPieza();
                    lastMoveWasCapture = true;
                    addCaptured(captured, jugador);
                    return true;
                }
            }
        }

        // Castling (already validated by legal move filter; just move rook accordingly)
        if (mover instanceof Rey && Math.abs(pieza.getY() - y) == 2) {
            int torreColumna = (y > pieza.getY()) ? 7 : 0;
            int nuevaTorreColumna = (y > pieza.getY()) ? y - 1 : y + 1;
            Casilla torreCasilla = tab.tabla[pieza.getX()][torreColumna];

            if (torreCasilla.tienePieza() && torreCasilla.getPieza() instanceof Torre) {
                Pieza torre = torreCasilla.getPieza();
                Casilla nuevaTorreCasilla = tab.tabla[pieza.getX()][nuevaTorreColumna];

                // simulate
                Tablero copiaTablero = new Tablero(this.tabla);
                Casilla copiaReyCasilla = copiaTablero.tabla[pieza.getX()][pieza.getY()];
                Casilla copiaReyDestino = copiaTablero.tabla[x][y];
                Casilla copiaTorreCasilla = copiaTablero.tabla[pieza.getX()][torreColumna];
                Casilla copiaNuevaTorreCasilla = copiaTablero.tabla[pieza.getX()][nuevaTorreColumna];

                Pieza reyCopia = copiaReyCasilla.getPieza();
                Pieza torreCopia = copiaTorreCasilla.getPieza();

                copiaReyCasilla.setPieza(new Pieza('-')); copiaReyCasilla.quitarPieza();
                copiaReyDestino.setPieza(reyCopia);
                copiaTorreCasilla.setPieza(new Pieza('-')); copiaTorreCasilla.quitarPieza();
                copiaNuevaTorreCasilla.setPieza(torreCopia);

                boolean sigueJaque = (jugador == j2) ? hayJaque(j1, j2, null, copiaTablero) : hayJaque(j2, j1, null, copiaTablero);
                if (sigueJaque) return false;

                // Move rook
                torreCasilla.quitarPieza();
                torreCasilla.setPieza(new Pieza('-'));
                nuevaTorreCasilla.setPieza(torre);

                // Move king
                pieza.setPieza(new Pieza('-'));
                pieza.quitarPieza();
                objetivo.setPieza(mover);
                return true;
            }
        }

        // Capture
        if (objetivo.tienePieza()) {
            if (objetivo.getPieza().getPlayer() == jugador) return false;

            Tablero copiaTablero = new Tablero(this.tabla);
            Casilla copiaOrigen = copiaTablero.tabla[pieza.getX()][pieza.getY()];
            Casilla copiaDestino = copiaTablero.tabla[x][y];
            Pieza moverCopia = copiaOrigen.getPieza();

            copiaOrigen.setPieza(new Pieza('-')); copiaOrigen.quitarPieza();
            copiaDestino.setPieza(moverCopia);

            boolean sigueJaque = (jugador == j2) ? hayJaque(j1, j2, null, copiaTablero) : hayJaque(j2, j1, null, copiaTablero);
            if (sigueJaque) return false;

            Pieza captured = objetivo.getPieza();
            objetivo.setPieza(new Pieza('-')); objetivo.quitarPieza();
            pieza.setPieza(new Pieza('-')); pieza.quitarPieza();
            objetivo.setPieza(mover);

            lastMoveWasCapture = true;
            addCaptured(captured, jugador);
            return true;
        }

        // Normal move
        Tablero copiaTablero = new Tablero(this.tabla);
        Casilla copiaOrigen = copiaTablero.tabla[pieza.getX()][pieza.getY()];
        Casilla copiaDestino = copiaTablero.tabla[x][y];
        Pieza moverCopia = copiaOrigen.getPieza();

        copiaOrigen.setPieza(new Pieza('-')); copiaOrigen.quitarPieza();
        copiaDestino.setPieza(moverCopia);

        boolean sigueJaque = (jugador == j2) ? hayJaque(j1, j2, null, copiaTablero) : hayJaque(j2, j1, null, copiaTablero);
        if (sigueJaque) return false;

        pieza.setPieza(new Pieza('-')); pieza.quitarPieza();
        objetivo.setPieza(mover);
        return true;
    }

    private void addCaptured(Pieza captured, Player captor) {
        if (captured == null) return;
        Player white = (j1.isWhite()) ? j1 : j2;
        if (captor == white) {
            capturedByWhite.add(captured);
        } else {
            capturedByBlack.add(captured);
        }
    }

    public boolean validarMovimientoPieza(int x, int y, ArrayList<Pair> movimientosDisponibles){
        for(Pair parsito: movimientosDisponibles){
            if(parsito.X == x && parsito.Y == y){
                return true;
            }
        }
        return false;
    }
    
    
    
    // Método estatico para obtener los movimientos de una pieza que se mueve vertical o diagonalmente.
    public static void agregarMovimientos(Tablero tabla, ArrayList<Pair> res, int startX, int startY, int dx, int dy, Player player) {
        int x = startX;
        int y = startY;
        
        while (true) {
            x += dx;
            y += dy;
            
            if (x < 0 || x >= 8 || y < 0 || y >= 8) {
                break;
            }

            Casilla casilla = tabla.tabla[x][y];
            if (!casilla.tienePieza()) {
                res.add(new Pair(x, y));
            } else {
                if (casilla.getPieza().getPlayer() != player) {
                    res.add(new Pair(x, y)); 
                }
                break;
            }
        }
    }
    public static boolean amenazaCasilla(Player p1, Player p2, Tablero esteTablero, int x, int y, ArrayList<Movimiento> movJ1, ArrayList<Movimiento> movJ2) {
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Casilla casilla = esteTablero.tabla[i][j];
                if(casilla.tienePieza() == false) continue;
                if(casilla.getPieza().getPlayer() == p1){
                    //de p1 a p2
                    ArrayList<Pair> mov = casilla.getPieza().getMovimientos(esteTablero, movJ1, movJ2);
            
                    
                    for(Pair p: mov){
                        if(x == p.X && y == p.Y){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // Configurar modo vs bot desde la selección (color humano y profundidad)
    public void configureVsBot(boolean humanPlaysWhite, int depth) {
        this.vsBot = true;
        Player white = (j1.isWhite()) ? j1 : j2;
        Player black = (!j1.isWhite()) ? j1 : j2;
        this.botSide = humanPlaysWhite ? black : white;
        this.botDepth = Math.max(1, depth);
    }

}
