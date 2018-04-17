package leetcode;

/**
 * Created by yuank on 3/14/18.
 */
public class LE_125_Valid_Palindrome {
    /*
        Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.

        For example,
        "A man, a plan, a canal: Panama" is a palindrome.
        "race a car" is not a palindrome.

        Note:
        Have you consider that the string might be empty? This is a good question to ask during an interview.

        For the purpose of this problem, we define empty string as valid palindrome.
     */

    public static  boolean isPalindrome(String s) {
        if (s == null ||s.length() == 0) return true;

        int l = 0;
        int r = s.length() - 1;
        s = s.toLowerCase();

        while (l < r) {
            while (l < r && !Character.isLetterOrDigit(s.charAt(l))) {
                l++;
            }
            while (l < r && !Character.isLetterOrDigit(s.charAt(r))) {
                r--;
            }

            if (s.charAt(l) != s.charAt(r)) return false;
            l++;
            r--;
        }
        return true;
    }
}
