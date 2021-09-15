package leetcode;

import java.util.*;

public class LE_1776_Car_Fleet_II {
    /**
     * here are n cars traveling at different speeds in the same direction along a one-lane road. You are given an array
     * cars of length n, where cars[i] = [positioni, speedi] represents:
     *
     * positioni is the distance between the ith car and the beginning of the road in meters. It is guaranteed that
     * positioni < positioni+1.
     * speedi is the initial speed of the ith car in meters per second.
     *
     * For simplicity, cars can be considered as points moving along the number line. Two cars collide when they occupy
     * the same position. Once a car collides with another car, they unite and form a single car fleet. The cars in the
     * formed fleet will have the same position and the same speed, which is the initial speed of the slowest car in the
     * fleet.
     *
     * Return an array answer, where answer[i] is the time, in seconds, at which the ith car collides with the next car,
     * or -1 if the car does not collide with the next car. Answers within 10-5 of the actual answers are accepted.
     *
     * Example 1:
     * Input: cars = [[1,2],[2,1],[4,3],[7,2]]
     * Output: [1.00000,-1.00000,3.00000,-1.00000]
     * Explanation: After exactly one second, the first car will collide with the second car, and form a car fleet with
     * speed 1 m/s. After exactly 3 seconds, the third car will collide with the fourth car, and form a car fleet with
     * speed 2 m/s.
     *
     * Example 2:
     * Input: cars = [[3,4],[5,4],[6,3],[9,1]]
     * Output: [2.00000,1.00000,1.50000,-1.00000]
     *
     * Constraints:
     * 1 <= cars.length <= 105
     * 1 <= positioni, speedi <= 106
     * positioni < positioni+1
     *
     * Hard
     */

    /**
     * Mono Stack
     *
     * We care about the collision time of the cars in front us.
     * We iterate from the last to the first car, and we maintain a stack of car indices,
     * where their collision time is strict DECREASING.
     *
     * Imagine a,b,c on the road, if the a catches b later than b catches c, then a won't catch b but b+c.
     *
     * 这道题目最核心的逻辑就是, 如果从右往左的扫描的过程中，扫到了第i个，然后往右计算是否碰撞中，如果没法在第i + 1追上i + 2以前追上i + 1，
     * 那么其实可以把i + 2车当作i的右边第一辆车，以此类推.
     *
     * 其实就是简单的追及问题 追不上前面一辆车就看看能否追上前面第二辆车 要么最后追上了某一辆车 要么永远追不上前面的车 而追不上的情况正
     * 好stack就空了 自己就成了后面车的塞子.
     *
     * It's still mono-stack problem. The difference is that we have two condition this time: we keep a decreasing time
     * stack and increasing speed stack in one stack at the same time.
     * #1.If the current speed is less than the speed afterwards, the car before this car can never go across this car to
     *    hit the car after it.
     * #2.At the same time, if the current colliding time of current car is t1, any car who has smaller colliding time than
     *    t1 before this car won't hit the cars after it.
     * That's why we add two conditions to maintain a mono-stack. Finally, after the stack is well-maintained, the car
     * still in the stack would be the one that current car will hit. Calculate its time, put it in the res.
     *
     * Time O(n)
     * Space O(n)
     */
    class Solution {
        public double[] getCollisionTimes(int[][] cars) {
            Deque<Integer> stack = new ArrayDeque<>();

            int n = cars.length;
            double[] res = new double[n];

            /**
             * !!!
             * The iteration from the end of the array takes care of the position restraints. For current car, we look
             * to its right for possible collisions.
             * The collision time for a car won't be affected by the cars on its left. Thus if going from right to left,
             * we can fix the collision time along the way.
             */
            for (int i = n - 1; i >= 0; i--) {
                int curPos = cars[i][0];
                int curSpeed = cars[i][1];

                /**
                 * !!!
                 */
                res[i] = -1.0;

                while (!stack.isEmpty()) {
                    int idx = stack.peekLast();
                    int pos = cars[idx][0];
                    int speed = cars[idx][1];

                    /**
                     * Enforce the collision time of the car (with some car on his right) is strict DECREASING.
                     *
                     * target car : the last one in the stack.
                     *
                     * #1."curSpeed <= speed"
                     *    current car speed is slower or equal to the target car.
                     *    So collision with target car is not possible, no collision time available.
                     *
                     * #2."res[idx] > 0 && 1.0 * (pos - curPos) / (curSpeed - speed) >= res[idx]"
                     *    The current car's speed is faster than the target car, but the target car collides with some car
                     *    on his right and the collision happens before current car can collide with it.
                     *    So collision with target car is not possible, no collision time available.
                     *
                     * In other words, the collision time for a car will only be affected by the cars on its right.
                     * Again among all the cars on current car's right, there are certain cars that won't affect current
                     * car's collision time. Namely, if the car has a higher speed than the current car, or, if the car's
                     * collision time is earlier than the current car's collision time with this car, then such cars won't
                     * affect current car's collision time, and can be ignored. (After collision, the result is that the
                     * car group takes the speed of the slower car, so you can think that the faster car that catches up
                     * with the slower car just vanishes.)
                     *
                     * The mono stack has only kept those cars that can possibly affect the current car.
                     */
                    if (curSpeed <= speed || (res[idx] > 0 && 1.0 * (pos - curPos) / (curSpeed - speed) >= res[idx])) {
                        stack.pollLast();
                    } else {
                        break;
                    }
                }

                if (!stack.isEmpty()) {
                    int prev = stack.peekLast();
                    int prevPos = cars[prev][0];
                    int prevSpeed = cars[prev][1];

                    res[i] = 1.0 * (prevPos - curPos) / (curSpeed - prevSpeed);
                }

                stack.addLast(i);
            }

            return res;
        }
    }
}
