package Interviews.Lyft.lc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Product_Of_Array_Except_Self {

    /**
     * multiply all numbers and divide by current number.
     *
     * Need to consider :
     * 1.elements is 0
     * 2.integer overflow in multiplying
     */
    class Solution1 {
        public int[] productExceptSelf1(int[] nums) {
            List<Integer> list = new ArrayList<>();
            long multiply = 1L;
            int n = nums.length;

            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == 0) list.add(i);
                if (multiply != 0) multiply *= nums[i];
            }

            int[] res = new int[nums.length];

            if (multiply == 0){
                if (list.size() > 1) {
                    return res;
                } else {
                    int idx = list.get(0);

                    long left = 1L;
                    for (int i = 0; i < idx; i++) {
                        left *= nums[i];
                    }

                    long right = 1L;
                    for (int i = idx + 1; i < n; i++) {
                        right *= nums[i];
                    }

                    res[idx] = (int) (left * right);
                }
            } else {
                for (int i = 0; i < n; i++) {
                    res[i] = (int)(multiply / nums[i]);
                }
            }

            return res;
        }

        public int[] productExceptSelf2(int[] nums) {
            int n = nums.length;

            List<Integer> list = new ArrayList<>();

            long multiply = 1L;
            long left = 1L;
            long right = 1L;

            int first = -1;
            int second = -1;

            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != 0) {
                    if (first == -1) {
                        left *= nums[i];
                    } else if (second == -1) {
                        right *= nums[i];
                    }
                }

                if (nums[i] == 0) {
                    list.add(i);

                    if (first == -1) {
                        first = i;
                    } else if (second == -1) {
                        second = i;
                    }
                }

                if (multiply != 0) multiply *= nums[i];
            }

            int[] res = new int[nums.length];

            if (multiply == 0){
                if (list.size() > 1) {
                    return res;
                } else {
                    int idx = list.get(0);
                    res[idx] = (int) (left * right);
                }
            } else {
                for (int i = 0; i < n; i++) {
                    res[i] = (int)(multiply / nums[i]);
                }
            }

            return res;
        }
    }

    /**
     * Improved from Solution2
     * Use 1 extra array, 2 passes.
     *
     * Since the result space will not be counted, this solution is space O(1)
     */
    class Solution3 {
        public int[] productExceptSelf(int[] nums) {
            if (nums == null || nums.length == 0) return nums;

            int[] res = new int[nums.length];
            res[0] = 1;

            for (int i = 1; i < nums.length; i++) {
                res[i] = res[i - 1] * nums[i - 1];
            }

            int right = 1;
            for (int i = nums.length - 1; i >= 0; i--) {
                res[i] *= right;
                right *= nums[i];
            }

            return res;
        }
    }

    /**
     * use 2 extra arrays, 3 passes
     */
    class Solution2 {
        public int[] productExceptSelf(int[] nums) {
            int length = nums.length;

            int[] L = new int[length];
            int[] R = new int[length];

            // Final answer array to be returned
            int[] answer = new int[length];

            L[0] = 1;
            for (int i = 1; i < length; i++) {
                L[i] = nums[i - 1] * L[i - 1];
            }

            R[length - 1] = 1;
            for (int i = length - 2; i >= 0; i--) {
                R[i] = nums[i + 1] * R[i + 1];
            }

            for (int i = 0; i < length; i++) {
                answer[i] = L[i] * R[i];
            }

            return answer;
        }
    }

    /**
     * Required to minimize number of multiplication.
     *
     * Modified from Solution2, record index of 0 and process separately.
     */
    class Solution4 {
        public int[] productExceptSelf(int[] nums) {
            List<Integer> list = new ArrayList<>();

            int n = nums.length;
            int[] res = new int[n];

            res[0] = 1;
            for (int i = 1; i < nums.length; i++) {
                if (nums[i - 1] == 0) {
                    list.add(i - 1);
                } else {
                    res[i] = res[i - 1] * nums[i - 1];
                }
            }

            if (nums[n - 1] == 0) {
                list.add(n - 1);
            }

            if (list.size() > 1) {
                Arrays.fill(res, 0);
                return res;
            } else if (list.size() == 1) {
                int idx = list.get(0);
                int temp = res[idx];
                Arrays.fill(res, 0);
                res[idx] = temp;

                for (int i = idx + 1; i < n; i++) {
                    res[idx] *= nums[i];
                }

                return res;
            }

            int right = 1;
            for (int i = n - 1; i >= 0; i--) {
                res[i] *= right;
                right *= nums[i];
            }

            return res;
        }
    }
}
