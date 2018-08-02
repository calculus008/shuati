package leetcode;

/**
 * Created by yuank on 3/17/18.
 */
public class LE_132_Palindrome_Partition_II {
    /**
        Given a string s, partition s such that every substring of the partition is a palindrome.

        Return the minimum cuts needed for a palindrome partitioning of s.

        For example, given s = "aab",
        Return 1 since the palindrome partitioning ["aa","b"] could be produced using 1 cut.
     */

    /**
        DP
        Time : O(n ^ 2)
        Space : O(n ^ 2)


         cut[i] is the minimum of cut[j - 1] + 1 (j <= i), if [j, i] is palindrome.
         If [j, i] is palindrome, [j + 1, i - 1] is palindrome, and c[j] == c[i].

         a   b   a   |   c  c
                         j  i
                j-1  |  [j, i] is palindrome
            cut(j-1) +  1
    */
    public int minCut(String s) {
        if (s == null || s.length() == 0) return 0;

        int len = s.length();
        int[] cuts = new int[len];
        boolean[][] isPalindrome = new boolean[len][len];

        for (int i = 0; i < len; i++) {
            int min = i; //number of cuts if each cut is a single char, example a b a, length is 3, 2 cuts
            for (int j = 0; j <= i; j++) {//枚举分割点with j
                //Check if substring i to j is a palindrome:
                //c='acbca' : j=0, i=4
                // i - j < 2: 0 -> aa, 1 -> aba
                if (s.charAt(i) == s.charAt(j) && (i - j < 2 || isPalindrome[j + 1][i - 1])) {
                    isPalindrome[j][i] = true;
                    min = j == 0 ? 0 : Math.min(min, cuts[j - 1] + 1);
                }
            }
            cuts[i] = min;
        }
        return cuts[len - 1];
    }
}
