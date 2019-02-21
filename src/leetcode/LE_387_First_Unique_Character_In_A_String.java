package leetcode;

public class LE_387_First_Unique_Character_In_A_String {
    /**
     * Given a string, find the first non-repeating character in
     * it and return it's index. If it doesn't exist, return -1.
     *
     * Examples:
     *
     * s = "leetcode"
     * return 0.
     *
     * s = "loveleetcode",
     * return 2.
     * Note: You may assume the string contain only lowercase letters.
     */

    class Solution {
        public int firstUniqChar(String s) {
            if (null == s || s.length() == 0) return -1;

            char[] chars = s.toCharArray();
            int[] count = new int[256];
            for (char c : chars) {
                count[c]++;
            }

            for (int i = 0; i < chars.length; i++) {
                if (count[chars[i]] == 1) {
                    return i;
                }
            }

            return -1;
        }
    }
}
