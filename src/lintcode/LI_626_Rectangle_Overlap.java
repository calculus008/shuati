package lintcode;

import common.Point;

public class LI_626_Rectangle_Overlap {
    /**
     * Given two rectangles, find if the given two rectangles overlap or not.
     *
     * l1: Top Left coordinate of first rectangle.
     * r1: Bottom Right coordinate of first rectangle.
     * l2: Top Left coordinate of second rectangle.
     * r2: Bottom Right coordinate of second rectangle.
     *
     * l1 != r1 and l2 != r2
     *
     * Example
     * Example 1:
     *
     * Input : l1 = [0, 8], r1 = [8, 0], l2 = [6, 6], r2 = [10, 0]
     * Output : true
     * Example 2:
     *
     * Input : l1 = [0, 8], r1 = [8, 0], l2 = [9, 6], r2 = [10, 0]
     * Output : false
     *
     * Easy
     */

    /**
     * Think the other way, instead trying to list all possible intersected cases,
     * just list the cases of no intersection
     */
    public boolean doOverlap(Point l1, Point r1, Point l2, Point r2) {
        if (l1.x > r2.x || l2.x > r1.x)
            return false;

        if (l1.y < r2.y || l2.y < r1.y)
            return false;

        return true;
    }
}
