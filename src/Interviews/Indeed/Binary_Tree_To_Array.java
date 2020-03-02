package Interviews.Indeed;

import apple.laf.JRSUIUtils;
import common.TreeNode;

import java.util.*;

public class Binary_Tree_To_Array {
    /**
     * Store a binary tree to array, 怎么存节省空间？
     * <p>
     * 我的理解是如果只寸binary tree的话，一个TreeNode 要存一个value,
     * 已经两个pointer to left and right child, 大约占 12 byte。
     * 如果假设一个Pointer 也是４byte的话（３２位系统）。
     * 我看网上大家的答案大部分都是用一个heap. 大体意思是如果这个tree 比较满的话，用一个array 来存就行, Node i 的 两个孩子的index
     * 是 2*i, 2 * i + 1. 如果 i 从１开始的话。这样子一个node 只寸值，也就是只用了4 byte空间，原来的1/3.
     * 但是如果这个tree比较稀疏的话，可以把这个稀疏Array 转化成 dense format，也就是说用两个array, 一个存值，一个存 值对应的index.
     * 不知道还有没有其他更好的方法？
     * 另外我把用 heap 表示 binary tree的答案写出来。不知道对不对？
     * <p>
     * 也可以用两个array，preorder存一个，inorder存一个， 回复起来也比较方便
     * <p>
     * 请问  “用heap的方法”是什么意思？lz的code里并没有看到heap呀？
     * heap  底层结构是数组  他在数组里的实现是左儿子 是2*i 右儿子是2*i + 1  希望你懂了~  如果不懂请看这个链接~
     * http://www.cse.hut.fi/en/researc ... ial/taulukkona.html
     * <p>
     * 如果是比较稀疏的BT,可以存一下pre/post order+inorder.我觉得这样不错，因为只存了non-null的结点，
     * size只是所有non-null节点的两倍而已。
     * <p>
     * 是的，如果有duplicate的话，就不能用inorder+preorder了。
     * <p>
     * 用int[] 比较好，因为每个node从12byte降到了4个byte，相对位置用index就可以表示。目前来看，上面的heap方法是最佳办法，
     * 就是用int数组来实现，左右孩子就是2i，2i+1。
     * <p>
     * http://www.cse.hut.fi/en/research/SVG/TRAKLA2/tutorials/heap_tutorial/taulukkona.html
     * <p>
     * <p>
     * #
     * 给你⼀一个树，让你设计⼀一个数据结构来保存树的结构，⽤用array就好了了，
     * follow up就是如果树不不full怎么办
     * <p>
     * #
     * https://stackoverflow.com/questions/2675756/efficient-array-storage-for-binary-tree
     * <p>
     * LE_297_Serialize_And_Deserialize_Binary_Tree
     */

    /**
     * Use heap like representation of a tree - put node value into array. For a 32 bit system, for each node,
     * node value takes 4 bytes, each pointer to children takes 4 bytes, total is 12 bytes. Use array, we use
     * array index to track between parent and children, therefore, we only need to use 4 bytes to save value
     * of the node, so for each node, we just 4 bytes (1/3 of the tree node space).
     *
     * Root value is in index 1, for a give parent in index i, its left child is in heap[2 *1], right child is
     * in heap[2 * i + 1]. For a given child in idx k, its parent is in heap[k / 2].
     *
     * But when we init the array, we will init it as a complete tree, the size is 2 ^ height. So it is good for
     * condense tree, not good for sparse tree.
     *
     * http://www.cse.hut.fi/en/research/SVG/TRAKLA2/tutorials/heap_tutorial/taulukkona.html
     */
    public int[] compressDenseBT(TreeNode root) {
        int height = getHeight(root);

        if (height == 0) {
            return new int[]{};
        }

        int len = (int) Math.pow(2, height);
        int[] heap = new int[len];

        Queue<TreeNode> nodeQ = new LinkedList<>();
        Queue<Integer> idxQ = new LinkedList<>();

        nodeQ.offer(root);
        idxQ.offer(1);

        while (!nodeQ.isEmpty()) {
            TreeNode cur = nodeQ.poll();
            int idx = idxQ.poll();

            heap[idx] = cur.val;

            if (cur.left != null) {
                nodeQ.offer(cur.left);
                idxQ.offer(2 * idx);
            }

            if (cur.right != null) {
                nodeQ.offer(cur.right);
                idxQ.offer(2 * idx + 1);
            }
        }

        return heap;
    }

    private int getHeight(TreeNode root) {
        if (root == null) return 0;

        int l = getHeight(root.left);
        int r = getHeight(root.right);

        return Math.max(l, r) + 1;
    }

