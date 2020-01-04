package leetcode;

public class LE_482_License_Key_Formatting {
    /**
     * You are given a license key represented as a string S which consists only
     * alphanumeric character and dashes. The string is separated into N+1 groups
     * by N dashes.
     *
     * Given a number K, we would want to reformat the strings such that each
     * group contains exactly K characters, except for the first group which
     * could be shorter than K, but still must contain at least one character.
     * Furthermore, there must be a dash inserted between two groups and all
     * lowercase letters should be converted to uppercase.
     *
     * Given a non-empty string S and a number K, format the string according
     * to the rules described above.
     *
     * Example 1:
     * Input: S = "5F3Z-2e-9-w", K = 4
     *
     * Output: "5F3Z-2E9W"
     *
     * Explanation: The string S has been split into two parts, each part has 4 characters.
     * Note that the two extra dashes are not needed and can be removed.
     *
     * Example 2:
     * Input: S = "2-5g-3-J", K = 2
     *
     * Output: "2-5G-3J"
     *
     * Explanation: The string S has been split into three parts, each part has 2 characters
     * except the first part as it could be shorter as mentioned above.
     *
     *  Note:
     * The length of string S will not exceed 12,000, and K is a positive integer.
     * String S consists only of alphanumerical characters (a-z and/or A-Z and/or 0-9) and dashes(-).
     * String S is non-empty.
     *
     * Easy
     */

    /**
     * Key insights:
     * 1.Iterate backwards since the first token's length may not be K.
     * 2.Try to append '-' before append in for loop so that it can avoid adding extra '-' in the end.
     *   Example :
     *   input - ab-cd-ef-gh
     *   K= 4
     *
     *   ABCD-EFGH
     *
     *   When going backwards to the end, we check count in the next iteration, which won't happen
     *   since we reaches the end of the for loop, so no '-' will be added
     *
     */
    class Solution1 {
        public String licenseKeyFormatting(String S, int K) {
            if (S == null || S.length() == 0) return S;

            int n = S.length();
            char[] chs = S.toCharArray();
            int count = 0;
            StringBuilder sb = new StringBuilder();

            for (int i = n - 1; i >= 0; i--) {
                if (chs[i] != '-') {
                    if (count == K) {
                        sb.append('-');
                        count = 0;
                    }
                    sb.append(Character.toUpperCase(chs[i]));
                    count++;
                }
            }

            return sb.reverse().toString();
        }
    }

    /**
     * Most concise, using Java String and StringBuilder methods, but not efficient.
     * 27%, compare with Solution1 95%.
     *
     * !!!
     * S = S.replaceAll()
     */
    class Solution2 {
        public String licenseKeyFormatting(String S, int K) {
            S = S.replaceAll("-", "").toUpperCase();
            StringBuilder sb = new StringBuilder(S);

            int i = sb.length() - K;
            while (i > 0) {
                sb.insert(i, '-');
                i -= K;
            }

            return sb.toString();
        }
    }
}
