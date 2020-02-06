package src.Interviews.Servicenow;

public class MaxProductOfFiveElements {
    /**
     * 在 Array 里面求５个数乘积最大。
     * 没啥算法，就是模拟法　５个最大正数　２个最小负数×３个最大正数　　４个最小负数×１个最大正数　　里面挑一个最大。
     * 不要排序　扫一遍数据　拿到５个最大正数　４个最小负数　然后比较就行
     *
     * Variation of LE_628_Maximum_Product_Of_Three_Numbers
     */

    /**
     * https://www.geeksforgeeks.org/find-the-largest-three-elements-in-an-array/
     *
     * Ask interviewer if we need to assume product values is with int range, or
     * we need to use long.
     */

    public static int MaxProduct(int[] nums) {
        if (nums == null || nums.length < 5) return 0;

        int first = Integer.MIN_VALUE;
        int second = Integer.MIN_VALUE;
        int thrid = Integer.MIN_VALUE;
        int fourth = Integer.MIN_VALUE;
        int fifth = Integer.MIN_VALUE;

        int minfirst = Integer.MAX_VALUE;
        int minSecond = Integer.MAX_VALUE;
        int minThird = Integer.MAX_VALUE;
        int minFourth = Integer.MAX_VALUE;

        /**
         * don't need to care about negative or positive, just get the
         * largest 5 elements and smallest 4 elements, compute 3 possible
         * cases and then get the largest one.
         */
        for (int num : nums) {
            if (num > first) {
                fifth = fourth;
                fourth = thrid;
                thrid = second;
                second = first;
                first = num;
            } else if (num > second) {
                fifth = fourth;
                fourth = thrid;
                thrid = second;
                second = num;
            } else if (num > thrid) {
                fifth = fourth;
                fourth = thrid;
                thrid = num;
            } else if (num > fourth) {
                fifth = fourth;
                fourth = num;
            } else if (num > fifth) {
                fifth = num;
            }

            if (num < minfirst) {
                minFourth = minThird;
                minThird = minSecond;
                minSecond = minfirst;
                minfirst = num;
            } else if (num < minSecond) {
                minFourth = minThird;
                minThird = minSecond;
                minSecond = num;
            } else if (num < minThird) {
                minFourth = minThird;
                minThird = num;
            } else if (num < minFourth) {
                minFourth = num;
            }
        }

        int p1 = first * second * thrid * fourth * fifth;
        int p2 = first * second * thrid * minfirst * minSecond;
        int p3 = first * minfirst * minSecond * minThird * minFourth;

        return Math.max(Math.max(p1, p2), p3);
    }

    public static void main(String[] args) {
        int[] nums = {-10, -2, -8, -6, -15};

        int res = MaxProduct(nums);
        System.out.println(res);
    }
}
