package leetcode;

/**
 * Created by yuank on 4/3/18.
 */
public class LE_245_Shortest_Word_Distance_III {
    /**
        This is a follow up of Shortest Word Distance(LE_243). The only difference is now word1 could be the same as word2.

        Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.

        word1 and word2 may be the same and they represent two individual words in the list.

        For example,
        Assume that words = ["practice", "makes", "perfect", "coding", "makes"].

        Given word1 = “makes”, word2 = “coding”, return 1.
        Given word1 = "makes", word2 = "makes", return 3.

        Note:
        You may assume word1 and word2 are both in the list.
     */

        /*
        ["practice", "makes", "perfect", "coding", "makes"].

        When get to first "makes"
        First a = 1,
        Then  "a = b", a = -1,
              b = 1,

        When get to second "makes"
        First a = 4,
        Then  "a = b", a = 1,
              b = 4

        Calcualte res = |4 - 1| = 3
    */
    //Time : O(n), Space : O(1)
    public int shortestWordDistance(String[] words, String word1, String word2) {
        int res = words.length;
        int a = -1;
        int b = -1;
        for (int i = 0; i < words.length; i++) {
            if (word1.equals(words[i])) {
                a = i;
            }
            if (word2.equals(words[i])) {

                //====
                if (word1.equals(word2)) {
                    a = b;
                }
                //====

                b = i;
            }

            if(a != -1 && b != -1) {
                res = Math.min(res, Math.abs(a - b));
            }
        }
        return res;
    }
}
