package Interviews.Indeed;

public class Binary_Tree_To_Array {
    /**
     * Store a binary tree to array, 怎么存节省空间？
     *
     * 我的理解是如果只寸binary tree的话，一个TreeNode 要存一个value,
     * 已经两个pointer to left and right child, 大约占 12 byte。
     * 如果假设一个Pointer 也是４byte的话（３２位系统）。
     * 我看网上大家的答案大部分都是用一个heap. 大体意思是如果这个tree 比较满的话，用一个array 来存就行, Node i 的 两个孩子的index
     * 是 2*i, 2 * i + 1. 如果 i 从１开始的话。这样子一个node 只寸值，也就是只用了4 byte空间，原来的1/3.
     * 但是如果这个tree比较稀疏的话，可以把这个稀疏Array 转化成 dense format，也就是说用两个array, 一个存值，一个存 值对应的index.
     * 不知道还有没有其他更好的方法？
     * 另外我把用 heap 表示 binary tree的答案写出来。不知道对不对？
     *
     * 也可以用两个array，preorder存一个，inorder存一个， 回复起来也比较方便
     *
     * 请问  “用heap的方法”是什么意思？lz的code里并没有看到heap呀？
     * heap  底层结构是数组  他在数组里的实现是左儿子 是2*i 右儿子是2*i + 1  希望你懂了~  如果不懂请看这个链接~
     * http://www.cse.hut.fi/en/researc ... ial/taulukkona.html
     *
     * 如果是比较稀疏的BT,可以存一下pre/post order+inorder.我觉得这样不错，因为只存了non-null的结点，
     * size只是所有non-null节点的两倍而已。
     *
     * 是的，如果有duplicate的话，就不能用inorder+preorder了。
     *
     * 用int[] 比较好，因为每个node从12byte降到了4个byte，相对位置用index就可以表示。目前来看，上面的heap方法是最佳办法，
     * 就是用int数组来实现，左右孩子就是2i，2i+1。
     *
     * http://www.cse.hut.fi/en/research/SVG/TRAKLA2/tutorials/heap_tutorial/taulukkona.html
     *
     *
     * #
     * 给你⼀一个树，让你设计⼀一个数据结构来保存树的结构，⽤用array就好了了，
     * follow up就是如果树不不full怎么办
     *
     * #
     * https://stackoverflow.com/questions/2675756/efficient-array-storage-for-binary-tree
     *
     * LE_297_Serialize_And_Deserialize_Binary_Tree
     */
}
