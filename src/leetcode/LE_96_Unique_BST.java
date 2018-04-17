package leetcode;

/**
 * Created by yuank on 3/9/18.
 */
public class LE_96_Unique_BST {
    /*
        Given n, how many structurally unique BST's (binary search trees) that store values 1...n?

        For example,
        Given n = 3, there are a total of 5 unique BST's.

           1         3     3      2      1
            \       /     /      / \      \
             3     2     1      1   3      2
            /     /       \                 \
           2     1         2                 3
     */

    /*     DP, Time and Space : O(n)

           1         3     3      2      1
            \       /     /      / \      \
             3     2     1      1   3      2
            /     /       \                 \
           2     1         2                 3

           n = 3
           root :  1  left : 0  right : 2, f(0) * f(2)
                   2  left : 1  right : 1, f(1) * f(1)
                   3  left : 2  right : 0, f(2) * f(0)

           f(i, n), 1 <= i <= n: the number of unique BST, where the number i is the root of BST, and the sequence ranges from 1 to n.
           G(n) = F(1, n) + F(2, n) + ... + F(n, n), G(0)=1, G(1)=1.

           Given a sequence 1…n, we pick a number i out of the sequence as the root, then the number of unique BST with the specified root F(i),
           is the cartesian product of the number of BST for its left and right subtrees. For example, F(3, 7): the number of unique
           BST tree with number 3 as its root. To construct an unique BST out of the entire sequence [1, 2, 3, 4, 5, 6, 7] with 3 as the root,
           which is to say, we need to construct an unique BST out of its left subsequence [1, 2] and another BST out of the right subsequence [4, 5, 6, 7],
           and then combine them together (i.e. cartesian product). The tricky part is that we could consider the number of unique BST out of
           sequence [1,2] as G(2), and the number of of unique BST out of sequence [4, 5, 6, 7] as G(4). Therefore, F(3,7) = G(2) * G(4).

           i.e.

           F(i, n) = G(i-1) * G(n-i)	1 <= i <= n

           Combining the above two formulas, we obtain the recursive formula for G(n). i.e.

           G(n) = G(0) * G(n-1) + G(1) * G(n-2) + … + G(n-1) * G(0)
     */

    public static int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] += dp[j] * dp[i - j - 1];
            }
        }

        return dp [n];
    }

}
