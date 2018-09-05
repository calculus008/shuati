package lintcode;

import java.util.PriorityQueue;

/**
 * Created by yuank on 9/5/18.
 */
public class LI_486_Merge_K_Sorted_Arrays {
    /**
         Given k sorted integer arrays, merge them into one sorted array.

         Example
         Given 3 sorted arrays:

         [
         [1, 3, 5, 7],
         [2, 4, 6],
         [0, 8, 9, 10, 11]
         ]
         return [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11].

         Challenge
         Do it in O(N log k).

         N is the total number of integers.
         k is the number of arrays.
     */

    class Element {
        int row;
        int col;
        int val;

        public Element (int row, int col, int val) {
            this.row = row;
            this.col = col;
            this.val = val;
        }
    }

    public int[] mergekSortedArrays(int[][] arrays) {
        if (arrays == null) return new int[]{};

        PriorityQueue<Element> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
        int size = 0;

        for (int i = 0; i < arrays.length; i++) {
            size += arrays[i].length;
            /**
             * 题意输入是变长的2D array
             */
            if (arrays[i].length == 0) continue;//!!!

            pq.offer(new Element(i, 0, arrays[i][0]));
        }

        if (size == 0) return new int[]{};

        int[] res = new int[size];
        int idx = 0;

        while(!pq.isEmpty()) {
            Element cur = pq.poll();
            res[idx++] = cur.val;
            int row = cur.row;
            int col = cur.col;

            /**
             * !!!相当于检查是否到链表的尾部
             */
            if (cur.col < arrays[row].length - 1) {
                pq.offer(new Element(row, col + 1, arrays[row][col + 1]));
            }
        }

        return res;
    }
}