    /**
     * For sparse tree, we don't use array, instead we use map :
     * index in a full tree -> node value
     *
     * By doing this we don't need to allocate array of size 2 ^ height of the tree,
     * instead, we just have a map with n entries, n is the number of nodes in the tree.
     *
     */
    public Map<Integer, Integer> compressSparseBT(TreeNode root) {
        Map<Integer, Integer> map = new HashMap<>();

        Queue<TreeNode> nodeQ = new LinkedList<>();
        Queue<Integer> idxQ = new LinkedList<>();

        nodeQ.offer(root);
        idxQ.offer(1);

        while (!nodeQ.isEmpty()) {
            TreeNode cur = nodeQ.poll();
            int idx = idxQ.poll();

            map.put(idx, cur.val);

            if (cur.left != null) {
                nodeQ.offer(cur.left);
                idxQ.offer(2 * idx);
            }

            if (cur.right != null) {
                nodeQ.offer(cur.right);
                idxQ.offer(2 * idx + 1);
            }
        }

        return map;
    }

    /**
     * Another way to represent sparse tree, use the same method in LE_297_Serialize_And_Deserialize_Binary_Tree,
     * use BFS or DFS to put all node values (and null) into a string representation (with n - 1 separators).
     *
     * Space : 32 * n + 32 * (n - 1) = 64 * n - 32
     */
    public String serializeBTBFS(TreeNode root) {
        if (root == null) return "";

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        StringBuilder sb = new StringBuilder();

        while (!q.isEmpty()) {
            TreeNode cur = q.poll();

            if (cur == null) {
                sb.append("#").append(" ");
                continue;
            }

            sb.append(cur.val).append(" ");

            q.offer(cur.left);
            q.offer(cur.right);
        }

        return sb.toString();
    }

    public TreeNode deserializeBT(String s) {
        if (s == null || s.length() == 0) return null;

        String[] str = s.split(" ");

        TreeNode root = new TreeNode(Integer.parseInt(str[0]));

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        for (int i = 1; i < str.length; i++) {
            TreeNode cur = q.poll();

            if (!str[i].equals("#")) {
                cur.left = new TreeNode(Integer.parseInt(str[i]));
                q.offer(cur.left);
            }

            i++;

            if (!str[i].equals("#")) {
                cur.right = new TreeNode(Integer.parseInt(str[i]));
                q.offer(cur.right);
            }
        }

        return root;
    }


    /**
     * More compression
     *
     * Use bit to compress, each value take 32 bit (suppose we are on 32 bit system), for n code. all
     * values take 32n bits, each node will use one bit to tell if the child is null (bit value 1 means
     * the child node is null, 0 means it's not), so the total bits the representation uses:
     *
     * 32 * n + 2 * n = 34 * n
     *
     * compress():
     * Just use BFS method of the last solution, instead,we put them in an ArrayList, then go through
     * this list to do bit compression.
     */
    public String serializeBT(TreeNode root) {
        if (root == null) return "";

        List<String> list = new ArrayList<>();

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            TreeNode cur = q.poll();

            if (cur == null) {
                list.add("#");
                continue;
            }

            list.add(String.valueOf(cur.val));

            q.offer(cur.left);
            q.offer(cur.right);
        }

        return compress(list);
    }

    public String compress(List<String> l) {
        String ans = "";

        int cur = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).equals("#")) {
                setNextBit(sb, cur, 1);
            } else {
                setNextBit(sb, cur, 0);
                int value = Integer.parseInt(l.get(i));

                for (int j = 0; j < 32; j++) {
                    if ((value & (1 << j)) != 0) {
                        setNextBit(sb, cur, 1);
                    } else {
                        setNextBit(sb, cur, 0);
                    }
                }
            }
        }

        return sb.toString();
    }

    private void setNextBit(StringBuilder sb, int cur, int val) {
        int b = cur / 8;

        if (sb.length() <= b) {
            sb.append((char)0);
        }

        if (val != 0) {
            char c = sb.charAt(b);
            c |= (1 << (cur % 8));
            sb.setCharAt(b, c);

        }

        cur++;
    }

    private int getNextBit(String s, int cur) {
        int b = cur / 8;
        int ret = 0;

        if ((s.charAt(b) & (1 << (cur % 8))) == 1) {
            ret = 1;
        }

        cur++;

        return ret;
    }

    public TreeNode deserializeBT2(String s) {
        if (s == null || s.length() == 0) return null;

        int cur = 0;
        TreeNode root = getNext(s, cur);

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            TreeNode n = q.poll();

            if (q != null) {
                n.left = getNext(s, cur);
                n.right = getNext(s, cur);

                q.offer(n.left);
                q.offer(n.right);
            }
        }

        return root;
    }

    public TreeNode getNext(String s, int cur) {
        if (cur / 8 >= s.length()) return null;

        int val = getNextBit(s, cur);
        if (val == 1) return null;

        int value = 0;
        for (int i = 0; i < 32; i++) {
            int next = getNextBit(s, cur);
            if (next == 1) {
                value |= (1 << i);
            }
        }

        TreeNode node = new TreeNode(value);
        return node;
    }
}
