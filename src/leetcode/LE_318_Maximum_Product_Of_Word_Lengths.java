package leetcode;

/**
 * Created by yuank on 5/3/18.
 */
public class LE_318_Maximum_Product_Of_Word_Lengths {
    /**
         Given a string array words, find the maximum value of length(word[i]) * length(word[j])
         where the two words do not share common letters. You may assume that each word will
         contain only lower case letters.

         If no such two words exist, return 0.

         Example 1:
         Given ["abcw", "baz", "foo", "bar", "xtfn", "abcdef"]
         Return 16
         The two words can be "abcw", "xtfn".

         Example 2:
         Given ["a", "ab", "abc", "d", "cd", "bcd", "abcd"]
         Return 4
         The two words can be "ab", "cd".

         Example 3:
         Given ["a", "aa", "aaa", "aaaa"]
         Return 0
         No such pair of words.

         Medium
     */

    /**
     * Time  : O(n ^ 2)
     * Space : O(n)
     * @param words
     * @return
     */
    public int maxProduct(String[] words) {
        if (words == null || words.length < 2) return 0;
        int[] bytes = new int[words.length];
        int res = 0;

        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words[i].length(); j++) {
                /**
                 * "the two words do not share common letters" :
                 * Since all letters are lower cases, so we
                 * can use a 32 bit integer to represent a word
                 * (what letters have been used)
                 */
                bytes[i] |= 1 << (words[i].charAt(j) - 'a');
            }
        }

        for (int i = 0; i < bytes.length; i++) {
            for (int j = i + 1; j < bytes.length; j++) {
                /**
                 * "bytes[i] & bytes[j]) == 0" :
                 * No common letter shared between words[i] and words[j]
                 */
                if ((bytes[i] & bytes[j]) == 0) {
                    int prod = words[i].length() * words[j].length();
                    res = Math.max(prod, res);
                }
            }
        }

        return res;
    }
}
