package lintcode;

import common.ListNode;

/**
 * Created by yuank on 7/11/18.
 */
public class LI_228_Middle_Of_Linked_List {
    /**
         Find the middle node of a linked list.

         Example
         Given 1->2->3, return the node with value 2.

         Given 1->2, return the node with value 1.

         Challenge
         If the linked list is in a data stream, can you find the middle without iterating the linked list again?

         Easy
     */

    /**
         问题分析
         这个问题可能大家会觉得，WTF 这么简单有什么好做的？你可能的想法是：

         先遍历一下整个链表，求出长度 L，然后再遍历一下链表找到第 L/2 的那个位置的节点。

         但是在你抛出这个想法之后，面试官会追问你：如果只允许遍历链表一次怎么办？

         可以看到这种 Follow up 并不是让你优化算法的时间复杂度，而是严格的限制了你遍历整个链表的次数。
         你可能会认为，这种优化有意义么？事实上是很有意义的。因为遍历一次这种场景，在真实的工程环境中会经常遇到，
         也就是我们常说的数据流问题（Data Stream Problem）。

         数据流问题 Data Stream Problem
         所谓的数据流问题，就是说，你需要设计一个在线系统，这个系统不断的接受一些数据，并维护这些数据的一些信息。
         比如这个问题就是在数据流中维护中点在哪儿。（维护中点的意思就是提供一个接口，来获取中点）

         类似的一些数据流问题还有：

         数据流中位数 http://www.lintcode.com/problem/data-stream-median/
         数据流最大 K 项 http://www.lintcode.com/problem/top-k-largest-numbers-ii/
         数据流高频 K 项 http://www.lintcode.com/problem/top-k-frequent-words-ii/
         这类问题的特点都是，你没有机会第二次遍历所有数据。上述问题部分将在《九章算法强化班》中讲解。

         用双指针算法解决链表中点问题
         我们可以使用双指针算法来解决链表中点的问题，更具体的，我们可以称之为快慢指针算法。该算法如下：

         ListNode slow = head, fast = head.next;
         while (fast != null && fast.next != null) {
         slow = slow.next;
         fast = fast.next.next;
         }

         return slow;

         在上面的程序中，我们将快指针放在第二个节点上，慢指针放在第一个节点上，while 循环中每一次快指针走两步，慢指针走一步。这样当快指针走到头的时候，慢指针就在中点了。

         快慢指针的算法，在下一小节的“带环链表”中，也用到了。

         一个小练习
         将上述代码改为提供接口的模式，即设计一个 class，支持两个函数，一个是 add(node) 加入一个节点，一个是 getMiddle() 求中间的那个节点。
     */

    public ListNode middleNode(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null && fast.next != null ) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
}
