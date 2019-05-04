package Interviews.Google;

public class Chocolate_Sweetness {
    /**
     * Google 高频
     *
     * https://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=499490&extra=page%3D1%26filter%3Dsortid%26sortid%3D335&page=1
     *
     * 一道关于切巧克力的题。题目是给定一个数组（一板巧克力），数组里的每一个数字都表示其中一块的甜度。
     * 有n个人来分享这巧克力，需要切n-1刀。其他n-1个人会把甜度高的portion都拿走 剩下的留给你。
     * 问如何切割这块巧克力你自己尽可能能拿到最高的甜度。比如数组[3,2,3,1,4],3个人分，
     * 切成[3,2 |3,1 |4]，你能拿到最多的4。后来问了朋友，听说可以用二分答案来做？
     *
     * https://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=499453
     *
     * followup可能有：
     * 如果最高的甜度是按照max而非sum
     *
     * Given the number of bags,
     * return the MAX capacity of each bag
     *
     * 这题问的是把最小值尽可能变大，总共要K份
     *
     * 首先要知道leetcode 1014 and 410问的东西和这题很像但是不一样：
     * - 原题问的是把最大值尽可能变小，总共要K份
     * 可以把他想成一个Bar Graph，每一个subarray sum是一个Bar，总共要至少K个Bar，而且最高的那个Bar不
     * 得超过二分法猜的 Target．二分法的过程中我们要尽可能的把那个Target压小，直到Target再小一格的话
     * 就达不到 K 个Bar了．使用左闭右开的二分法模板，最后left的数值是“符合条件的最小数字”，在这一题的条
     * 件就是至少要K个Bar，而每个Bar都不能超过猜测的Target ，所以left 会得到最小化的最大值．
     *
     * - 这题问的是把最小值尽可能变大，总共要K份
     * 同理，我们也把题目想成一个Bar Graph，每一个subarray sum 是一个Bar，总共至少K个Bar，而这一次我们
     * 要让所有的Bar都至少达标一个猜测的Target，这样Target就是Bar Graph的最小值．二分法的过程中我们要尽
     * 可能的把这个猜测的Target变大，直到再变大一格就没办法分成K份了．可是如果要把猜测的数值尽可能变大，
     * 就没办法直接套用左闭右开的二分法模板了．其他的二分法模板应该能解决，但我很任性就只想记一种模板，所
     * 以为何不把二分法确认的条件换一下？我们也可以去猜一个Target，要求每一个Bar 都要Target，但如果用了
     * 这个Target 就不足以分成K份了，找这个Target的最小值．也就是说：使用二分法会找到一个最小值的Target，
     * 虽然不能分成K份，但它已经小到再小一格就能分成K份了．使用左闭右开模板Left会得到这个Target，所以
     * Left-1就刚刚好是能分成K份的最大化最小值．
     *
     * 原题是猜一个数使每一片段都<=过这个数，至少可以分成n片
     * 这题是猜一个数使每一片段都>=这个数，这少可以分成n片（或是找最小的一个数，让他无法分成n片）
     *
     * https://www.geeksforgeeks.org/maximum-number-chocolates-distributed-equally-among-k-students/
     */

    public static int chcolates(int[] chocolates, int N) {//N people to divide chocolates
        int l = Integer.MAX_VALUE;
        int r = 0;

        for (int w : chocolates) {
            /**
             * !!!
             * l should be the min value in array
             * (not max like in max-min type problem)
             */
            l = Math.min(l, w);
            r += w;
        }

        r++;
        while (l < r) {
            int m = l + (r - l) / 2;

            System.out.println("l = " + l + ", r = " + r + ", m = " + m);

            int count = countGroups(chocolates, m);
            if (count < N) {
                r = m;
            } else {//count >= N
                l = m + 1;
            }
        }

        System.out.println("l=" + l + ", r = " + r);
        return l - 1;
    }

    /**
     * !!!
     * if sum of each group should at least be sweet, the max
     * number of groups can be formed.
     *
     * 这是这道题和max-min二分的关键区别，这里，我们猜了个数-sweet,
     * 要求是每个group的sum要>=sweet (不是<=).所以计算出以sweet为
     * group sum 的 lower-bound，最多能组成多少组。
     */
    private static int countGroups(int[] chocolates, int sweet) {
//        System.out.println("sweet = " + sweet);
        int count = 1;
        int sum = 0;
        int n = chocolates.length;

        for (int i = 0; i < chocolates.length;  i++) {
            sum += chocolates[i];

            if (sum >= sweet && i != n - 1) {
                count++;
                sum = 0;
            }
        }

        /**
         * 最后一组，如果sum小于sweet，只能把它并入到前一个group,
         * 否则不满足group sum lower-bound的要求。
         */
        if (sum < sweet) {
            count--;
        }

        System.out.println("If sum of each group must be bigger than " + sweet + ", max number group is " + count);
        return count;
    }

    public static void main(String[] args) {
        int[] nums = {3, 4, 5, 5, 6};//((3, 4), (5), (5), (6))
        int k = 4;
        System.out.println(chcolates(nums, k) == 5 ? "correct" : "incorrect");
        //((3, 4), (10), (6))
        k = 3;
        System.out.println(chcolates(nums, k) == 6 ? "correct" : "incorrect");

        int[] nums1 = {5, 5, 6, 7, 8, 9, 9, 5};
        k = 8;//((5, 5), (6, 7), (8), (9), (9, 5))
        System.out.println(chcolates(nums1, k) == 5 ? "correct" : "incorrect");
        k = 7;//((5, 5), (6, 7), (8), (9), (9, 5))
        System.out.println(chcolates(nums1, k) == 5 ? "correct" : "incorrect");
        k = 1;
        System.out.println(chcolates(nums1, k) == 54 ? "correct" : "incorrect");

        int[] nums2 = {5, 3, 4, 4};
        k = 2;//((5, 3), (4, 4))
        System.out.println(chcolates(nums2, k) == 8 ? "correct" : "incorrect");
        k = 3;//((5), (3), (4), (4))
        System.out.println(chcolates(nums2, k) == 4 ? "correct" : "incorrect");

        int[] nums3 = {4, 4, 4, 4};
        k = 4;
        System.out.println(chcolates(nums3, k) == 4 ? "correct" : "incorrect");
    }

}
