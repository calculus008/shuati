package Interviews.Lyft.lc;

import java.util.Arrays;

public class Asteroid_Collision_Variation {
    /**
     * LE_735_Asteroid_Collision 的变形题。
     *
     * 每次撞过之后，小的会爆炸，大的的尺寸会变成大的减去小的，感觉比leetcode上的还好做一些...
     * space station 在右边 How many asteroids will hit station。 同时要求O(1) space solution
     * space station 在最右侧， 问题是最后有多少小行星会撞击space station. eg 如果是一直向左飞的话就不会撞击
     *
     * 原题是还剩多少asteroid
     * 而这题是最后撞到右边的有多少； 原题中飞向左边的就算存活下来了 因为不是飞向右边所以也不能算
     *
     * The question I got was only returning the number of Asteroid left.
     */


    /**
     * Final solution for this problem, a variation of Soluion2 from LE_735_Asteroid_Collision
     *
     * Key points:
     * There are only four possible end states:
     *
     * [-,-,..,-]
     * [+,+,..,+]
     * [-,-,...-,+,+,...,+]
     * []
     *
     * Also we only care about collisions and the only possible scenarios for collisions :
     *
     * [+, -]
     *
     * So we set :
     * a is last positive, b is first negative after a
     * If the invariant is true, asteroids at index a and b will collide.
     * If not, we just move the pointers of both a and b to the right.
     *
     * In the end, a will be at the last index of the final left asteroids.
     *
     * so we need to find the final value of a, then we can get the number of asteroid moving right
     * (and hit station)
     */

    public static int asteroidCollisionNumberHitsStation(int[] as) {
        System.out.println("Input : " + Arrays.toString(as));

        if (as == null || as.length == 0) return 0;
        if (as.length == 1) return as[0] > 0 ? 1 : 0;

        int a = 0, b = 1;
        while (b < as.length) {
            if (a < 0 || as[a] < 0 || as[b] > 0) {
                as[++a] = as[b++];  // Win-Win (both are survived)
//                System.out.println("#1.a="+a+",b="+b);
            } else if (as[a] == -1 * as[b]) {
                a--;
                b++;           // Lost-Lost (both are destroyed)
//                System.out.println("#2.a="+a+",b="+b);
            } else if (as[a] < -1 * as[b]) {
                as[b] += as[a];
                a--;                // Win (b replace a, a go back)
//                System.out.println("#3.a="+a+",b="+b);
            } else {
                as[a] += as[b];
                b++;                // Lost (b goes on, a stay)
//                System.out.println("#4.a="+a+",b="+b);
            }
        }

        int count = 0;
        for (int i = a; i >= 0; i--) {
            if (as[i] > 0) count++;
        }

        System.out.println("Number of asteriods hits stations on right: " + count);
        System.out.println("Final State:" + Arrays.toString(Arrays.copyOfRange(as, 0, a + 1)));

//        System.out.println("a="+a+", b="+b);
        return count;
    }

    /**
     * This should be the answer, no need to be so complicated with 2 pointers.
     * Since we only want to find the number of asteroids hits right side,
     * just iterator from right to left, cancel the collided elements, only count as
     * positive number after collision.
     *
     * Observation:
     * The only possible collisions happen for [+, -], for example, the following input will
     * only have 3 possible partitions that will have collisions:
     *
     * [-, -, +, +, -, -, -, +, -, +, -]
     *           |________|  |__|  |__|
     *
     * Therefore, we only need to iterate from right to left, sum negative numbers, once running
     * into a positive number, sum it with negative sum so far. If result > 0, it means positive
     * number wins the collision, it will reach right side, so increase count and reset sum to 0.
     * Otherwise, negative wins, just add cur positive number to sum.
     *
     * Time  : O(n)
     * Space : O(1)
     */
    public static int asteroidCollisionNumberHitsStation1(int[] as) {
        System.out.println("Input : " + Arrays.toString(as));

        if (as == null || as.length == 0) return 0;
        if (as.length == 1) return as[0] > 0 ? 1 : 0;

        int sum = 0;
        int count = 0;
        for (int i = as.length - 1; i >= 0 ; i--) {
            if (as[i] > 0) {
                if (as[i] + sum > 0) {
                    count++;
                    sum = 0;
                } else {
                    sum += as[i];
                }
            } else {
                sum += as[i];
            }
        }

        System.out.println("Number of asteriods hits stations on right: " + count);

        return count;
    }

