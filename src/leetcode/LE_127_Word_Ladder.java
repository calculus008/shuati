package leetcode;

import java.util.*;

/**
 * Created by yuank on 7/16/18.
 */
public class LE_127_Word_Ladder {
    /**
         Given two words (beginWord and endWord), and a dictionary's word list, find the length
         of the 'shortest transformation sequence from beginWord to endWord, such that:

         Only one letter can be changed at a time.
         Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
         Note:

         Return 0 if there is no such transformation sequence.
         All words have the same length.
         All words contain only lowercase alphabetic characters.
         You may assume no duplicates in the word list.
         You may assume beginWord and endWord are non-empty and are not the same.
         Example 1:

         Input:
         beginWord = "hit",
         endWord = "cog",
         wordList = ["hot","dot","dog","lot","log","cog"]

         Output: 5

         Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
         return its length 5.


         Example 2:

         Input:
         beginWord = "hit"
         endWord = "cog"
         wordList = ["hot","dot","dog","lot","log"]

         Output: 0

         Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.

         Medium

         https://leetcode.com/problems/word-ladder
     */

    /**
     * http://zxi.mytechroad.com/blog/searching/127-word-ladder/
     *
     * BFS
     *
     * Time  : O(n*26^l) -> O(n*26^l/2), l = length of solution, n is length of wordList.
     * Space : O(n)
     *
     * 另一种算法
     * Time Complexity: O(M×N), where M is the length of words and N is the total number of words in
     *                  the input word list. Finding out all the transformations takes M iterations
     *                  for each of the N words. Also, breadth first search in the worst case might
     *                  go to each of the N words.
     *
     *                  This should be the upper bound.
     *
     * Space Complexity: O(M × N), Queue for BFS in worst case would need space for all N words.
     */

    /**
     Solution 2 : BFS. improved version from Solution 1. 54 ms
     Improvement :
     1.Create a separate method "findNeighbors()" to get all valid neighbors for current String.
     2.For String that is already visited, since we want to "find the length of SHORTEST transformation sequence",
     whenever we see a String that has appears previously, we just ignore it. In Solution 1, we have a Set to
     record the visited strings, here, we simply remove it from dictionary.

     Also it seems that Leetcode requires that endWord must be in the given dictionary (or wordList),
     */
    class Solution_BFS_2 {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            if (wordList == null || wordList.size() == 0 || beginWord == null || endWord == null) {
                return 0;
            }

            if (beginWord.equals(endWord)) {
                return 1;
            }

            HashSet<String> dict = new HashSet<>();
            for (String word : wordList) {
                dict.add(word);
            }

            /**
             * Need to clarify if endWord exists in the given dictionary, here it must exist.
             */
            if (!dict.contains(endWord)) {
                return 0;
            }

            Queue<String> queue = new LinkedList<>();
            queue.offer(beginWord);

            int res = 1;

            while (!queue.isEmpty()) {
                int size = queue.size();
                res++;

                for (int i = 0; i < size; i++) {
                    String cur = queue.poll();
                    List<String> neighbors = findNeighbors(cur, dict);

                    for (String neighbor : neighbors) {
                        if (endWord.equals(neighbor)) {
                            return res;
                        }

                        queue.offer(neighbor);
                    }
                }
            }

            return 0;
        }

