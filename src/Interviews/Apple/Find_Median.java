package Interviews.Apple;

public class Find_Median {
    /**
     * 说有一些整数，知道最大最小值，并可以调用两个API，输入整数x，返回数字中大于等于或者小于等于x的个数。问题是求中位数
     * 题不难，面试官很看中对思路的描述，希望你把完整的算法清晰的解释出来再开始，而不是一上来就xjb码
     */
    //dummy method
    public static int getBiggerOrEqual(int[] nums, int n) {
        int count = 0;
        for (int num : nums) {
            if (num >= n) count++;
        }
        return count;
    }

    //dummy method
    public static int getSmnallerOrEqual(int[] nums, int n) {
        int count = 0;
        for (int num : nums) {
            if (num <= n) count++;
        }
        return count;
    }

    public static double findMedian(int[] nums, int min, int max) {
        if (max == min) return max;

        int len = getBiggerOrEqual(nums, min);

        int target = len / 2;
        System.out.println("len=" + len + ", target="+target);


        int small = len;
        int l = min;
        while (l < max) {
            small = getBiggerOrEqual(nums, l);
            System.out.println("small=" +small+ ", l=" + l);
            if (small <= target) break;
            l++;
        }
        l--;

        int big = len;
        int r = max;
        while (r > min) {
            big = getSmnallerOrEqual(nums, r);
            System.out.println("big="+big + ", r=" + r);
            if (big <= target) break;
            r--;
        }
        r++;

        System.out.println("small=" + small + ", big=" + big + ". l="+l + ", r="+r);

        if (len % 2 == 0) return (l + r) / 2.0;


        return l;
    }

    //7 - 99

    public static void main(String[] args) {
//        int[] input = {1, 6, 6, 6, 8, 8, 8, 12};
//        int[] input = {1, 6, 6, 6, 100, 100, 100, 100, 120};
        int[] input = {1, 6, 6, 6, 100, 100, 100, 100, 120};
//        int[] input = {1, 100};


        System.out.println(findMedian(input, 1, 120));
    }
}
