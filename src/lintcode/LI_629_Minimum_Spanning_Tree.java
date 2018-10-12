package lintcode;

import common.Connection;
import common.UnionFindWithCount1;

import java.util.*;

/**
 * Created by yuank on 10/11/18.
 */
public class LI_629_Minimum_Spanning_Tree {
    /**
         Given a list of Connections, which is the Connection class (the city name at both ends of the
         edge and a cost between them), find some edges, connect all the cities and spend the least amount.
         Return the connects if can connect all the cities, otherwise return empty list.

         Example
         Gievn the connections = ["Acity","Bcity",1], ["Acity","Ccity",2], ["Bcity","Ccity",3]

         Return ["Acity","Bcity",1], ["Acity","Ccity",2]

         Notice
         Return the connections sorted by the cost, or sorted city1 name if their cost is same,
         or sorted city2 if their city1 name is also same.

         Hard
     */

    /**
     * Kruskal's algorithm：
     * 1.将边排序后选择边
     * 2.在选择边的过程中使用并查集维护保证无环。
     *
     * https://en.wikipedia.org/wiki/Kruskal%27s_algorithm
     *
     * Solution 1 : Not using any UFS class, just use one HashMap "parents" and implement "find()" to have
     *              the UFS functionality.
     * Solution 2 : Use UFS class. Since all fields are String, it uses HashMap "name2ID" to map each city
     *              name to an unique none-negative ID so that we can do union and query with UFS.
     */
    public class Solution1 {
        HashMap<String, String> parents;

        public List<Connection> lowestCost(List<Connection> connections) {
            List<Connection> res = new ArrayList<>();
            if (connections == null || connections.size() == 0) return res;

            parents = new HashMap<>();

            /**
             * Use lambda body to implement Comparator for list sort
             */
            connections.sort((a, b) -> {
                int compare = Integer.compare(a.cost, b.cost);
                if (compare == 0) {
                    compare = a.city1.compareTo(b.city1);
                }
                if (compare == 0) {
                    compare = a.city2.compareTo(b.city2);
                }
                return compare;
            });

            for (Connection con : connections) {
                String root1 = find(con.city1);
                String root2 = find(con.city2);
                /**
                 * 表明city1 and city2 are not connected yet,
                 * connect them (union)
                 */
                if (!root1.equals(root2)) {
                    /**
                     * 相当于UnionFind的union()
                     */
                    parents.put(root1, root2);
                    res.add(con);
                }
            }

            /**
             * "res.size()" is number of edges, "parents.size() - 1"
             * is number nodes
             */
            if (res.size() != parents.size() - 1) {
                return new ArrayList<Connection>();
            }

            return res;
        }

        /**
         * !!!
         */
        private String find(String x) {
            if (!parents.containsKey(x)) {
                /**
                 * 这里，起到UnionFind中初始化的效果，
                 * 所有node的parent都指向自己。
                 */
                parents.put(x, x);
            } else if (!x.equals(parents.get(x))) {
                /**
                 * 这才是UnionFind的query(or find)
                 */
                parents.put(x, find(parents.get(x)));
            }

            return parents.get(x);
        }
    }


    public class Solution2 {
        int n = 0;
        Map<String, Integer> name2ID = new HashMap<>();

        public List<Connection> lowestCost(List<Connection> connections) {
            List<Connection> ans = new ArrayList<>();
            /**
             * init UFS with max possible number of nodes
             */
            UnionFindWithCount1 ufs = new UnionFindWithCount1(connections.size() * 2);

            connections.sort((a, b) -> {
                int compare = Integer.compare(a.cost, b.cost);
                if (compare == 0) {
                    compare = a.city1.compareTo(b.city1);
                }
                if (compare == 0) {
                    compare = a.city2.compareTo(b.city2);
                }
                return compare;
            });

            for (Connection item : connections) {
                /**
                 * calling getID() actually does the init for
                 * HashMap "name2ID", this way we don't need to
                 * loop through connections first to init "name2ID"
                 */
                int c1 = getID(item.city1);
                int c2 = getID(item.city2);
                if (ufs.query(c1) != ufs.query(c2)) {
                    ans.add(item);
                    ufs.union(c1, c2);
                }
            }
            if (ans.size() == n - 1) {
                return ans;
            } else {
                return new ArrayList<>();
            }
        }

        public int getID(String name) {
            if (name2ID.containsKey(name)) {
                return name2ID.get(name);
            } else {
                name2ID.put(name, n++);
                return n - 1;
            }
        }
    }
}
