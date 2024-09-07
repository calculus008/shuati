package leetcode;

import java.util.Arrays;
import java.util.Stack;

public class LE_735_Asteroid_Collision {
    /**
     * We are given an array asteroids of integers representing asteroids in a row.
     *
     * For each asteroid, the absolute value represents its size, and the sign represents
     * its direction (positive meaning right, negative meaning left). Each asteroid moves
     * at the same speed.
     *
     * Find out the state of the asteroids after all collisions. If two asteroids meet,
     * the smaller one will explode. If both are the same size, both will explode. Two
     * asteroids moving in the same direction will never meet.
     *
     * Example 1:
     * Input:
     * asteroids = [5, 10, -5]
     * Output: [5, 10]
     * Explanation:
     * The 10 and -5 collide resulting in 10.  The 5 and 10 never collide.
     *
     * Example 2:
     * Input:
     * asteroids = [8, -8]
     * Output: []
     * Explanation:
     * The 8 and -8 collide exploding each other.
     *
     * Example 3:
     * Input:
     * asteroids = [10, 2, -5]
     * Output: [10]
     * Explanation:
     * The 2 and -5 collide resulting in -5.  The 10 and -5 collide resulting in 10.
     *
     * Example 4:
     * Input:
     * asteroids = [-2, -1, 1, 2]
     * Output: [-2, -1, 1, 2]
     * Explanation:
     * The -2 and -1 are moving left, while the 1 and 2 are moving right.
     * Asteroids moving the same direction never meet, so no asteroids will meet each other.
     *
     * Note:
     * The length of asteroids will be at most 10000.
     * Each asteroid will be a non-zero integer in the range [-1000, 1000].
     *
     * Medium
     *
     * https://leetcode.com/problems/asteroid-collision
     */


    /**
     * https://leetcode.com/problems/asteroid-collision/discuss/109694/JavaC%2B%2B-Clean-Code
     *
     * !!!
     * overall, there are totally 4 scenarios will happen:
     *   1.+ +
     *   2.- -
     *   3.+ -
     *   4.- +
     *
     * when collision happens: only 3 which is + - (All astroids move at the same speed!!!)
     *
     * use a stack to keep track of the previous and compare current value with previous ones
     *
     * Time and Space : O(n)
     */
    class Solution1 {
        public int[] asteroidCollision(int[] asteroids) {
            Stack<Integer> stack = new Stack<>();

            for (int num : asteroids) {
                if (num > 0) {
                    stack.add(num);
                } else {
                    while (!stack.isEmpty() && stack.peek() > 0 && stack.peek() < -num) {
                        stack.pop();
                    }

                    if (stack.isEmpty() || stack.peek() < 0) {
                        stack.push(num);
                    } else if (stack.peek() == -num) {
                        stack.pop();
                    }
                }
            }

            return stack.stream().mapToInt(i -> i).toArray();
        }
    }

    /**
     * !!!
     * Time : O(n)
     * Space : O(1)
     *
     * A variation of the problem that requires O(1) space - Asteroid_Collision_Variation
     */
    class Solution2 {
        public int[] asteroidCollision(int[] asteroids) {
            int len = helper(asteroids, 0, 1) + 1;
            return Arrays.copyOfRange(asteroids, 0, len);
        }

        /**
         * There are only four possible end states:
         *
         * [-,-,..,-]
         * [+,+,..,+]
         * [-,-,...-,+,+,...,+]
         * []
         *
         * a is last positive, b is first negative after a.
         * so in the end, we get final value for a, that is the end of the final result.
         *
         * Also the surviving asteroids will not change in its values. That's why we don't
         * need to change any values during iteration. We just need to move index pointers.
         */
        private int helper(int[] as, int a, int b) {
            if (as.length <= 1) return as.length;

            while (b < as.length) {
                if (a < 0 || as[a] < 0 || as[b] > 0) {
                    as[++a] = as[b++];  // Win-Win (both are survived)
                } else if (as[a] == -1 * as[b]) {
                    a--;
                    b++;           // Lost-Lost (both are destroyed)
                } else if (as[a] < -1 * as[b]) {
                    a--;                // Win (b replace a, a go back)
                } else {
                    b++;                // Lost (b goes on, a stay)
                }
            }
            return a;
        }
    }
}
