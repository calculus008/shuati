package Interviews.Facebook;

import java.util.*;

public class Sort_Strings_By_Customized_Alphabetical_Order {
    /**
     * Given a string str and an array of strings strArr[], the task is to sort the array according to the alphabetical
     * order defined by str.
     *
     * Note: str and every string in strArr[] consists of only lower case alphabets.
     *
     * Examples:
     * Input: str = “fguecbdavwyxzhijklmnopqrst”,
     * strArr[] = {“geeksforgeeks”, “is”, “the”, “best”, “place”, “for”, “learning”}
     * Output: for geeksforgeeks best is learning place the
     * Input: str = “avdfghiwyxzjkecbmnopqrstul”,
     * strArr[] = {“rainbow”, “consists”, “of”, “colours”}
     * Output: consists colours of rainbow
     *
     *
     */

    /**
     * Counting Sort Variation
     * https://medium.com/analytics-vidhya/sorting-strings-using-counting-sort-modified-1a6ca02b9a9f
     *
     * https://playcode.io/475393/
     */


    /**
     * O(nlogn)
     * https://www.geeksforgeeks.org/sort-the-array-of-strings-according-to-alphabetical-order-defined-by-another-string/
     */
    public class Solution2 {
        private static void sort(String[] strArr, String str) {
            Comparator<String> myComp = new Comparator<String>() {
                @Override
                public int compare(String a, String b) {
                    for (int i = 0; i < Math.min(a.length(), b.length()); i++) {
                        if (str.indexOf(a.charAt(i)) == str.indexOf(b.charAt(i))) {
                            continue;
                        } else if (str.indexOf(a.charAt(i)) > str.indexOf(b.charAt(i))) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                    return 0;
                }
            };

            Arrays.sort(strArr, myComp);
        }
    }
}
