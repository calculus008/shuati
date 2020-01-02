package leetcode;

import java.util.Arrays;

public class LE_604_Design_Compressed_String_Iterator {
    /**
     * Design and implement a data structure for a compressed string iterator.
     * It should support the following operations: next and hasNext.
     *
     * The given compressed string will be in the form of each letter followed
     * by a positive integer representing the number of this letter existing in
     * the original uncompressed string.
     *
     * next() - if the original string still has uncompressed characters, return
     * the next letter; Otherwise return a white space.
     *
     * hasNext() - Judge whether there is any letter needs to be uncompressed.
     *
     * Note:
     * Please remember to RESET your class variables declared in StringIterator, as static/class variables are persisted across multiple test cases. Please see here for more details.
     *
     * Example:
     * StringIterator iterator = new StringIterator("L1e2t1C1o1d1e1");
     *
     * iterator.next(); // return 'L'
     * iterator.next(); // return 'e'
     * iterator.next(); // return 'e'
     * iterator.next(); // return 't'
     * iterator.next(); // return 'C'
     * iterator.next(); // return 'o'
     * iterator.next(); // return 'd'
     * iterator.hasNext(); // return true
     * iterator.next(); // return 'e'
     * iterator.hasNext(); // return false
     * iterator.next(); // return ' '
     *
     * Easy
     */

    class StringIterator1 {
        char[] chs;
        int idx;
        int count;
        char cur;

        public StringIterator1(String compressedString) {
            chs = compressedString.toCharArray();
            cur = chs[0];
            idx = 1;
            count = getCount();
        }

        public char next() {
            if (count == 0) {
                if (idx != chs.length) {
                    cur = chs[idx];
                    idx++;
                    count = getCount();
                } else {
                    return ' ';
                }
            }
            count--;
            return cur;
        }

        public boolean hasNext() {
            return !(count == 0 && idx == chs.length);
        }

        private int getCount() {
            int num = 0;
            while (idx < chs.length && Character.isDigit(chs[idx])) {
                num = num * 10 + chs[idx++] - '0';
            }
            return num;
        }
    }

    /**
     * Use java 8 stream to parse char and number into arr and counts
     */
    public class StringIterator2 {
        int idx;
        String[] arr;
        int[] counts;

        public StringIterator2(String str) {
            arr = str.split("\\d+");
            counts = Arrays.stream(str.substring(1).split("[a-zA-Z]+")).mapToInt(Integer::parseInt).toArray();
        }

        public char next() {
            if(!hasNext()) {
                return ' ';
            }

            char ch = arr[idx].charAt(0);
            if(--counts[idx] == 0) {
                ++idx;
            }

            return ch;
        }

        public boolean hasNext() {
            if(idx == arr.length) {
                return false;
            }

            return true;
        }
    }
}
