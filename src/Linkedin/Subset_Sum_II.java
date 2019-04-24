package Linkedin;

public class Subset_Sum_II {
    /**
     * Given a set of non-negative integers, and a value sum, determine
     * count the number of subsets with sum equal to given sum.
     * Example:
     *
     * we have a set of items of size {3, 2, 5, 6, 7} and the max size is
     * K = 13. So the solutions are {5, 6, 2} and {6, 7}.Return value should
     * be 2.
     *
     * This problem is also known as summing n coins without repeats to a target sum.
     */

    /**
     * Time : O(n * target)
     */
    int countCombinations(int[] numbers, int target) {
        // d[i][j] = n means there are n combinations of the first j numbers summing to i.
        int[][] d = new int[target + 1][numbers.length + 1];

        // There is always 1 combination summing to 0, namely the empty set.
        for (int j = 0; j <= numbers.length; ++j) {
            d[0][j] = 1;
        }

        // For each total i, calculate the effect of using or omitting each number j.
        for (int i = 1; i <= target; ++i) {
            for (int j = 1; j <= numbers.length; ++j) {
                // "First j numbers" is 1-indexed, our array is 0-indexed.
                int number = numbers[j - 1];

                // Initialize value to 0.
                d[i][j] = 0;

                // How many combinations were there before considering the jth number?
                d[i][j] += d[i][j - 1];

                // How many things summed to i - number?
                if (i - number >= 0) {
                    d[i][j] += d[i - number][j - 1];
                }
            }
        }

        // Return the entry in the table storing all the number combos summing to target.
        return d[target][numbers.length - 1];
    }

}
