package Interviews.Karat;

import java.util.*;

public class Parent_Children_Relationship {
    /**
     * 1.Group by number of parents:
     * <p>
     * For graph
     * 1   2    4
     * \ /   / | \
     * 3   5  8  9
     * \ / \     \
     * 6   7    11
     * <p>
     * Input: parent child relation
     * [
     * (1, 3), (2, 3), (3, 6), (5, 6),
     * (5, 7), (4, 5), (4, 8), (4, 9), (9, 11)
     * <p>
     * ]
     * <p>
     * Output:
     * [
     * [1, 2, 4],        // Individuals with zero parents
     * [5, 7, 8, 9, 11] // Individuals with exactly one parent
     * ]
     */
    public static List<List<Integer>> getRelationship(int[][] input) {
        List<List<Integer>> res = new ArrayList<>();
        if (null == input || input.length == 0) return res;

        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, List<Integer>> bucket = new HashMap<>();

        for (int[] pair : input) {
            int parent = pair[0];
            int child = pair[1];

            map.put(child, map.getOrDefault(child, 0) + 1);

            if (!map.containsKey(parent)) {
                map.put(parent, 0);
            }
        }

        for (int key : map.keySet()) {
            int count = map.get(key);
            if (!bucket.containsKey(count)) {
                bucket.put(count, new ArrayList<>());
            }
            bucket.get(count).add(key);
        }

        for (Integer key : bucket.keySet()) {
            print(key.toString(), bucket.get(key));
        }

        return new ArrayList<>(bucket.values());
    }


    /**
     * 2.Has common ancestor
     * <p>
     * For graph
     * 1   2    4
     * \ /   / | \
     * 3   5  8  9
     * \ / \     \
     * 6   7    11
     * <p>
     * Input: parent child relation
     * [
     * (1, 3), (2, 3), (3, 6), (5, 6),
     * (5, 7), (4, 5), (4, 8), (4, 9), (9, 11)
     * ]
     * <p>
     * hasCommonAncestor(3, 7) => false
     * hasCommonAncestor( 6, 7) => true
     */

    private static Map<Integer, List<Integer>> getGraph(int[][] input) {
        Map<Integer, List<Integer>> map = new HashMap<>();

        /**
         * Create adjacent list, child -> list of its parents
         */
        for (int[] pair : input) {
            int parent = pair[0];
            int child = pair[1];

            if (!map.containsKey(child)) {
                map.put(child, new ArrayList<>());
            }
            map.get(child).add(parent);

            if (!map.containsKey(parent)) {
                map.put(parent, new ArrayList<>());
            }
        }

        return map;
    }

    public static boolean hasCommonAncestor(int v1, int v2, int[][] input) {
        Map<Integer, List<Integer>> map = getGraph(input);

        Set<Integer> set = new HashSet<>();
        Queue<Integer> q = new LinkedList<>();
        q.offer(v1);

        /**
         * coloring v1's ancestors using BFS
         */
        while (!q.isEmpty()) {
            int cur = q.poll();
            set.add(cur);

            for (int p : map.get(cur)) {
                q.offer(p);
            }
        }

//        System.out.println(set);

        /**
         * coloing v2's ancestors using BFS
         */
//        q.clear();
        q.offer(v2);
        while (!q.isEmpty()) {
            int cur = q.poll();

            System.out.println(cur);

            /**
             * see the other color
             */
            if (set.contains(cur)) return true;

            for (int p : map.get(cur)) {
                q.offer(p);
            }
        }

        return false;
    }

    /**
     * 3.Earliest ancestor
     * Write a function that, for a given individual in our data set, returns their earliest known ancestor
     * -- the one at the farthest distance from the input individual. If there is more than one ancestor
     * tied for "earliest", return any one of them. If the input individual has no parents, the function
     * should return null (or -1).
     */

    public static int findEarliestAncestor(int v, int[][] input) {
        Map<Integer, List<Integer>> map = getGraph(input);

        //no parent

        System.out.println(map.get(v));
        if (map.get(v).size() == 0) return -1;

        int res = -1;
        Queue<Integer> q = new LinkedList<>();
        q.offer(v);

        while (!q.isEmpty()) {
            res = q.peek();
            int size = q.size();

            for (int i = 0; i < size; i++) {
                int cur = q.poll();
                for (int p : map.get(cur)) {
                    q.offer(p);
                }
            }
        }

        return res;
    }

