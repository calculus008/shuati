package leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yuank on 5/15/18.
 */
public class LE_346_Moving_Average_From_Data_Stream {
    /**
         Given a stream of integers and a window size, calculate the moving average of all integers in the sliding window.

         For example,
         MovingAverage m = new MovingAverage(3);
         m.next(1) = 1
         m.next(10) = (1 + 10) / 2
         m.next(3) = (1 + 10 + 3) / 3
         m.next(5) = (10 + 3 + 5) / 3

         Easy
     */

    /**
         Solution 1 : Array

         Time : O(1)

         The basic idea is that you have an array that keeps appending new numbers until it is full,
         then after it is full, new numbers will replace the oldest element in the array.
         So given a window size 3, you have
         [ ][ ][ ]
         When a number comes in, say 1, you start filling up the array
         [1][ ][ ]
         Later at some point, let's say the array is full like this
         [1][2][3]
         Then when another number comes in, you would want to remove 1 and insert 4
         [2][3][4]

         But you don't have to shift the array if you keep the index of the OLDEST(!!!) element.
         In the example code, it's called insert :

         Given this array, insert is at 0:
         [1][2][3] insert = 0
         If you get 4, you insert at 0, then increment insert:
         [4][2][3] insert = 1
         Then if you get 5, you insert at 1 and increment insert:
         [4][5][3] insert = 2
         Then if you get 6, you insert at 2 and increment insert, but divide and find the remainder with array length:
         [4][5][6] insert = 3 % 3 = 0

         The modulus allows you to repeat the insert index.
     */

    class MovingAverage1 {
        int[] window;
        int insert;
        int n;
        long sum;

        /** Initialize your data structure here. */
        public MovingAverage1(int size) {
            window = new int[size];
            insert = 0;
            n = 0;
            sum = 0;
        }

        public double next(int val) {
            if (n < window.length) {//!!! calculate what is divider
                n++;
            }

            sum -= window[insert];
            sum += val;
            window[insert] = val;

            /**
             * !!!
             */
            insert = (insert + 1) % window.length;

            return sum == 0 ? 0 : (double)sum / n; //!!! use "n", not window.length!!!
        }
    }

    /**
     * Solution 2 : Use bounded queue
     *
     * Time : O(n)
     */

    class MovingAverage2 {
        Queue<Integer> q;
        double sum = 0;
        int size;

        public MovingAverage2(int s) {
            q = new LinkedList();
            size = s;
        }

        public double next(int val) {
            if(q.size() == size){
                sum = sum - q.poll();
            }
            q.offer(val);
            sum += val;
            return sum/q.size();
        }
    }
}
