package Interviews.DoorDash;

public class Path_To_Nodes_In_Binary_Search_Tree {
    /**
     * DoorDash
     *
     * 给一个BST的root，和两个node，求两个node的path
     * 我就是用找lowest common ancestor做的
     * 然后是在recursion的过程中把path上的node存下来，最后要求把这条path上的所有Node输出嘛
     * recursive的过程中的node应该不是path中的node吧，应该是正好不需要的node吧，我是找到
     * common ancestor之后再找那个ancestor到两个node的路径再把他们连在一起的
     *
     * 我当时没有想太复杂的方法，就是直接脑子想到什么就说什么了，而且我写好之后问面试官，面试官也
     * 说这样okay，没说什么其他的，我后来也就没想怎么优化我的方法，我是先找LCA,然后分别遍历左右
     * 部分的，然后把lca到left node的路径reverse一下，然后再去掉最后一位，也就是lca，因为右子树
     * 遍历的时候也会有lca会造成重复，然后把两个路径拼到一起就好了，你是什么时候面呀？第几轮？
     *
     * 就是用那个方法，找到LCA后，在写个function findpath（lca, node1/node2）分别找到路径和
     * 在一起就可以了，反正这肯定是正解，但是是不是最优解我就无从知晓了，但是我觉得面试官不太
     * care是不是最优，能做出来就行，
     */
}
