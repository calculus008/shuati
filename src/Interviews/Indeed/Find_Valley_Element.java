package Interviews.Indeed;

public class Find_Valley_Element {
    /**
     * 给⼀一个数组，例例如[8,5,3,6,1,4,7], 返回任意⼀一个local minimum，也就是任意⼀一个⾕谷值，
     * ⽐比如在这个例例⼦子⾥里里，返回3和1都是正确的。数组没有重复的数，所以不不会出现[1,1,1,1,1]
     * 这种没有结果的情况情况
     *
     * LE_162_Find_Peak_Element
     */

    public static int findValleyElement(int[] nums) {
        int l = 0;
        int r = nums.length - 1;

        while (l < r) {
            int m = l + (r - l) / 2;

            if (nums[m] < nums[m + 1]) {
                r = m;
            } else {
                l = m + 1;
            }
        }

        return nums[l];
    }

    public static void main(String[] args) {
        int[] nums = {8,5,3,6,1,4,7};

//        int[] nums = {7,6,4,3,2,5,8};
        System.out.println(findValleyElement(nums));
    }
}
