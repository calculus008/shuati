package Interviews.Amazon;

import java.util.Arrays;

public class Make_Sorted_By_One_Swap {
    /**
     * https://www.geeksforgeeks.org/check-if-array-can-be-sorted-with-one-swap/
     *
     * Given an array containing N elements. Find if it is possible to sort it in
     * non-decreasing order using at most one swap.
     *
     * "借鉴这个思路 https://www.geeksforgeeks.org/ch ... rted-with-one-swap/
     * 假设需要升序的数组，用一个数组indices存index i where nums > nums[i+1]
     * 然后检查这个数组，如果只有1个元素，那么swap(nums, nums[i+1])，看是否有序
     * 如果有2个元素i, j， 那么swap[nums, nums[i+1]], 看是否有序
     * 如果有多于2个元素，若indices中的元素均为consecutive，那么翻转nums[indices[0], indices.back() + 2]，看是否有序"
     *
     * Examples:
     *
     * Input : arr[] = {1, 2, 3, 4}
     * Output : YES
     * The array is already sorted
     *
     * Input : arr[] = {3, 2, 1}
     * Output : YES
     * Swap 3 and 1 to get [1, 2, 3]
     *
     * Input : arr[] = {4, 1, 2, 3}
     * Output :NO
     */
    static boolean checkSorted(int n, int arr[]) {
        /**
         * Find counts and positions of elements that are out of order.
         */
        int first = 0, second = 0;
        int count = 0;

        for (int i = 1; i < n; i++) {
            if (arr[i] < arr[i - 1]) {
                count++;

                if (first == 0) {
                    first = i;
                } else {
                    second = i;
                }
            }
        }

        if (count > 2) return false;

        /**
         * If all elements are sorted already
         **/
        if (count == 0) return true;

        /**
         * Cases like {1, 5, 3, 4, 2}, we swap 5 and 2.
         **/
        if (count == 2) {
            swap(arr, first - 1, second);
        } else if (count == 1) {// Cases like {1, 2, 4, 3, 5}
            swap(arr, first - 1, first);
        }

        /**
         * Now check if array becomes sorted for cases like {4, 1, 2, 3}
         **/
        for (int i = 1; i < n; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }

        return true;
    }

    static int[] swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        return arr;
    }

    static void swap1(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void reverse(int[] arr, int i, int j) {
        while (i < j) {
            swap1(arr, i, j);
//            System.out.println(Arrays.toString(arr));

            i++;
            j--;
        }
    }


    // Driver Code
    public static void main(String[] args) {
        int arr[] = {2, 1, 3};
        int arr1[] = {1, 5, 3, 4, 2};
        int arr2[] = {1, 6, 5, 4, 3, 2, 7, 8};
        int arr3[] = {1, 4, 3, 2};
        int arr4[] = {1, 4, 3, 2, 5, 6, 10, 7};


//        int n = arr.length;
//        if (checkSorted(n, arr))
//            System.out.println("Yes");
//        else
//            System.out.println("No");

        System.out.println(checkSorted1(arr));
        System.out.println(checkSorted1(arr1));
        System.out.println(checkSorted1(arr2));
        System.out.println(checkSorted1(arr3));
        System.out.println(checkSorted1(arr4));

    }

    /**
     * 给一个partially sorted array, 判断是否能做一次swap变成有序(返回1)，或者一次翻转子array变成有序(返回2)，如果不能就返回0。
     *
     * 假设需要升序的数组，用一个数组indices存index i where nums > nums[i+1]
     *
     * #1.One Swap
     * 然后检查这个数组，如果只有1个元素，那么swap(nums, nums[i+1])，看是否有序
     * 如果有2个元素i, j， 那么swap[nums, nums[i+1]], 看是否有序
     *
     * #2.Reverse subarray
     * 如果有多于2个元素，若indices中的元素均为consecutive，那么翻转nums[indices[0], indices.back() + 2]，看是否有序
     */
    static int checkSorted1(int arr[]) {
        /**
         * Find counts and positions of elements that are out of order.
         */
        int first = -1, second = 0, last = 0;
        int count = 0;

        int n = arr.length;

        boolean[] isReversed = new boolean[n];

        for (int i = 0; i < n - 1 ; i++) {
            if (arr[i] > arr[i + 1]) {
                isReversed[i] = true;
                count++;

                if (first == -1) {
                    first = i;
                } else {
                    second = i;
                }

                last = i;
            }
        }

        System.out.println("count=" + count + ", first=" + first + ", second=" + second + ", last=" + last);
        if (count > 0 && count <= 2) {
            if (count == 1) {
                swap(arr, first, first + 1);
            } else if (count == 2) {
                swap(arr, first, second + 1); //Cases like {1, 5, 3, 4, 2}, we swap 5 and 2.
            }
            System.out.println(Arrays.toString(arr));

            if (isSorted(arr)) return 1;
        } else if (count > 2) {
//            System.out.println(Arrays.toString(isReversed));

            if (isConsecutive(arr, isReversed, first, last)) {
                reverse(arr, first, last + 1);//!!! last + 1
            }
            System.out.println(Arrays.toString(arr));

            if (isSorted(arr)) return 2;
        }



        return -1;
    }

    /**
     * Now check if array becomes sorted for cases like {4, 1, 2, 3}
     **/
    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }
        return true;
    }

    private static boolean isConsecutive(int[] arr, boolean[] isReversed, int first, int last) {
        for (int i = first; i <= last; i++) {
            if (!isReversed[i]) return false;
        }

        return true;
    }
}
