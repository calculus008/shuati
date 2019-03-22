package leetcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LE_384_Shuffle_An_Array {
    /**
     * Shuffle a set of numbers without duplicates.
     *
     * Example:
     *
     * // Init an array with set 1, 2, and 3.
     * int[] nums = {1,2,3};
     * Solution solution = new Solution(nums);
     *
     * // Shuffle the array [1,2,3] and return its result.
     * Any permutation of [1,2,3] must equally likely to be returned.
     * solution.shuffle();
     *
     * // Resets the array back to its original configuration [1,2,3].
     * solution.reset();
     *
     * // Returns the random shuffling of array [1,2,3].
     * solution.shuffle();
     */

    /**
     * Brutal Force
     * Time  : O(n ^ 2)
     * Space : O(n)
     */
    class Solution1 {
        private int[] array;
        private int[] original;

        private Random rand = new Random();

        private List<Integer> getArrayCopy() {
            List<Integer> asList = new ArrayList<Integer>();
            for (int i = 0; i < array.length; i++) {
                asList.add(array[i]);
            }
            return asList;
        }

        public Solution1(int[] nums) {
            array = nums;
            original = nums.clone();
        }

        public int[] reset() {
            array = original;
            original = original.clone();
            return array;
        }

        public int[] shuffle() {
            List<Integer> aux = getArrayCopy();

            for (int i = 0; i < array.length; i++) {
                int removeIdx = rand.nextInt(aux.size());
                array[i] = aux.get(removeIdx);
                aux.remove(removeIdx);
            }

            return array;
        }
    }

    /**
     * Fisher-Yates Algorithm
     * On each iteration of the algorithm, we generate a random integer between the current index
     * and the last index of the array. Then, we swap the elements at the current index and the
     * chosen index - this simulates drawing (and removing) the element from the hat, as the next
     * range from which we select a random index will not include the most recently processed one.
     * One small, yet important detail is that it is possible to swap an element with itself
     * - otherwise, some array permutations would be more likely than others.
     *
     * Time and Space : O(n)
     */
    class Solution2 {
        private int[] array;
        private int[] original;

        Random rand = new Random();

        private int randRange(int min, int max) {
            return rand.nextInt(max - min) + min;
        }

        private void swapAt(int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        public Solution2(int[] nums) {
            array = nums;
            original = nums.clone();
        }

        public int[] reset() {
            array = original;
            original = original.clone();
            return original;
        }

        public int[] shuffle() {
            for (int i = 0; i < array.length; i++) {
                swapAt(i, randRange(i, array.length));
            }
            return array;
        }
    }
}
