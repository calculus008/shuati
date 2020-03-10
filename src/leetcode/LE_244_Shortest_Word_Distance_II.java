package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuank on 4/3/18.
 */
public class LE_244_Shortest_Word_Distance_II {
    /**
        This is a follow up of Shortest Word Distance (LE_243)
        The only difference is now you are given the list of words
        and your method will be called repeatedly many times with different parameters.
        How would you optimize it?

        Design a class which receives a list of words in the constructor, and implements a method that takes two words word1 and word2 and
        return the shortest distance between these two words in the list.

        For example,
        Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

        Given word1 = “coding”, word2 = “practice”, return 3.
        Given word1 = "makes", word2 = "coding", return 1.

        Note:
        You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
     */

    class WordDistance {
        HashMap<String, List<Integer>> map;

        public WordDistance(String[] words) {
            map = new HashMap<>();
            for (int i = 0; i < words.length; i++) {
                if (!map.containsKey(words[i])) {
                    map.put(words[i], new ArrayList<>());
                }
                //!!!
                map.get(words[i]).add(i);
            }
        }

        //Time and Space : O(m + n)
        public int shortest(String word1, String word2) {
            /**
             * !!!
             * "List<Integer> l1", NOT "List l1"!!!!!!
             */
            List<Integer> l1 = map.get(word1);
            List<Integer> l2 = map.get(word2);

            int size1 = l1.size();
            int size2 = l2.size();

            int res = Integer.MAX_VALUE;
            int m = 0, n = 0;

            while (m < size1 && n < size2) {
                res = Math.min(res, Math.abs(l1.get(m) - l2.get(n)));

                /**
                 * !!! !!!
                 * 注意 ：是比较list中存的下标值，不是list自己的下标
                 */
                if (l1.get(m) < l2.get(n)) {
                    m++;
                } else {
                    n++;
                }
            }

            return res;
        }
    }
}
