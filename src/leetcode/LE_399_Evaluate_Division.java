package leetcode;

import java.util.*;

public class LE_399_Evaluate_Division {
    /**
         Equations are given in the format A / B = k, where A and B are variables represented as strings,
         and k is a real number (floating point number). Given some queries, return the answers.
         If the answer does not exist, return -1.0.

         Example:
         Given a / b = 2.0, b / c = 3.0.
         queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
         return [6.0, 0.5, -1.0, 1.0, -1.0 ].

         The input is:
         vector<pair<string, string>> equations, vector<double>& values,
         vector<pair<string, string>> queries ,

         where equations.size() == values.size(),
         and the values are positive. This represents the equations. Return vector<double>.

         According to the example above:

         equations = [ ["a", "b"], ["b", "c"] ],
         values = [2.0, 3.0],
         queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].

         The input is always valid. You may assume that evaluating the queries will result
         in no division by zero and there is no contradiction.

         Medium

         https://leetcode.com/problems/evaluate-division
     */


    /**
     * Huahua's DFS version
     *
     * a / b = 2.0, b / c = 3.0
     *
     * Graph:
     * a -> (b -> 2.0)
     * b -> (c -> 3.0)
     *
     * a / c :
     * divide(a / c) => divide(b / c) => divide(c / c)
     *  2.0 * 3.0    <-   3.0 * 1.0   <-    1.0
     *
     *  Time : O(e + q * e)  e: number of equations, q: number of queries
     *  Space : O(e)
     */
    class Solution_best {
        /**
         * !!! value is another HashMap:  start node -> (end node -> weight(equation value))
         */
        Map<String, HashMap<String, Double>> g = new HashMap<>();

        public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
            for (int i = 0; i < equations.length; ++i) {
                String x = equations[i][0];
                String y = equations[i][1];
                double k = values[i];
                /**
                 * computeIfAbsent : concise, but very expensive
                 */
                g.computeIfAbsent(x, l -> new HashMap<String, Double>()).put(y, k);
                g.computeIfAbsent(y, l -> new HashMap<String, Double>()).put(x, 1.0 / k);
            }

            double[] ans = new double[queries.length];

            for (int i = 0; i < queries.length; ++i) {
                String x = queries[i][0];
                String y = queries[i][1];
                if (!g.containsKey(x) || !g.containsKey(y)) {
                    ans[i] = -1.0;
                } else {
                    ans[i] = divide(x, y, new HashSet<String>());
                }
            }

            return ans;
        }

        private double divide(String x, String y, Set<String> visited) {
            if (x.equals(y)) {
                return 1.0;
            }

            visited.add(x);

            if (!g.containsKey(x)) {
                return -1.0;
            }

            for (String n : g.get(x).keySet()) {
                if (visited.contains(n)) {
                    continue;
                }

                visited.add(n);
                double d = divide(n, y, visited); //find if a pth between n and y exists
                if (d > 0) {// n -> y path exists
                    /**
                     * !!!
                     * d = n / y, x / y = (n / y) * (x / n) = d * (x / n)
                     */
                    return d * g.get(x).get(n);
                }
            }

            return -1.0;
        }
    }


    /**
     * http://zxi.mytechroad.com/blog/graph/leetcode-399-evaluate-division/
     *
     * Graph
     * DFS
     *
     * Time  : O(e + q * e)
     * Space : O(e)
     */
    class Solution1 {
        class Pair{
            String s;
            double d;
            public Pair(String s, double d) {
                this.s = s;
                this.d = d;
            }
        }

        public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
            /**
             * Build a Directed and Weighed graph
             */
            Map<String, List<Pair>> graph = new HashMap<>();
            int n = equations.length;

            for (int i = 0; i < n; i++) {
                String key = equations[i][0];
                String s = equations[i][1];
                double d = values[i];

                if (!graph.containsKey(key)) {
                    graph.put(key, new ArrayList<>());
                }
                graph.get(key).add(new Pair(s, d));

                if (!graph.containsKey(s)) {
                    graph.put(s, new ArrayList<>());
                }
                graph.get(s).add(new Pair(key, 1/d));
            }

            /**
             * DFS to find answers
             */
            double[] res = new double[queries.length];
            for (int i = 0; i < queries.length; i++) {
                String[] query = queries[i];
                res[i] = dfs(query[0], query[1], graph, new HashSet<String>(), 1.0);
                if (res[i] == 0.0) {
                    res[i] = -1.0;
                }
            }

            return res;
        }

        /**
         * Find path from s1 to s2, at the same time, calculate value along the way
         */
        private double dfs(String s1, String s2, Map<String, List<Pair>> graph, Set<String> visited, double value) {
            if (visited.contains(s1)) {
                return 0.0;
            }

            if (!graph.containsKey(s1)) {
                return 0.0;
            }

            if (s1.equals(s2)) {
                return value;
            }

            //!!!backtracking
            visited.add(s1);

            List<Pair> list = graph.get(s1);
            double tmp = 0.0;
            for (int i = 0; i < list.size(); i++) {
                tmp = dfs(list.get(i).s, s2, graph, visited, value * list.get(i).d);
                /**
                 * return value 0.0 means the path in dfs() not found.
                 * once dfs() find a path, break from loop and return the value "tmp"
                 */
                if (tmp != 0.0) {
                    break;
                }
            }
            //!!!backtracking
            visited.remove(s1);

            return tmp;
        }
    }



    /**
     * Huahua's UnionFind version
     * Time  : O(e + q)
     * Space : O(e)
     */
    class Solution3 {
        class Node {
            public String parent;
            public double ratio;
            public Node(String parent, double ratio) {
                this.parent = parent;
                this.ratio = ratio;
            }
        }

        class UnionFindSet {
            private Map<String, Node> parents = new HashMap<>();

            public Node find(String s) {
                if (!parents.containsKey(s)) {
                    return null;
                }

                Node n = parents.get(s);
                if (!n.parent.equals(s)) {
                    Node p = find(n.parent);
                    n.parent = p.parent;
                    n.ratio *= p.ratio;
                }
                return n;
            }

            public void union(String s, String p, double ratio) {
                boolean hasS = parents.containsKey(s);
                boolean hasP = parents.containsKey(p);

                if (!hasS && !hasP) {
                    parents.put(s, new Node(p, ratio));
                    parents.put(p, new Node(p, 1.0));
                } else if (!hasP) {
                    parents.put(p, new Node(s, 1.0 / ratio));
                } else if (!hasS) {
                    parents.put(s, new Node(p, ratio));
                } else {
                    Node rS = find(s);
                    Node rP = find(p);
                    rS.parent = rP.parent;
                    rS.ratio = ratio / rS.ratio * rP.ratio;
                }
            }
        }

        public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
            UnionFindSet u = new UnionFindSet();

            for (int i = 0; i < equations.length; ++i) {
                u.union(equations[i][0], equations[i][1], values[i]);
            }

            double[] ans = new double[queries.length];

            for (int i = 0; i < queries.length; ++i) {
                Node rx = u.find(queries[i][0]);
                Node ry = u.find(queries[i][1]);
                if (rx == null || ry == null || !rx.parent.equals(ry.parent)) {
                    ans[i] = -1.0;
                } else {
                    ans[i] = rx.ratio / ry.ratio;
                }
            }

            return ans;
        }
    }
}