package Interviews.Karat.Practice;

import java.util.*;

/**
 * Time : O(V + E)
 */
class Parent_Children {
    public static List<List<Integer>> parent(int[][] input) {
        List<List<Integer>> res = new ArrayList<>();
        if (input == null || input.length == 0) return res;

        Map<Integer, List<Integer>> map = buildMap(input);

        Map<Integer, List<Integer>> count = new HashMap<>();

        for (int child : map.keySet()) {
            int num = map.get(child).size();

            if (!count.containsKey(num)) {
                count.put(num, new ArrayList<>());
            }
            count.get(num).add(child);
        }

        if (count.get(0) != null) {
            printList("---zero parent---", count.get(0));
            res.add(count.get(0));
        }

        if (count.get(1) != null) {
            printList("---one parent---", count.get(1));
            res.add(count.get(1));
        }

        return res;
    }

    public static Map<Integer, List<Integer>> buildMap(int[][] input) {
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int[] r : input) {
            int parent = r[0];
            int child = r[1];

            if (!map.containsKey(child)) {
                map.put(child, new ArrayList<>());
            }

            if (!map.containsKey(parent)) {
                map.put(parent, new ArrayList<>());
            }

            map.get(child).add(parent);
        }

        return map;
    }

    public static boolean hasCommonAncestorBFS(int[][] input, int v1, int v2) {
        Map<Integer, List<Integer>> map = buildMap(input);

        Set<Integer> set = new HashSet<>();
        Queue<Integer> q = new LinkedList<>();
        q.offer(v1);

        while (!q.isEmpty()) {
            int cur = q.poll();
            set.add(cur);
            List<Integer> parents = map.get(cur);

            for (int p : parents) {
                /**
                 * !!!
                 * q.offer(p), not set.add(p)!!!
                 */
                q.offer(p);
            }
        }

        q.offer(v2);
        while (!q.isEmpty()) {
            int cur = q.poll();
            if (set.contains(cur)) return true;
            /**
             * !!!
             * use the same set
             */
            set.add(cur);

            List<Integer> parents = map.get(cur);

            for (int p : parents) {
                /**
                 * !!!
                 * q.offer(p), not set.add(p)!!!
                 */
                q.offer(p);
            }
        }

        return false;
    }

    public static boolean hasCommonAncestorDFS(int[][] input, int v1, int v2) {
        Map<Integer, List<Integer>> map = buildMap(input);

        Set<Integer> set1 = new HashSet<>();
        getAll(map, set1, v1);
        Set<Integer> set2 = new HashSet<>();
        getAll(map, set2, v2);

        for (int elem : set1) {
            if (set2.contains(elem)) return true;
        }

        return false;
    }

    public static void getAll(Map<Integer, List<Integer>> map, Set<Integer> set, int v) {
        set.add(v);

        if (!map.containsKey(v) || map.get(v).size() == 0) return;

        for (int p : map.get(v)) {
            getAll(map, set, p);
        }
    }

    public static int earliestAncestor(int[][] input, int v) {
        Map<Integer, List<Integer>> map = buildMap(input);

        if (!map.containsKey(v) || map.get(v).size() == 0) return -1;

        int res = -1;
        Queue<Integer> q = new LinkedList<>();
        q.offer(v);

        while (!q.isEmpty()) {
            /**
             * !!!
             */
            res = q.peek();
            int size = q.size();

            for (int i = 0; i < size; i++) {
                int cur = q.poll();
                List<Integer> parents = map.get(cur);
                for (int p : parents) {
                    q.offer(p);
                }
            }
        }

        return res;
    }

    public static void printList(String title, List<Integer> list) {
        System.out.println(title);
        System.out.println(Arrays.toString(list.toArray()));
    }

    public static void main(String[] args) {
        int[][] edges1 = new int[][]{{1, 4}, {1, 5}, {2, 5}, {3, 6}, {6, 7}};
        parent(edges1);

        int[][] edges2 = {{1, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 7}, {4, 5}, {4, 8}, {4, 9}, {9, 11}};
        parent(edges2);

        System.out.println("---hasCommonAncestorBFS---");
        System.out.println(hasCommonAncestorBFS(edges1, 6, 7));
        System.out.println(hasCommonAncestorBFS(edges1, 4, 7));
        System.out.println(hasCommonAncestorBFS(edges2, 11, 7));
        System.out.println(hasCommonAncestorBFS(edges2, 11, 3));
        System.out.println(hasCommonAncestorBFS(edges2, 3, 3));

        System.out.println("---hasCommonAncestorDFS---");
        System.out.println(hasCommonAncestorDFS(edges1, 6, 7));
        System.out.println(hasCommonAncestorDFS(edges1, 4, 7));
        System.out.println(hasCommonAncestorDFS(edges2, 11, 7));
        System.out.println(hasCommonAncestorDFS(edges2, 11, 3));
        System.out.println(hasCommonAncestorDFS(edges2, 3, 3));

        System.out.println(earliestAncestor(edges2, 11));
        System.out.println(earliestAncestor(edges2, 4));
        System.out.println(earliestAncestor(edges2, 3));
        System.out.println(earliestAncestor(edges2, 9));


    }
}