    /**
     *
     **/
    static void graph(int[][] edges, int i1, int i2, int i3) {

        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, List<Integer>> parent = new HashMap<>();

        for (int[] edge : edges) {
            if (!inDegree.containsKey(edge[0])) {
                inDegree.put(edge[0], 0);
                parent.put(edge[0], new ArrayList<Integer>());
            }
            if (!inDegree.containsKey(edge[1])) {
                inDegree.put(edge[1], 0);
                parent.put(edge[1], new ArrayList<Integer>());
            }
            inDegree.put(edge[1], inDegree.getOrDefault(edge[1], 0) + 1);
            parent.get(edge[1]).add(edge[0]);
        }

        // problem # 1 find nodes
        List<Integer> zeroParent = new ArrayList<>();
        List<Integer> oneParent = new ArrayList<>();

        for (Integer node : inDegree.keySet()) {
            if (inDegree.get(node) == 0) {
                zeroParent.add(node);
            } else if (inDegree.get(node) == 1) {
                oneParent.add(node);
            }
        }

        print("zero", zeroParent);
        print("one", oneParent);

        System.out.println("common " + i1 + " " + i2 + " " + common(i1, i2, parent));
        System.out.println("lowest common " + i1 + " " + i2 + " " + lowestCommon(i1, i2, parent));
        System.out.println("furthest " + i3 + " " + furthest(i3, parent));
    }

    static int lowestCommon(Integer i1, Integer i2, Map<Integer, List<Integer>> parent) {
        Set<Integer> parent1 = new HashSet<>();

        getAll(i1, parent1, parent);
        if (parent1.contains(i2)) {
            return i2;
        }

        Queue<Integer> q = new LinkedList<>();
        q.offer(i2);
        int size = q.size();

        while (!q.isEmpty()) {
            int cur = q.poll();
            if (parent1.contains(cur)) {
                return cur;
            }
            for (Integer p : parent.get(cur)) {
                q.offer(p);
            }
        }
        return -1;
    }

    static int furthest(Integer i, Map<Integer, List<Integer>> parent) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(i);
        int size = q.size();
        int ances = i;

        while (!q.isEmpty()) {
            ances = q.peek();
            while (size-- > 0) {
                int cur = q.poll();
                for (Integer p : parent.get(cur)) {
                    q.offer(p);
                }
            }
            size = q.size();
        }
        return ances;
    }

    static boolean common(Integer i1, Integer i2, Map<Integer, List<Integer>> parent) {
        Set<Integer> parent1 = new HashSet<>();
        getAll(i1, parent1, parent);
        Set<Integer> parent2 = new HashSet<>();
        getAll(i2, parent2, parent);

        for (Integer i : parent1) {
            if (parent2.contains(i)) {
                return true;
            }
        }
        return false;
    }

    static void getAll(Integer i, Set<Integer> parents, Map<Integer, List<Integer>> parent) {
        parents.add(i);
        if (!parent.containsKey(i) || parent.get(i).isEmpty()) {
            return;
        }
        for (Integer p : parent.get(i)) {
            getAll(p, parents, parent);
        }
    }

    static void print(String s, List<Integer> list) {
        System.out.println(s);
        for (Integer i : list) {
            System.out.print(i + " --> ");
        }
        System.out.println();
    }

    /**
     *
     *     1 2  3
     *    /\/   \
     *   4 5    6
     *           \
     *           7
     */
    public static void main(String args[]) {
        int[][] edges = new int[][]{{1, 4}, {1, 5}, {2, 5}, {3, 6}, {6, 7}};
        getRelationship(edges);
        System.out.println(hasCommonAncestor(6,7, edges));
        System.out.println(findEarliestAncestor(3, edges));
        graph(edges, 4, 1, 3);

//        graph(edges, 4, 5, 7);
//        graph(edges, 6, 7, 7);
//        graph(edges, 3, 7, 7);
    }
}
