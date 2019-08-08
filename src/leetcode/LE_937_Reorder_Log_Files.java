package leetcode;

import java.util.Arrays;
import java.util.Comparator;

public class LE_937_Reorder_Log_Files {
    /**
     * You have an array of logs.  Each log is a space delimited string of words.
     *
     * For each log, the first word in each log is an alphanumeric identifier.  Then, either:
     *
     * Each word after the identifier will consist only of lowercase letters, or;
     * Each word after the identifier will consist only of digits.
     * We will call these two varieties of logs letter-logs and digit-logs.  It is guaranteed
     * that each log has at least one word after its identifier.
     *
     * Reorder the logs so that all of the letter-logs come before any digit-log.  The letter-logs
     * are ordered lexicographically ignoring identifier, with the identifier used in case of ties.
     * The digit-logs should be put in their original order.
     *
     * Return the final order of the logs.
     *
     * Example 1:
     *
     * Input: ["a1 9 2 3 1","g1 act car","zo4 4 7","ab1 off key dog","a8 act zoo"]
     * Output: ["g1 act car","a8 act zoo","ab1 off key dog","a1 9 2 3 1","zo4 4 7"]
     *
     *
     * Note:
     *
     * 0 <= logs.length <= 100
     * 3 <= logs[i].length <= 100
     * logs[i] is guaranteed to have an identifier, and a word after the identifier.
     *
     * Easy
     */

    class Solution {
        public String[] reorderLogFiles(String[] logs) {
            Comparator<String> comparator = new Comparator<String>() {
                public int compare(String s1, String s2) {
                    /**
                     * public String[] split(String regex,
                     *              int limit)
                     */
                    String[] c1 = s1.split(" ", 2);
                    String[] c2 = s2.split(" ", 2);

                    boolean isNum1 = Character.isDigit(c1[1].charAt(0));
                    boolean isNum2 = Character.isDigit(c2[1].charAt(0));

                    if (!isNum1 && !isNum2) {//log content are both letters
                        int comp = c1[1].compareTo(c2[1]);
                        if (comp == 0) {
                            comp = c1[0].compareTo(c2[0]);
                        }
                        return comp;
                    }

                    return isNum1 ? (isNum2 ? 0 : 1) : -1;
                }
            };

            Arrays.sort(logs, comparator);

            return logs;
        }
    }
}
