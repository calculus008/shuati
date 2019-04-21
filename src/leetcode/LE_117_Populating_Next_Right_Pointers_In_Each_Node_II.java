package leetcode;

/**
 * Created by yuank on 3/13/18.
 */
public class LE_117_Populating_Next_Right_Pointers_In_Each_Node_II {
    /**

        Follow up for problem "Populating Next Right Pointers in Each Node".

        What if the given tree could be any binary tree? Would your previous solution still work?

        Note:

        You may only use constant extra space.
        For example,
        Given the following binary tree,
                 1
               /  \
              2    3
             / \    \
            4   5    7
        After calling your function, the tree should look like:
                 1 -> NULL
               /  \
              2 -> 3 -> NULL
             / \    \
            4-> 5 -> 7 -> NULL
     */


    //Important

    /**
          在第一个循环中
                     1  -  移动层
                   /  \
                  2    3  - 连接层
                 / \    \
                4   5    7

          在第二个循环中
                     1
                   /  \
                  2    3  - 移动层
                 / \    \
                4   5    7 - 连接层
    */

    //Time and Space : O(n)
    public static void connect(TreeLinkNode root) {
        TreeLinkNode pre = null;   //因为树不满(not a perfect BT)，所以要记住连接层的前一个元素，以便用它的next连接到cur.
        TreeLinkNode head = null;  //记住每个连接层的第一个元素，在遍历完当前连接层后回到head,以便往下一层走。
        TreeLinkNode cur = root;

        /**
            每个循环实际上涉及两层：
            cur所在的层可以叫移动层，这里next连接已经在上一个循环中完成,在当前循环中以next链接从左往右移动。
            当前循环中做连接处理的是在cur的children那层(下一层), 可以叫连接层。
            每次循环后，当前连接层成为下个循环中的移动层。
         **/
        while (cur != null) {
            /**
             * 处理每一层
             */
            while (cur != null) {
                /**
                 * 对当前node的左右chilren以同样的逻辑处理。
                 * 每一次的链接都是从pre链接到当前的左或右节点。
                 * 对每个连接层的第一个节点，只是把它设为head, 没有连接的动作。
                 **/
                if (cur.left != null) {
                    if (pre != null) { //链接
                        pre.next = cur.left;
                    } else {//这实际上表明这是本层的第一个元素。
                        head = cur.left;//Set head
                    }
                    pre = cur.left; //然后set pre.
                }

                if (cur.right != null) {
                    if (pre != null) {
                        pre.next = cur.right;
                    } else {
                        head = cur.right;//Set head
                    }
                    pre = cur.right;
                }

                /**
                 * 左右chilren都处理完之后再移动到下一个node.
                 */
                cur = cur.next;
            }

            /**
             * 每层处理完后，cur指回head(起点），这样就能走往下一层。
             */
            cur = head;
            pre = null;
            head = null;
        }
    }
}
