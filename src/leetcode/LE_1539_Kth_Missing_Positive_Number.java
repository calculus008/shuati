package leetcode;

public class LE_1539_Kth_Missing_Positive_Number {
    /**
     * Given an array arr of positive integers sorted in a strictly increasing order, and an integer k.
     *
     * Find the kth positive integer that is missing from this array.
     *
     * Example 1:
     * Input: arr = [2,3,4,7,11], k = 5
     * Output: 9
     * Explanation: The missing positive integers are [1,5,6,8,9,10,12,13,...]. The 5th missing positive integer is 9.
     *
     * Example 2:
     * Input: arr = [1,2,3,4], k = 2
     * Output: 6
     * Explanation: The missing positive integers are [5,6,7,...]. The 2nd missing positive integer is 6.
     *
     * Constraints:
     * 1 <= arr.length <= 1000
     * 1 <= arr[i] <= 1000
     * 1 <= k <= 1000
     * arr[i] < arr[j] for 1 <= i < j <= arr.length
     *
     * Easy
     */

    /**
     * Time : O(n)
     */
    class Solution1 {
        public int findKthPositive(int[] arr, int k) {
            /**
             * Check if the kth missing number is less than the first element of the array. If it's the case, return k
             */
            if (k < arr[0]) return k;

            /**
             * Decrease k by the number of positive integers which are missing before the array starts: k -= arr[0] - 1.
             */
            k -= arr[0] - 1;
            int n = arr.length;

            for (int i = 0; i < n - 1; i++) {
                /**
                 * At each step, compute the number of missing positive integers in-between i + 1th and ith elements: missing = arr[i + 1] - arr[i] - 1.
                 */
                int missing = arr[i + 1] - arr[i] - 1;

                /**
                 * Compare k to the currMissing. If k <= currMissing then the number to return is in-between arr[i + 1] and arr[i], and you could return it: arr[i] + k.
                 */
                if (k <= missing) {
                    return arr[i] + k;
                }

                /**
                 * decrease k by currMissing and proceed further.
                 */
                k -= missing;
            }

            /**
             * We're here because the element to return is greater than the last element of the array. Return it: arr[n - 1] + k.
             */
            return arr[n - 1] + k;
        }
    }

    /**
     * Another simple O(n) solution
     *
     * Key: "positive integers sorted in a strictly increasing order" -> meaning no duplicates!!!
     *
     * Suppose the array starts with 8, and you are told to find 5th missing element.
     * Now since array starts with 8 ,it means number 1 to 7 are missing.
     * so 5th missing element is 5 itself (because all numbers before 5 are missing)(1,2,3,4,5). similar is the case for
     * 1st to 7th missing element.
     *
     * Now what if the array starts with 2 i.e for ex [2,8] and ur told to find 5the missing element.
     * here 5 is not the answer, because all number before 5 are not missing, one number is found i.e 2, so the 5th number
     * is actually 6 (1,3,4,5,6) (5 incremented by 1 since one number is found)
     *
     * but what if array had a number <= 6 , ex 3 i.e [2,3,8] then 6 wont the the answer since one more number <=6 is
     * found, the missing numbers are [1,4,5,6,7] so again we increment 6 to 7.
     *
     * so we need to keep incrementing K unless all elements in array are less than K,
     * with this K shifts by one for each element <= K found in the list.
     */
    class Solution2 {
        public int findKthPositive(int[] arr, int k) {
            for(int i : arr){
                if(i <= k) {
                    k++;
                } else {
                    break;
                }
            }
            return k;
        }
    }

    /**
     * arr is sorted !!! -> Binary Search
     *
     * We need a way to check on how many positive integers are missing before the given array element to use binary search.
     * To do that, let's compare the input array [2, 3, 4, 7, 11] with an array with no missing integers: [1, 2, 3, 4, 5].
     * The number of missing integers is a simple difference between the corresponding elements of these two arrays:
     *
     * Before 2, there is 2 - 1 = 1 missing integer.
     * Before 3, there is 3 - 2 = 1 missing integer.
     * Before 4, there is 4 - 3 = 1 missing integer.
     * Before 7, there are 7 - 4 = 3 missing integers.
     * Before 11, there are 11 - 5 = 6 missing integers.
     *
     * The number of positive integers which are missing before the arr[idx] is equal to arr[idx] - idx - 1.
     *
     * While l <= r:
     * Choose the pivot index in the middle: m = l + (r - l) / 2
     * If the number of positive integers which are missing before is less than k arr[m] - m - 1 < k - continue to
     * search on the right side of the array: l = m + 1.
     * Otherwise, continue to search on the left: r = m - 1.
     *
     * At the end of the loop, l = r + 1, and the kth missing number is in-between arr[r] and arr[l].
     * The number of integers missing before arr[r] is arr[r] - r - 1.
     * Hence, the number to return is arr[r] + (k - (arr[r] - r - 1)) = k + (r + 1) = k + l.
     */
    class Solution3 {
        public int findKthPositive(int[] arr, int k) {
            int l = 0;
            int r = arr.length - 1;

            while (l <= r) {
                int m = l + (r - l) / 2;

                if (arr[m] - m - 1 < k) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }

            return l + k;
        }
    }

    /**
     * Another form of binary search
     *
     * If A[m] - 1 - m < k, m is too small, we update left = m.
     * If A[m] - 1 - m >= k, m is big enough, we update right = m.
     *
     * Note that, we exit the while loop, l = r,
     * which equals to the number of missing number used (!!! Between index 0 and index l, there are k missing numbers)
     * So the Kth positive number will be l + k. (l is index, not number itself !!!)
     *
     * Example:
     * [2,3,4,7,11], k = 5
     *  0 1 2 3 4
     * Between index 0 and index 4, there are 5 missing numbers and 4 not-missing numbers (numbers at index 0, 1, 2, 3),
     * so the 5th missing number is 4 + 5 = 9.
     *
     * In the range of [1, l+k], there are l numbers not missing, and k numbers are missing.
     */
    class Solution4 {
        public int findKthPositive(int[] A, int k) {
            int l = 0, r = A.length, m;

            while (l < r) {
                m = (l + r) / 2;
                if (A[m] - 1 - m < k)  {
                    l = m + 1;
                } else {
                    r = m;
                }
            }
            return l + k;
        }
    }
}
