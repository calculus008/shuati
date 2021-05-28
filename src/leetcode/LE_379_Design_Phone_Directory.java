package leetcode;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class LE_379_Design_Phone_Directory {
    /**
     * Design a phone directory that initially has maxNumbers empty slots that can store numbers. The directory should
     * store numbers, check if a certain slot is empty or not, and empty a given slot.
     *
     * Implement the PhoneDirectory class:
     *
     * PhoneDirectory(int maxNumbers) Initializes the phone directory with the number of available slots maxNumbers.
     * int get() Provides a number that is not assigned to anyone. Returns -1 if no number is available.
     * bool check(int number) Returns true if the slot number is available and false otherwise.
     * void release(int number) Recycles or releases the slot number.
     *
     * Example 1:
     * Input
     * ["PhoneDirectory", "get", "get", "check", "get", "check", "release", "check"]
     * [[3], [], [], [2], [], [2], [2], [2]]
     * Output
     * [null, 0, 1, true, 2, false, null, true]
     *
     * Explanation
     * PhoneDirectory phoneDirectory = new PhoneDirectory(3);
     * phoneDirectory.get();      // It can return any available phone number. Here we assume it returns 0.
     * phoneDirectory.get();      // Assume it returns 1.
     * phoneDirectory.check(2);   // The number 2 is available, so return true.
     * phoneDirectory.get();      // It returns 2, the only number that is left.
     * phoneDirectory.check(2);   // The number 2 is no longer available, so return false.
     * phoneDirectory.release(2); // Release number 2 back to the pool.
     * phoneDirectory.check(2);   // Number 2 is available again, return true.
     *
     *
     * Constraints:
     * 1 <= maxNumbers <= 104
     * 0 <= number < maxNumbers
     * At most 2 * 104 calls will be made to get, check, and release.
     *
     * Medium
     */

    /**
     * At first look, it seems that you can just use HashMap as internal data structure. It works well for release() and
     * check(), but get() does not work, how do you keep tracking what is the next available unit? So it requires some
     * different data structure.
     */

    /**
     * Strict O(1) solution
     * Use a pointer "next" to mark the next new number (never released), and a HashSet to collect all recycled numbers.
     * The get() method will look up first in the recycling set rather than assign a brand new number in bank. No need
     * to "initialize" any collection or array, so O(1) is garanteed.
     */
    class PhoneDirectory1 {
        int max;
        int next;
        Set<Integer> set;

        /** Initialize your data structure here
         @param maxNumbers - The maximum numbers that can be stored in the phone directory.
         */
        public PhoneDirectory1(int maxNumbers) {
            max = maxNumbers;
            next = 0;
            set = new HashSet<>();
        }

        /** Provide a number which is not assigned to anyone.
         @return - Return an available number. Return -1 if none is available.
         */
        public int get() {
            if (set.size() > 0) {
                Iterator<Integer> it = set.iterator();
                int ret = it.next();
                it.remove();
                return ret;
            }

            if (next < max) {
                int ret = next;
                next++;
                return ret;
            }

            return -1;
        }

        /** Check if a number is available or not. */
        public boolean check(int number) {
            return isValid(number) && !isTaken(number);
        }

        /** Recycle or release a number. */
        public void release(int number) {
            if (isValid(number) && isTaken(number)) {
                set.add(number);
            }
        }

        /**
         * !!!
         * Must check if the number is a valid one
         */
        private boolean isValid(int number) {
            return number >= 0 && number < max;
        }

        private boolean isTaken(int number) {
            return number < next && !set.contains(number);
        }
    }

    /**
     * https://leetcode.com/problems/design-phone-directory/discuss/122908/Java-O(1)-time-o(n)-space-single-Array-99ms-beats-100
     * https://leetcode.com/problems/design-phone-directory/discuss/566643/Java-Free-List-approach.-O(1)-timebeats-100.
     *
     * Array
     * It uses the idea of a Free List : https://en.wikipedia.org/wiki/Free_list
     * We have allocated pool of numbers of given size. Each free number in array points to next free number (emulating linked list inside array).
     *
     * Initialization takes O(n), if you are asked to implement using an array, this should be the solution.
     * It seems that this problem is designed for using Free List.
     *
     * Here the index value (0 ~ maxNumbers - 1) can be deemed as "address", next[i] is the value that is stored at mem block with address "i",
     * we make it point to the next available free mem block. For example : next[2] = 3, mem block at address "2" contains the address of next
     * available free mem bock - 3. Once a mem block is allocated, we set its value to -1. "pos" is the pointer that points to the head of the
     * "linked list" of free mem blocks.
     *
     * get():
     * 1.Check if there's still free mem block. That is : if next[pos] == -1. If no free mem, return -1.
     * 2.We still have free mem block, its address is pos.
     *   Remember the address, save it in "ret" so we will return later :        "int ret = pos;"
     *   Move pos so it points to the next free mem block in the "linked list":  "pos = next[pos];"
     *   Flag the allocated mem block as taken :                                 "next[ret] = -1;"
     *
     * release(int number):
     * 1.Check if number in valid range and if it is already taken.
     * 2.Release:
     *   Think it as putting the released mem block to the front of the "linked list" by let it point to the head:
     *      next[number] = pos;
     *   Set the head of the "linked list" to this mem block (make it the head)
     *      pos = number;
     */
    class PhoneDirectory2 {
        int[] next;
        int pos;
        int max;

        public PhoneDirectory2(int maxNumbers) {
            max = maxNumbers;
            next = new int[max];
            for (int i = 0; i < max; i++) {
                /**
                 * "0 <= number < maxNumbers"
                 * so when i = maxNumbers - 1, next[maxNumbers - 1] can't point to index maxNumbers, since it is
                 * out of the index range. Instead, using mod, we let it point to index 0. If we keep doing get()
                 * until the last one (maxNumbers - 1), it points to index 0, by this time, numbers[0] = -1, meaning it
                 * is already allocated, so get() returns -1.
                 */
                next[i] = (i + 1) % max;
            }
            pos = 0;
        }

        public int get() {
            if (next[pos] == -1) return -1;

            int ret = pos;
            pos = next[pos];
            next[ret] = -1;

            return ret;
        }

        public boolean check(int number) {
            return isValid(number) && next[number] != -1;
        }

        public void release(int number) {
            if (!isValid(number) || next[number] != -1) return;

            next[number] = pos;
            pos = number;
        }

        private boolean isValid(int number) {
            return number >= 0 && number < max;
        }
    }
}
