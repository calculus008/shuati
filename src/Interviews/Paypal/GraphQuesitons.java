package Interviews.Paypal;

import java.util.*;

public class GraphQuesitons {
    /**
     * 1.Group by number of parents:
     *
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
    public List<List<Integer>> getRelationship(List<int[]> input) {
        List<List<Integer>> res = new ArrayList<>();
        if (null == input || input.size() == 0) return res;

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
     *
     * Input: parent child relation
     * [
     * (1, 3), (2, 3), (3, 6), (5, 6),
     * (5, 7), (4, 5), (4, 8), (4, 9), (9, 11)
     * ]
     *
     * hasCommonAncestor(3, 7) => false
     * hasCommonAncestor( 6, 7) => true
     */

    public boolean hasCommonAncestor(int v1, int v2, List<int[]> input) {
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int[] pair : input) {
            int parent = pair[0];
            int child = pair[1];

            if (!map.containsKey(child)) {
                map.put(child, new ArrayList<>());
            }
            map.get(child).add(parent);

            if (!map.containsKey(parent)) {
                map.putIfAbsent(parent, new ArrayList<>());
            }
        }

        Set<Integer> set = new HashSet<>();
        Queue<Integer> q = new LinkedList<>();
        q.offer(v1);

        while (!q.isEmpty()) {
            int cur = q.poll();
            set.add(cur);

            for (int p : map.get(cur)) {
                q.offer(p);
            }
        }

        q.offer(v2);
        while (!q.isEmpty()) {
            int cur = q.poll();
            if (!set.add(cur)) return true;

            for (int p : map.get(cur)) {
                q.offer(p);
            }
        }

        return false;
    }

    /**
     * 3.Earliest ancestor
     * Write a function that, for a given individual in our dataset, returns their earliest known ancestor
     * -- the one at the farthest distance from the input individual. If there is more than one ancestor
     * tied for "earliest", return any one of them. If the input individual has no parents, the function
     * should return null (or -1).
     */

    public int findEarliestAncestor(int v, List<int[]> input) {
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int[] pair : input) {
            int parent = pair[0];
            int child = pair[1];

            if (!map.containsKey(child)) {
                map.put(child, new ArrayList<>());
            }
            map.get(child).add(parent);

            if (!map.containsKey(parent)) {
                map.putIfAbsent(parent, new ArrayList<>());
            }
        }

        //no parent
        if (map.get(v).size() == 0) return -1;

        int res = -1;
        int max = Integer.MIN_VALUE;
        int d = 0;
        Queue<Integer> q = new LinkedList<>();
        q.offer(v);
        Set<Integer> set = new HashSet<>();

        while (!q.isEmpty()) {
            d++;
            int size = q.size();

            for (int i = 0; i < size; i++) {
                int cur = q.poll();
                if (set.contains(cur)) continue;

                set.add(cur);

                if (map.get(cur).size() == 0) {
                    if (max < d) {
                        max = d;
                        res = cur;
                    }
                }

                for (int p : map.get(cur)) {
                    q.offer(p);
                }
            }
        }

        return res;
    }
}
