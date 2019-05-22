package Interviews.DoorDash;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */

/**
 * public boolean areFourConnected(int player){
 *
 *     // horizontalCheck
 *     for (int j = 0; j + 3 < getHeight(); j++ ){
 *         for (int i = 0; i < getWidth(); i++){
 *             if (this.board[i][j] == player && this.board[i][j+1] == player
 *                 && this.board[i][j+2] == player && this.board[i][j+3] == player){
 *                 return true;
 *             }
 *         }
 *     }
 *
 *     // verticalCheck
 *     for (int i = 0; i + 3 < getWidth(); i++ ){
 *         for (int j = 0; j < getHeight(); j++){
 *             if (this.board[i][j] == player && this.board[i + 1][j] == player
 *                 && this.board[i + 2][j] == player && this.board[i + 3][j] == player){
 *                 return true;
 *             }
 *         }
 *     }
 *
 *     // ascendingDiagonalCheck
 *     for (int i = 3; i < getWidth(); i++){
 *         for (int j = 0; j + 3 < getHeight(); j++){
 *             if (this.board[i][j] == player && this.board[i-1][j+1] == player
 *                 && this.board[i-2][j+2] == player && this.board[i-3][j+3] == player)
 *                 return true;
 *         }
 *     }
 *
 *     // descendingDiagonalCheck
 *     for (int i = 3; i < getWidth(); i++){
 *         for (int j = 3; j < getHeight(); j++){
 *             if (this.board[i][j] == player && this.board[i-1][j-1] == player
 *                 && this.board[i-2][j-2] == player && this.board[i-3][j-3] == player)
 *                 return true;
 *         }
 *     }
 *
 *     return false;
 * }
 */
public class ConnectFour {
    private static final char[] players = new char[] { 'X', 'O' };

    private final int width, height;
    private final char[][] grid;
    private int lastCol = -1, lastTop = -1;

    public ConnectFour(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new char[height][];
        for (int h = 0; h < height; h++) {
            Arrays.fill(this.grid[h] = new char[width], '.');
        }
    }

    public String toString() {
        return IntStream.range(0, this.width)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining()) + "\n" +
                Arrays.stream(this.grid)
                        .map(String::new)
                        .collect(Collectors.joining("\n"));
    }

    /**
     * Prompts the user for a column, repeating until a valid
     * choice is made.
     */
    public void chooseAndDrop(char symbol, Scanner input) {
        do {
            System.out.print("\nPlayer " + symbol + " turn: ");
            int col = input.nextInt();

            if (! (0 <= col && col < this.width)) {
                System.out.println("Column must be between 0 and " +
                        (this.width - 1));
                continue;
            }
            for (int h = this.height - 1; h >= 0; h--) {
                if (this.grid[h][col] == '.') {
                    this.grid[this.lastTop=h][this.lastCol=col] = symbol;
                    return;
                }
            }

            System.out.println("Column " + col + " is full.");
        } while (true);
    }

    /**
     * Detects whether the last chip played was a winning move.
     */
    public boolean isWinningPlay() {
        if (this.lastCol == -1) {
            throw new IllegalStateException("No move has been made yet");
        }
        char sym = this.grid[this.lastTop][this.lastCol];
        String streak = String.format("%c%c%c%c", sym, sym, sym, sym);
        return contains(this.horizontal(), streak) ||
                contains(this.vertical(), streak) ||
                contains(this.slashDiagonal(), streak) ||
                contains(this.backslashDiagonal(), streak);
    }

    /**
     * The contents of the row containing the last played chip.
     */
    private String horizontal() {
        return new String(this.grid[this.lastTop]);
    }

    /**
     * The contents of the column containing the last played chip.
     */
    private String vertical() {
        StringBuilder sb = new StringBuilder(this.height);
        for (int h = 0; h < this.height; h++) {
            sb.append(this.grid[h][this.lastCol]);
        }
        return sb.toString();
    }

    /**
     * The contents of the "/" diagonal containing the last played chip
     * (coordinates have a constant sum).
     */
    private String slashDiagonal() {
        StringBuilder sb = new StringBuilder(this.height);
        for (int h = 0; h < this.height; h++) {
            int w = this.lastCol + this.lastTop - h;
            if (0 <= w && w < this.width) {
                sb.append(this.grid[h][w]);
            }
        }
        return sb.toString();
    }

    /**
     * The contents of the "\" diagonal containing the last played chip
     * (coordinates have a constant difference).
     */
    private String backslashDiagonal() {
        StringBuilder sb = new StringBuilder(this.height);
        for (int h = 0; h < this.height; h++) {
            int w = this.lastCol - this.lastTop + h;
            if (0 <= w && w < this.width) {
                sb.append(this.grid[h][w]);
            }
        }
        return sb.toString();
    }

    private static boolean contains(String haystack, String needle) {
        return haystack.indexOf(needle) >= 0;
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            int height = 6, width = 8, moves = height * width;
            ConnectFour board = new ConnectFour(width, height);
            System.out.println("Use 0-" + (width - 1) + " to choose a column.");
            System.out.println(board);

            for (int player = 0; moves-- > 0; player = 1 - player) {
                char symbol = players[player];
                board.chooseAndDrop(symbol, input);
                System.out.println(board);
                if (board.isWinningPlay()) {
                    System.out.println("Player " + symbol + " wins!");
                    return;
                }
            }
            System.out.println("Game over, no winner.");
        }
    }
}
