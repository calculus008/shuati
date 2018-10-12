package common;

import java.util.HashMap;

/**
 * Created by yuank on 10/12/18.
 */

/**
 * "Put when query" version
 */
public class UnionFindSetString1 {
    private HashMap<String, String> parents = new HashMap<>();

    public boolean union(String x, String y) {
        String p1 = query(x);
        String p2 = query(y);
        if (p1.equals(p2)) {
            return false;
        }

        parents.put(p1, p2);
        return true;
    }

    public String query(String x) {
        if (!parents.containsKey(x)) {
            parents.put(x, x);
        } else if(!x.equals(parents.get(x))) {
            parents.put(x, query(parents.get(x)));
        }

        return parents.get(x);
    }
}
