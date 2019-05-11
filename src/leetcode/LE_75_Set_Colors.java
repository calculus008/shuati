package leetcode;

/**
 * Created by yuank on 3/5/18.
 */
public class LE_75_Set_Colors {
    /**
        Given an array with n objects colored red, white or blue, sort them so that objects of the same color are adjacent,
        with the colors in the order red, white and blue.

        Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.

        Note:
        You are not suppose to use the library's sort function for this problem.

        So called "Dutch Flag" problem, can also be used for 3 way partition : https://en.wikipedia.org/wiki/Dutch_national_flag_problem#Pseudocode
     */

    /**
         在颜色排序（Sort Color）这个问题中，传统的双指针算法可以这么做：

         先用 partition 的方式区分开 0 和 1, 2
         再在右半部分区分开 1 和 2
         这个算法不可避免的要使用两次 Parition，写两个循环。许多面试官会要求你，能否只 partition 一次，也就是只用一个循环。

         用一个循环的方法如下：

         http://www.jiuzhang.com/solution/sort-colors

         分析一下核心代码部分：


        public void sortColors(int[] a) {
            if (a == null || a.length <= 1) {
                return;
            }

            int pl = 0;
            int pr = a.length - 1;
            int i = 0;
            while (i <= pr) {
                if (a[i] == 0) {
                    swap(a, pl, i);
                    pl++;
                    i++;
                } else if(a[i] == 1) {
                    i++;
                } else {
                    swap(a, pr, i);
                    pr--;
                }
            }
        }

         pl 和 pr 是传统的双指针，分别代表 0 ~ pl-1 都已经是 0 了，pr+1 ~ a.length - 1 都已经是 2 了。
         另一个角度说就是，如果你发现了一个 0 ，就可以和 pl 上的数交换，pl 就可以 ++；如果你发现了一个 2 就可以和 pr 上的数交换 pr 就可以 --。

         这样，我们用第三根指针 i 来循环整个数组。如果发现 0，就丢到左边（和 pl 交换，pl++），如果发现 2，就丢到右边（和 pr 交换，pr--），如果发现 1，就不管（i++）

         这就是三根指针的算法，两根指针在两边，一根指针扫描所有的数。

         这里有一个实现上的小细节，当发现一个 0 丢到左边的时候，i需要++，但是发现一个2 丢到右边的时候，i不用++。原因是，从pr 换过来的数有可能是0或者2，
         需要继续判断丢到左边还是右边。而从 pl 换过来的数，要么是0要么是1，不需要再往右边丢了。因此这里 i 指针还有一个角度可以理解为，i指针的左侧，都是0和1。

         类似的题
         G家问过一个类似的题：给出 low, high 和一个数组，将数组分为三个部分，< low, >= low & <= high, > high。解法和本题一模一样
     */

    /**
        Time: O(n), Space : O(1)
        left : final location of the end of 0,
        right : final location of the start of 2
    **/
    public static void sortColors(int[] nums) {
        if(nums == null || nums.length == 0) return;

        int left = 0;
        int index = 0;
        int right = nums.length - 1;

        while (index <= right) {
            if (nums[index] == 0) {
                swap(nums, left++, index++);
            } else if (nums[index] == 1) {
                index++;
            } else {
                swap(nums, right--, index);
            }
        }

    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * [2, 0, 2, 1, 1, 0]
     *  l              r
     *  i
     *
     * [0, 0, 2, 1, 1, 2]
     *  l           r
     *  i
     *
     * [0, 0, 2, 1, 1, 2]
     *     l        r
     *     i
     *
     * [0, 0, 2, 1, 1, 2]
     *        l     r
     *        i
     *
     * [0, 0, 1, 1, 2, 2]
     *        l  r
     *        i
     */
    class Solution_Practice {
        public void sortColors(int[] nums) {
            if (nums == null || nums.length == 0) return;

            int l = 0;
            int r = nums.length - 1;
            int i = 0;

            /**
             * !!1
             * 1."<="
             * 2.i <= r, NOT l <= r
             */
            while (i <= r) {
                if (nums[i] == 0) {
                    swap(nums, i, l);
                    l++;
                    i++;
                } else if (nums[i] == 1) {
                    i++;
                } else {
                    swap(nums, i, r);
                    r--;
                }
            }
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}
