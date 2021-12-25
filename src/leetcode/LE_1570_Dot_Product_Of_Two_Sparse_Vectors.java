package leetcode;

import java.util.*;

public class LE_1570_Dot_Product_Of_Two_Sparse_Vectors {
    /**
     * Given two sparse vectors, compute their dot product.
     *
     * Implement class SparseVector:
     *
     * SparseVector(nums) Initializes the object with the vector nums
     * dotProduct(vec) Compute the dot product between the instance of SparseVector and vec
     * A sparse vector is a vector that has mostly zero values, you should store the sparse vector efficiently and
     * compute the dot product between two SparseVector.
     *
     * Follow up: What if only one of the vectors is sparse?
     *
     * Example 1:
     * Input: nums1 = [1,0,0,2,3], nums2 = [0,3,0,4,0]
     * Output: 8
     * Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
     * v1.dotProduct(v2) = 1*0 + 0*3 + 0*0 + 2*4 + 3*0 = 8
     *
     * Example 2:
     * Input: nums1 = [0,1,0,0,0], nums2 = [0,0,0,0,2]
     * Output: 0
     * Explanation: v1 = SparseVector(nums1) , v2 = SparseVector(nums2)
     * v1.dotProduct(v2) = 0*0 + 1*0 + 0*0 + 0*0 + 0*2 = 0
     *
     * Example 3:
     * Input: nums1 = [0,1,0,0,2,0,0], nums2 = [1,0,0,0,3,0,4]
     * Output: 6
     *
     * Constraints:
     * n == nums1.length == nums2.length
     * 1 <= n <= 10^5
     * 0 <= nums1[i], nums2[i] <= 100
     *
     * Medium
     *
     * https://leetcode.com/problems/dot-product-of-two-sparse-vectors/
     */

    /**
     * Two Pointers + Design
     *
     * Index-Value Pairs
     */
    class SparseVector1 {
        public List<int[]> list;
        public int n;

        /**
         * O(n), n is length of nums
         */
        SparseVector1(int[] nums) {
            n = nums.length;
            list = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (nums[i] != 0) {
                    list.add(new int[]{i, nums[i]});
                }
            }
        }

        /**
         * O(L + L2)
         */
        public int dotProduct(SparseVector1 vec) {
            int i = 0, j = 0;
            List<int[]> list2 = vec.list;

            int l1 = list.size();
            int l2 = list2.size();

            if (l1 == 0 || l2 == 0) return 0;

            int res = 0;
            while (i < l1 && j < l2) {
                int[] v1 = list.get(i);
                int[] v2 = list2.get(j);

                if (v1[0] == v2[0]) {
                    res += v1[1] * v2[1];
                    i++;
                    j++;
                } else if (v1[0] < v2[0]) {
                    i++;
                } else {
                    j++;
                }
            }

            return res;
        }
    }

    /**
     * HashMap Solution
     */
    class SparseVector2 {
        /**
         * key - index, val - value of nums[index]
         */
        Map<Integer, Integer> map = new HashMap<>();

        SparseVector2(int[] nums) {
            for(int i = 0; i < nums.length; i++) {
                if(nums[i] != 0) {
                    map.put(i, nums[i]);
                }
            }
        }

        public int dotProduct(SparseVector2 vec) {
            if(map.size() == 0 || vec.map.size() == 0) return 0;

            //Optimize by switching whichever smaller HashMap
            if(map.size() > vec.map.size()) {
                return vec.dotProduct(this);
            }

            int sum = 0;
            for(Integer key : map.keySet()) {
                if(vec.map.containsKey(key)) {
                    sum += (map.get(key) * vec.map.get(key));
                }
            }
            return sum;
        }
    }
}
