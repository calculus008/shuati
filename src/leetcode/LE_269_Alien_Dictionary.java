package leetcode;

import java.util.*;

/**
 * Created by yuank on 4/15/18.
 */
public class LE_269_Alien_Dictionary {
    /**
     * There is a new alien language which uses the latin alphabet.
     * However, the order among letters are unknown to you.
     * You receive a list of non-empty words from the dictionary,
     * where words are sorted lexicographically by the rules of this new language.
     * Derive the order of letters in this language.

         Example 1:
         Given the following words in dictionary,

         [
         "wrt",
         "wrf",
         "er",
         "ett",
         "rftt"
         ]
         The correct order is: "wertf".

         Example 2:
         Given the following words in dictionary,

         [
         "z",
         "x"
         ]
         The correct order is: "zx".

         Example 3:
         Given the following words in dictionary,

         [
         "z",
         "x",
         "z"
         ]
         The order is invalid, so return "".

         Note:
         You may assume all letters are in lowercase.
         You may assume that if a is a prefix of b, then a must appear before b in the given dictionary.
         If the order is invalid, return an empty string.
         There may be multiple valid order of letters, return any one of them is fine.
     */

    /**
     * https://medium.com/@wangfengfight/leetcode-269-alien-dictionary-and-followups-77c0390629da
     */

    /**
     Topological Sort
     1.Create graph in HashMap
     2.Find the start node, which is the one that has 0 in degree.
     3.From start node, do topological sort using BFS

     Time : O(V + E) -> O(n), Space : O(n) -> O(26) -> O(1)

     Other topological sort problems : LE_207, LE_210
     */
    class Solution1 {
        public String alienOrder(String[] words) {  // has bug -- ["abc","ab"]
            if (words == null || words.length == 0) return "";

            StringBuilder res = new StringBuilder();

            Map<Character, Set<Character>> map = new HashMap<>();

            int count = 0;
            int[] degree = new int[26];
            for (String word : words) {
                for (char c : word.toCharArray()) {
                    if (degree[c - 'a'] == 0) {
                        count++; //Get the total number of characters, to be used to verify if the order is invalid.
                        degree[c - 'a'] = 1; //init chars that appear as 1, since we can't find the start node with indegree '0'.
                    }
                }
            }

            //Create graph in HashMap
            for (int i = 0; i < words.length - 1; i++) {
                char[] cur = words[i].toCharArray();
                char[] next = words[i + 1].toCharArray();
                int len = Math.min(cur.length, next.length);
                for (int j = 0; j < len; j++) {
                    if (cur[j] != next[j]) {
                        if (!map.containsKey(cur[j])) {
                            map.put(cur[j], new HashSet<>());
                        }

                        /**
                         !!! need to use if here to avoid duplicates.
                         Yes, set won't have duplicate element, but the calculation for degree will go wrong
                         */
                        if (map.get(cur[j]).add(next[j])) {
                            degree[next[j] - 'a']++;
                        }
                        break;//!!!
                    }

                    if (j == next.length-1 && j < cur.length-1) return "";  ///this is for the case of ["abc","ab"]
                }
            }

            Queue<Character> queue = new LinkedList<>();
            for (int i = 0; i < 26; i++) {
                if (degree[i] == 1) {
                    queue.offer((char) ('a' + i));
                    // break;
                }
            }

            while (!queue.isEmpty()) {
                char c = queue.poll();
                res.append(c);
                if (map.containsKey(c)) {//!!!
                    for (char ch : map.get(c)) {
                        //!!! decrease the degree, and insert the new nodes which have no parents now
                        if (--degree[ch - 'a'] == 1) {
                            queue.offer(ch);
                        }
                    }
                }
            }

            if (count != res.length()) return "";

            return res.toString();
        }
    }

