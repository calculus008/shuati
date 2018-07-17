package lintcode;

import java.util.*;

/**
 * Created by yuank on 7/17/18.
 */
public class LI_120_Word_Ladder {
    /**
         Given two words (start and end), and a dictionary, find the length of shortest transformation sequence from start to end, such that:

         Only one letter can be changed at a time
         Each intermediate word must exist in the dictionary

         Example
         Given:
         start = "hit"
         end = "cog"
         dict = ["hot","dot","dog","lot","log"]
         As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
         return its length 5.

         !!! Notice:
            From the Example, we know that the end does not need to be in the tiven dictionary ("cog" is not in "dict")
            This is different from LE_127_Word_Ladder, where end must be in the given dictionary.
     */

    public int ladderLength(String start, String end, Set<String> dict) {
        if (dict == null || dict.size() == 0) {
            return 0;
        }

        if (start.equals(end)) {
            return 1;
        }

        /**
         * !!! Must add "end" to dict since the algorithm requires it.
         * In "findNeighbors()", only word exists in dict can be added to the returned list.
         */
        dict.add(end);

        Queue<String> queue = new LinkedList<>();
        queue.offer(start);

        int res = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            res++;

            for (int i = 0; i < size; i++) {
                String cur = queue.poll();
                List<String> neighbors = findNeighbors(cur, dict);

                for (String neighbor : neighbors) {
                    if (end.equals(neighbor)) {
                        return res;
                    }

                    queue.offer(neighbor);
                }
            }
        }

        return 0;
    }

    /**
     * The string returned in the list must exit in dict.
     * Timne : O((l ^ 2)* 25)
     */
    public List<String> findNeighbors(String s, Set<String> dict) {
        char[] chars = s.toCharArray();
        List<String> neighbors = new ArrayList<>();

        for (int i = 0; i < s.length(); i++) {
            char original = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (original == c) {
                    continue;
                }

                chars[i] = c;
                String next = new String(chars);

                if (dict.contains(next)) {//!!!"dict.contains() -> O(l), l is word length
                    neighbors.add(next);
                    dict.remove(next);//!!!
                }
            }
            chars[i] = original;
        }

        return neighbors;
    }
}
