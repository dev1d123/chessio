package source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;

public class BotEngine {

    public static class AIMove {
        public final int fromX, fromY, toX, toY;
        // Nuevo: puntuación ligera para ordenar (p.ej., capturas primero)
        public final int score;
        public AIMove(int fx, int fy, int tx, int ty) { this(fx, fy, tx, ty, 0); }
        public AIMove(int fx, int fy, int tx, int ty, int score) {
            this.fromX = fx; this.fromY = fy; this.toX = tx; this.toY = ty; this.score = score;
        }
    }

    private static class TTEntry {
        final int depth;
        final int score;
        TTEntry(int depth, int score) { this.depth = depth; this.score = score; }
    }

    private final Map<String, TTEntry> tt = new HashMap<>();
    private AIMove bestRoot;

    public AIMove computeBestMove(Tablero board, Player toMove, ArrayList<Movimiento> movSelf, ArrayList<Movimiento> movOpp, int depth, long timeLimitMs) {
        bestRoot = null;
        long start = System.currentTimeMillis();
        alphaBeta(board, toMove, movSelf, movOpp, depth, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1, true, start, timeLimitMs);
        return bestRoot;
    }

    private int alphaBeta(Tablero board, Player toMove, ArrayList<Movimiento> movSelf, ArrayList<Movimiento> movOpp, int depth, int alpha, int beta, boolean isRoot, long start, long timeLimitMs) {
        if (timeLimitMs > 0 && System.currentTimeMillis() - start > timeLimitMs) {
            return evaluate(board, toMove); // cutoff by time
        }
        String key = simpleKey(board, toMove);
        TTEntry entry = tt.get(key);
        if (entry != null && entry.depth >= depth) {
            return entry.score;
        }
        ArrayList<AIMove> moves = generateLegalMoves(board, toMove, movSelf, movOpp);
        if (depth == 0 || moves.isEmpty()) {
            int sc = evaluate(board, toMove);
            tt.put(key, new TTEntry(depth, sc));
            return sc;
        }

        // Ordenar por score descendente y limitar cantidad según profundidad
        moves.sort((a, b) -> Integer.compare(b.score, a.score));
        int limit = moveCap(depth);

        int bestScore = Integer.MIN_VALUE + 1;
        AIMove localBest = null;

        Player opponent = (toMove == board.p1) ? board.p2 : board.p1;
        ArrayList<Movimiento> nextSelf = movOpp; // in next ply, roles swap
        ArrayList<Movimiento> nextOpp = movSelf;

        for (int idx = 0; idx < moves.size() && idx < limit; idx++) {
            AIMove m = moves.get(idx);
            Tablero cp = new Tablero(board);
            // apply move on copy
            Pieza p = cp.tabla[m.fromX][m.fromY].getPieza();
            cp.tabla[m.fromX][m.fromY].setPieza(new Pieza('-'));
            cp.tabla[m.fromX][m.fromY].quitarPieza();
            cp.tabla[m.toX][m.toY].setPieza(p);

            int score = -alphaBeta(cp, opponent, nextSelf, nextOpp, depth - 1, -beta, -alpha, false, start, timeLimitMs);
            if (score > bestScore) {
                bestScore = score;
                localBest = m;
            }
            if (bestScore > alpha) alpha = bestScore;
            if (alpha >= beta) break; // alpha-beta cutoff
        }

        if (isRoot && localBest != null) {
            bestRoot = localBest;
        }
        tt.put(key, new TTEntry(depth, bestScore));
        return bestScore;
    }

    // Topes de movimientos por profundidad: fácil (<=2), medio (<=3), difícil (>3)
    private int moveCap(int depth) {
        if (depth <= 1) return 6;
        if (depth <= 2) return 8;
        if (depth <= 3) return 12;
        return 16;
    }

    private ArrayList<AIMove> generateLegalMoves(Tablero board, Player player, ArrayList<Movimiento> own, ArrayList<Movimiento> opp) {
        ArrayList<AIMove> list = new ArrayList<>();
        Player opponent = (player == board.p1) ? board.p2 : board.p1;

        // Localizar una sola vez al rey del jugador
        int kxInit = -1, kyInit = -1;
        for (int r = 0; r < 8; r++) {
            for (int c2 = 0; c2 < 8; c2++) {
                if (board.tabla[r][c2].tienePieza()
                        && board.tabla[r][c2].getPieza() instanceof Rey
                        && board.tabla[r][c2].getPieza().getPlayer() == player) {
                    kxInit = r; kyInit = c2;
                    break;
                }
            }
            if (kxInit != -1) break;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casilla c = board.tabla[i][j];
                if (!c.tienePieza()) continue;
                if (c.getPieza().getPlayer() != player) continue;

                Pieza moving = c.getPieza();
                ArrayList<Pair> pseudo = moving.getMovimientos(board, own, opp);
                for (Pair mv : pseudo) {
                    int toX = mv.getX(), toY = mv.getY();

                    // Puntuación simple: capturas primero (MVV-LVA aproximado)
                    int sc = 0;
                    if (board.tabla[toX][toY].tienePieza()
                            && board.tabla[toX][toY].getPieza().getPlayer() == opponent) {
                        int capturedVal = pieceValue(board.tabla[toX][toY].getPieza());
                        int moverVal = pieceValue(moving);
                        sc = 1000 + capturedVal - moverVal;
                    }

                    // simulate en copia para validar jaque
                    Tablero cp = new Tablero(board);
                    Pieza p = cp.tabla[i][j].getPieza();
                    cp.tabla[i][j].setPieza(new Pieza('-'));
                    cp.tabla[i][j].quitarPieza();
                    cp.tabla[toX][toY].setPieza(p);

                    // Calcular posición del rey tras el movimiento sin re-escanear todo el tablero
                    int kx = (moving instanceof Rey) ? toX : kxInit;
                    int ky = (moving instanceof Rey) ? toY : kyInit;
                    if (kx == -1) continue;

                    if (!Juego.isSquareAttacked(cp, opponent, kx, ky)) {
                        list.add(new AIMove(i, j, toX, toY, sc));
                    }
                }
            }
        }

        // Ordenar por score descendente
        list.sort((a, b) -> Integer.compare(b.score, a.score));
        return list;
    }

    private int evaluate(Tablero board, Player toMove) {
        // Simplificado: solo material (quitamos movilidad para acelerar)
        int material = 0;
        Player opp = (toMove == board.p1) ? board.p2 : board.p1;
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                Casilla c = board.tabla[i][j];
                if (!c.tienePieza()) continue;
                Pieza p = c.getPieza();
                int val = pieceValue(p);
                if (p.getPlayer() == toMove) material += val; else material -= val;
            }
        }
        return material;
    }

    private int pieceValue(Pieza p) {
        if (p instanceof Peon) return 100;
        if (p instanceof Caballo) return 320;
        if (p instanceof Alfil) return 330;
        if (p instanceof Torre) return 500;
        if (p instanceof Reina) return 900;
        // King is invaluable; small baseline to stabilize
        if (p instanceof Rey) return 0;
        return 0;
    }

    private String simpleKey(Tablero t, Player sideToMove) {
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
        }
        sb.append(sideToMove.isWhite() ? 'w' : 'b');
        return sb.toString();
    }
}
