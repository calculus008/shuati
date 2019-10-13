package lintcode;

public class LI_646_First_Position_Unique_Character {
    /**
     * Given a string, find the first non-repeating character in it and return it's index. If it doesn't exist, return -1.
     *
     * Example
     * Example 1:
     *
     * Input : s = "lintcode"
     * Output : 0
     * Example 2:
     *
     * Input : s = "lovelintcode"
     * Output : 2
     *
     * Easy
     */

    public int firstUniqChar(String s) {
        int[] count = new int[256];

        char[] chars = s.toCharArray();
        for (char c : chars) {
            count[c]++;
        }

        for (int i = 0; i < chars.length; i++) {
            if (count[chars[i]] == 1) return i;
        }

        return -1;
    }
}
