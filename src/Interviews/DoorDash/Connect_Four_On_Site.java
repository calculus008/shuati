package Interviews.DoorDash;

import java.util.Arrays;
import java.util.Scanner;

public class Connect_Four_On_Site {
    static final String[] PLAYER_SYMBOLS = new String[]{"X", "O", "A", "B", "C", "D", "E", "Y", "Z", "H"};

    public final int numRows;
    public final int numCols;
    private final int[][] board;
    private final int humans;
    private final int robots;
    private final int players;

    class Move {
        public final int playerIndex;
        public final int row;
        public final int col;

        public Move(int playerIndex, int row, int col) {
            this.playerIndex = playerIndex;
            this.row = row;
            this.col = col;
        }
    }

    public Connect_Four_On_Site(int rows, int cols, int humans, int robots) {
        this.board = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(board[i], -1);
        }
        this.numRows = rows;
        this.numCols = cols;
        this.humans = humans;
        this.robots = robots;
        this.players = humans + robots;
    }

    /**
     * TODO:  IMPLEMENT!
     *
     * This method is called after each move.
     *
     * @param board = 2 dimensional index of player indexes which are 0 or greater
     * @param lastMove = Move object representing the last move made
     * @return index of winning player; -1 if no winner detected
     */
    public int winningPlayerIndex(int[][] board, Move lastMove) {
        int m = board.length;
        int n = board[0].length;


        int player = lastMove.playerIndex;
        int curRow = lastMove.row;
        int curCol = lastMove.col;


        int start = Math.max(0, curCol - 3);
        for (int i = start; i + 3 < n; i++) {
            if (board[curRow][i] == player && board[curRow][i + 1] == player
                    && board[curRow][i + 2] == player && board[curRow][i + 3] == player) {
                return player;
            }
        }

        int startRow = Math.max(0, curRow - 3);
        for (int i = start; i + 3 < m; i++) {
            if (board[i][curCol] == player && board[i + 1][curCol] == player
                    && board[i + 2][curCol] == player && board[i + 3][curCol] == player) {
                return player;
            }
        }

        int count = 0;
        int x = curRow;
        int y = curCol;
        while (x >= 0 && y >= 0) {
            if (board[x][y] == player) {
                count++;
                x--;
                y--;
                if (count >= 4) {
                    return player;
                }
            } else {
                break;
            }
        }

        x = curRow + 1;
        y = curCol + 1;
        while (x < m && y < n) {
            if (board[x][y] == player) {
                count++;
                x++;
                y++;
                if (count >= 4) {
                    return player;
                }
            } else {
                break;
            }
        }

        int count1 = 0;
        int x1 = curRow;
        int y1 = curCol;
        while (x1 < m && y1 >= 0) {
            if (board[x1][y1] == player) {
                count1++;
                x1++;
                y1--;
                if (count1 >= 4) {
                    return player;
                }
            } else {
                break;
            }
        }

        x1 = curRow - 1;
        y1 = curCol + 1;
        while (x1 >= 0 && y1 < n) {
            if (board[x1][y1] == player) {
                count1++;
                x1--;
                y1++;
                if (count1 >= 4) {
                    return player;
                }
            } else {
                break;
            }
        }



//         //horizontal
//         for (int i = 0; i < m; i++) {
//           for (int j = 0; j + 3 < n; j++) {
//             if (board[i][j] == player && board[i][j + 1] == player && board[i][j + 2] == player && board[i][j + 3] == player) {
//               return player;
//             }
//           }
//         }

//         //vertical
//         for (int i = 0; i + 3 < m; i++) {
//           for (int j = 0; j < n; j++) {
//             if (board[i][j] == player && board[i + 1][j] == player && board[i + 2][j] == player && board[i + 3][j] == player) {
//               return player;
//             }
//           }
//         }

//         //diagonal from upper left to lower right
//         for (int i = 3; i < m; i++) {
//           for (int j = 3; j < n; j++) {
//             if (board[i][j] == player && board[i - 1][j - 1] == player && board[i - 2][j - 2] == player && board[i - 3][j - 3] == player) {
//               return player;
//             }
//           }
//         }

//         //diagnoal from lower left to upper right
//         for (int i = 3; i < m; i++) {
//           for (int j = 0; j + 3 < n; j++) {
//             if (board[i][j] == player && board[i - 1][j + 1] == player && board[i - 2][j + 2] == player && board[i - 3][j + 3] == player) {
//               return player;
//             }
//           }
//         }

        return -1;
    }



    public String getUserInput(String prompt) {
        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);

        //  prompt for the user's name
        System.out.print(prompt);

        // get their input as a String
        String userInput = scanner.next();

        return userInput;
    }

    public void printBoard(int[][] board, String[] playerSymbols) {
        System.out.println("\n" + boardTerminalString(board, PLAYER_SYMBOLS) + "\n");
    }

    public String boardTerminalString(int[][] board, String[] playerSymbols) {
        StringBuilder sb = new StringBuilder();
        sb.append("   ");
        for (int col = 0; col < numCols; col++) {
            sb.append(String.format("%1$" + 3 + "s", col + 1 + ""));
        }
        sb.append("\n");
        for (int row = 0; row < numRows; row++) {
            sb.append(String.format("%1$" + 3 + "s", row + 1 + ""));
            for (int col = 0; col < numCols; col++) {
                int playerIndex = board[row][col];
                String playerSymbol = playerIndex > -1 ? PLAYER_SYMBOLS[playerIndex] : ".";
                playerSymbol = String.format("%1$" + 3 + "s", playerSymbol);
                sb.append(playerSymbol);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean isValidMove(Move move) {
        if (move == null || move.row < 0 || move.col < 0) {
            return false;
        }

        if (move.row >= numRows || move.col >= numCols) {
            return false;
        }

        // can't place over existing piece
        if (board[move.row][move.col] > -1) {
            return false;
        }

        // check that we are stacking if not on bottom row
        if (move.row < numRows - 1 && board[move.row + 1][move.col] < 0) {
            return false;
        }

        return true;
    }

    public void updateBoard(int[][] board, Move move) {
        board[move.row][move.col] = move.playerIndex;
    }

    /**
     * @param playerIndex
     * @param moveString
     * @return Move object or null if unable to parse
     */
    public Move parseMove(int playerIndex, String moveString) {
        try {
            String[] tokens = moveString.split(",");
            if (tokens.length < 2) {
                return null;
            }
            int row = Integer.parseInt(tokens[0].trim());
            int col = Integer.parseInt(tokens[1].trim());
            return new Move(playerIndex, row - 1, col - 1);
        } catch (Exception e) {
            return null;
        }
    }

    public void start() {
        while (true) {
            int moveCount = 0;
            for (int currentPlayerIndex = 0; currentPlayerIndex < players; currentPlayerIndex++) {
                printBoard(board, PLAYER_SYMBOLS);
                Move move = null;
                while (true) {
                    String moveString = getUserInput("Player #" + (currentPlayerIndex + 1) + " Move [ row, col ]:  ");
                    move = parseMove(currentPlayerIndex, moveString);
                    if (isValidMove(move)) {
                        updateBoard(board, move);
                        moveCount++;
                        break;
                    } else {
                        System.out.println("INVALID MOVE");
                    }
                }// while
                if (currentPlayerIndex == winningPlayerIndex(board, move)) {
                    printBoard(board, PLAYER_SYMBOLS);
                    System.out.println("\n\nPlayer #" + (currentPlayerIndex + 1) + " wins!\n\n");
                    System.exit(0);
                }
                if (moveCount == numRows * numCols) {
                    printBoard(board, PLAYER_SYMBOLS);
                    System.out.println("\n\nIt's a draw!");
                    System.exit(0);
                }
            }// for
        }// while
    }

    public static void main(String[] args) throws Throwable {
        int rows = int32OrDefault(System.getProperty("rows"), 6);
        int cols = int32OrDefault(System.getProperty("cols"), 7);
        int humans = int32OrDefault(System.getProperty("humans"), 2);
        int robots = int32OrDefault(System.getProperty("robots"), 0);

        System.out.println("\n>>>>> Board = " + rows + " rows x " + cols + " cols; Human Players = " + humans + "; Robot Players = " + robots + "\n\n");

        Connect_Four_On_Site game = new Connect_Four_On_Site(rows, cols, humans, robots);
        game.start();
    }

    public static int int32OrDefault(String in, int defaultValue) {
        if (in == null || in.trim().length() == 0) {
            return defaultValue;
        }
        return Integer.parseInt(in);
    }


}
