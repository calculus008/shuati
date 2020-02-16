package Interviews.Karat;

public class Number_Of_substrings_In_Other {
    /**
     * Suppose we are given a string s1, we need to the find total number
     * of substring(including multiple occurrences of the same substring)
     * of s1 which are present in string s2.
     *
     * Input : s1 = aab
     *         s2 = aaaab
     * Output :6
     * Substrings of s1 are ["a", "a", "b", "aa",
     * "ab", "aab"]. These all are present in s2.
     * Hence, answer is 6.
     *
     * Input :s1 = abcd
     *        s2 = swalencud
     * Output :3
     */

    /**
     * https://www.geeksforgeeks.org/number-of-substrings-of-one-string-present-in-other/
     *
     *
     */
    public static int countSubstrs(String s1, String s2) {
        int ans = 0;

        char[] chs = s1.toCharArray();

        for (int i = 0; i < s1.length(); i++) {
            String substr = "";
            for (int j = i; j < s1.length(); j++) {
                substr += chs[j];

                System.out.println(substr);

                // check the presence of s3 in s2
                if (s2.indexOf(substr) != -1)
                    ans++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String s1 = "aab", s2 = "aaaab";
        System.out.println(countSubstrs(s1, s2));
    }
}
