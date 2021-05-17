package leetcode;

public class LE_900_RLE_Iterator {
    /**
     * Write an iterator that iterates through a run-length encoded sequence.
     *
     * The iterator is initialized by RLEIterator(int[] A), where A is a run-length encoding of some sequence.
     * More specifically, for all even i, A[i] tells us the number of times that the non-negative integer
     * value A[i+1] is repeated in the sequence.
     *
     * The iterator supports one function: next(int n), which exhausts the next n elements (n >= 1) and returns
     * the last element exhausted in this way.  If there is no element left to exhaust, next returns -1 instead.
     *
     * For example, we start with A = [3,8,0,9,2,5], which is a run-length encoding of the sequence [8,8,8,5,5].
     * This is because the sequence can be read as "three eights, zero nines, two fives".
     *
     * Example 1:
     *
     * Input: ["RLEIterator","next","next","next","next"], [[[3,8,0,9,2,5]],[2],[1],[1],[2]]
     * Output: [null,8,8,5,-1]
     * Explanation:
     * RLEIterator is initialized with RLEIterator([3,8,0,9,2,5]).
     * This maps to the sequence [8,8,8,5,5].
     * RLEIterator.next is then called 4 times:
     *
     * .next(2) exhausts 2 terms of the sequence, returning 8.  The remaining sequence is now [8, 5, 5].
     *
     * .next(1) exhausts 1 term of the sequence, returning 8.  The remaining sequence is now [5, 5].
     *
     * .next(1) exhausts 1 term of the sequence, returning 5.  The remaining sequence is now [5].
     *
     * .next(2) exhausts 2 terms, returning -1.  This is because the first term exhausted was 5,
     * but the second term did not exist.  Since the last term exhausted does not exist, we return -1.
     *
     * Note:
     * 0 <= A.length <= 1000
     * A.length is an even integer.
     * 0 <= A[i] <= 10^9
     * There are at most 1000 calls to RLEIterator.next(int n) per test case.
     * Each call to RLEIterator.next(int n) will have 1 <= n <= 10^9.
     *
     * Medium
     */

    /**
     * idx : current index we are at in data[]
     * count : how many items we have consumed by now in current position.(!!!)
     */
    class RLEIterator {
        int idx;
        int count;
        int[] data;

        public RLEIterator(int[] A) {
            idx = 0;
            count = 0;
            data = A;
        }

        public int next(int n) {
            /**
             * It's intuitive to check if we have run out current elements, if yes, we move to the next one.
             * But here, we need to go on until we find the answer or hit the end of the array,
             * this strongly suggests the logic above should be contained in a while loop within the array range.
             *
             * While keep iterating in while loop, actually all 3 elements must be updated:
             * idx : move to the next one (+2)
             * count : we move to a new element, therefore, we consume nothing yet, so it should be set to 0.
             * n : the number of elements we already consumed for current element :
             *     data[idx] - count (count is what has consumed for current item!!!)
             *     So we still need to get : n - (data[idx] - n)
             */
            while (idx < data.length) {//!!!
                if (count + n > data[idx]) {
                    n -= data[idx] - count;//!!!
                    count = 0;
                    idx += 2;
                } else {
                    count += n;
                    return data[idx + 1];
                }
            }

            return -1;
        }
    }
}
