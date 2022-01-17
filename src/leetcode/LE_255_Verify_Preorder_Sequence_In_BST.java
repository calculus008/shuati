package leetcode; /**
 * Created by yuank on 4/10/18.
 */
import java.util.Stack;

public class LE_255_Verify_Preorder_Sequence_In_BST  {
    /**
         Given an array of numbers, verify whether it is the correct pre-order traversal sequence of a binary search tree.

         You may assume each number in the sequence is unique.

         Follow up:
         Could you do it using only constant space complexity?
     */

    /**
              6
             /\
            1  8
           /\
          0 3
           /\
          2 5

        Preorder: 6 1 0 3 2 5 8

        https://leetcode.com/problems/verify-preorder-sequence-in-binary-search-tree/discuss/68142/Java-O(n)-and-O(1)-extra-space

        "Kinda simulate the traversal, keeping a stack of nodes (just their values) of which we’re still in the left subtree.
        If the next number is smaller than the last stack value, then we’re still in the left subtree of all stack nodes,
        so just push the new one onto the stack. But before that, pop all smaller ancestor values, as we must now be in their right subtrees
       (or even further, in the right subtree of an ancestor). Also, use the popped values as a lower bound, since being in their right subtree
       means we must never come across a smaller number anymore."


       As for all other BST problems, we also use the special property of BST to solve the problem. BST inorder is in sorted order.
       For preorder, there's an interesting property, as above example show :

        6 | 1 0 3 2 5 | 8

      The separator shows root, left subtree and right subtree. The first element in preorder must be root, then it goes to left subtree,
      those values are smaller than root, until there's a number bigger than root, then we know we reach right subtree. So just use this
      property, use a stack, start with root, keep pushing elements smaller than current number from preorder sequence, until it hits
      a bigger value, then push in that value, during the process, if a number is smaller than the last min value, it is wrong.


        6
        6 1
        6 1 0   <= 3
        pop 0, 1, min = 1
        6 3
        6 3 2   <= 5
        pop 2, 3  min = 3
        6 5     <= 8
        pop 5, 6  min = 6
        8
     */
    public boolean verifyPreorder1(int[] preorder) {
        if(null == preorder) return false;

        Stack<Integer> stack = new Stack<>();

        /**
         * min here is actually "last min", it is the last value that is smaller than
         * the last number in preorder, then current number in preorder is supposed to
         * be bigger than it, if its smaller, then we know it is wrong.
         */
        int min = Integer.MIN_VALUE;
        for(int num : preorder) {
            if(num < min) return false;

            while(!stack.isEmpty() && stack.peek() < num) {
                min = stack.pop();
            }

            stack.push(num);
        }

        return true;
    }

    /**
     *
     Same logic, use one more stack to be easier to understand
     Basically this is how to recover inorder sequence from preorder sequence of a BST.

     Preorder: 6 1 0 3 2 5 8

     stack :    8

     inorder :  0 1 2 3 5 6
     */

    public boolean verifyPreorder2(int[] preorder) {
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> inorder = new Stack<>();

        for(int v : preorder){
            if(!inorder.isEmpty() && v < inorder.peek()) {
                return false;
            }

            while(!stack.isEmpty() && v > stack.peek()){
                inorder.push(stack.pop());
            }
            stack.push(v);
        }
        return true;
    }
}
