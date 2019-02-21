package lintcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuank on 8/31/18.
 */
public class LI_209_First_Unique_Character_In_A_String {
    /**
     Find the first unique character in a given string. You can assume that there is at
     least one unique character in the string.

     Follow up
     What if it is in a data stream? (online)
     */

    public char firstUniqChar(String str) {
        int[] map = new int[256];
        char[] chars = str.toCharArray();

        for (char c : chars) {
            map[(int)c]++;
        }

        for (char c : chars) {
            if (map[(int)c] == 1) {
                return c;
            }
        }

        return '0';
    }

    /**
     * Solution for follow up (online)
     *
     * 实际上，用了3个数据结构，map, set, linked list
     *
     * linked list : chars appear only once, newer one added at the tail, so the head (dummy) node is the one
     *               that is unique and appeared first.
     * set : chars appear more than once.
     * map : record previous node for each node in linked list. Simulate a double linked list.
     *
     * By maintaining this logic, first unique char is always at the head of linked list (dummy.next.val)
     *
     * We can also use double linked list like in LRU to do this.
     *
     * Or use LinkedHashSet, refer to Solution 2 in LI_685_First_Unique_Number_In_Stream
     *
     */
    class ListCharNode {
        public char val;
        public ListCharNode next;
        public ListCharNode(char val) {
            this.val = val;
            this.next = null;
        }
    }

    class DataStream {
        /**
         * 1.save pointer to the previous node, simulate a double linked list
         * 2.also serves the purpose to check if the element exists in linked list
         *   in O(1).
         **/
        private Map<Character, ListCharNode> charToPrev;
        private Set<Character> dupChars; //save the char that appears more than once
        private ListCharNode dummy, tail;

        public DataStream() {
            charToPrev = new HashMap<>();
            dupChars = new HashSet<>();
            dummy = new ListCharNode('.');
            tail = dummy;
        }

        public void add(char c) {
            /**
             * #3.c appears more than or equal to 2 times
             *   It's in set, not in linked list. Do nothing
             * **/
            if (dupChars.contains(c)) {
                return;
            }

            /**
             *   #1.c appears for the very first time in the stream,
             *   it's not in linked list or set. Only add it to linked list.
             **/
            if (!charToPrev.containsKey(c)) {
                ListCharNode node = new ListCharNode(c);
                charToPrev.put(c, tail);
                tail.next = node;
                tail = node;
                return;
            }

            /**
             * #2.c appears for the 2nd time.
             *   It's in linked list, not in set, remove it from linked list and add it to set
             * **/
            ListCharNode prev = charToPrev.get(c);
            prev.next = prev.next.next;

            /**
             * !!!
             */
            if (prev.next == null) {
                // tail node removed
                tail = prev;
            } else {
                charToPrev.put(prev.next.val, prev);
            }

            charToPrev.remove(c);

            dupChars.add(c);
        }

        public char firstUniqueChar() {
            return dummy.next.val;
        }
    }

    /**
     * @param str: str: the given string
     * @return: char: the first unique character in a given string
     */
    public char firstUniqChar2(String str) {
        DataStream ds = new DataStream();
        for (int i = 0; i < str.length(); i++) {
            ds.add(str.charAt(i));
        }
        return ds.firstUniqueChar();
    }

}
