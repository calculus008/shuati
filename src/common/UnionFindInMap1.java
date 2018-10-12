package common;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by yuank on 10/12/18.
 */

/**
 * Simplified from UnifonFindInMap
 * "Put in query", so need to init parents HashMap when creating UFS instance.
 */
public class UnionFindInMap1 {
    private HashMap<Integer, Integer> parents = new HashMap<Integer, Integer>();

    public boolean union(int x, int y) {
        int p1 = query(x);
        int p2 = query(y);
        if (p1 == p2) {
            return false;
        }
        parents.put(p1, p2);
        return true;
    }

    public int query(int x) {
        if (!parents.containsKey(x)) {
            parents.put(x, x);
        } else if(x != parents.get(x)) {
            parents.put(x, query(parents.get(x)));
        }

        return parents.get(x);
    }
}
