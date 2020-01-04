package leetcode;

import java.util.Arrays;

/**
 * Created by yuank on 4/19/18.
 */
public class LE_287_Find_The_Duplicate_Number {
    /**
         Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive),
         prove that at least one duplicate number must exist.
         Assume that there is only one duplicate number, find the duplicate one.

         Note:
         You must not modify the array (assume the array is read only).
         You must use only constant, O(1) extra space.
         Your runtime complexity should be less than O(n2).
         There is only one duplicate number in the array, but it could be repeated more than once.

        Medium
     */

    /**
     * Important
     * 关键是要求both space O(1)
     */


    /**
     * This one works, but it needs Space O(n).So it does not meet the requirement for space O(n)
     **/
    public int findDuplicate1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int[] dup = new int[nums.length];
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (++dup[nums[i]] > 1) {
                res = nums[i];
                break;
            }
        }

        return res;
    }

    /**
     *
     * @param nums
     * @return
     *
     * Time : O(n), Space : O(1)
     *
     * 如果把数据看做一个 LinkedList，第 i 个位置上的值代表第 i 个点的下一个点是什么的话，
     * 我们就能画出一个从 0 出发的，一共有 n + 1 个点的 Linked List。可以证明的一件事情是，
     * 这个 Linked List 一定存在环。因为无环的 Linked List 里 非空next 的数目和节点的数目关系是差一个（节点多，非空next少）
     *
     * 那么，我们证明了这是一个带环链表。而我们要找的重复的数，也就是两个点都指向了同一个点作为 next 的那个点。也就是环的入口。
     * 因此完全套用 Linked List Cycle 这个题快慢指针的方法即可。
     *
     *  col 0 1 2 3
     * nums 2 1 3 1
     *
     * col
     *  0 - 2
     *  1 - 1
     *  2 - 3
     *  3 - 1
     *
     *  0 - 2 - 3 - 1 - 1 - 1
     *  There's a loop
     *
     *  Check LE_142_Linked_List_Cycle_II
     *
     *  https://segmentfault.com/a/1190000003817671
     *
     */
    public int findDuplicate2(int[] nums) {
        int slow = nums[0];
        int fast = nums[nums[0]];

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }

        fast = 0;

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }

    /**
     * Solution 3
     * 排序后二分找到第一个A[i]<=i的数，
     * Time : O(nlogn)
     *
     * 但是不符合题意： You must not modify the array (assume the array is read only).
     */
    public int findDuplicate3(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        Arrays.sort(nums);
        int start = 0;
        int end = nums.length - 1;
        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (nums[mid] <= mid) {
                end = mid;
            } else {
                start = mid;
            }
        }

        if (nums[start] == nums[start+1]) {
            return nums[start];
        }
        return nums[end];
    }

    /**
     * Solution 4
     * Time : O(nlogn)
     * https://www.jiuzhang.com/solution/find-the-duplicate-number/#tag-highlight
     *
     * 这个题比较好的理解方法是画一个坐标轴：
     * x轴是 0, 1, 2, ... n。
     * y轴是对应的 <=x 的数的个数(!!!)，比如 <=0 的数的个数是0，就在（0,0）这个坐标画一个点。<=n 的数的个数是 n+1 个，就在 (n,n+1)画一个点。
     *
     * 我们可以知道这个折线图的有如下的一些属性：
     * 大部分时候，我们会沿着斜率为 1 的那条虚线前进, 如果出现了一些空缺的数，就会有横向的折线, 一旦出现了重复的数，就会出现一段斜率超过 1 的折线.
     * 斜率超过 1 的折线只会出现一次.
     *
     * 试想一下，对比 y=x 这条虚线，当折线冒过了这条虚线出现在这条虚线的上方的时候，一定是遇到了一个重复的数。
     * 一旦越过了这条虚线以后，就再也不会掉到虚线的下方或者和虚线重叠。因为折线最终会停在 (n,n+1) 这个位置，
     * 如果要从 y=x 这条虚线或者这条虚线的下方到达 (n,n+1) 这个位置，一定需要一个斜率 > 1的折线段，而这个与题目所说的重复的数只有一个就是矛盾的。
     *
     * 因此可以证明，斜率超过1 的折线只会出现1次，且会将折线整体带上 y=x 这条虚线的上方。(!!!)因此第一个在 y=x 上方的 x 点，就是我们要找的重复的数。
     *
     */
    class Solution_Jiuzhang {
        public int findDuplicate(int[] nums) {
            /**
             * !!!
             * l = 1, earch range 1 ~ n
             */
            int l = 1;
            int r = nums.length - 1;  // n

            while (l + 1 < r) {
                int mid = l + (r - l) / 2;
                if (count(nums, mid) <= mid) {
                    l = mid;
                } else {
                    r = mid;
                }
            }

            /**
             * return r or l, NOT nums[r] or nums[l],
             * here l and r are number of elements in nums
             * that are smaller than a certain index number.
             */
            if (count(nums, l) <= l) {
                return r;
            }
            return l;
        }

        private int count(int[] nums, int mid) {
            int cnt = 0;
            for (int item : nums) {
                if (item <= mid) {
                    cnt++;
                }
            }
            return cnt;
        }
    }


    /**
     * use huahua's binary search template
     */
    class Solution_Jiuzhang_1 {
        public int findDuplicate(int[] nums) {
            int n = nums.length;
            int l = 0;
            int r = n - 1;

            while (l < r) {
                int m = l + (r - l) / 2;
                if (count(nums, m) <= m) {
                    l = m + 1;
                } else {
                    r = m;
                }
            }

            return l;
        }

        private int count(int[] nums, int m) {
            int count = 0;
            for (int num : nums) {
                if (num <= m) {
                    count++;
                }
            }
            return count;
        }
    }
}
