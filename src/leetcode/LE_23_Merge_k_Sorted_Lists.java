package leetcode;

import java.util.PriorityQueue;

/**
 * Created by yuank on 8/17/18.
 */
public class  LE_23_Merge_k_Sorted_Lists {
    /**
         Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.

         Example:

         Input:
         [
         1->4->5,
         1->3->4,
         2->6
         ]
         Output: 1->1->2->3->4->4->5->6

     * Interviews.Linkedin question and follow up
     *
     * 合并K个有序数组 - LI_486_Merge_K_Sorted_Arrays
     * 之后又问了一下时间复杂度, Should be N * Log(K)
     *
     * follow up 1，
     * 如果数据太大了放不下怎么办？
     * 答曰：输入输出都放文件，内存就放一个priority queue
     *
     * Divide the big data file into K smaller files,
     * each smaller file can fit into memory to be sorted,
     * then merge those K sorted files.
     * Time : O(nlogK)
     *
     * follow up 2：
     * 万一大到连priority queue也放不下怎么办？
     * 答曰：那就得用多个queue，两两merge，然后问了复杂度。
     *
     * follow up 3：
     * 如果再得提高速度怎么办。
     * 答曰：因为两两merge时不会相互影响，可以并行着处理
     */

    /**
     * A distributed sort/merge is very similar to a sort/merge on a single host.
     * The basic idea is to split the files among the separate hosts. Have each
     * host sort its individual files and then begin the merge operation that I
     * described in Divide key value pairs into equal lists without access to key
     * value counts. So each host has a priority queue containing the next item
     * from each of the files that it sorted.
     *
     * One of the hosts maintains a priority queue that contains the next item from
     * each of the other hosts. It selects the first one from that queue, outputs it,
     * and polls the host it came from for the next item, which it inserts into the
     * priority queue and continues.
     *
     * It's a priority queue of priority queues, distributed among multiple hosts.
     * Graphically, it looks something like this:
     *
     *    Host1            Host2             Host3            Host4
     * ------------------------------------------------------------------
     * F1 F2 F3 F4      F5 F6 F7 F8      F9 F10 F11 F12   F13 F14 F15 F16
     *  \  |  |  /      \  |  |  /       \   |   |  /      \   |   |  /
     *  ----------      ----------       ------------      ------------
     *     PQ1             PQ2               PQ3               PQ4
     *      \               \                /                 /
     *       \               \              /                 /
     *        \               \            /                 /
     *         \               \          /                 /
     *           ---------------\        /------------------
     *                           \      /
     *                            \    /
     *                             \  /
     *                              --
     *                           Master PQ
     *                        on primary host
     *
     *  Total data n is divided into k files. Now k files is further divided into
     *  l hosts. For each host, number of files : k / l, pq size : k / l,
     *  total data : n/k * k/l = n/l, total time : O(n/l * log(k/l)).
     *
     *  For l hosts, total Time complexity : O(l * n/l * log(k/l)) = O(n * log(k / l)),
     *  but they are processing in parallel.
     *
     *  On master, pq size is l, time : O(n * log(l))
     *
     *  Total : O(n * log(l) + n * log(k / l)) = O(n * (log(l) + log(k / l)))
     *
     * Now, it's highly inefficient to be requesting a single item at a time from
     * the individual hosts. The primary host could request, say, 1,000 items from
     * each host and hold them in individual buffers. Whenever a host's buffer runs out,
     * the primary host requests another buffer full from the host. That will reduce
     * the amount of network traffic.
     *
     * This also reduces I/O on the individual hosts: you never have to write the
     * combined files to disk. You sort the individual files and write them to disk as
     * described in my earlier answer, but then you begin the merge on the individual
     * hosts and send the items to the primary host that does the big merge.
     *
     * How Hadoop handles merge sort big data set:
     * https://stackoverflow.com/questions/3624384/sorting-large-data-using-mapreduce-hadoop
     * http://sortbenchmark.org/YahooHadoop.pdf
     */

    /**
     * Time : O(nlogk), k is the number of linked lists, n is total number of nodes
     *
     * The comparison cost will be reduced to O(logk) for every pop and insertion to priority queue.
     * But finding the node with the smallest value just costs O(1)time. There are n nodes in the final
     * linked list
     *
     * Space complexity :
     * O(n) : Creating a new linked list costs O(n) space.
     * O(k) :The code above present applies in-place method which cost O(1) space.
     *       And the priority queue (often implemented with heaps) costs O(k) space
     *       (it's far less than NN in most situations).
     * **/
    class Solution1 {
        public ListNode mergeKLists(ListNode[] lists) {
            if (lists == null || lists.length == 0) return null;
            PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.length, (a, b) -> a.val - b.val); //!!!"<>"

            /**
             * !!!
             */
            ListNode dummy = new ListNode(0);
            ListNode cur = dummy;

            for (ListNode list : lists) {
                if (list != null) {//!!!
                    pq.add(list);
                }
            }

            while (!pq.isEmpty()) {//!!!
                cur.next = pq.poll();
                cur = cur.next;
                if (cur.next != null) {
                    pq.add(cur.next);
                }
            }

            return dummy.next;
        }
    }

    /**
     * Time : O(kn)
     * Space : O(1)
     */
    class Solution2 {
        public ListNode mergeKLists(ListNode[] lists) {
            if (null == lists || lists.length == 0) {
                return null;
            }

            ListNode dummy = new ListNode(0);
            ListNode cur = dummy;

            int k = lists.length;
            while (true) {
                int min = Integer.MAX_VALUE;
                int minRow = -1;

                for (int i = 0; i < k; i++) {
                    if (lists[i] != null) {
                        if (min > lists[i].val) {
                            min = lists[i].val;
                            minRow = i;
                        }
                    }
                }

                if (minRow < 0) {
                    break;
                }

                cur.next = lists[minRow];
                cur = cur.next;
                lists[minRow] = lists[minRow].next;
            }

            return dummy.next;
        }
    }
}