    /**
     * Pretty similar to Course Schedule problem (LC 210), the only difference is that
     * we need to build graph by comparing every consecutive pair of strings firstly,
     * and then doing topological sort for the graph to get the result string*
     */
    class Solution2 {
        public String alienOrder(String[] words) {
            if (words == null || words.length == 0) {
                return "";
            }

            Map<Character, Set<Character>> graph = new HashMap<>();
            Map<Character, Integer> inDegreeMap = new HashMap<>();

            /**
             *  !!!
             *  MUST initialize the dist, to avoid null exception for those character
             *  that will have zero inDegrees (i.e. starting characters)
             **/
            for (String word : words) {
                for (char c : word.toCharArray()) {
                    inDegreeMap.put(c, 0);
                }
            }

            /**
             *  build graph, as well as fill out inDegree dist for every character
             * */
            for (int i = 0; i < words.length - 1; i++) {
                String curWord = words[i];
                String nextWord = words[i + 1];
                int minLength = Math.min(curWord.length(), nextWord.length());

                /**
                 * According to given dictionary with specified order, traverse every pair of words,
                 * then put each pair into graph dist to build the graph, and then update inDegree dist
                 * for every "nextChar" (increase their inDegree by 1 every time)
                 * */
                for (int j = 0; j < minLength; j++) {
                    char curChar = curWord.charAt(j);
                    char nextChar = nextWord.charAt(j);
                    if (curChar != nextChar) {
                        /* update graph dist */
                        graph.putIfAbsent(curChar, new HashSet<>());
                        Set<Character> set = graph.get(curChar);

                        /** WARNING: we must check if we already build curChar -> nextChar relationship in graph
                         * if it contains, we cannot update inDegree dist again. Otherwise, this nextChar
                         * will never be put in the queue when we do BFS traversal
                         * eg: for the input: {"za", "zb", "ca", "cb"}, we have two pairs of a -> b relationship
                         * if we increase inDegree value of 'b' again, the final result will not have 'b', since
                         * inDegree of b will stay on 1 when queue is empty
                         * correct graph: a -> b, z -> c
                         * incorrect graph: a -> b, a -> b, z -> c
                         * */
                        if (!set.contains(nextChar)) {
                            set.add(nextChar);
                            graph.put(curChar, set);

                            /**
                             * update inDegree dist
                             **/
                            inDegreeMap.put(nextChar, inDegreeMap.getOrDefault(nextChar, 0) + 1);
                        }

                        /**
                         *  we can determine the order of characters ony by first different pair of characters
                         *  so we cannot add relationship by the rest of characters
                         * */
                        break;
                    }
                }
            }

            /**
             *  after building graph, we will have an input that has exact same format as Course Schedule,
             *  then we can use BFS to do topological sort
             * */
            StringBuilder sb = new StringBuilder();
            Queue<Character> queue = new LinkedList<>();

            /**
             *  put all starting node into queue, which means put all nodes that have inDegree = 0
             * */
            for (char key : inDegreeMap.keySet()) {
                if (inDegreeMap.get(key) == 0) {
                    queue.offer(key);
                }
            }

            /**
             *  BFS traversal to build result string
             * */
            while (!queue.isEmpty()) {
                char curChar = queue.poll();
                sb.append(curChar);

                /**
                 *  traverse all next node of current node in graph, update inDegree value then
                 *  put all nodes with zero inDegree into queue
                 **/
                if (graph.containsKey(curChar)) {
                    for (char nextChar : graph.get(curChar)) {
                        inDegreeMap.put(nextChar, inDegreeMap.get(nextChar) - 1);
                        if (inDegreeMap.get(nextChar) == 0) {
                            queue.offer(nextChar);
                        }
                    }
                }
            }

            /**
             * check if input order is valid
             **/
            if (sb.length() != inDegreeMap.size()) {
                return "";
            }
            return sb.toString();
        }
    }

    class Solution_Practice {
        public String alienOrder(String[] words) {
            if (words == null || words.length == 0) return "";

            Map<Character, Set<Character>> graph = new HashMap<>();
            Map<Character, Integer> indegree = new HashMap<>();

            /**
             * #1.Must init indegree with 0 !!!
             */
            for (String word : words) {
                for (char c : word.toCharArray()) {
                    indegree.put(c , 0);
                }
            }

            int n = words.length;

            /**
             * #2.For loop from o to n - 1, compare current with next one
             */
            for (int i = 0; i < n - 1; i++) {
                String s1 = words[i];
                String s2 = words[i + 1];
                /**
                 * #3.only need to loop for minLen times
                 */
                int minLen = Math.min(s1.length(), s2.length());

                for (int j = 0; j < minLen; j++) {
                    char c1 = s1.charAt(j);
                    char c2 = s2.charAt(j);
                    if (c1 != c2) {
                        graph.putIfAbsent(c1, new HashSet<>());
                        Set<Character> set = graph.get(c1);

                        /**
                         * #4.Only process when set does not contain c2, c1 -> c2, so indegree for c2 plus one
                         */
                        if (!set.contains(c2)) {
                            set.add(c2);
                            indegree.put(c2, indegree.getOrDefault(c2, 0) + 1);
                        }

                        break;
                    }

                }
            }

            Queue<Character> q = new LinkedList<>();
            for (char key : indegree.keySet()) {
                if (indegree.get(key) == 0) {
                    q.offer(key);
                }
            }

            StringBuilder sb = new StringBuilder();
            while (!q.isEmpty()) {
                char cur = q.poll();
                sb.append(cur);

                if (!graph.containsKey(cur)) continue;

                for (Character c : graph.get(cur)) {
                    indegree.put(c, indegree.get(c) - 1);
                    if (indegree.get(c) == 0) {
                        q.offer(c);
                    }
                }
            }

            return sb.length() == indegree.size() ? sb.toString() : "";
        }
    }

}
