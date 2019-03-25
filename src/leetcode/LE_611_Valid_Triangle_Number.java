package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 7/9/18.
 */
public class LE_611_Valid_Triangle_Number {
    /**
         Given an array consists of non-negative integers, your task is to count the number of triplets chosen
         from the array that can make triangles if we take them as side lengths of a triangle.

         Example 1:
         Input: [2,2,3,4]
         Output: 3
         Explanation:
         Valid combinations are:
         2,3,4 (using the first 2)
         2,3,4 (using the second 2)
         2,2,3

         Note:
         The length of the given array won't exceed 1000.
         The integers in the given array are in the range of [0, 1000].

         Medium

         LI_609_Two_Sum_Less_Than_Or_Equal_To_Target
     */

    /**
     * Time : O(n ^ 2), Space : O(1)
     **/
    class Solution1 {
        public int triangleNumber(int[] nums) {
            /**
             * 1.Must sort
             */
            Arrays.sort(nums);
            int n = nums.length;
            int count = 0;

            /**
             * 2.当前的for循环确定3条边中最长的那条(nums[i])
             */
            for (int i = n - 1; i >= 2; i--) {//!!!"i--"
                int l = 0;

                /**
                 * 3.当最长的边确定后，找其他两条边，
                 *   在已经排好序的数组中，这两条边的上边界是nums[i-1]
                 */
                int r = i - 1;
                while (l < r) {
                    if (nums[l] + nums[r] > nums[i]) {
                        count += r - l;
                        /**
                         * 4.上边界下移，继续找
                         */
                        r--;
                    } else {
                        l++;
                    }
                }
            }

            return count;
        }

        public int triangleCount_JiuZhang(int S[]) {
            int left = 0, right = S.length - 1;
            int ans = 0;
            Arrays.sort(S);

            for (int i = 0; i < S.length; i++) {
                left = 0;
                right = i - 1;
                while (left < right) {
                    if (S[left] + S[right] > S[i]) {
                        ans = ans + (right - left);
                        right--;
                    } else {
                        left++;
                    }
                }
            }
            return ans;
        }
    }

    /**
     * Binary Search
     **/
    class Solution2 {
        public int triangleNumber(int[] nums) {
            if (nums.length < 3) return 0;
            Arrays.sort(nums);
            int count = 0;
            for (int i = 0; i < nums.length; i++) {
                for (int j = i + 1; j < nums.length; j++) {
                    count += binarySearch(nums, nums[i] + nums[j], j + 1);
                }
            }
            return count;
        }

        private int binarySearch(int[] nums, int target, int start)
        {
            int left = start, right = nums.length;
            while (left < right)
            {
                int mid = left + (right - left) / 2;
                if (nums[mid] < target) left = mid + 1;
                else right = mid;
            }
            return left - start;
        }
    }

    /**
     * 变形题
     * Given一个array of non-negative integers, 找出3组数字可以组成3角形, 每个数字表示边长,
     * 组成3角形的充要条件就是任2边的和要大于第3边
     *
     * Output is all possible triplets, not total number of triangles.
     * */
    public class CanFormTriangle {

        public void main(String[] args) {
            int[] arr = {3, 2, 1, 9, 5, 7, 4, 2, 9, 5};
            solutionsOfTriangle(arr);
        }


        public List<List<Integer>> solutionsOfTriangle(int[] arr) {
            List<List<Integer>> res = new ArrayList<List<Integer>>();
            Arrays.sort(arr);

            for (int i = arr.length - 1; i >= 2; i--) {
                int left = 0;
                int right = i - 1;

                while (left < right) {
                    if (arr[left] + arr[right] > arr[i]) {
                        for (int j = left; j < right; j++) {
                            List<Integer> list = new ArrayList<Integer>();
                            list.add(arr[i]);
                            list.add(arr[j]);
                            list.add(arr[right]);
                            res.add(list);
                        }
                        right--;
                    } else {
                        left++;
                    }
                }
            }

            return res;
        }
    }

}
