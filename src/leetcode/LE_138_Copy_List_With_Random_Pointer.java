package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuank on 3/17/18.
 */
public class LE_138_Copy_List_With_Random_Pointer {
    /**
        A linked list is given such that each node contains an additional random pointer
        which could point to any node in the list or null.

        Return a deep copy of the list.
     */

    //Time and Space : O(n)
    public RandomListNode copyRandomList1(RandomListNode head) {
        if (head == null) return head;

        Map<RandomListNode, RandomListNode> map = new HashMap<>();
        RandomListNode cur = head;
        while (cur != null) {
            map.put(cur, new RandomListNode(cur.label));
            cur = cur.next;
        }

        cur = head;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }

        return map.get(head);
    }

    //copy and split
    public RandomListNode copyRandomList2(RandomListNode head) {
        if(null == head)
            return null;

        /**
         * 1.copy node
         */
        RandomListNode cur = head;
        while(null != cur) {
            RandomListNode newNode = new RandomListNode(cur.label);
            newNode.next = cur.next;
            cur.next = newNode;
            cur = newNode.next;
        }

        /**
         * 2.point random pointer to copied node
         */
        cur = head;
        RandomListNode copiedNode;
        while(null != cur) {
            copiedNode = cur.next;
            copiedNode.random = (null == cur.random ? null : cur.random.next);
            cur = copiedNode.next;
        }

        /**
         * 3.split
         */
        cur = head;
        RandomListNode newHead = cur.next;
        RandomListNode newNode;
        while(null != cur) {
            newNode = cur.next;
            cur.next = newNode.next;

            newNode.next = null == cur.next?null:cur.next.next;

            cur = cur.next;
            newNode = newNode.next;
        }

        return newHead;
    }

    public RandomListNode copyRandomList_practice(RandomListNode head) {
        if (head == null) return null;

        RandomListNode cur = head;
        while (cur != null) {
            RandomListNode newNode = new RandomListNode(cur.label);
            newNode.next = cur.next;
            cur.next =  newNode;
            cur = newNode.next;
        }

        cur = head;
        while (cur != null) {
            if (cur.random != null) {
                cur.next.random = cur.random.next;
            }
            cur = cur.next.next;
        }

        cur = head;
        RandomListNode newHead = cur.next;
        RandomListNode newNode;
        while (cur != null) {
            newNode = cur.next;
            cur.next = newNode.next;
            newNode.next = cur.next == null ? null : cur.next.next;
            cur = cur.next;
        }

        return newHead;
    }
}
