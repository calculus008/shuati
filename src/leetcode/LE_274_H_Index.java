package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 4/16/18.
 */
public class LE_274_H_Index {
    /**
     * Given an array of citations (each citation is a non-negative integer) of a researcher, write a function to compute the researcher's h-index.

         According to the definition of h-index on Wikipedia: "A scientist has index h if h of his/her N papers have at least h citations each,
         and the other N − h papers have no more than h citations each."

         For example, given citations = [3, 0, 6, 1, 5], which means the researcher has 5 papers in total and each of them had
         received 3, 0, 6, 1, 5 citations respectively. Since the researcher has 3 papers with at least 3 citations each and the remaining
         two with no more than 3 citations each, his h-index is 3.

         Note: If there are several possible values for h, the maximum one is taken as the h-index.

         Medium

         https://leetcode.com/problems/h-index
     */

    class Solution_sorting {
        /**
         * Time : O(nlogn)
         * Space : O(1)
         */
        public int hIndex(int[] citations) {
            Arrays.sort(citations);
            int res = 0;
            int i = citations.length - 1;

            while (i >= 0 && citations[i] > res) {
                i--;
                res += 1;
            }

            return res;
        }
    }



    /**
     Solution 1 : Time : O(nlogn), Space : O(1)
     https://leetcode.com/problems/h-index/solution/

     input : [3, 0, 6, 1, 5]
     sort  : [0, 1, 3, 5, 6]

                  k      res    k=citations.length-1-res
     [0, 1, 3, 5, 6]      0                4

               k         res    k=citations.length-1-res
     [0, 1, 3, 5, 6]      1                3

            k            res    k=citations.length-1-res
     [0, 1, 3, 5, 6]      2                2

         k               res    k=citations.length-1-res
     [0, 1, 3, 5, 6]      3                1
     */
    public int hIndex1(int[] citations) {
        Arrays.sort(citations);
        int res = 0;
        while (res < citations.length && citations[citations.length - 1 - res] > res) {
            res++;
        }

        return res;
    }

    /**
     Solution 2 : counting sort
     Time : O(n), Space : O(n)

     input : [3, 0, 6, 1, 5]

     c[0] = 3 < 5 : index = 3 -> count[3]++ = 1;
     c[1] = 0 < 5 : index = 0 -> count[0]++ = 1;
     c[2] = 6 > 5 : index = 5 -> count[5]++ = 1;
     c[3] = 1 < 5 : index = 1 -> count[1]++ = 1;
     c[4] = 5 ==5 : index = 5 -> count[5]++ = 2;

     col      0  1  2  3  4  5
     count : [1, 1, 0, 1, 0, 2]

                     i    sum  i
     [1, 1, 0, 1, 0, 2]    2   5

                  i       sum  i
     [1, 1, 0, 1, 0, 2]    2   4

               i          sum  i
     [1, 1, 0, 1, 0, 2]    3   3   return 3
     */
    public int hIndex2(int[] citations) {
        //被引用了i次的文章有几个
        int[] count = new int[citations.length + 1];
        for (int i = 0; i < citations.length; i++) {
            int index = citations[i] > citations.length ? citations.length : citations[i]; //!!! ">", NOT ">="
            count[index]++;
        }

        int sum = 0;
        for (int i = citations.length; i > 0; i--) {
            sum += count[i];
            if (sum >= i) return i;
        }
        return 0;
    }

}
