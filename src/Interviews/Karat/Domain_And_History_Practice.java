package Interviews.Karat;

import java.util.*;

public class Domain_And_History_Practice {
    /**
     * Same as LE_811_Subdomain_Visit_Count
     * Just use Map as input and output, simplify some of the processing for input/output
     *
     * Key:
     * 1.Each iteration, first put the whole string in result map
     * 2.Move from left to right, check '.' to get sub-domain name, then put and sum.
     */
    public static Map<String, Integer> getCount(Map<String, Integer> count) {
        Map<String, Integer> map = new HashMap<>();
        if (count == null || count.size() == 0) return map;

        for (String key : count.keySet()) {
            int num = count.get(key);
            map.put(key, map.getOrDefault(key, 0) + num);

            for (int i = 0; i < key.length(); i++) {
                if (key.charAt(i) == '.') {
                    String curKey = key.substring(i + 1);
                    map.put(curKey, map.getOrDefault(curKey, 0) + num);
                }
            }
        }

        return map;
    }

    /**
     * variation of LI_79_Longest_Common_Substring
     */
    public static List<String> longestCommonHistory(String[] user1, String[] user2) {
        int m = user1.length;
        int n = user2.length;

        int[][] dp = new int[m + 1][n + 1];
        int maxLen = 0;
        int endIdx = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (user1[i - 1].equals(user2[j - 1])) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;

                    if (dp[i][j] > maxLen) {
                        maxLen = dp[i][j];
                        /**
                         * !!!
                         * it's "i - 1"
                         */
                        endIdx = i - 1;
                    }
                } else {
                    dp[i][j] = 0;
                }
            }
        }

        List<String> res = new ArrayList<>();
        for (int i = endIdx - maxLen + 1; i <= endIdx; i++) {
            res.add(user1[i]);
        }

        return res;
    }

    public static void printMap(String title, Map<String, Integer> map) {
        System.out.println(title);
        for (String key : map.keySet()) {
            System.out.println(key + " : " + map.get(key));
        }
    }

    public static void printStrList(String title, List<String> list) {
        System.out.println(title);
        System.out.println(Arrays.toString(list.toArray()));
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("google.com", 60);
        map.put("yahoo.com", 50);
        map.put("sports.yahoo.com", 80);

        printMap("domain visit count", getCount(map));

        String[] user11 = new String[] {"3234.html", "xys.html", "7hsaa.html"};
        String[] user22 = new String[] {"3234.html", "sdhsfjdsh.html", "xys.html", "7hsaa.html"};
        String[] user0 = new String[] {"/nine.html", "/four.html", "/six.html", "/seven.html", "/one.html"};
        String[] user1 = new String[] {"/one.html", "/two.html", "/three.html", "/four.html", "/six.html"};
        String[] user2 = new String[] {"/nine.html", "/two.html", "/three.html", "/four.html", "/six.html", "/seven.html" };
        String[] user3 = new String[] {"/three.html", "/eight.html"};

        printStrList("user0 and user1", longestCommonHistory(user0, user1));
        printStrList("user0 and user2", longestCommonHistory(user0, user2));
        printStrList("user0 and user3", longestCommonHistory(user0, user3));
        printStrList("user1 and user2", longestCommonHistory(user1, user2));

    }
}
