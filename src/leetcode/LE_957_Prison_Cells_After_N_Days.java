package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LE_957_Prison_Cells_After_N_Days {
    /**
     * There are 8 prison cells in a row, and each cell is
     * either occupied or vacant.
     *
     * Each day, whether the cell is occupied or vacant
     * changes according to the following rules:
     *
     * If a cell has two adjacent neighbors that are both
     * occupied or both vacant, then the cell becomes occupied.
     * Otherwise, it becomes vacant.
     * (Note that because the prison is a row, the first and the
     * last cells in the row can't have two adjacent neighbors.)
     *
     * We describe the current state of the prison in the following
     * way: cells[i] == 1 if the i-th cell is occupied, else cells[i] == 0.
     *
     * Given the initial state of the prison, return the state
     * of the prison after N days (and N such changes described above.)
     *
     * Example 1:
     *
     * Input: cells = [0,1,0,1,1,0,0,1], N = 7
     * Output: [0,0,1,1,0,0,0,0]
     * Explanation:
     * The following table summarizes the state of the prison on each day:
     * Day 0: [0, 1, 0, 1, 1, 0, 0, 1]
     * Day 1: [0, 1, 1, 0, 0, 0, 0, 0]
     * Day 2: [0, 0, 0, 0, 1, 1, 1, 0]
     * Day 3: [0, 1, 1, 0, 0, 1, 0, 0]
     * Day 4: [0, 0, 0, 0, 0, 1, 0, 0]
     * Day 5: [0, 1, 1, 1, 0, 1, 0, 0]
     * Day 6: [0, 0, 1, 0, 1, 1, 0, 0]
     * Day 7: [0, 0, 1, 1, 0, 0, 0, 0]
     *
     * Example 2:
     *
     * Input: cells = [1,0,0,1,0,0,1,0], N = 1000000000
     * Output: [0,0,1,1,1,1,1,0]
     *
     * Medium
     */

    /**
     * https://leetcode.com/problems/prison-cells-after-n-days/discuss/205684/JavaPython-Find-the-Loop-or-Mod-14
     *
     * Key
     * Naive solution will time out when N is big.
     *
     * Note that cells.length = 8, and cells[0] and cells[7] will become 0.
     * In fact, cells have only 2 ^ 6 = 64 different states.
     * And there will be a loop.
     *
     * Time Complexity: O(2^N), where N is the number of cells in the prison
     * Space Complexity: O(2^N * N)
     */
    public int[] prisonAfterNDays(int[] cells, int N) {
        int n = cells.length;

        Map<String, Integer> map = new HashMap<>();

        while (N > 0) {
            int[] cells1 = new int[8];

            map.put(Arrays.toString(cells), N);
            N--;

            for (int i = 1; i < 7; i++) {
                cells1[i] = (cells[i - 1] == cells[i + 1]) ? 1 : 0;
            }

            cells = cells1;

            String key = Arrays.toString(cells);
            if (map.containsKey(key)) {
                N %= map.get(key) - N;
            }
        }

        return cells;
    }
}