    public static void main(String[] args) {
        int[] nums1 = {5, 10, -5};
        int[] nums2 = {-2, -1, 1, 2};
        int[] nums3 = {10, 2, -5};
        int[] nums4 = {-1, -2, 50, -15, 60, -30, -5, -6};
        int[] nums5 = {10, -10, 50, -20, -30};
        int[] nums6 = {10, -10};

//        System.out.println(Arrays.toString(asteroidCollision(nums1)));
//        System.out.println(Arrays.toString(asteroidCollision(nums2)));
//        System.out.println(Arrays.toString(asteroidCollision(nums3)));

//        System.out.println(Arrays.toString(asteroidCollision1(nums1)));
//        System.out.println(Arrays.toString(asteroidCollision1(nums2)));
//        System.out.println(Arrays.toString(asteroidCollision1(nums3)));

//        asteroidCollisionNumberHitsStation(nums1);
//        asteroidCollisionNumberHitsStation(nums2);
//        asteroidCollisionNumberHitsStation(nums3);
//        asteroidCollisionNumberHitsStation(nums4);
//        asteroidCollisionNumberHitsStation(nums5);
//        asteroidCollisionNumberHitsStation(nums6);

        System.out.println("------------");

        asteroidCollisionNumberHitsStation1(nums1);
        asteroidCollisionNumberHitsStation1(nums2);
        asteroidCollisionNumberHitsStation1(nums3);
        asteroidCollisionNumberHitsStation1(nums4);
        asteroidCollisionNumberHitsStation1(nums5);
        asteroidCollisionNumberHitsStation1(nums6);
    }

    /**
     * 面经上提到过给定 Asteroid class, use this class as input to rewrite the answer
     */
    static class Asteroid {
        public final int mass;
        public final int direction;

        public Asteroid(int mass, int direction) {
            this.mass = mass;
            this.direction = direction;
        }

        public int getMass() {
            return this.mass;
        }

        public int getDirection() {
            return this.direction;
        }

        public String toString() {
            return "Asteroid(" + mass + ", " + direction + ")";
        }
    }

    public static int asteroidCollisionNumberHitsStation2(Asteroid[] as) {
        System.out.println("Input : " + Arrays.toString(as));

        if (as == null || as.length == 0) return 0;
        if (as.length == 1) return as[0].direction > 0 ? 1 : 0;

        int sum = 0;
        int count = 0;
        for (int i = as.length - 1; i >= 0 ; i--) {
            if (as[i].direction > 0) {
                if (as[i].mass + sum > 0) {
                    count++;
                    sum = 0;
                } else {
                    sum += as[i].mass;
                }
            } else {
                sum += as[i].mass;
            }
        }

        return count;
    }

    /**
     * Follow up:
     *
     * 如果彗星有不同的速度，之间有不同的距离，结果有什么不同，如何设计算法？
     * 好像没有什么好的办法，只能一步步更新，先算每个彗星碰到另外一个的时间，得到最小的碰撞时间，
     * 更新彗星的坐标，距离和质量，反复重复知道没有碰撞为止。
     * For example,
     *             space station    A1    A2    A3
     * mass            0             1     2     3
     * distance        0             6     8    10
     * speed           0            -1     2    -2
     *
     * at time 0.5, A2 and A3 collide. 注意：A2 and A3的相对速度是 2 - (-2) = 4, 距离是10 - 8 = 2,
     * 所以： t = 2 / 4 = 0.5
     * and update status：
     *             space station    A1           A3
     * mass            0             1            3
     * distance        0           5.5            9
     * speed           0            -1           -2
     *
     * at time 4, A1 and A3 collide, and update status：
     *             space station                 A3
     * mass            0                          3
     * distance        0                          2
     * speed           0                         -2
     *
     * finally at time 5, A3 hits space station, and only 1 asteroid hits space station.
     *
     * 因为速度不同，collision的情况会更多，在同一个方向上，速度快的会追上前面速度慢的。
     */

}

