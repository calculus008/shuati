package leetcode;

/**
 * Created by yuank on 3/4/18.
 */
public class LE_69_Sqrt {
    /*
        Implement int sqrt(int x).

        Compute and return the square root of x.

        x is guaranteed to be a non-negative integer.
     */

    //Same as LE_367

    public int mySqrt(int x) {
        if(x==0) return 0;

        int l = 1; //!!!!!
        int h = x;

        while (l <= h) {
            int mid = l + (h-l)/2;
            //The key is that midmid will overflow the integer if mid is large enough.
            // Therefore it is better to do mid> x/mid, as that will definitely NOT overflow as long as mid is a valid positive int.
            if(mid == x/mid) {
                return mid;
            }else if(mid > x/mid) {
                h = mid -1;
            }else {
                l = mid + 1;
            }
        }

        return h;
    }

    //使用九章binary search模版
    public int sqrt_JiuZhang(int x) {
        if (x <= 1) return x;

        int start = 1;
        int end = x;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (mid == x / mid) {
                return mid;
            } else if (mid > x / mid) {
                end = mid;
            } else {
                start = mid;
            }
        }

        return start;
    }
}
