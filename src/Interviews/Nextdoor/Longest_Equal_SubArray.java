package Interviews.Nextdoor;

public class Longest_Equal_SubArray {
    /**
     * a 由 1 和 0 组成. 求 0，1个数相同的subarray 最大长度.
     */

    public static int longest_equal_subarray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                array[i] = -1;
            }
        }

        int n = array.length;
        int[] prefix_sums = new int[n + 1];
        prefix_sums[0] = 0;

        for (int i = 1; i <= n; i++) {
            prefix_sums[i] = prefix_sums[i - 1] + array[i - 1];
        }

        int result = 0;
        for (int i = 0; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                if (prefix_sums[j] == prefix_sums[i]) {
                    result = Math.max(result, j - i);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int result = longest_equal_subarray(new int[]{0, 1, 0, 1, 0, 1});
        System.out.println(result);
    }
}
