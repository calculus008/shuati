package Interviews.Indeed;

import java.util.Arrays;

public class Unroll_Linked_List {
    /**
     * Given a LinkedList, every node contains a array. Every element of the array is char
     * implement two functions
     * 1. get(int index) find the char at the index
     * 2. insert(char ch, int index) insert the char to the index
     *
     * * class Node {
     * *         char[] chars = new char[5]; //固定大小5
     * *         int len;
     * * }
     * * class LinkedList {
     * *         Node head;
     * *         int totalLen;
     * * }
     *
     *
     */
    static class Node {
        char[] chars = new char[5];
        int len;

        Node next;

        public Node() {
        }
    }

    /**
     * Purpose of this data structure is to improve get, insert and delete time complexity
     * while keep random access property of array.
     *
     * n : number elements in list
     * size : average size of array in each node, upper bound is 5 here.
     *
     * Instead of O(n) for insert/delete in pure array:
     *
     * delete/insert : O(n / size + size)
     * get : O(n / size)
     */
    static class LinkedList {
        Node head;
        int totalLen;

        final int MAX_ARRAY_SIZE = 5;

        public LinkedList() {

        }

        public LinkedList(Node head, int total) {
            this.head = head;
            this.totalLen = total;

            Node cur = head;
            int count = 0;
            while (cur != null) {
                count += cur.len;
                cur = cur.next;
            }

            this.totalLen = count;
        }


        public char get(int index) {
            if (index < 0 || index >= totalLen) {
                return ' ';
            }

            Node cur = head;

            while (cur != null && index >= 0) {
                /**
                 * ">="
                 */
                if (index >= cur.len) {
                    index -= cur.len;
                } else {
                    return cur.chars[index];
                }

                /**
                 * !!!
                 */
                cur = cur.next;
            }

            return ' ';
        }

        /**
         *     0  1  2      3  4  5  6  7      8  9  10
         *    [a, b, c] -> [a, b, c, d, e] -> [a, b, c]
         *
         *    #1 insert('x', 2):
         *     normal case, we find the insertion node, the node still has space left,
         *     we first shift, then put value in the correct index.
         *     0  1  2  3      4  5  6  7  8     9  10  11
         *    [a, b, x, c] -> [a, b, c, d, e] -> [a, b, c]
         *
         *
         *    The rest is just try to get to #1 case.
         *
         *    #2 cur is null
         *      a.If head is null, just create a new node as head
         *      b.If head is not null, for this example : #2 insert('x', 11)
         *        cur is null, also create a new node at the tail.
         *      一头一尾
         *
         *   #3 insertion node is full
         *      we need to move the last element to the next node, then shift to make space for the new value.
         *
         *      if next is null or also full, we need to create new node and insert after insertion node so
         *      that we can move the last element to the new node.
         *
         *   Finally, we move to #1 case and do the normal insertion.
         *
         *
         */
        public void insert(char ch, int index) {
            if (index > totalLen) return;

            Node cur = head;

            /**
             * locate the insertion node
             */
            while (cur != null) {
                if (index >= cur.len) {
                    index -= cur.len;

                    if (index == 0 && cur.len < MAX_ARRAY_SIZE) {
                        index = cur.len;
                        break;
                    }
                } else {
                    break;
                }

                cur = cur.next;
            }

            /**
             * #2
             */
            if (cur == null) {
                Node newNode = new Node();
                if (head == null) {
                    head = newNode;
                } else {
                    Node tail = head;
                    while (tail.next != null) {
                        tail = tail.next;
                    }
                    tail.next = newNode;
                }

                /**
                 * !!!
                 */
                cur = newNode;
            }

            /**
             * #3
             */
            if (cur.len == MAX_ARRAY_SIZE) {
                if (cur.next == null || cur.next.len == MAX_ARRAY_SIZE) {
                    Node newNode = new Node();
                    newNode.next = cur.next;
                    cur.next = newNode;
                }

                Node next = cur.next;
                /**
                 * !!!
                 * here next.len is not updated, so the shift starts at index next.len, NOT next.len - 1
                 * !!!
                 */
                for (int i = next.len; i >= 1; i--) {
                    next.chars[i] = next.chars[i - 1];
                }
                next.len++;
                next.chars[0] = cur.chars[cur.len - 1];
                cur.len--;
            }

            /**
             * #1
             */
            cur.len++;
            for (int i = cur.len - 1; i > index; i--) {
                cur.chars[i] = cur.chars[i - 1];
            }
            cur.chars[index] = ch;

            totalLen++;
        }

