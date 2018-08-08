package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 3/8/18.
 */
public class LE_93_Restore_IP_Addresses {
    /**
        Given a string containing only digits, restore it by returning all possible valid IP address combinations.

        For example:
        Given "25525511135",

        return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)
     */

    //Solution 1: 3-loop divides the string s into 4 substring: s1, s2, s3, s4. Check if each substring is valid.
    public static List<String> restoreIpAddresses1(String s) {
        List<String> res = new ArrayList<>();
        if (null == s || s.length() < 4) return res;

        int n = s.length();

        //i, j, k is used to get substring, so its upper boundary plus 1
        for (int i = 1; i < 4 && i < n - 2; i++) {
            for (int j = i + 1; j < i + 4 && j < n - 1; j++) {
                for (int k = j + 1; k < j + 4 && k < n; k++) {
                    if (isValid(s.substring(0, i)) && isValid(s.substring(i, j)) && isValid(s.substring(j, k)) && isValid(s.substring(k, n))) {
                        res.add(s.substring(0, i) + "." + s.substring(i, j) + "." + s.substring(j, k) + "." + s.substring(k, n));
                    }
                }
            }
        }

        return res;
    }

    private static boolean isValid(String s) {
        int n = s.length();

        if (n == 0 || n > 3 || Integer.parseInt(s) > 255 || (s.startsWith("0") && n > 1)) return false;

        return true;
    }


    //Solution 2 : DFS and backtracking
    //Time : O(3 ^ 4) -> O(1), Space : O(n)
    public static List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        if (s == null || s.length() < 4) return res;

        helper1(res, s, 0, 0, new StringBuilder());
        // helper2(res, s, 0, 0, "");
        return res;
    }

    //count : number of tokens processed, starting from 0, max is 3. So each call to helper() tries one of the four tokens
    //Using StingBuilder to save memory. 4ms
    public static void helper1(List<String> res, String s, int idx, int count, StringBuilder sb) {
        if (count > 4) return;
        //!!! idx == s.length()
        if (count == 4 && idx == s.length()) {
            res.add(sb.toString());
            return;
        }

        for (int i = 1; i < 4; i++) {
            if (idx + i > s.length()) break;
            String temp = s.substring(idx, idx + i);

            //check if number is valid IP token
            if((temp.startsWith("0") && temp.length() > 1) || (i == 3 && Integer.parseInt(temp) > 255)) continue;

            int n = sb.length();
            sb.append(temp).append(count == 3 ? "" : "."); //no need to append "." for the last token
            helper1(res, s, idx + i, count + 1, sb);
            sb.setLength(n);
        }
    }

    //My DFS
    public List<String> restoreIpAddresses_my(String s) {
        List<String> res = new ArrayList<>();
        if (s == null || s.length() < 4) {
            return res;
        }

        helper(s, res, new StringBuilder(), 0, 0);
        return res;
    }

    private void helper(String s, List<String> res, StringBuilder sb, int start, int count) {
        if (count > 4) return;

        if (start == s.length() && count == 4) {
            res.add(sb.toString());
            return;
        }

        int len = sb.length();

        for (int i = 1; i <= 3 && start + i <= s.length(); i++) {
            String temp = s.substring(start, start + i);

            if ((temp.charAt(0) == '0' && temp.length() > 1) || (temp.length() == 3 && Integer.parseInt(temp) > 255)) {
                continue;
            }

            sb.append(temp).append(count == 3 ? "" : ".");
            helper(s, res, sb, start + i, count + 1);
            sb.setLength(len);
        }
    }


    //Use String, 5 ms
    public static void helper2(List<String> res, String s, int idx, int count, String ret) {
        if (count > 4) return;
        if (count == 4 && idx == s.length()) {
            res.add(ret);
            return;
        }

        for (int i = 1; i < 4; i++) {
            if (idx + i > s.length()) break;
            String temp = s.substring(idx, idx + i);
            if ((temp.startsWith("0") && temp.length() > 1) || (i == 3 && Integer.parseInt(temp) > 255)) continue;
            helper2(res, s, idx + i, count + 1, ret + temp + (count == 3 ? "" : "."));
        }
    }

}
