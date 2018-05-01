package leetcode;

import java.util.PriorityQueue;

/**
 * Created by yuank on 4/30/18.
 */
public class LE_313_Super_Ugly_Number {
    /**
         Write a program to find the nth super ugly number.

         Super ugly numbers are positive numbers whose all prime factors are in the given prime list primes of size k. For example, [1, 2, 4, 7, 8, 13, 14, 16, 19, 26, 28, 32] is the sequence of the first 12 super ugly numbers given primes = [2, 7, 13, 19] of size 4.

         Note:
         (1) 1 is a super ugly number for any given primes.
         (2) The given numbers in primes are in ascending order.
         (3) 0 < k ≤ 100, 0 < n ≤ 106, 0 < primes[i] < 1000.
         (4) The nth super ugly number is guaranteed to fit in a 32-bit signed integer.

         Medium
     */

    /**
         Time : O(nlogk)
         Space : O(max(k, n))

         Similar : LE_264 Ugly Number

         primes = [2, 7, 13, 19]

         pq
         2, 1, 2
         7, 1, 7
         13, 1, 13
         19, 1, 19

         res[1] = 2
         next.prime * res[next.index] = 2 * res[1] = 2 * 2 = 4, 1+1=2, 2

         4, 2, 2
         7, 1, 7
         13, 1, 13
         19, 1, 19

         res[2] = 4
         next.prime * res[next.index] = 2 * res[2] = 2 * 4 = 8, 2 + 1 = 3, 2

         7, 1, 7
         8, 3, 2
         13, 1, 13
         19, 1, 19

         res[3] = 7
         next.prime * res[next.index] = 7 * res[1] = 7 * 2 = 14, 1 + 1 = 2, 7

         8, 3, 2
         13, 1, 13
         14, 2, 7
         19, 1, 19

         ......
     */

    class Num {
        int val;
        int index;
        int prime;

        public Num(int val, int index, int prime) {
            this.val = val;
            this.index = index;
            this.prime = prime;
        }
    }

    public int nthSuperUglyNumber(int n, int[] primes) {
        PriorityQueue<Num> pq = new PriorityQueue<>((a, b) -> (a.val - b.val) );
        int[] res = new int[n];
        res[0] = 1;

        //init pq
        for (int i = 0; i < primes.length; i++) {
            pq.add(new Num(primes[i], 1, primes[i]));
        }

        for (int i = 1; i < n; i++) {
            res[i] = pq.peek().val;
            while (res[i] == pq.peek().val) {
                Num next = pq.poll();
                pq.add(new Num(next.prime * res[next.index], next.index + 1, next.prime));
            }
        }

        return res[n - 1];
    }
}
