package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 4/4/18.
 */
public class LE_248_Strobogrammatic_Number_III {
    /**
        A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).

        Write a function to count the total strobogrammatic numbers that exist in the range of low <= num <= high.

        For example,
        Given low = "50", high = "100", return 3. Because 69, 88, and 96 are three strobogrammatic numbers.

        Note:
        Because the range might be a large number, the low and high numbers are represented as string.
     */

    public int strobogrammaticInRange(String low, String high) {
        List<String> list = new ArrayList<>();
        for (int i = low.length(); i <= high.length(); i++) {
            list.addAll(helper(i, i));
        }

        // List<String> res = new ArrayList<>();
        int res = 0;
        for(String num : list){
            if ((num.length() == low.length() && num.compareTo(low) < 0)
                    || (num.length() == high.length() && num.compareTo(high) > 0)) {
                continue;
            }
            // res.add(num);
            res++;
        }
        return res;
    }

    //copy from LE_247
    public List<String> helper(int cur, int org) {
        if (cur == 0) return new ArrayList<String>(Arrays.asList("")); //"asList"
        if (cur == 1) return new ArrayList<String>(Arrays.asList("0", "1", "8"));

        List<String> res = new ArrayList<String>();
        //!!!
        List<String> list = helper(cur - 2, org);
        for (int i = 0; i < list.size(); i++) {
            String k = list.get(i);
            if (cur != org) {//!!!
                res.add("0" + k + "0");
            }
            res.add("1" + k + "1");
            res.add("6" + k + "9");
            res.add("9" + k + "6");
            res.add("8" + k + "8");
        }

        return res;
    }

}
