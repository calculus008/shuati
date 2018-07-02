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
          j         i
            j                 i
                    j         i
                     j            i
                            j     i

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
            if (count[s.charAt(i)]-- > 0) total--;

            //total == 0 : there's a window satisfied requirement
            while (total == 0) {
                if (minLen > i - j + 1) {
                    from = j;
                    minLen = i - j + 1;
                }

                /**
                  This while is for sliding window step 2 - shrink the window.This step check if any of the required chars is missing
                  in the window, once "total++" executes, while loop will stop.

                  Since we already did  "count[s.charAt(i)]--" in the first step (expand), for chars that are not required, its count
                  value will not exceed 0 (initial value before for loop is 0, here, just add back the value that has been subtracted)
                 */
                if (++count[s.charAt(j++)] > 0) total++;
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(from, from + minLen);
    }
}
