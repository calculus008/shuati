package leetcode;

public class LE_832_Flipping_An_Image {
    /**
     * Given an n x n binary matrix image, flip the image horizontally, then invert it, and return the resulting image.
     *
     * To flip an image horizontally means that each row of the image is reversed.
     *
     * For example, flipping [1,1,0] horizontally results in [0,1,1].
     * To invert an image means that each 0 is replaced by 1, and each 1 is replaced by 0.
     *
     * For example, inverting [0,1,1] results in [1,0,0].
     *
     *
     * Example 1:
     * Input: image = [[1,1,0],[1,0,1],[0,0,0]]
     * Output: [[1,0,0],[0,1,0],[1,1,1]]
     * Explanation: First reverse each row: [[0,1,1],[1,0,1],[0,0,0]].
     * Then, invert the image: [[1,0,0],[0,1,0],[1,1,1]]
     *
     * Example 2:
     * Input: image = [[1,1,0,0],[1,0,0,1],[0,1,1,1],[1,0,1,0]]
     * Output: [[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]
     * Explanation: First reverse each row: [[0,0,1,1],[1,0,0,1],[1,1,1,0],[0,1,0,1]].
     * Then invert the image: [[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]
     *
     * Constraints:
     * n == image.length
     * n == image[i].length
     * 1 <= n <= 20
     * images[i][j] is either 0 or 1.
     *
     * Easy
     *
     * https://leetcode.com/problems/flipping-an-image/submissions/
     */
    class Solution1 {
        public int[][] flipAndInvertImage(int[][] image) {
            int m = image.length;
            int n = image[0].length;

            for (int[] row : image) {
                for (int i = 0; i < n / 2; i++) {
                    int temp = row[i];
                    row[i] = row[n - 1 - i] ^ 1;
                    row[n - 1 - i] = temp ^ 1;
                }

                if (n % 2 == 1) {
                    row[n / 2] ^= 1;
                }
            }

            return image;
        }
    }

    class Solution2 {
        public int[][] flipAndInvertImage(int[][] image) {
            int n = image[0].length;

            for (int[] row : image) {
                for (int i = 0; i < n / 2; i++) {
                    /**
                     * if the values are not the same,
                     * but you swap and flip, nothing will change.
                     * So if they are same, we toggle both, otherwise we do nothing
                     */
                    if (row[i] == row[n - 1 - i]) {
                        row[i] ^= 1;
                        row[n - 1 - i] = row[i];
                    }
                }

                 if (n % 2 == 1) {
                     row[n / 2] ^= 1;
                 }
            }

            return image;
        }
    }

    class Solution3 {
        public int[][] flipAndInvertImage(int[][] image) {
            int n = image[0].length;

            for (int[] row : image) {
                /**
                 * Use "i * 2 < n", then no need to process the middle index separately
                 */
                for (int i = 0; i * 2 < n; i++) {
                    if (row[i] == row[n - 1 - i]) {
                        row[i] ^= 1;
                        row[n - 1 - i] = row[i];
                    }
                }
            }

            return image;
        }
    }
}
