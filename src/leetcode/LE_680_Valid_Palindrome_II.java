package leetcode;

/**
 * Created by yuank on 7/9/18.
 */
public class LE_680_Valid_Palindrome_II {
    /**
         Given a non-empty string s, you may delete at most one character. Judge whether you can make it a palindrome.

         Example 1:
         Input: "aba"
         Output: True
         Example 2:
         Input: "abca"
         Output: True
         Explanation: You could delete the character 'c'.
         Note:
         The string will only contain lowercase characters a-z. The maximum length of the string is 50000.

         Easy

         Related : LE_125_Valid_Palindrome
     */

    //Time : O(n), Space : O(1)
    public boolean validPalindrome(String s) {
        int l = 0, h = s.length() - 1;
        while (l < h) {
            if (s.charAt(l) != s.charAt(h)) {
                return isPalindrome(s, l, h - 1) || isPalindrome(s, l + 1, h);
            }
            l++;
            h--;
        }
        return true;
    }

    private boolean isPalindrome(String s, int l, int h) {
        while (l < h) {
            if (s.charAt(l) != s.charAt(h)) return false;
            l++;
            h--;
        }
        return true;
    }
}
