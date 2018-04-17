package leetcode;

/**
 * Created by yuank on 3/7/18.
 */
public class LE_86_Partition_List {
    /*
        Given a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.

        You should preserve the original relative order of the nodes in each of the two partitions.

        For example,
        Given 1->4->3->2->5->2 and x = 3,
        return 1->2->2->4->3->5.
     */

    public ListNode partition(ListNode head, int x) {
        if (head == null)
            return null;

        //用两个LIST分别放大的和小的数，因为最后要连接两个LIST，因此需要知道每个LIST的一头(head1.next)一尾(dummy1)。
        ListNode dummy1 = new ListNode(0);
        ListNode dummy2 = new ListNode(0);

        ListNode head1 = dummy1;
        ListNode head2 = dummy2;

        ListNode runner = head;

        while (runner != null) {
            if (runner.val < x) {
                dummy1.next = runner;
                dummy1 = dummy1.next;
            } else {
                dummy2.next = runner;
                dummy2 = dummy2.next;
            }
            runner = runner.next;
        }

        //!!!
        dummy2.next = null;

        dummy1.next = head2.next;

        //!!!注意：最后的结果是head1指向的下一个节点
        return head1.next;

    }
}
