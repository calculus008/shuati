package Interviews.Karat.Practice;

import java.util.*;

public class Domain_And_History {
    public static Map<String, Integer> getCount(Map<String, Integer> input) {
        Map<String, Integer> res = new HashMap<>();
        if (input == null || input.size() == 0) return res;

        for (String key : input.keySet()) {
            int num = input.get(key);

            res.put(key, res.getOrDefault(key, 0) + num);
            for (int i = 0; i < key.length(); i++) {
                if (key.charAt(i) == '.') {
                    String curKey = key.substring(i + 1);
                    res.put(curKey, res.getOrDefault(curKey, 0) + num);
                }
            }
        }
        return res;
    }


    public static List<String> longestCommonSub(String[] user1, String[] user2) {
        List<String> res = new ArrayList<>();

        int m = user1.length;
        int n = user2.length;
        int[][] dp = new int[m + 1][n + 1];

        int maxLen = 0;
        int endIdx = 0;

        for (int i = 1;i <= m ; i++) {
            for (int j = 1; j <= n; j++) {
                if (user1[i - 1].equals(user2[j - 1])) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;

                    if (dp[i][j] > maxLen) {
                        maxLen = dp[i][j];
                        endIdx = i - 1;
                    }
                } else {
                    dp[i][j] = 0;
                }
            }
        }

        for (int i = endIdx - maxLen + 1; i <= endIdx; i++) {
            res.add(user1[i]);
        }

        return res;
    }

    public static void printMap(String title, Map<String, Integer> map) {
        System.out.println(title);
        for (String key : map.keySet()) {
            System.out.println(key + "->" + map.get(key));
        }
    }

    public static void printStrList(String title, List<String> l) {
        System.out.println(title);
        System.out.println(Arrays.toString(l.toArray()));
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

        printStrList("user0 and user1", longestCommonSub(user0, user1));
        printStrList("user0 and user2", longestCommonSub(user0, user2));
        printStrList("user0 and user3", longestCommonSub(user0, user3));
        printStrList("user1 and user2", longestCommonSub(user1, user2));
    }
}
