package src.Interviews.Indeed;

public class Dice_Sum {
    /**
     * Solution1
     * Combination Sum like DFS
     *
     * Time : O(6 ^ dice)
     */
    int count = 0;
    public double getPossibilityDFS(int dice, int target) {
        if (dice <= 0 || target < dice || target > target * 6) return 0.0;

        int total = (int)Math.pow(6, dice);

        dfs(dice, target);

        return (float)count / total;
    }


    private void dfs(int dice, int target) {
        if (dice == 0 && target == 0) {
            count++;
            return;
        }

        if (dice == 0 || target <= 0) return;

        for (int i = 1; i <= 6; i++) {
            dfs(dice - 1, target - i);
        }
    }

    /**
     * Solution 2
     * DFS with mem, Best Solution
     *
     * 记忆化搜索解法，复杂度一定比DP要低，就是低到哪里去不太好计算。复杂度是 O(状态的个数*状态转移的复杂度)
     * 是O(6 * dice)，因为每次只向下查6个。anyway，这个是最优解。面试时候直接给这个就行吧。
     *
     * or 复杂度应该和dp一样，O（6*dice*target）??
     */
    public double getPossibilityDFSWithMem(int dice, int target) {
        if (dice <= 0 || target < dice || target > target * 6) return 0.0;

        int total = (int)Math.pow(6, dice);

        int[][] mem = new int[dice + 1][target + 1];
        int count = helper(dice, target, mem);

        return (float)count / total;
    }

    private int helper(int dice, int target, int[][] mem) {
        if (dice == 0 && target == 0) {
            return 1;
        }

        if (dice == 0 || target <= 0) return 0;

        if (mem[dice][target] != 0) {
            return mem[dice][target];
        }

        int res = 0;
        for (int i = 1; i <= 6; i++) {
            res += helper(dice - 1, target - i, mem);
        }

        mem[dice][target] = res;

        return res;
    }

    /**
     * Solution 3
     * DP
     */
    public double getPossibilityDP(int dice, int target) {
        if (dice <= 0 || target < dice || target > target * 6) return 0.0;

        int total = (int)Math.pow(6, dice);

        int[][] dp = new int[dice + 1][target + 1];

        for (int j = 1; j <= 6; j++) {
            dp[1][j] = 1;
        }

        for (int i = 2; i <= dice; i++) {
            for (int j = i; j <= target; j++) {
                for (int k = 1; k <= 6 && j - k >= i - 1; k++) {
                    dp[i][j] += dp[i - 1][j - k];
                }
            }
        }

        int count = dp[dice][target];

        return (float)count / total;
    }

    public static void main(String[] args) {
        Dice_Sum test = new Dice_Sum();
        System.out.println(test.getPossibilityDFS(5, 21));
        System.out.println(test.getPossibilityDFSWithMem(5, 21));
        System.out.println(test.getPossibilityDP(5, 21));
    }
}
