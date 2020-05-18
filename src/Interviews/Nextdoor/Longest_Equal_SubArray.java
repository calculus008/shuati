package Interviews.Nextdoor;

import java.util.HashMap;

public class Longest_Equal_SubArray {
    /**
     * Given an array containing only 0s and 1s, find the largest subarray which contain
     * equal no of 0s and 1s. Expected time complexity is O(n).
     * Examples:
     *
     * Input: arr[] = {1, 0, 1, 1, 1, 0, 0}
     * Output: 1 to 6 (Starting and Ending indexes of output subarray)
     *
     * Input: arr[] = {1, 1, 1, 1}
     * Output: No such subarray
     *
     * Input: arr[] = {0, 0, 1, 1, 0}
     * Output: 0 to 3 Or 1 to 4
     *
     * https://www.geeksforgeeks.org/largest-subarray-with-equal-number-of-0s-and-1s/
     */

    /**
     * Approach: The concept of taking cumulative sum, taking 0’s as -1 will help us in
     * optimising the approach. While taking the cumulative sum, there are two cases when
     * there can be a sub-array with equal number of 0’s and 1’s.
     *
     * One, when cumulative sum=0, which signifies that sub-array from index (0) till present
     * index has equal number of 0’s and 1’s.
     * Two, when we encounter a cumulative sum value which we have already encountered before,
     * which means that sub-array from the previous index+1 till the present index has equal
     * number of 0’s and 1’s as they give a cumulative sum of 0 .
     *
     * In a nutshell this problem is equivalent to finding two indexes i & j in array[] such
     * that array[i] = array[j] and (j-i) is maximum. To store the first occurrence of each
     * unique cumulative sum value we use a hash_map wherein if we get that value again we can
     * find the sub-array size and compare it with maximum size found till now.
     */
    public static int maxLen(int arr[], int n) {
        // Creates an empty hashMap hM
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

        int sum = 0; // Initialize sum of elements
        int max_len = 0; // Initialize result

        int ending_index = -1;
        int start_index = 0;

        for (int i = 0; i < n; i++) {
            arr[i] = (arr[i] == 0) ? -1 : 1;
        }

        // Traverse through the given array
        for (int i = 0; i < n; i++) {
            // Add current element to sum
            sum += arr[i];

            if (sum == 0) {
                max_len = i + 1;
                ending_index = i;
            }

//            if (map.containsKey(sum + n)) {
//                if (max_len < i - map.get(sum + n)) {
//                    max_len = i - map.get(sum + n);
//                    ending_index = i;
//                }
//            } else {// Else put this sum in hash table
//                map.put(sum + n, i);
//            }
            if (map.containsKey(sum)) {
                if (max_len < i - map.get(sum)) {
                    max_len = i - map.get(sum);
                    ending_index = i;
                }
            } else {// Else put this sum in hash table
                map.put(sum, i);
            }
        }

        for (int i = 0; i < n; i++) {
            arr[i] = (arr[i] == -1) ? 0 : 1;
        }

        int end = ending_index - max_len + 1;

        System.out.println(end + " to " + ending_index);

        return max_len;
    }

    public static void main(String[] args) {
        int arr[] = {1, 0, 0, 1, 0, 1, 1};
        int n = arr.length;

        System.out.println(maxLen(arr, n));
    }
}
