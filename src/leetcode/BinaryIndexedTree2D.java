package leetcode;

/**
 * Created by yuank on 4/29/18.
 */
class BinaryIndexedTree2D {
    int[][] tree;
    int[][] input;
    int m;
    int n;

    public BinaryIndexedTree2D(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return;

        m = matrix.length;
        n = matrix[0].length;

        tree = new int[m+1][n+1];
        input = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                update(i, j, matrix[i][j]);
            }
        }
    }

    public void update(int row, int col, int val) {
        if (m == 0 || n == 0) return;

        int delta = val - input[row][col];
        input[row][col] = val;

        for (int i = row + 1; i <= m; i += lowBit(i)) {
            for (int j = col + 1; j <= n; j += lowBit(j)) {
                tree[i][j] += delta;
            }
        }
    }

    //Asssume the caller already takes care of +1 index, similar to case in LE_303, which already uses padding in index
    public int query(int row, int col) {
        int sum = 0;
        for (int i = row; i > 0; i -= lowBit(i)) {
            for (int j = col; j > 0; j -= lowBit(j)) {
                sum += tree[i][j];
            }
        }
        return sum;
    }

    private int lowBit(int i) {
        return i & (-i);
    }
}

