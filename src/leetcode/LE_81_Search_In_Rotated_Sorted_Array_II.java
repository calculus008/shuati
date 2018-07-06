package leetcode;

/**
 * Created by yuank on 3/6/18.
 */
public class LE_81_Search_In_Rotated_Sorted_Array_II {
    /**
        Follow up for "Search in Rotated Sorted Array":
        What if duplicates are allowed?

        Would this affect the run-time complexity? How and why?

        Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

        (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).

        Write a function to determine if a given target is in the array.

        The array may contain duplicates.
     */

    //Follow up question from LE_33
    //Time : Worest case O(n)
    public static boolean search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        int start = 0;
        int end = nums.length - 1;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (nums[mid] == target) {
                return true;
            }

            if (nums[start] == nums[mid] && nums[mid] == nums[end]) {
                start++;
                end--;
            } else if (nums[start] <= nums[mid]) {
                if(nums[start] <= target && target <= nums[mid]) {
                    end = mid;
                } else {
                    start = mid;
                }
            } else {
                if(nums[mid] <= target && target <=nums[end]) {
                    start = mid;
                } else {
                    end = mid;
                }
            }
        }

        if (nums[start] == target) return true;
        if (nums[end] == target) return true;
        return false;
    }

    /**
     这个问题在面试中不会让实现完整程序
     只需要举出能够最坏情况的数据是 [1,1,1,1... 1] 里有一个0即可。
     在这种情况下是无法使用二分法的，复杂度是O(n)
     因此写个for循环最坏也是O(n)，那就写个for循环就好了
      如果你觉得，不是每个情况都是最坏情况，你想用二分法解决不是最坏情况的情况，那你就写一个二分吧。
      反正面试考的不是你在这个题上会不会用二分法。这个题的考点是你想不想得到最坏情况。
     **/
    public boolean search_JiuZhang(int[] A, int target) {
        for (int i = 0; i < A.length; i ++) {
            if (A[i] == target) {
                return true;
            }
        }
        return false;
    }
}
