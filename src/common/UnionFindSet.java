package common;

/**
 * Created by yuank on 4/23/18.
 */

/**
 *
 * Disjoint Set/Union Find Forest
 * http://zxi.mytechroad.com/blog/data-structure/sp1-union-find-set/
 *
 * Time : find : O(1), union : O(1), amortised
 *
 * Space : O(n)
 */
public class UnionFindSet {
    private int[] parents;
    private int[] ranks;

    public UnionFindSet(int n) {
        parents = new int[n + 1];
        ranks = new int[n + 1];

        //!!! each node points to itself in the beginning
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
            ranks[i] = 1;
        }
    }

    public int find(int u) {
        while (parents[u] != u) {//Path Compression
            parents[u] = parents[parents[u]];
            u = parents[u];
        }
        return u;
    }

    public boolean union(int u, int v) {
        int pu = find(u);
        int pv = find(v);

        if (pu == pv) return false;

        //Uion by Rank
        if (ranks[pu] > ranks[pv]) {
            parents[pv] = pu;
        } else if (ranks[pv] > ranks[pu]) {
            parents[pu] = pv;
        } else {
            parents[pv] = pu;
            ranks[pu]++;
        }

        return true;
    }
}
