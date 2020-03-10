package src.Interviews.Indeed.Practice;

public class Unroll_Linked_List_Practice {
    class Node {
        int len;
        char[] chs;
        Node next;

        public Node() {
            chs = new char[5];
        }
    }

    final int MAX_LEN= 5;
    int totalLength;
    Node head;

    class LinkedList {
        Node head;
        int totalLength;

        public LinkedList() {}

        public LinkedList(Node head, int total) {
            this.head = head;
            this.totalLength = total;
        }
    }

    public char get(int idx) {
        if (idx < 0 || idx >= totalLength) return ' ';

        Node cur = head;
        while (cur != null && idx >= 0) {
            if (idx >= cur.len) {
                idx -= cur.len;
            } else {
                return cur.chs[idx];
            }
        }

        return ' ';
    }

    public void insert(char c , int idx) {
        if (idx < 0 || idx > totalLength) return;

        Node cur = head;
        Node pre = null;
        while (cur != null) {
            if (idx >= cur.len) {
                idx -= cur.len;

                if (idx == 0 && cur.len < MAX_LEN) {
                    idx = cur.len;
                    break;
                }
            } else {
                break;
            }

            /**
             * !!!
             */
            pre = cur;
            cur = cur.next;
        }

        if (cur == null) {
            Node newNode = new Node();

            if (head == null) {
                head = newNode;
            } else {
                pre.next = newNode;
            }

            /**
             * !!!
             */
            cur = newNode;
        }

        if (cur.len == MAX_LEN) {
            if (cur.next == null || cur.next.len == MAX_LEN) {
                Node n = new Node();
                n.next = cur.next;
                cur.next = n;
            }

            Node next = cur.next;

            /**
             * !!!
             */
            next.len++;
            for (int i = next.len - 1; i >= 1; i--) {
                next.chs[i] = next.chs[i - 1];
            }
            next.chs[0] = cur.chs[cur.len - 1];
            /**
             * !!!
             */
            cur.len--;
        }

        /**
         * !!!
         */
        cur.len++;
        for (int i = cur.len - 1; i > idx; i--) {
            cur.chs[i] = cur.chs[i - 1];
        }
        cur.chs[idx] = c;
        totalLength++;
    }

    public void delete(int idx) {
        if (idx < 0 || idx >= totalLength) return;

        Node cur = head;
        Node pre = null;

        while (cur != null && idx >= 0) {
            if (idx >= cur.len) {
                idx -= cur.len;
            } else {
                break;
            }

            pre = cur;
            cur = cur.next;
        }

        if (cur == null) return;

        if (pre != null && pre.len + cur.len - 1 <= MAX_LEN) {
            for (int i = 0; i < cur.len; i++) {
                if (i == idx) continue;

                pre.chs[pre.len++] = cur.chs[i];
            }

            cur.len = 1;
        }

        /**
         * !!!
         */
        cur.len--;
        totalLength--;

        if (cur.len == 0) {
            if (pre != null) {
                pre.next = cur.next;
            } else {
                head = cur.next;
            }
        } else {
            /**
             * !!!
             * "i < cur.len;"
             */
            for (int i = idx; i < cur.len; i++) {
                cur.chs[i] = cur.chs[i + 1];
            }
        }
    }
}
