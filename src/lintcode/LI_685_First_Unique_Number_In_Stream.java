package lintcode;

import java.util.*;

/**
 * Created by yuank on 9/10/18.
 */
public class LI_685_First_Unique_Number_In_Stream {
    /**
         Given a continuous stream of numbers, write a function that returns the first
         unique number whenever terminating number is reached(include terminating number).
         If there no unique number before terminating number
         or you can't find this terminating number, return -1.

         Example
         Given a stream [1, 2, 2, 1, 3, 4, 4, 5, 6] and a number 5
         return 3

         Given a stream [1, 2, 2, 1, 3, 4, 4, 5, 6] and a number 7
         return -1

         Medium
     */

    /**
     * A variation from LRU
     *
     * 对于任何一个num, 有3种状态
     * 1 从未出现过
     * 2 出现过一次
     * 3 出现过两次以上
     *
     * map和Dlist组合成一个数据结构，记录只出现过一次的数。通过map, 由num可以以O(1)找到对应的DListNode,
     * 这里用到Double Linked List特性，给定node, 可以在O(1)时间里删除或把该node加在tail处。
     * 同时，Dlist保留了出现一次数字的出现的先后顺序，Dlist的结构 ：
     *
     * head <=> node1 <=> node2 .... <=> nodeN <=> tail
     *
     * 如果没有数字只出现一次：
     *
     * head <=> tail
     *
     * 这样做的好处是在删除和加入的时候不用处理pre和next为null的情况。
     *
     * set记录出现过两次以上的数。
     */
    public class Solution1 {
        class DListNode {
            DListNode pre;
            DListNode next;
            int val;

            public DListNode(int val) {
                this.val = val;
            }
        }

        Map<Integer, DListNode> map;
        Set<Integer> set;
        DListNode head;
        DListNode tail;

        public int firstUniqueNumber(int[] nums, int number) {
            map = new HashMap<>();
            set = new HashSet<>();

            head = new DListNode(0);
            tail = new DListNode(0);
            head.next = tail;
            tail.pre = head;

            boolean found = false;

            for (int num : nums) {
                if (map.containsKey(num)) {
                    //num appeared only once before, then move it to set, remove it from map/list
                    set.add(num);
                    removeFromList(num);
                    map.remove(num);
                } else if(!set.contains(num)){//num appears in stream for the first time
                    DListNode cur = new DListNode(num);
                    addToTail(cur);
                    map.put(num, cur);
                }

                //else, num in set, appeared at least twice before, do nothing

                if (num == number) {
                    found = true;
                    break;
                }
            }

            //!!!"you can't find this terminating number, return -1"
            if (!found) return -1;

            return head.next == tail ? -1 : head.next.val;
        }

        private void removeFromList(int num) {
            DListNode node = map.get(num);
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }

        private void addToTail(DListNode cur) {
            cur.next = tail;
            cur.pre = tail.pre;
            tail.pre.next = cur;
            tail.pre = cur;
        }
    }


    /**
     * Solution 2 : use LinkedHashSet (Java impl of double linked list)
     * Use LinkedHashSet to keep track of unique elements and keep the order.
     * If uniques already has the number, remove it from uniques and add to dupes.
     * If dupes has the number, it means the number has been removed from uniques.
     * So I do nothing about it.Otherwise, the number is unique at the moment, add it to uniques.
     */
    public class Solution2 {
        /**
         * @param nums: a continuous stream of numbers
         * @param number: a number
         * @return: returns the first unique number
         */
        public int firstUniqueNumber(int[] nums, int number) {
            if (nums == null || nums.length == 0) {
                return -1;
            }

            Set<Integer> uniques = new LinkedHashSet<Integer>();
            Set<Integer> dupes = new HashSet<Integer>();
            boolean found = false;

            int i = 0;
            for (int num : nums) {
                if (uniques.contains(num)) {
                    uniques.remove(num);
                    dupes.add(num);
                } else {
                    if (!dupes.contains(num)) {
                        uniques.add(num);
                    }
                }

                if (num == number) {
                    found = true;
                    break;
                }
            }

            if (uniques.size() == 0 || !found) {
                return -1;
            }

            //!!!Get the first element in LinkedHashSet by using Iterator
            int res = 0;
            Iterator<Integer> itr = uniques.iterator();
            while (itr.hasNext()) {
                res = itr.next();
                break;
            }
            return res;
        }
    }
}
