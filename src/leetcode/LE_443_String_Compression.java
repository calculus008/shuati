package leetcode;

public class LE_443_String_Compression {
    /**
     * Given an array of characters, compress it in-place.
     *
     * The length after compression must always be smaller than or equal to the original array.
     *
     * Every element of the array should be a character (not int) of length 1.
     *
     * After you are done modifying the input array in-place, return the new length of the array.
     *
     *
     * Follow up:
     * Could you solve it using only O(1) extra space?
     *
     *
     * Example 1:
     *
     * Input:
     * ["a","a","b","b","c","c","c"]
     *
     * Output:
     * Return 6, and the first 6 characters of the input array should be: ["a","2","b","2","c","3"]
     *
     * Explanation:
     * "aa" is replaced by "a2". "bb" is replaced by "b2". "ccc" is replaced by "c3".
     *
     *
     * Example 2:
     *
     * Input:
     * ["a"]
     *
     * Output:
     * Return 1, and the first 1 characters of the input array should be: ["a"]
     *
     * Explanation:
     * Nothing is replaced.
     *
     *
     * Example 3:
     *
     * Input:
     * ["a","b","b","b","b","b","b","b","b","b","b","b","b"]
     *
     * Output:
     * Return 4, and the first 4 characters of the input array should be: ["a","b","1","2"].
     *
     * Explanation:
     * Since the character "a" does not repeat, it is not compressed. "bbbbbbbbbbbb" is replaced by "b12".
     * Notice each digit has it's own entry in the array.
     *
     *
     * Note:
     *
     * All characters have an ASCII value in [35, 126].
     * 1 <= len(chars) <= 1000.
     *
     * Easy
     *
     * https://leetcode.com/problems/string-compression
     */

    /**
     * String + Two Pointers
     *
     * Key : it requires in-place!!!
     */
    class Solution3 {
        public int compress(char[] chars) {
            int i = 0, j = 0;

            while(i < chars.length){
                char cur = chars[i];
                int count = 0;

                while(i < chars.length && chars[i] == cur){
                    i++;
                    count++;
                }

                chars[j++] = cur;

                if(count != 1) {
                    for (char c : Integer.toString(count).toCharArray()) {
                        chars[j++] = c;
                    }
                }
            }
            return j;
        }
    }

    class Solution2 {
        public int compress(char[] chars) {
            int k = 0; // also a pointer to modify array in-place

            for (int i = 0; i < chars.length; ) {
                chars[k] = chars[i];
                int j = i + 1;

                while (j < chars.length && chars[j] == chars[i]) {
                    j++;
                }

                if (j - i > 1) { // need compression
                    String freq = j - i + "";

                    for (char c : freq.toCharArray()) {
                        chars[++k] = c;
                    }
                }

                k++;
                i = j;
            }

            return k;
        }
    }

    class Solution1 {
        public int compress(char[] chars) {
            int i = 0;
            int n = chars.length;

            int j = 0;

            while (i < n) {
                char cur = chars[i];
                int count = 0;

                while (i < n && cur == chars[i]) {
                    count++;
                    i++;
                }

                chars[j++] = cur;

                if (count != 1) {
//                    for (char c : Integer.toString(count).toCharArray()) {

                    String s = count + "";
                    for (char c :s.toCharArray()) {
                        chars[j++] = c;
                    }
                }
            }

            return j;
        }
    }
}