        public void delete(int index) {
            /**
             * need to check "index < 0"
             */
            if (index < 0 || index >= totalLen) return;

            Node cur = head;
            Node pre = null;

            while (cur != null && index >= 0) {
                if (index >= cur.len) {
                    index -= cur.len;
                } else {
                    break;
                }

                pre = cur;
                cur = cur.next;
            }

            /**
             * To save space, after deletion, if previous node plus the number of chars in current
             * node is smaller than MAX SIZE, move the chars in current node to previous node and
             * current node can be removed.
             */
            if (pre != null && pre.len + cur.len - 1 <= MAX_ARRAY_SIZE) {//!!! "<="
                for (int i = 0; i < cur.len; i++) {
                    if (i == index) continue;

                    /**
                     * one line doing two things:
                     * 1.move and append elements in cur into pre
                     * 2.update pre.len
                     */
                    pre.chars[pre.len++] = cur.chars[i];
                }

                cur.len = 1;
            }

            /**
             * cur.len must update first, so we can know if the current node is empty after deletion
             */
            cur.len--;
            totalLen--;

            if (cur.len == 0) {
                if (pre != null) {
                    pre.next = cur.next;
                } else {//!!! means cur is head, remove it then head should move to the next node
                    head = cur.next;
                }
            } else {
                /**
                 * Simlar to insert(), here is the base case, shift and delete in cur
                 */

                /**
                 * !!!
                 * "i < cur.len"
                 * Here cur.len is already update (minus 1)!!!!
                 *
                 * if we happen to delete the last one, the for loop
                 * won't need to execute.
                 */
                for (int i = index; i < cur.len; i++) {
                    cur.chars[i] = cur.chars[i + 1];
                }
            }
        }
    }



    /**
     * 接着问: 有没有可能更更快呢? 当然有可能了了 ⽤用rope数据结构 其实就是BST的变种
     * ⽤用splay tree可做， STL中的rope实际上是红⿊黑树，可是谁能现场写TMD红⿊黑树，splay树 能讲出来就不不错了了，
     * 注意碰到这个题能讲出⽤用splay优化是极⼤大的加分项 ，实在不不⾏行行就⽤用嘴 BB⼏几句句说⽤用平衡二叉搜索树优化
     *
     * https://www.geeksforgeeks.org/splay-tree-set-1-insert/
     * https://www.geeksforgeeks.org/splay-tree-set-2-insert-delete/
     * https://www.geeksforgeeks.org/splay-tree-set-3-delete/
     *
     * The worst case time complexity of Binary Search Tree (BST) operations like search, delete, insert
     * is O(n). The worst case occurs when the tree is skewed. We can get the worst case time complexity
     * as O(Logn) with AVL and Red-Black Trees.
     *
     * Like AVL and Red-Black Trees, Splay tree is also self-balancing BST. The main idea of splay tree
     * is to bring the recently accessed item to root of the tree, this makes the recently searched item
     * to be accessible in O(1) time if accessed again. The idea is to use locality of reference
     * (In a typical application, 80% of the access are to 20% of the items). Imagine a situation where
     * we have millions or billions of keys and only few of them are accessed frequently, which is very
     * likely in many practical applications.
     *
     * All splay tree operations run in O(log n) time on average, where n is the number of entries in the
     * tree. Any single operation can take Theta(n) time in the worst case.
     */


    public static void printLL(Node head) {
        Node cur = head;

        StringBuilder sb = new StringBuilder();
        while (cur != null) {
            sb.append(Arrays.toString(cur.chars)).append(" -> ");
            cur = cur.next;
        }

        sb.setLength(sb.length() - 4);
        System.out.println(sb.toString());
    }

    private static void printElement(LinkedList ll) {
        System.out.println("===totalLen : " + ll.totalLen + "===");
        for (int i = 0; i < ll.totalLen; i++) {
            System.out.println("index " + i +  " : " + ll.get(i));
        }
        System.out.println("===");
    }

    public static void main(String[] args) {
        LinkedList ll = new LinkedList();

        ll.insert('a', 0);
        printLL(ll.head);

        ll.insert('b', 1);
        printLL(ll.head);

        ll.insert('c', 2);
        printLL(ll.head);

        ll.insert('d', 3);
        printLL(ll.head);

        ll.insert('e', 4);
        printLL(ll.head);

        ll.insert('a', 5);
        printLL(ll.head);

        ll.insert('b', 6);
        printLL(ll.head);

        ll.insert('c', 7);
        printLL(ll.head);

        ll.insert('d', 8);
        printLL(ll.head);

        ll.insert('e', 9);
        printLL(ll.head);

        ll.insert('f', 10);
        printLL(ll.head);

        ll.insert('x', 4);
        printLL(ll.head);

//        printElement(ll);

//        System.out.println("index 12 : " + ll.get(12));

        ll.insert('y', 6);
        printLL(ll.head);

        ll.insert('z', 4);
        printLL(ll.head);

        ll.insert('1', 4);
        printLL(ll.head);

        ll.insert('2', 4);
        printLL(ll.head);

        ll.insert('3', 4);
        printLL(ll.head);

        ll.insert('4', 4);
        printLL(ll.head);

        ll.delete(5);
        printLL(ll.head);
        printElement(ll);

        ll.delete(5);
        printLL(ll.head);

        ll.delete(7);
        printLL(ll.head);
        printElement(ll);

        ll.delete((14));
        printLL(ll.head);
        printElement(ll);

    }
}
