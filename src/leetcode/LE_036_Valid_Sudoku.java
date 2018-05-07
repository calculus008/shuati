package leetcode;

import java.util.HashSet;

/**
 * Created by yuank on 2/26/18.
 */
public class LE_36_Valid_Sudoku {
    /*
    Determine if a Sudoku is valid.
    The Sudoku board could be partially filled, where empty cells are filled with the character '.'.
     */

    /*
    For a block traversal, it goes the following way.
    0,0, 0,1, 0,2; < — 3 Horizontal Steps followed by 1 Vertical step to next level.
    1,0, 1,1, 1,2; < — 3 Horizontal Steps followed by 1 Vertical step to next level.
    2,0, 2,1, 2,2; < — 3 Horizontal Steps.
    And so on…
    But, the j iterates from 0 to 9.
    But we need to stop after 3 horizontal steps, and go down 1 step vertical.
    Use % for horizontal traversal. Because % increments by 1 for each j : 0%3 = 0 , 1%3 = 1, 2%3 = 2, and resets back.
    So this covers horizontal traversal for each block by 3 steps.

    Use / for vertical traversal. Because / increments by 1 after every 3 j: 0/3 = 0; 1/3 = 0; 2/3 =0; 3/3 = 1.
    So far, for a given block, you can traverse the whole block using just j.

    But because j is just 0 to 9, it will stay only first block. But to increment block, use i.
    To move horizontally to next block, use % again : ColIndex = 3 * i%3 (Multiply by 3 so that the next block is after 3 columns.
    Ie 0,0 is start of first block, second block is 0,3 (not 0,1);
    Similarly, to move to next block vertically, use / and multiply by 3 as explained above.

    Form the row and column in each sub block
    j     0 1 2 3 4 5 6 7 8
    j/3   0 0 0 1 1 1 2 2 2  row
    j%3   0 1 2 0 1 2 0 1 2  column


    Form the starting row and column index for each sub block
    i        0 1 2 3 4 5 6 7 8
    3*(i/3)  0 0 0 3 3 3 6 6 6 row
    3*(i%3)  0 3 6 0 3 6 0 3 6 column
     */

    public boolean isValidSudoku(char[][] board) {
        HashSet<Character> row = new HashSet<>();
        HashSet<Character> column = new HashSet<>();
        HashSet<Character> subsquare = new HashSet<>();

        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j]!='.' && !row.add(board[i][j]))
                    return false;
                if(board[j][i]!='.' && !column.add(board[j][i]))
                    return false;

                int r = 3*(i/3) + j/3;
                int c = 3*(i%3) + j%3;
                if(board[r][c]!='.' && !subsquare.add(board[r][c]))
                    return false;

            }
            row.clear();
            column.clear();
            subsquare.clear();
        }

        return true;
    }
}