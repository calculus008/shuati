package src.Interviews.Karat;

public class Longest_Repeating_Non_overlapping_Substring {
    /**
     * Given a string str, find the longest repeating non-overlapping substring in it.
     * In other words find 2 identical substrings of maximum length which do not overlap.
     * If there exists more than one such substring return any of them.
     *
     * Examples:
     *
     * Input : str = "geeksforgeeks"
     * Output : geeks
     *
     * Input : str = "aab"
     * Output : a
     *
     * Input : str = "aabaabaaba"
     * Output : aaba
     *
     * Input : str = "aaaaaaaaaaa"
     * Output : aaaaa
     *
     * Input : str = "banana"
     * Output : an
     *          or na
     *
     * 找到一个string里面最长的重复substring的长度 （aaaaaaaabbbbbaaa -> a 8)??
     */

    /**
     * https://www.geeksforgeeks.org/longest-repeating-and-non-overlapping-substring/
     *
     * Length of longest non-repeating substring can be recursively
     * defined as below.
     *
     * LCSRe(i, j) stores length of the matching and
     *             non-overlapping substrings ending
     *             with i'th and j'th characters.
     *
     * If str[i-1] == str[j-1] && (j-i) > LCSRe(i-1, j-1)
     *      LCSRe(i, j) = LCSRe(i-1, j-1) + 1,
     * Else
     *      LCSRe(i, j) = 0
     *
     * Where i varies from 1 to n and
     *       j varies from i+1 to n
     *
     * Similar DP solution as LI_79_Longest_Common_Substring
     *
     * Almost same as LE_1062_Longest_Repeating_Substring (allow overlapping)
     */

    static String longestRepeatedSubstring(String str) {
        int n = str.length();
        int dp[][] = new int[n + 1][n + 1];

        String res = ""; // To store result
        int res_length = 0; // To store length of result

        // building table in bottom-up manner
        int i, index = 0;
        for (i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                // (j-i) > LCSRe[i-1][j-1] to remove
                // overlapping
                if (str.charAt(i - 1) == str.charAt(j - 1)
                        && dp[i - 1][j - 1] < (j - i)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;

                    // updating maximum length of the
                    // substring and updating the finishing
                    // index of the suffix
                    if (dp[i][j] > res_length) {
                        res_length = dp[i][j];
                        index = Math.max(i, index);
                    }
                } else {
                    dp[i][j] = 0;
                }
            }
        }

        // If we have non-empty result, then insert all
        // characters from first character to last
        // character of String
        if (res_length > 0) {
            for (i = index - res_length + 1; i <= index; i++) {
                res += str.charAt(i - 1);
            }
        }

        return res;
    }

    // Driver program to test the above function
    public static void main(String[] args) {
        String str = "geeksforgeeks";
        System.out.println(longestRepeatedSubstring(str));
        String str1 = "aaaaaaabbbbbaaa ";
        System.out.println(longestRepeatedSubstring(str1));
    }
}
