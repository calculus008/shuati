package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 1/7/18.
 */
public class AddOperators {
    public static List<String> addOperators(String num, int target) {
        List<String> res = new ArrayList<>();
        if(null == num || num.length() == 0) return res;

        helper(res, num, target, "", 0, 0, 0, 0);

        return res;
    }

    public static void helper(List<String> res, String num, int target, String path, int pos, long val, long pre, int depth) {
        System.out.println("pos=" + pos + ", val=" + val + ", pre=" + pre + ", path=" +path + ", depath=" + depth);
        if(pos == num.length()) {
            if(target == val) {
                System.out.println("   add " + path);
                res.add(path);
            }
            return;
        }

        for(int i = pos; i < num.length(); i++) {
            if(num.charAt(pos) == '0' && i != pos) {
                break;
            }

            long cur = Long.parseLong(num.substring(pos, i + 1));

            if(pos == 0) {
                helper(res, num, target, path + cur, i+1, cur, cur, 1);
            } else {
                helper(res, num, target, path + "+" + cur, i + 1, val + cur, cur, depth +1);
                helper(res, num, target, path + "-" + cur, i + 1, val - cur, -cur, depth +1); //!!! pre is -cur
                helper(res, num, target, path + "*" + cur, i + 1, val - pre + pre * cur, cur * pre, depth +1);
            }
        }
    }

    public static void main(String [] args) {
        addOperators("123", 6);
    }
}
