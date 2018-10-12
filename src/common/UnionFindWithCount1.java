package common;

/**
 * Created by yuank on 10/10/18.
 */
public class UnionFindWithCount1 {
    int count;
    int[] parents;

    public UnionFindWithCount1(int size) {
        count = 0;
        parents = new int[size + 1];

        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }
    }

    public void union(int a, int b) {
        int root_a = query(a);
        int root_b = query(b);
        if (root_a == root_b) return;

        //union by rank optimzation
        parents[Math.min(root_a, root_b)] = Math.max(root_a, root_b);
        count--;
    }

    public int query(int x) {
        if (parents[x] != x) {
            parents[x] = query(parents[x]);
        }

        return parents[x];
    }

    public int getCount() {
        return count;
    }

    public void setCount(int n) {
        count = n;
    }
}
