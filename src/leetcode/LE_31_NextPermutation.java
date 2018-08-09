package leetcode;

/**
 * Created by yuank on 2/26/18.
 */
public class LE_31_NextPermutation {
    /**
    Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.

    If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).

    The replacement must be in-place, do not allocate extra memory.

    Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
    1,2,3 → 1,3,2
    3,2,1 → 1,2,3
    1,1,5 → 1,5,1
     */

    /**
        we observe that for any given sequence that is in descending order, no next larger permutation is possible.
        For example, no next permutation is possible for the following array: [9, 5, 4, 3, 1].

        We need to find the first pair of two successive numbers a[i] and a[i−1], from the right, which satisfy
        a[i]>a[i−1]. Now, no rearrangements to the right of a[i−1] can create a larger permutation since that
        subarray consists of numbers in descending order. Thus, we need to rearrange the numbers to the right
        of a[i−1] including itself.

        To find the next permutation, we have to swap some numbers at different positions, to minimize the increased amount,
        we have to make the highest changed position as high as possible. Notice that index larger than or equal to i is not
        possible as num[i,n-1] is reversely sorted. So, we want to increase the number at index i-1, clearly, swap it with
        the smallest number between num[i,n-1] that is larger than num[i-1]. For example, original number is 121543321,
        we want to swap the '1' at position 2 with '2' at position 7.

        Example :
        1 2 1 5 4 3 3 2 1

        After the first while loop:
        1 2 1 5 4 3 3 2 1
            * i

        After the second while loop:
        1 2 1 5 4 3 3 2 1
            * i       j

        Swap nums[i-i] and nums[j]:
        1 2 2 5 4 3 3 1 1
            * i       j

        Reverse from i - result , the smallest number bigger than the given number
        1 2 2 1 1 3 3 4 5
              i       j

        https://leetcode.com/problems/next-permutation/solution/
     **/

    public int[] nextPermutation(int[] nums) {
        if (nums == null) return new int[]{};

        if (nums.length <= 1) {
            return nums;
        }

        int len = nums.length;
        int i = len - 1;
        while (i > 0 && nums[i] <= nums[i - 1]) {
            i--;
        }

        if (i != 0) {
            int j = len - 1;
            while (nums[j] <= nums[i - 1]) {
                j--;
            }

            /**
             !!! swap index i - 1 and j, not i and j
             swap it withthe smallest number between num[i,n-1] that is larger than num[i-1].
             For example, original number is 121543321, we want to swap the '1' at position 2 with '2' at position 7.
                                               *    *
             **/
            swap(nums, i - 1, j);
        }

        //!!! reverse has to be here, outside the if block since it will execute for case that no swap requires, for example "54321"
        //!!! Reverse section starts at index i, not reverse the entire nums
        reverse(nums, i, len - 1);
        return nums;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int i, int j) {
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

//    public void nextPermutation(int[] nums) {
//        int i = nums.length - 2;
//        while (i >= 0 && nums[i + 1] <= nums[i]) {
//            i--;
//        }
//        if (i >= 0) {
//            int j = nums.length - 1;
//            while (j >= 0 && nums[j] <= nums[i]) {
//                j--;
//            }
//            swap(nums, i, j);
//        }
//        reverse(nums, i + 1);
//    }
//
//    private void reverse(int[] nums, int i) {
//        int j = nums.length-1;
//        while(i<j) {
//            swap(nums, i, j);
//            i++;
//            j--;
//        }
//    }
//
//    private void swap(int[] nums, int i, int j) {
//        int temp = nums[i];
//        nums[i] = nums[j];
//        nums[j] = temp;
//    }
}
