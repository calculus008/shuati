package leetcode;

/**
 * Created by yuank on 3/11/18.
 */
public class LE_76_Min_Window_Substring
{
    /**
        Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).

        For example,
        S = "ADOBECODEBANC"
        T = "ABC"
        Minimum window is "BANC".

        Note:
        If there is no such window in S that covers all characters in T, return the empty string "".

        If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.
     */

    //Very Important

    /**
         Sliding Window :
         1.First move right side of the window to include all required chars in the window (expand)
         2.Move left side, as long as all required chars in window, keep moving,
           record start index and length of window each step (in order to retrieve it at the end (shrink).

              A D O B E C O D E B A N C
              j         i                 : total == 0, find first valid window
                j                 i       : ++count[A], total++, total = 1, break while loop, it means window
                                            is missing one required char, start moving i (or right side), until find another 'A'
                        j         i       : After find 2nd valid window, start moving j (in while loop), until 'C' is out of the window
                         j            i   : move i, find 3rd valid window
                                j     i   : move j

         "total == 0" means all required chars are in window now, this window is a valid window.
     */



    public static String minWindow(String s, String t) {
        // if (s == null || t == null) return "";
        // if (s.length() < t.length()) return "";

        int[] count = new int[128];
        for (char c : t.toCharArray()) {
            count[c]++;
        }

        int total = t.length();
        int from = 0;
        int minLen = Integer.MAX_VALUE;
        for (int i = 0, j = 0; i < s.length(); i++) {
//            if (count[s.charAt(i)]-- > 0) total--;

            if (count[s.charAt(i)] > 0) {
                total--;
            }
            count[s.charAt(i)]--;

            /**
             * total == 0 : there's a window satisfies requirement
             * (all required chars are in current window).
             * when total != 0 (after total++), it means the window is no longer
             * valid, one required char is out of the window
             */
            while (total == 0) {
                if (minLen > i - j + 1) {
                    from = j;
                    minLen = i - j + 1;
                }

                /**
                  This while is for sliding window step 2 - shrink the window.This step check if any of the required chars is missing
                  in the window, once "total++" executes, while loop will stop.

                  Since we already did  "count[s.charAt(i)]--" in the first step (expand or move i), for chars that are not required, its count
                  value will not exceed 0 (initial value before for loop is 0, here, just add back the value that has been subtracted)
                  In other words, when in this while loop, we move j, it just iterates chars that have been iterated before (when move i),
                  therefore, for none required char c, count[c] value will NOT be bigger than 0.
                 */
//                if (++count[s.charAt(j++)] > 0) total++;

                count[s.charAt(j)]++;
                if (count[s.charAt(j)] > 0) {
                    total++;
                }
                j++;
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(from, from + minLen);
    }
}
