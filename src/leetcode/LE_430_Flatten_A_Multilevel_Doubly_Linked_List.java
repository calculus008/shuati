package leetcode;

public class LE_430_Flatten_A_Multilevel_Doubly_Linked_List {
    /**
     * You are given a doubly linked list which in addition to the next and previous pointers,
     * it could have a child pointer, which may or may not point to a separate doubly linked
     * list. These child lists may have one or more children of their own, and so on, to produce
     * a multilevel data structure, as shown in the example below.
     *
     * Flatten the list so that all the nodes appear in a single-level, doubly linked list.
     * You are given the head of the first level of the list.
     *
     * Medium
     */

    // Definition for a Node.
    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    };

    class Solution_Practice {
        public Node flatten(Node head) {
            if (head == null) return head;

            Node p = head;
            while (p != null) {
                if (p.child == null) {
                    p = p.next;
                    continue;
                }

                Node next = p.next;

                Node temp = p.child;
                while (temp.next != null) {
                    temp = temp.next;
                }

                temp.next = next;
                if (next != null) {
                    next.prev = temp;
                }

                p.next = p.child;
                p.child.prev = p;
                p.child = null;
            }

            return head;
        }
    }

    /**
     * Key insights:
     * If current node has child list, we only need to connect (or insert) it into parent list.
     *
     * Important!!! : While we do this, we do NOT move at all - p still remains at p, not moving to the next node.
     *                Then in the nest iteration, p.child is null (set null in last iteration), we keeps moves on.
     *                If we have nested child lists, it will be unfolded just the same. Very clever!
     *
     * Best Solution, no need to do recursion.
     */
    class Solution_Iteration {
        public Node flatten(Node head) {
            if( head == null) return head;

            Node p = head;
            while( p!= null) {
                /**
                 * CASE 1: if no child, proceed
                 */
                if( p.child == null ) {
                    p = p.next;
                    continue;
                }

                /**
                 *CASE 2: got child, find the tail of the child and link it to p.next
                 */
                Node temp = p.child;

                /**
                 * Find the tail of the child
                 */
                while( temp.next != null ) {
                    temp = temp.next;
                }

                /**
                 * connect child list TAIL to the rest of the list
                 */
                temp.next = p.next;
                /**
                 * !!!
                 * check null
                 */
                if( p.next != null )  {
                    p.next.prev = temp;
                }

                /**
                 *  Connect child list HEAD with parent list and set child to null
                 */
                p.next = p.child;
                p.child.prev = p;
                p.child = null;
            }

            return head;
        }
    }

    class Solution_Recursion_1 {
        public Node flatten(Node head) {
            Node p = head;
            // Traverse the list
            while (p != null) {
                if (p.child != null) {
                    /**
                     * Remember who is current node points to as next
                     */
                    Node right = p.next;

                    /**
                     * !!!
                     * It is a DoubleLinked List, so must set next and prev of
                     * the two connecting nodes.
                     *
                     * Then set child to null!!!
                     */
                    p.next = flatten(p.child);
                    p.next.prev = p;
                    p.child = null;

                    /**
                     * To use current "flatten()" as recursion function, we need to solve the issue
                     * how to get the tail of the child list, here it simply do another traversal until
                     * p is null, then p reaches the end of child list.
                     */
                    while (p.next != null) {
                        p = p.next;
                    }

                    /**
                     * connect the child list end to the rest of the DLL
                     */
                    if (right != null) {
                        p.next = right;
                        p.next.prev = p;
                    }
                }
                p = p.next;
            }

            return head;
        }
    }

    /**
     * Concise, but it seems that it has done too many set operation.
     */
    class Solution_Recursion_2 {
        Node pre;
        public Node flatten(Node head) {
            pre = null;
            traverse(head);
            return head;
        }

        public void traverse(Node head) {
            if(head == null) {
                return;
            }
            if(pre != null) {
                pre.next = head;
                head.prev = pre;
            }
            pre = head;

            Node child = head.child;
            head.child = null;
            Node next = head.next;
            head.next = null;
            traverse(child);
            traverse(next);
        }
    }


    /**
     * work but clumsy, didn't find a good way for a clean recursion
     */
    class Solution_My {
        public Node flatten(Node head) {
            if (head == null) return head;

            Node dummy = new Node();
            dummy.next = head;
            Node cur = head;

            while (cur != null) {
                if (cur.child != null) {
                    Node next = cur.next;
                    cur.next = cur.child;
                    cur.next.prev = cur;

                    Node tail = next(cur.child);
                    tail.next = next;
                    if (next != null) next.prev = tail;

                    cur.child = null;
                    cur = next;
                } else {
                    cur = cur.next;
                }
            }

            return dummy.next;
        }

        private Node next(Node head) {
            Node n = head;
            Node pre = null;
            while (n != null) {
                if (n.child != null) {
                    Node next = n.next;
                    n.next = n.child;
                    n.next.prev = n;

                    Node tail = next(n.child);
                    tail.next = next;
                    if (next != null) next.prev = tail;

                    n.child = null;
                    pre = tail;
                    n = next;
                } else {
                    pre = n;
                    n = n.next;
                }
            }

            return pre;
        }
    }
}
