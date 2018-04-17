package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/17/18.
 */
public class LE_131_Palindrome_Partition {
    /*
        Given a string s, partition s such that every substring of the partition is a palindrome.

        Return all possible palindrome partitioning of s.

        For example, given s = "aab",
        Return

        [
          ["aa","b"],
          ["a","a","b"]
        ]
     */

    //Backtracking
    //Time : O(2 ^ n), Space : O(n)
    public List<List<String>> partition(String s) {
        List<List<String>> res  = new ArrayList<>();
        if (s == null || s.length() == 0) return res;

        helper(res, new ArrayList<>(), s);
        return res;
    }

    public void helper(List<List<String>> res, List<String> temp, String s) {
        if (s.length() == 0) {
            res.add(new ArrayList<>(temp));
            return;
        }

        for (int i = 0; i < s.length(); i++) {
            if (isPalindrome(s.substring(0, i + 1))) {
                temp.add(s.substring(0, i + 1));
                helper(res, temp, s.substring(i + 1));
                temp.remove(temp.size() - 1);
            }
        }
    }

    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left++) != s.charAt(right--)) {//Don't forget "left++" and "right--"!!!
                return false;
            }
        }
        return true;
    }
}
