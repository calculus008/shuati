package lintcode;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created by yuank on 10/5/18.
 */
public class LI_543_Kth_Largest_In_N_Arrays {
    /**
         Find K-th largest element in N arrays.

         Example
         In n=2 arrays [[9,3,2,4,7],[1,2,3,4,8]], the 3rd largest element is 7.

         In n=2 arrays [[9,3,2,4,8],[1,2,3,4,2]], the 1st largest element is 9, 2nd largest element is 8, 3rd largest element is 7 and etc.

         Notice
         You can swap elements in the array
     */

    /**
     * Time  : O(mlogn + klogm)
     * Space : O(m)
     */
    public class Solution {
        class Element {
            int x, y, val;
            public Element(int x, int y, int val) {
                this.x = x;
                this.y = y;
                this.val = val;
            }
        }

        public int KthInArrays(int[][] arrays, int k) {
            //!!!Comparator!!! Max Heap
            PriorityQueue<Element> pq = new PriorityQueue<>((a, b) -> b.val - a.val);

            int m = arrays.length;

            for (int[] array : arrays) {
                if (array != null && array.length != 0) {
                    Arrays.sort(array);
                }

                //这种写法只能用于Integer[], not int[]
                // Arrays.sort(array, Collections.reverseOrder());
            }

            for (int i = 0; i < m; i++) {
                /**
                 * 题意arrays是变长的数组，所以要判断每个数组是否长度为零。
                 */
                if (arrays[i] != null && arrays[i].length != 0) {
                    pq.offer(new Element(i, arrays[i].length - 1, arrays[i][arrays[i].length - 1]));
                }
            }

            while (k > 1 && !pq.isEmpty()) {
                Element e = pq.poll();

                //!!!
                k--;

                if (e.y != 0) {
                    pq.offer(new Element(e.x, e.y - 1, arrays[e.x][e.y - 1]));
                }
            }

            return pq.poll().val;
        }
    }
}
