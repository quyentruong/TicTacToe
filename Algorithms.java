/**
 * Uses various algorithms to play Tic Tac Toe.
 */
public class Algorithms {

    /**
     * Algorithms cannot be instantiated.
     */
    private Algorithms() {}


    /**
     * Play a random move.
     * @param board     the Tic Tac Toe board to play on
     */
    public static void random (Board board) {
        Random.run(board);
    }

    /**
     * Play using the Alpha-Beta Pruning algorithm. Include depth in the
     * evaluation function.
     * @param board     the Tic Tac Toe board to play on
     */
    public static void alphaBetaAdvanced (Board board) {
        AlphaBetaAdvanced.run(board.getTurn(), board, Double.POSITIVE_INFINITY);
    }

    /**
     * Play using the Alpha-Beta Pruning algorithm. Include depth in the
     * evaluation function and a depth limit.
     * @param board     the Tic Tac Toe board to play on
     * @param ply       the maximum depth
     */
    public static void alphaBetaAdvanced (Board board, int ply) {
        AlphaBetaAdvanced.run(board.getTurn(), board, ply);
    }

}