package leetcode;

/**
 * Created by yuank on 3/29/18.
 */
public class LE_223_Rectangle_Area {
    /*
        Find the total area covered by two rectilinear rectangles in a 2D plane.

        Each rectangle is defined by its bottom left corner and top right corner as shown in the figure.

        Rectangle Area
        Assume that the total area is never beyond the maximum possible value of int.
     */

    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        int l1 = C - A;
        int w1 = D - B;

        int l2 = G - E;
        int w2 = H - F;

        int res = l1 * w1 + l2 * w2;

        int left = Math.max(A, E);
        int right = Math.min(C, G);
        int top = Math.min(D, H);
        int down = Math.max(B, F);

        if (right > left && top > down) {
            res -= (right - left) * (top - down);
        }
        return res;
    }
}
