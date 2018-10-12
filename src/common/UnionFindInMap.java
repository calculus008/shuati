package common;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by yuank on 10/10/18.
 */

/**
 * Instead of using array, this UnionFind Implementation use HashMap.
 * So it can handle index value other than positive integers.
 */
public class UnionFindInMap {
    private HashMap<Integer, Integer> parents = new HashMap<Integer, Integer>();

    public UnionFindInMap(HashSet<Integer> set) {
        for (Integer num : set) {
            parents.put(num, num);
        }
    }

    public void union(int x, int y) {
        int root_x = query(x);
        int root_y = query(y);

        if (root_x != root_y) {
            parents.put(root_x, root_y);
        }
    }

    public int query(int x) {
        /**
         * First, find root
         */
        int root = parents.get(x);
        while (root != parents.get(root)) {
            root = parents.get(root);
        }

        /**
         * Do path compression
         */
        int temp = -1;
        while (x != parents.get(x)) {
            temp = parents.get(x);
            parents.put(x, root);
            x = temp;
        }
        return root;
    }
}