        public List<String> findNeighbors(String s, HashSet<String> dict) {
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

                    if (dict.contains(next)) {//!!!"dict.contains() -> O(l), l is word length !!!
                        neighbors.add(next);
                        dict.remove(next);//!!!
                    }
                }
                chars[i] = original;
            }

            return neighbors;
        }
    }


    class Solution_BFS_1 {
        /**
         * Solution 1 : BFS, 83ms
         * <p>
         * Time  : O(n * (26 ^ l))
         * Space : O(n)
         */
        public int ladderLength_1(String beginWord, String endWord, List<String> wordList) {
            HashSet<String> dict = new HashSet<>();
            for (String word : wordList) {
                dict.add(word);
            }

            HashSet<String> visited = new HashSet<>();
            Queue<String> queue = new LinkedList<>();
            visited.add(beginWord);
            queue.offer(beginWord);

            int res = 1;

            while (!queue.isEmpty()) {
                int size = queue.size();
                res++;

                for (int i = 0; i < size; i++) {
                    String cur = queue.poll();
                    char[] chars = cur.toCharArray();

                    for (int j = 0; j < cur.length(); j++) {
                        char original = chars[j];
                        for (char c = 'a'; c <= 'z'; c++) {
                            if (original == c) {
                                continue;
                            }
                            chars[j] = c;
                            String s = new String(chars);

                            if (dict.contains(s)) {
                                if (visited.contains(s)) {
                                    continue;
                                }

                                if (endWord.equals(s)) {
                                    return res;
                                }

                                visited.add(s);
                                queue.offer(s);
                            }

                        }
                        chars[j] = original;
                    }
                }
            }

            return 0;
        }
    }

    /**
     * The string returned in the list must exit in dict.
     * Timne : O((l ^ 2)* 25), l is length of s.
     *         1.iterate through s - O(l)
     *         2.try 25 letters - O(25)
     *         3.use set.contains() check if new string is dict - O(l)
     */
    public List<String> findNeighbors(String s, HashSet<String> dict) {
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

                if (dict.contains(next)) {//!!!"dict.contains() -> O(l), l is word length !!!
                    neighbors.add(next);
                    dict.remove(next);//!!!
                }
            }
            chars[i] = original;
        }

        return neighbors;
    }

    /**
     * Bi-direction BFS, 34 ms
     *
     * Time  : O(n*26^l/2), l = len(word), n=|wordList|
     * Space : O(n)
     */
    class Solution_bi_direction_DFS {
        public int ladderLength_3(String beginWord, String endWord, List<String> wordAsList) {
            if (!wordAsList.contains(endWord)) return 0;

            int l = beginWord.length();
            Set<String> wordList = new HashSet<String>(wordAsList);
            Set<String> start = new HashSet<String>();
            Set<String> end = new HashSet<String>();
            int length = 0;

            start.add(beginWord);
            end.add(endWord);

            /**
             * !!!
             * In Bi-direction BFS, we no longer use Queue !!!
             * Use two sets start and end. While condition checks if both are empty.
             */
            while (!start.isEmpty() && !end.isEmpty()) {//!!!
                length++;

                //use the smaller set, balance the sets on 2 ends
                if (start.size() > end.size()) {
                    Set<String> temp = new HashSet<>();
                    temp = start;
                    start = end;
                    end = temp;
                }

                /**
                 * !!!
                 * Must create a new set to record the new strings generated in each level,
                 * then use it as the set for comparison in the next level. Can't add new
                 * word to start or end.
                 */
                Set<String> next = new HashSet<String>();

                for (String word : start) {
                    char[] wordArray = word.toCharArray();
                    for (int i = 0; i < l; i++) {
                        char old = wordArray[i];
                        for (char c = 'a'; c <= 'z'; c++) {
                            wordArray[i] = c;
                            String str = String.valueOf(wordArray);

                            if (end.contains(str)) {
                                return length + 1;
                            }

                            if (wordList.contains(str)) {
                                next.add(str);
                                /**
                                 * !!!
                                 * remove from dictionary, serve the purpose of marking it as visited
                                 */
                                wordList.remove(str);
                            }
                        }
                        wordArray[i] = old;
                    }
                }

                start = next;//!!!
                // Set<String> temp1 = new HashSet<>();
                // temp1 = start;
                // start = next;
                // next = temp1;
            }
            return 0;
        }
    }

    class Solution {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            /**
                Those checks are not needed as the problem states :
                    You may assume no duplicates in the word list.
                    You may assume beginWord and endWord are non-empty and are not the same.
             **/
//            if (null == beginWord || null == endWord || beginWord.length() == 0 || endWord.length() == 0) {
//                return 0;
//            }
//
//            if (beginWord.equals(endWord)) {
//                return 1;
//            }


            Set<String> dict = new HashSet<>(wordList);

            /**
             * need to do this check, leetcode test cases require endWord must be in wordList
             */
            if (!dict.contains(endWord)) {
                return 0;
            }

            Set<String> start = new HashSet<>();
            Set<String> end = new HashSet<>();

            start.add(beginWord);
            end.add(endWord);

            int n = beginWord.length();
            int steps = 0;

            while (start.size() != 0 && end.size() != 0) {//!!!
                steps++;

                if (start.size() < end.size()) {
                    Set<String> temp = new HashSet<>();
                    temp = start;
                    start = end;
                    end = temp;
                }

                Set<String> nexts = new HashSet<>();

                for (String cur : start) {//!!!
                    char[] chars = cur.toCharArray();

                    for (int i = 0; i < n; i++) {
                        char original = chars[i];

                        for (char c = 'a'; c <= 'z'; c++) {
                            if (c == original) {
                                continue;
                            }

                            chars[i] = c;
                            String next = String.valueOf(chars);

                            if (end.contains(next)) {//!!!
                                return steps + 1;//!!!
                            }

                            if (dict.contains(next)) {
                                nexts.add(next);//!!!
                                dict.remove(next);//!!!
                            }
                        }

                        chars[i] = original;
                    }
                }

                start = nexts;
            }

            return 0;
        }
    }

    //14 ms
    class Solution_Practice_Bi_BFS {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            if (null == beginWord || null == endWord || null == wordList) {
                return 0;
            }

            Set<String> dict = new HashSet<>(wordList);

            if (!dict.contains(endWord)) {
                return 0;
            }

            if (beginWord.equals(endWord)) {
                return 0;
            }

            Set<String> start = new HashSet<>();
            Set<String> end = new HashSet<>();

            start.add(beginWord);
            end.add(endWord);

            int steps = 1;

            while (!start.isEmpty() && !end.isEmpty()) {
                steps++;
                Set<String> set = new HashSet<>();

                for (String cur : start) {
                    char[] chars = cur.toCharArray();
                    for (int j = 0; j < chars.length; j++) {
                        char temp = chars[j];
                        for (char c = 'a'; c <= 'z'; c++) {
                            if (c == temp) {
                                continue;
                            }

                            chars[j] = c;
                            String next = new String(chars);

                            if (end.contains(next)) {
                                return steps;
                            }

                            if (dict.contains(next)) {
                                set.add(next);
                                dict.remove(next);
                            }
                        }
                        chars[j] = temp;
                    }
                }

                start = set;
                if (start.size() > end.size()) {
                    Set<String> buf = new HashSet<>();
                    buf = start;
                    start = end;
                    end = buf;
                }
            }

            return 0;
        }
    }


    /**
     * What if you are challenged of NOT using 'a' ~ 'z'?
     *
     * First build the buckets, for each word, we replace each letter as '_' to get the bucket
     * ex. "HITS" -> "_ITS" and "H_TS" and "HI_S" and "HIT_" then put the word into the bucket
     * so the words in each bucket are the next word to each other, only differs in one letter
     * then use BFS to traverse from the begin word, each time we meet a word, get all the buckets
     * it could have and find the next word.
     *
     * For example :
     * "hit", "hot", they are neighbours to each other, in HashMap, there's an entry
     *
     * "h_t" -> ["hit", "hot"]
     *
     * Time complexity: O(mn) + O(mn),
     * n - number of words, m- average length of word, first one is build buckets, second is BFS
     */
    class Word_Ladder_1_5 {
        public List<String> ladderLengthSolutionBetter(String start, String end, Set<String> dict) {
            Map<String, Set<String>> map = new HashMap<String, Set<String>>();
            dict.add(start);
            dict.add(end);

            for (String s : dict) {
                char[] arr = s.toCharArray();
                for (int i = 0; i < arr.length; i++) {
                    char original = arr[i];

                    arr[i] = '_';
                    String s2 = new String(arr);

                    if (!map.containsKey(s2)) {
                        map.put(s2, new HashSet<String>());
                    }
                    map.get(s2).add(s);

                    arr[i] = original;
                }
            }

            Queue<String> queue = new LinkedList<String>();
            Set<String> visited = new HashSet<String>();
            Map<String, String> res = new HashMap<String, String>();

            queue.offer(start);
            visited.add(start);

            while (!queue.isEmpty()) {
                int size = queue.size();

                for (int i = 0; i < size; i++) {
                    String cur = queue.poll();
                    char[] arr = cur.toCharArray();

                    for (int j = 0; j < arr.length; j++) {
                        char original = arr[j];

                        arr[j] = '_';
                        String s2 = String.valueOf(arr);

                        for (String s3 : map.get(s2)) {
                            if (end.equals(s3)) {
                                res.put(s3, cur);
                                return convertMapToList(res, start, end);
                            }

                            if (!visited.contains(s3)) {
                                visited.add(s3);
                                res.put(s3, cur);
                                queue.offer(s3);
                            }
                        }

                        arr[j] = original;
                    }
                }
            }

            return null;
        }


        List<String> convertMapToList(Map<String, String> map, String beginWord, String endWord) {
            List<String> res = new ArrayList<String>();

            while (!map.get(endWord).equals(beginWord)) {
                res.add(0, endWord);
                endWord = map.get(endWord);
            }

            res.add(0, endWord);
            res.add(0, beginWord);

//        for (int i = res.size() - 1; i >= 0; i--) {
//            System.out.print(res.get(i) + "==");
//        }
            return res;
        }
    }

    public class Solution_BFS_Practice_1 {
        /**
         * start and end don't need to be in dict
         */
        public int ladderLength(String start, String end, Set<String> dict) {
            if (start == null || end == null || dict == null) return 0;

            Queue<String> q = new LinkedList<>();
            q.offer(start);
            /**
             * a -> b: it is one change, but the path has two nodes,
             * here we return number of nodes along the path, need to return "2".
             * So steps init with "1".
             */
            int steps = 1;

            while (!q.isEmpty()) {
                steps++;
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    String cur = q.poll();
                    List<String> list = getNext(cur);

                    for (String next : list) {
                        if (next.equals(end)) return steps;

                        if (!dict.contains(next)) continue;

                        q.offer(next);
                        dict.remove(next);
                    }
                }
            }

            return 0;
        }

        List<String> getNext(String cur) {
            int len = cur.length();
            List<String> res = new ArrayList<>();
            char[] chars = cur.toCharArray();

            for (int i = 0; i < len; i++) {
                char origin = chars[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == origin) continue;

                    chars[i] = c;
                    res.add(new String(chars));
                }
                /**
                 * !!!
                 */
                chars[i] = origin;
            }

            return res;
        }
    }

    public class Solution_BFS_Practice_2 {
        /**
         * start and end don't need to be in dict
         */
        public int ladderLength(String start, String end, Set<String> dict) {
            if (start == null || end == null || dict == null) return 0;

            Queue<String> q = new LinkedList<>();
            q.offer(start);
            int steps = 1;

            /**
             * !!!
             * since end may not be in the dict and now we check if a newly generated word
             * is in dict in function getNext(), therefore, must make sure end is in dict.
             */
            dict.add(end);

            while (!q.isEmpty()) {
                steps++;
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    String cur = q.poll();
                    List<String> list = getNext(cur, dict);

                    for (String next : list) {
                        if (next.equals(end)) return steps;

                        q.offer(next);
                    }
                }
            }

            return 0;
        }

        List<String> getNext(String cur, Set<String> dict) {
            int len = cur.length();
            List<String> res = new ArrayList<>();
            char[] chars = cur.toCharArray();

            for (int i = 0; i < len; i++) {
                char origin = chars[i];

                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == origin) continue;

                    chars[i] = c;
                    String next = new String(chars);

                    if (!dict.contains(next)) continue;

                    res.add(next);
                    dict.remove(next);
                }
                /**
                 * !!!
                 * 在这里还原
                 */
                chars[i] = origin;
            }

            return res;
        }
    }
}
