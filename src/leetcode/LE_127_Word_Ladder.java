package leetcode;

import java.util.*;

/**
 * Created by yuank on 7/16/18.
 */
public class LE_127_Word_Ladder {
    /**
         Given two words (beginWord and endWord), and a dictionary's word list, find the length
         of shortest transformation sequence from beginWord to endWord, such that:

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
     */

    /**
     * http://zxi.mytechroad.com/blog/searching/127-word-ladder/
     *
     * BFS
     *
     * Time  : O(n*26^l) -> O(n*26^l/2), l = length of solution, n=|wordList|
     * Space : O(n)
     */


    /**
     * Solution 1 : BFS, 83ms
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


    /**
        Solution 2 : BFS. improved version from Solution 1. 54 ms
        Improvement :
        1.Create a separate method "findNeighbors()" to get all valid neighbors for current String.
        2.For String that is already visited, since we want to "find the length of SHORTEST transformation sequence",
          whenever we see a String that has appears previously, we just ignore it. In Solution 1, we have a Set to
          record the visited strings, here, we simply remove it from dictionary.

        Also it seems that Leetcode requires that endWord must be in the given dictionary (or wordList),
     */
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

    /**
     * The string returned in the list must exit in dict.
     * Timne : O((l ^ 2)* 25)
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
    public int ladderLength_3(String beginWord, String endWord, List<String> wordAsList) {
        if (!wordAsList.contains(endWord)) return 0;

        int l = beginWord.length();
        Set<String> wordList = new HashSet<String>(wordAsList);
        Set<String> start = new HashSet<String>();
        Set<String> end = new HashSet<String>();
        int length = 0;

        start.add(beginWord);
        end.add(endWord);

        while (!start.isEmpty() && !end.isEmpty()) {//!!!
            length++;

            //use the samller set, balance the sets on 2 ends
            if (start.size() > end.size()) {
                Set<String> temp = new HashSet<>();
                temp = start;
                start = end;
                end = temp;
            }

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
