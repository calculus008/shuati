package leetcode;

public class LE_848_Shifting_Letters {
    /**
         We have a string S of lowercase letters, and an integer array shifts.

         Call the shift of a letter, the next letter in the alphabet, (wrapping around so that 'z' becomes 'a').

         For example, shift('a') = 'b', shift('t') = 'u', and shift('z') = 'a'.

         Now for each shifts[i] = x, we want to shift the first i+1 letters of S, x times.

         Return the final string after all such shifts to S are applied.

         Example 1:

         Input: S = "abc", shifts = [3,5,9]
         Output: "rpl"
         Explanation:
         We start with "abc".
         After shifting the first 1 letters of S by 3, we have "dbc".
         After shifting the first 2 letters of S by 5, we have "igc".
         After shifting the first 3 letters of S by 9, we have "rpl", the answer.
         Note:

         1 <= S.length = shifts.length <= 20000
         0 <= shifts[i] <= 10 ^ 9

         Easy
     */

    public String shiftingLetters(String S, int[] shifts) {
        /**
         * suffix sum
         */
        long count = 0;
        int n = S.length();
        char[] c = S.toCharArray();

        /**
         * go backward and use suffix sum, instead of prefix sum
         */
        for (int i = n - 1; i >= 0; i--) {
            count += shifts[i];
            /**
             * !!! " + 'a'"
             */
            c[i] = (char)((c[i] - 'a' + count) % 26 + 'a');
        }

        return new String(c);
    }
}