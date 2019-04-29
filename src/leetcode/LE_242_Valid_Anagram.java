package leetcode;

/**
 * Created by yuank on 4/3/18.
 */
public class LE_242_Valid_Anagram {
    /**
        Given two strings s and t, write a function to determine if t is an anagram of s.

        For example,
        s = "anagram", t = "nagaram", return true.
        s = "rat", t = "car", return false.

        Note:
        You may assume the string contains only lowercase alphabets.

        Follow up:
        What if the inputs contain unicode characters? How would you adapt your solution to such case?
     */

    //Time : O(n), Space : O(1)
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;

        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        for (char c : t.toCharArray()) {
            count[c - 'a']--;
        }

        for (int i : count) {
            if (i != 0) return false;
        }

        return true;
    }
}
