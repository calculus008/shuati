package leetcode;

public class LE_706_Design_HashMap {
    /**
     * Design a HashMap without using any built-in hash table libraries
     *
     * Easy
     */

    class MyHashMap {
        class ListNode {
            int key;
            int val;
            ListNode next;

            ListNode(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        final ListNode[] nodes = new ListNode[10000];

        public void put(int key, int value) {
            int i = getIdx(key);
            if (nodes[i] == null) {
                nodes[i] = new ListNode(-1, -1);
            }

            ListNode prev = find(nodes[i], key);

            if (prev.next == null) {
                prev.next = new ListNode(key, value);
            } else {
                prev.next.val = value;
            }
        }

        public int get(int key) {
            int i = getIdx(key);
            if (nodes[i] == null) {
                return -1;
            }

            ListNode node = find(nodes[i], key);

            return node.next == null ? -1 : node.next.val;
        }

        public void remove(int key) {
            int i = getIdx(key);
            if (nodes[i] == null) {
                return;
            }

            ListNode prev = find(nodes[i], key);
            if (prev.next == null) {
                return;
            }

            prev.next = prev.next.next;
        }

        int getIdx(int key) {
            return Integer.hashCode(key) % nodes.length;
        }//or return key % nodes.length;

        ListNode find(ListNode bucket, int key) {
            ListNode node = bucket, prev = null;
            while (node != null && node.key != key) {
                prev = node;
                node = node.next;
            }
            return prev;
        }

    }
}
