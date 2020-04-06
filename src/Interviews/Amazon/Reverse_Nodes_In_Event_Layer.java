package Interviews.Amazon;

import common.TreeNode;

import java.util.*;

public class Reverse_Nodes_In_Event_Layer {
    /**
     * 给一个binary tree, reverse所有偶数层的nodes
     * (root算第一层，root下面一层算第二层， reverse第2， 4， 6， 8......层的nodes), 然后返回这个tree
     */

    TreeNode reverseEvenLevel(TreeNode root) {
        if (root == null) return root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean even = false;
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        Map<TreeNode, Boolean> leftOrRight = new HashMap<>(); //true if left, false if right

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<TreeNode> nextLevel = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                if (cur.left != null) {
                    nextLevel.add(cur.left);
                    parent.put(cur.left, cur);
                    leftOrRight.put(cur.left, true);
                }
                if (cur.right != null) {
                    nextLevel.add(cur.right);
                    parent.put(cur.right, cur);
                    leftOrRight.put(cur.right, false);
                }
            }

            if (even == false) {
                for (int i = 0; i < nextLevel.size() / 2; i++) {
                    TreeNode start = nextLevel.get(i);
                    TreeNode end = nextLevel.get(nextLevel.size() - i - 1);
                    TreeNode startP = parent.get(start);
                    TreeNode endP = parent.get(end);
                    if (leftOrRight.get(start)) {
                        //left child
                        int temp = startP.left.val;
                        startP.left.val = end.val;
                        if (leftOrRight.get(end))
                            endP.left.val = temp;
                        else
                            endP.right.val = temp;
                    } else {
                        //right child
                        int temp = startP.right.val;
                        startP.right.val = end.val;
                        if (leftOrRight.get(end))
                            endP.left.val = temp;
                        else
                            endP.right.val = temp;
                    }
                }
            }

            even = !even;
            queue.addAll(nextLevel);
        }
        return root;
    }

}
