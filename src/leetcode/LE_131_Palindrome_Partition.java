package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/17/18.
 */
public class LE_131_Palindrome_Partition {
    /**
        Given a string s, partition s such that every substring of the partition is a palindrome.

        Return all possible palindrome partitioning of s.

        For example, given s = "aab",
        Return

        [
          ["aa","b"],
          ["a","a","b"]
        ]
     */

    /**
     * Backtracking : 实际是组合问题(combination), 组合的元素不是字符串中的字符，是“挡板” - 字符串里可以分割的位置。
     *                n个字符有n-1个可分割的位置 ： “abcd" - "a | b | c | d", 3个”挡板“
     *
      Time : O(2 ^ n), Space : O(n)
     **/
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


    //Solution 2 : 另一种写法，少了每次递归时substring的操作，只通过startIdx来控制所取的substring
    public List<List<String>> partition2_JiuZhang(String s) {
        List<List<String>> res = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return res;
        }

        helper2(s, res, 0, new ArrayList<>());
        return res;
    }

    private void helper2(String s, List<List<String>> res, int startIdx, List<String> temp) {
        if (startIdx == s.length()) {
            res.add(new ArrayList<>(temp));
            return;
        }

        for (int i = startIdx; i < s.length(); i++) {
            /**
             * !!!
             */
            String cur = s.substring(startIdx, i + 1);

            if (isPalindrome(cur)) {
                temp.add(cur);
                helper2(s, res, i + 1, temp);
                temp.remove(temp.size() - 1);
            }
        }
    }

    private boolean isPalindrome22(String s) {
        int start = 0, end = s.length() - 1;
        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }

            //!!!
            start++;
            end--;
        }
        return true;
    }

    /**
     * Solution 3
     * 优化的解法 : pre-processing, 用"getIsPalindrome()"(DP)在一个二维数组中记录下s中palindrome的状态，
     *            这样，就不用每次运行"isPalindrome()"做判断了。
     */
    List<List<String>> results;
    boolean[][] isPalindrome;

    /**
     * @param s: A string
     * @return: A list of lists of string
     */
    public List<List<String>> partition_faster_JiuZhang(String s) {
        results = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return results;
        }

        getIsPalindrome(s);

        helper(s, 0, new ArrayList<Integer>());

        return results;
    }

    /**
     *  !!!
     */
    private void getIsPalindrome(String s) {
        int n = s.length();
        isPalindrome = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            isPalindrome[i][i] = true;
        }
        for (int i = 0; i < n - 1; i++) {
            isPalindrome[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
        }

        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                isPalindrome[i][j] = isPalindrome[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
            }
        }
    }

    private void helper(String s,
                        int startIndex,
                        List<Integer> partition) {
        if (startIndex == s.length()) {
            addResult(s, partition);
            return;
        }

        for (int i = startIndex; i < s.length(); i++) {
            if (!isPalindrome[startIndex][i]) {
                continue;
            }
            partition.add(i);
            helper(s, i + 1, partition);
            partition.remove(partition.size() - 1);
        }
    }

    private void addResult(String s, List<Integer> partition) {
        List<String> result = new ArrayList<>();
        int startIndex = 0;
        for (int i = 0; i < partition.size(); i++) {
            result.add(s.substring(startIndex, partition.get(i) + 1));
            startIndex = partition.get(i) + 1;
        }
        results.add(result);
    }
}
