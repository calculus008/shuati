package leetcode;

/**
 * Created by yuank on 4/13/18.
 */
public class LE_266_Palindrome_Permutation {
    /**
     *   Given a string, determine if a permutation of the string could form a palindrome.

         For example,
         "code" -> False, "aab" -> True, "carerac" -> True.
     */

    //Time : O(n), Space : O(1)
    public boolean canPermutePalindrome(String s) {
        char[] count = new char[256];
        for (char c : s.toCharArray()) {
            if (count[c] > 0) {
                count[c]--;
            } else {
                count[c]++;
            }
        }

        int res = 0;
        for(int i = 0; i < count.length; i++) {
            if (count[i] != 0) {
                res++;
            }
        }
        return res <= 1;
    }
}
