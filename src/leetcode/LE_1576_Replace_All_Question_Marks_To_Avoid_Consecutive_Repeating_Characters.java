package leetcode;

public class LE_1576_Replace_All_Question_Marks_To_Avoid_Consecutive_Repeating_Characters {
    /**
     * Given a string s containing only lowercase English letters and the '?' character, convert all the '?' characters
     * into lowercase letters such that the final string does not contain any consecutive repeating characters. You
     * cannot modify the non '?' characters.
     *
     * It is guaranteed that there are no consecutive repeating characters in the given string except for '?'.
     *
     * Return the final string after all the conversions (possibly zero) have been made. If there is more than one solution,
     * return any of them. It can be shown that an answer is always possible with the given constraints.
     *
     * Example 1:
     * Input: s = "?zs"
     * Output: "azs"
     * Explanation: There are 25 solutions for this problem. From "azs" to "yzs", all are valid. Only "z" is an invalid
     * modification as the string will consist of consecutive repeating characters in "zzs".
     *
     * Example 2:
     * Input: s = "ubv?w"
     * Output: "ubvaw"
     * Explanation: There are 24 solutions for this problem. Only "v" and "w" are invalid modifications as the strings
     * will consist of consecutive repeating characters in "ubvvw" and "ubvww".
     *
     * Example 3:
     * Input: s = "j?qg??b"
     * Output: "jaqgacb"
     *
     * Example 4:
     * Input: s = "??yw?ipkj?"
     * Output: "acywaipkja"
     *
     * Constraints:
     * 1 <= s.length <= 100
     * s consist of lowercase English letters and '?'.
     *
     * Easy
     *
     * https://leetcode.com/problems/replace-all-s-to-avoid-consecutive-repeating-characters/
     */
    class Solution1 {
        public String modifyString(String s) {
            char[] arr = s.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == '?') {
                    for (int j = 0; j < 3; j++) {
                        if (i > 0 && arr[i - 1] - 'a' == j) continue;
                        if (i + 1 < arr.length && arr[i + 1] - 'a' == j) continue;
                        arr[i] = (char) ('a' + j);
                        break;
                    }
                }
            }
            return String.valueOf(arr);
        }
    }

    class Solution2 {
        public String modifyString(String s) {
            char[] chars = s.toCharArray();

            char pre = chars[0];
            boolean[] candidates = new boolean[26];

            if (pre != '?') {
                candidates[pre - 'a'] = true;
            } else {
                if (s.length() == 1) return "a";

                for (int i = 0; i < 26; i++) {
                    char cur = (char)('a' + i);
                    if (cur != chars[1]) {
                        chars[0] = cur;
                        pre = cur;
                        candidates[pre - 'a'] = true;

                        break;
                    }
                }
            }

            for (int i = 1; i < chars.length; i++) {
                System.out.println(pre);

                if (chars[i] != '?') {
                    candidates[pre - 'a'] = false;
                    pre = chars[i];
                    candidates[pre - 'a'] = true;
                    continue;
                }

                char post = '1';
                if (i != chars.length - 1) {
                    if (chars[i + 1] != '?') {
                        post = chars[i + 1];
                        candidates[post - 'a'] = true;
                    }
                }

                for (int j = 0; j < 26; j++) {
                    if (!candidates[j]) {
                        char c = (char)('a' + j);
                        chars[i] = c;
                        candidates[pre - 'a'] = false;
                        pre = c;
                        candidates[pre - 'a'] = true;

                        if (!Character.isDigit(post)) {
                            candidates[post - 'a'] = false;
                        }

                        break;
                    }
                }
            }

            return new String(chars);
        }
    }
}
