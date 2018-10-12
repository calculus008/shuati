package common;

/**
 * Created by yuank on 4/25/18.
 */
public class UnionFindWithCount {
    int count; // # of connected components
    int[] parent;
    int[] rank;

    public UnionFindWithCount(char[][] grid) { // for problem 200
        count = 0;
        int m = grid.length;
        int n = grid[0].length;
        parent = new int[m * n];
        rank = new int[m * n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == '1') {
                    parent[i * n + j] = i * n + j;
                    ++count;
                }
                rank[i * n + j] = 0;
            }
        }
    }

    public UnionFindWithCount(int N) { // for problem 305 and others
        count = 0;
        parent = new int[N];
        rank = new int[N];
        for (int i = 0; i < N; ++i) {
            /**
             * init as -1, then use setParent() when adding new element.
             * this makes it possible to add element one by one, instead of adding all at the same time at init stage.
             */
            parent[i] = -1;
            rank[i] = 0;
        }
    }

    public boolean isValid(int i) { // for problem 305
        return parent[i] >= 0;
    }

    public void setParent(int i) {
        parent[i] = i;
        //!!! maintain counter
        ++count;
    }

    public int find(int i) { // path compression
        if (parent[i] != i) {
            parent[i] = find(parent[i]);
        }
        return parent[i];
    }

    public void union(int x, int y) { // union with rank
        int rootx = find(x);
        int rooty = find(y);
        if (rootx != rooty) {
            if (rank[rootx] > rank[rooty]) {
                parent[rooty] = rootx;
            } else if (rank[rootx] < rank[rooty]) {
                parent[rootx] = rooty;
            } else {
                parent[rooty] = rootx; rank[rootx] += 1;
            }
            //!!! maintain counter
            --count;
        }
    }

    public int getCount() {
        return count;
    }

}
