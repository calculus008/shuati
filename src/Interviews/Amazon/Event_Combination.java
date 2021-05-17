package Interviews.Amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Time : O(k * C(k, n))
 *
 * Get combination of k events out of n events
 */
public class Event_Combination {
    public static List<List<String>> combination(String[] events) {
        List<List<String>> res = new ArrayList<>();
        helper(res, new ArrayList<String>(), events, events.length, 2, 0);
        return res;
    }

    public static void helper(List<List<String>> res, List<String> temp, String[] events, int n, int k, int start) {
        if (k == 0) {
            res.add(new ArrayList<>(temp));
            return;
        }

        for (int i = start; i < n; i++) {
            temp.add(events[i]);
            helper(res, temp, events, n, k - 1, i + 1);
            temp.remove(temp.size() - 1);
        }
    }

    public static void main(String[] args) {
        String[] input = {"e1", "e2", "e3", "e4"};

        List<List<String>> res = combination(input);

        for (List<String> l : res) {
            System.out.println(Arrays.toString(l.toArray()));
        }
    }
}
