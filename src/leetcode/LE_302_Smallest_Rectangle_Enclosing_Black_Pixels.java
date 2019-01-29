package leetcode;

/**
 * Created by yuank on 4/28/18.
 */
public class LE_302_Smallest_Rectangle_Enclosing_Black_Pixels {
    /**
         An image is represented by a binary matrix with 0 as a white pixel and 1 as a black pixel.
         The black pixels are connected, i.e., there is only one black region. Pixels are connected
         horizontally and vertically. Given the location (x, y) of one of the black pixels,
         return the area of the smallest (axis-aligned) rectangle that encloses all black pixels.

         For example, given the following image:

         [
         "0010",
         "0110",
         "0100"
         ]
         and x = 0, y = 2,
         Return 6.

         Hard
     */

    /**
         Binary Search
         Time : O(mlogn + nlogm)
         Space : O(1)

         https://leetcode.com/problems/smallest-rectangle-enclosing-black-pixels/discuss/75127/C++JavaPython-Binary-Search-solution-with-explanation?page=3

         Imagine we project the 2D array to the bottom axis with the rule
         "if a column has any black pixel it's projection is black otherwise white"."

         Key - "The black pixels are connected, i.e., there is only one black region"

         There are only '1' and '0' in image.

         So we can search vertically and horizontally for left, right, top an down since it can
         be seen as sorted array (0->1 or 1->0) for the two sections separated by given x or y.
     */
    public int minArea(char[][] image, int x, int y) {
        if (image == null || image.length == 0 || image[0].length == 0) return 0;

        int m = image.length;
        int n = image[0].length;

        int left = findLeft(image, 0, y, true);
        int right = findRight(image, y, n - 1, true); //!!!起点还是y, 不是y+1, 否则会越界，比如: [[1]]
        int top = findLeft(image, 0, x, false);
        int down = findRight(image, x, m - 1, false); //!!!同理, 起点还是x, 不是x+1

        return (right - left + 1) * (down - top + 1);
    }

    int findLeft(char[][] image, int left, int right, boolean isHor) {
        while (left + 1 < right) {
            int mid = (right - left) / 2 + left;
            if (hasBlack(image, mid, isHor)) {
                right = mid;
            } else {
                left = mid;
            }
        }

        if (hasBlack(image, left, isHor)) {
            return left;
        } else {
            return right;
        }
    }

    int findRight(char[][] image, int left, int right, boolean isHor) {
        while (left + 1 < right) {
            int mid = (right - left) / 2 + left;
            if (hasBlack(image, mid, isHor)) {
                left = mid;
            } else {
                right = mid;
            }
        }

        if (hasBlack(image, right, isHor)) {//!!!这里相当于在逆序中搜索，先看right
            return right;//!!!
        } else {
            return left;//!!!
        }
    }

    boolean hasBlack(char[][] image, int x, boolean isHor) {
        if (isHor) {
            for (int i = 0; i < image.length; i++) {
                if (image[i][x] == '1') {//!!! char
                    return true;
                }
            }
        } else {
            for (int i = 0; i < image[0].length; i++) {
                if(image[x][i] == '1') {
                    return true;
                }
            }
        }
        return false;
    }
}
