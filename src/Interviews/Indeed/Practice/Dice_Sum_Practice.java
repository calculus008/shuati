package src.Interviews.Indeed.Practice;

public class Dice_Sum_Practice {
    /**
     * Time : O(6 ^ dice)
     */
    int count = 0;
    public double getPossibility(int dice, int target) {
        if (dice <= 0 || target < dice || target > dice * 6) return 0.0;

        double total = Math.pow(6, dice);

        dfs(dice, target);

        return (double)count / total;
    }

    public void dfs(int dice, int target) {
        if (dice == 0) {
            if (target == 0) {
                count++;
            }
            return;
        }

        /**
         * <=
         */
        if (target <= 0) return;

        for (int i = 1; i <= 6; i++) {
            dfs(dice - 1, target - i);
        }
    }


    public double getPossibilityMem(int dice, int target) {
        if (dice <= 0 || target < dice || target > dice * 6) return 0.0;

        double total = Math.pow(6, dice);

        int res = helper(dice, target, new int[dice + 1][target + 1]);

        return (double)res / total;
    }

    /**
     * Time : O(6 * dice * target)
     */
    public int helper(int dice, int target, int[][] mem) {
        if (mem[dice][target] != 0) {
            return mem[dice][target];
        }

        if (dice == 0) {
            if (target == 0) {
                /**
                 * !!!
                 */
                return 1;
            }
            return 0;
        }

        if (target <= 0) return 0;

        /**
         * !!!
         */
        int res = 0;
        for (int i = 1; i <= 6; i++) {
            res += helper(dice, target, mem);
        }

        mem[dice][target] = res;
        return res;
    }
}
