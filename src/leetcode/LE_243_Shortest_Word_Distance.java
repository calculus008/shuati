package leetcode;

/**
 * Created by yuank on 4/3/18.
 */
public class LE_243_Shortest_Word_Distance {
    /**
        Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.

        For example,
        Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

        Given word1 = “coding”, word2 = “practice”, return 3.
        Given word1 = "makes", word2 = "coding", return 1.

        Note:
        You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
     */

    //Time : O(n), Space : O(1)
    public int shortestDistance(String[] words, String word1, String word2) {
        int res = words.length;
        int a = -1;
        int b = -1;
        for (int i = 0; i < words.length; i++) {
            if (word1.equals(words[i])) {
                a = i;
            }
            if (word2.equals(words[i])) {
                b = i;
            }

            if(a != -1 && b != -1) {
                res = Math.min(res, Math.abs(a - b));
            }
        }
        return res;
    }
}
