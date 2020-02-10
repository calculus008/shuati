package Interviews.Karat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Treasures {
    /**
     * Now we have treasures, denoted by 1. Given a board and start and end positions for the player,
     * write a function to return the shortest simple path from start to end that includes all the treasures,
     * if one exists. A simple path is one that does not revisit any location.
     *
     * board3 = [
     * [  1,  0,  0, 0, 0 ],
     * [  0, -1, -1, 0, 0 ],
     * [  0, -1,  0, 1, 0 ],
     * [ -1,  0,  0, 0, 0 ],
     * [  0,  1, -1, 0, 0 ],
     * [  0,  0,  0, 0, 0 ],
     * ]
     *
     * treasure(board3, (5, 0), (0, 4)) -> None
     *
     * treasure(board3, (5, 1), (2, 0)) ->
     * [(5, 1), (4, 1), (3, 1), (3, 2), (2, 2), (2, 3), (1, 3), (0, 3), (0, 2),
     * (0, 1), (0, 0), (1, 0), (2, 0)]
     */

    static List<int[]> treasure(int[][] matrix, int starti, int startj, int endi, int endj) {
        int row = matrix.length, col = matrix[0].length, totalTreasure = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 1) {
                    totalTreasure++;
                }
            }
        }

        List<List<int[]>> res = new ArrayList<>();

        dfs(matrix, starti, startj, 0, totalTreasure, endi, endj, res, new ArrayList<>());

        System.out.println(res.size());

        if (res.size() < 1) return new ArrayList<>();
        List<int[]> shortest = res.get(0);

        for (int i = 1; i < res.size(); i++) {
            if (res.get(i).size() < shortest.size()) {
                shortest = res.get(i);
            }
        }

        printListArray("", shortest);
        return shortest;
    }

    static void dfs(int[][] matrix, int i, int j, int count, int totalTreasure, int endi, int endj, List<List<int[]>> res, List<int[]> cur) {
        int row = matrix.length, col = matrix[0].length;
        int[][] dir = new int[][]{{1, 0}, {-1, 0}, {0, -1}, {0, 1}};

        if (i >= 0 && i < row && j >= 0 && j < col) {
            if (matrix[i][j] == 1 || matrix[i][j] == 0) {
                cur.add(new int[]{i, j});
                if (matrix[i][j] == 1) {
                    count++;
                }
                int temp = matrix[i][j];
                matrix[i][j] = 2;
                if (i == endi && j == endj && count == totalTreasure) {
                    res.add(new ArrayList<>(cur));
                    cur.remove(cur.size() - 1);
                    matrix[i][j] = temp;
                    return;
                }
                for (int[] d : dir) {
                    int newi = i + d[0], newj = j + d[1];
                    //   System.out.println(i + " " + j + " " + newi + " " + newj);
                    dfs(matrix, newi, newj, count, totalTreasure, endi, endj, res, cur);
                }
                matrix[i][j] = temp;
                cur.remove(cur.size() - 1);
            }
        }
    }

    static void printArray(String s, int[] array) {
        System.out.println(s);
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    static void printSet(String s, Set<String> set) {
        System.out.println(s);
        for (String i : set) {
            System.out.println(i + " ");
        }
        System.out.println();
    }

    static void printList(String s, List<String> list) {
        System.out.println(s);
        for (String i : list) {
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printListArray(String s, List<int[]> list) {
        System.out.println(s);
        for (int[] i : list) {
            printArray("", i);
        }
        System.out.println();
    }

    static void printListInt(String s, List<Integer> list) {
        System.out.println(s);
        for (Integer i : list) {
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printMap(String s, Map<String, List<Integer>> map) {
        System.out.println(s);
        for (String ss : map.keySet()) {
            printListInt(ss, map.get(ss));
        }
        System.out.println();
    }

    static void printIntMap(String s, Map<Integer, Integer> map) {
        System.out.println(s);
        for (Integer i : map.keySet()) {
            System.out.println("key: " + i + " value: " + map.get(i));
        }
        System.out.println();
    }

    // Driver Code
    public static void main(String args[]) {
        int[][] matrix = new int[][]{
                {0, -1, -1, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        };
//        treasure(matrix, 0, 0, 2, 3);

        int[][] matrix1 = new int[][]{
                {1,  0,  0, 0, 0},
                {0, -1, -1, 0, 0},
                {0, -1,  0, 1, 0},
                {-1, 0,  0, 0, 0},
                {0,  1, -1, 0, 0},
                {0,  0,  0, 0, 0 },
        };

        treasure(matrix1, 5, 1, 2, 0);

    }

}
