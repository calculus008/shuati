package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuank on 4/4/18.
 */
public class LE_247_Strobogrammatic_Number_II {
    /*
        A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).

        Find all strobogrammatic numbers that are of length = n.

        For example,
        Given n = 2, return ["11","69","88","96"].
     */

    //Backtracking  Time : O(n ^ 2), space : O(n)
    public List<String> findStrobogrammatic(int n) {
        return helper(n, n);
    }

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
