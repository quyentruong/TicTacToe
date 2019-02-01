import java.util.HashSet;

/**
 * Represents the Tic Tac Toe board.
 */
public class Board {

    private String ANSI_RESET = "\u001B[0m";
    private String ANSI_RED = "\u001B[31m";
    private String ANSI_WHITE = "\u001B[37m";
    private String ANSI_GREEN = "\u001B[32m";
    static final int BOARD_WIDTH = 9;

    public enum State {Blank, X, O}

    private State[][] board;
    private State playersTurn;
    private State winner;
    private HashSet<Integer> movesAvailable;

    private int moveCount;
    private boolean gameOver;
    int xScore;
    int oScore;

    /**
     * Construct the Tic Tac Toe board.
     */
    Board() {
        board = new State[BOARD_WIDTH][BOARD_WIDTH];
        movesAvailable = new HashSet<>();
        reset();
    }

    /**
     * Set the cells to be blank and load the available moves (all the moves are
     * available at the start of the game).
     */
    private void initialize() {
        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                board[row][col] = State.Blank;
            }
        }

        movesAvailable.clear();

        for (int i = 0; i < BOARD_WIDTH * BOARD_WIDTH; i++) {
            movesAvailable.add(i);
        }
    }

    /**
     * Restart the game with a new blank board.
     */
    void reset() {
        moveCount = 0;
        gameOver = false;
        playersTurn = State.X;
        winner = State.Blank;
        initialize();
    }

    /**
     * Places an X or an O on the specified index depending on whose turn it is.
     *
     * @param index the position on the board (example: index 4 is location (0, 1))
     * @return true if the move has not already been played
     */
    public boolean move(int index) {
        return move(index % BOARD_WIDTH, index / BOARD_WIDTH);
    }

    /**
     * Places an X or an O on the specified location depending on who turn it is.
     *
     * @param x the x coordinate of the location
     * @param y the y coordinate of the location
     * @return true if the move has not already been played
     */
    private boolean move(int x, int y) {

        if (gameOver) {
            throw new IllegalStateException("TicTacToe is over. No moves can be played.");
        }

        if (board[y][x] == State.Blank) {
            board[y][x] = playersTurn;
        } else {
            return false;
        }

        moveCount++;
        movesAvailable.remove(y * BOARD_WIDTH + x);

        // The game is a draw.
        if (moveCount == BOARD_WIDTH * BOARD_WIDTH) {
            winner = State.Blank;
            gameOver = true;
        }
        xScore = 0;
        // Check for a winner.
        checkRow(y);
        checkColumn(x);
        checkDiagonalFromTopLeft(x, y);
        checkDiagonalFromTopRight(x, y);
        if (xScore == BOARD_WIDTH || oScore == BOARD_WIDTH) {
            gameOver = true;
            winner = playersTurn;
        }

        xScore = BOARD_WIDTH - xScore;
        playersTurn = (playersTurn == State.X) ? State.O : State.X;
        return true;
    }

    /**
     * Check to see if the game is over (if there is a winner or a draw).
     *
     * @return true if the game is over
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Get a copy of the array that represents the board.
     *
     * @return the board array
     */
    State[][] toArray() {
        return board.clone();
    }

    /**
     * Check to see who's turn it is.
     *
     * @return the player who's turn it is
     */
    public State getTurn() {
        return playersTurn;
    }

    /**
     * Check to see who won.
     *
     * @return the player who won (or Blank if the game is a draw)
     */
    public State getWinner() {
        if (!gameOver) {
            throw new IllegalStateException("TicTacToe is not over yet.");
        }
        return winner;
    }

    /**
     * Get the indexes of all the positions on the board that are empty.
     *
     * @return the empty cells
     */
    public HashSet<Integer> getAvailableMoves() {
        return movesAvailable;
    }

    /**
     * Checks the specified row to see if there is a winner.
     *
     * @param row the row to check
     */
    private void checkRow(int row) {
        int temp = 0;
        int temp2 = 0;
        for (int i = 0; i < BOARD_WIDTH; i++) {
//            if (board[row][i] != board[row][i - 1]) {
//                break;
//            }
            if (board[row][i] == State.X)
                temp++;
            if (board[row][i] == State.O)
                temp2++;
//            if (i == BOARD_WIDTH - 1) {
//                winner = playersTurn;
//                gameOver = true;
//            }
        }
        xScore = Math.max(xScore, temp);
        oScore = Math.max(oScore, temp2);
    }

    /**
     * Checks the specified column to see if there is a winner.
     *
     * @param column the column to check
     */
    private void checkColumn(int column) {
        int temp = 0;
        int temp2 = 0;
        for (int i = 0; i < BOARD_WIDTH; i++) {
//            if (board[i][column] != board[i - 1][column]) {
//                break;
//            }
            if (board[i][column] == State.X)
                temp++;
            if (board[i][column] == State.O)
                temp2++;
//            if (i == BOARD_WIDTH - 1) {
//                winner = playersTurn;
//                gameOver = true;
//            }
        }
        xScore = Math.max(xScore, temp);
        oScore = Math.max(oScore, temp2);
    }

    /**
     * Check the left diagonal to see if there is a winner.
     *
     * @param x the x coordinate of the most recently played move
     * @param y the y coordinate of the most recently played move
     */
    private void checkDiagonalFromTopLeft(int x, int y) {
        if (x == y) {
            int temp = 0;
            int temp2 = 0;
            for (int i = 0; i < BOARD_WIDTH; i++) {
//                if (board[i][i] != board[i - 1][i - 1]) {
//                    break;
//                }
                if (board[i][i] == State.X)
                    temp++;
                if (board[i][i] == State.X)
                    temp2++;
//                if (i == BOARD_WIDTH - 1) {
//                    winner = playersTurn;
//                    gameOver = true;
//                }
            }
            xScore = Math.max(xScore, temp);
            oScore = Math.max(oScore, temp2);
        }
    }

    /**
     * Check the right diagonal to see if there is a winner.
     *
     * @param x the x coordinate of the most recently played move
     * @param y the y coordinate of the most recently played move
     */
    private void checkDiagonalFromTopRight(int x, int y) {
        if (BOARD_WIDTH - 1 - x == y) {
            int temp = 0;
            int temp2 = 0;
            for (int i = 0; i < BOARD_WIDTH; i++) {
//                if (board[BOARD_WIDTH - 1 - i][i] != board[BOARD_WIDTH - i][i - 1]) {
//                    break;
//                }
                if (board[BOARD_WIDTH - 1 - i][i] == State.X)
                    temp++;
                if (board[BOARD_WIDTH - 1 - i][i] == State.X)
                    temp2++;
//                if (i == BOARD_WIDTH - 1) {
//                    winner = playersTurn;
//                    gameOver = true;
//                }

            }
            xScore = Math.max(xScore, temp);
            oScore = Math.max(oScore, temp2);
        }
    }

    /**
     * Get a deep copy of the Tic Tac Toe board.
     *
     * @return an identical copy of the board
     */
    public Board getDeepCopy() {
        Board board = new Board();

        for (int i = 0; i < board.board.length; i++) {
            board.board[i] = this.board[i].clone();
        }

        board.playersTurn = this.playersTurn;
        board.winner = this.winner;
        board.movesAvailable = new HashSet<>();
        board.movesAvailable.addAll(this.movesAvailable);
        board.moveCount = this.moveCount;
        board.gameOver = this.gameOver;
        return board;
    }

    @Override
    public String toString() {
        String[][] print = new String[BOARD_WIDTH][BOARD_WIDTH];
        for (int i = 1; i <= BOARD_WIDTH * BOARD_WIDTH; i++) {
            int row = (i % BOARD_WIDTH == 0) ? (i / BOARD_WIDTH) - 1 : i / BOARD_WIDTH;
            int col = (i - 1) % BOARD_WIDTH;
            if (board[row][col] == State.Blank) {
                print[row][col] = i + "";
            } else {
                print[row][col] = board[row][col].name();
            }
        }

        String color;
        for (int j = 0; j < BOARD_WIDTH; j++) {
            System.out.print(" ____");
        }
        System.out.println();
        for (int row = 0; row < BOARD_WIDTH; row++) {
            System.out.print("|");
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (board[row][col] == State.X)
                    color = ANSI_RED;
                else if (board[row][col] == State.O)
                    color = ANSI_GREEN;
                else
                    color = ANSI_WHITE;
                System.out.printf(" %s%-3s%s|", color, print[row][col], ANSI_RESET);

                if (col == BOARD_WIDTH - 1 && row != BOARD_WIDTH - 1) {
                    System.out.println();
                    System.out.print("|");
                    for (int j = 0; j < BOARD_WIDTH; j++) {
                        System.out.print("----|");
                    }
                }
            }

            System.out.println();
        }
        for (int j = 0; j < BOARD_WIDTH; j++) {
            System.out.print(" ----");
        }
        System.out.println();


        return "";
    }

}