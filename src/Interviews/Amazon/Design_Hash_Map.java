package Interviews.Amazon;

public class Design_Hash_Map {
    // Implement a hash map that supports the following operations: put, get, delete
    class MyHashMap<k, v>{
        class ListNode <k, v>{
            k key;
            v val;
            ListNode next;

            public ListNode(k key, v val) {
                this.key = key;
                this.val = val;
            }
        }

        final ListNode[] nodes = new ListNode[100];
        // ArrayList

        public void put(k key, v value) {
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

        public v get(k key) {
            int i = getIdx(key);
            if (nodes[i] == null) {
                return null;
            }

            ListNode node = find(nodes[i], key);

            return node.next == null ? null : (v)node.next.val;
        }

        public void delete(k key) {
            int i = getIdx(key);
            if (nodes[i] == null) {
                return;
            }

            ListNode prev = find(nodes[i], key);
            if (prev.next == null) {
                return;
            }
            //prev.next is the node we want to delete
            //we just make prev points to cur.next
            prev.next = prev.next.next;
            /**
             * !!!
             */
            prev.next.next = null;
        }

        private int getIdx(k key) {
//            return Object.hashCode(key) % nodes.length;
            return 1;
        }

        private ListNode find(ListNode bucket, k key) {
            ListNode  node = bucket;
            ListNode prev = null;

            while (node != null && node.key.equals(key)) {
                prev = node;
                node = node.next;
            }

            return prev;
        }
    }

}
