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

    /**
     *  userids : userids whoc did a purcase
     *  clicks : "IP_Address,Time,Ad_Text",
     *  ips: #"User_ID,IP_Address"
     *
     *  Output:
     *
     *  Expected output:
     *  Bought Clicked Ad Text
     *  1 of 2  2017 Pet Mittens
     *  0 of 1  The Best Hollywood Coats
     *  3 of 3  Buy wool coats for your pets
     */
    public static List<String> getAdData(String[] userids, String[] clicks, String[] ips) {
        /**
         * #1 map ip to user id
         */
        Map<String, String> ipMap = new HashMap<>();
        for (String ip : ips) {
            String[] e = ip.split(",");
            ipMap.put(e[1], e[0]);
        }

        Map<String, Integer> clickMap = new HashMap<>();//#2.count map for total clicks
        Map<String, Integer> convertMap = new HashMap<>();//#3.count map for conversion
        Map<String, Set<String>> activityMap = new HashMap<>();//#4.userid -> set of ads the user did purchase on

        for (String c : clicks) {
            String[] e = c.split(",");
            String ip = e[0];
            String ad = e[2];

            clickMap.put(ad, clickMap.getOrDefault(ad, 0) + 1);

            if (!ipMap.containsKey((ip))) continue;

            String userid = ipMap.get(ip);

            if (!activityMap.containsKey(userid)) {
                activityMap.put(userid, new HashSet<>());
            }
            activityMap.get(userid).add(ad);
        }

        for (String userid : userids) {
            if (!activityMap.containsKey(userid)) continue;

            for (String ad : activityMap.get(userid)) {
                convertMap.put(ad, convertMap.getOrDefault(ad, 0) + 1);
            }
        }

        List<String> res = new ArrayList<>();

        for (String ad : clickMap.keySet()) {
            int convert = convertMap.containsKey(ad) ? convertMap.get(ad) : 0;

            res.add(convert + "-" + clickMap.get(ad) + "-" + ad);
        }

        return res;
    }

    public static List<String> getAdsData(String[] userids, String[] logs, String[] ips) {
        Map<String, String> ipMap = new HashMap<>();
        for (String ip : ips) {
            String[] parts = ip.split(",");
            String userid = parts[0];
            String ipadr = parts[1];
            ipMap.put(ipadr, userid);
        }

        Map<String, Integer> clickMap = new HashMap<>();
        Map<String, Integer> conversionMap = new HashMap<>();
        Map<String, Set<String>> userActMap = new HashMap<>();

        for (String log : logs) {
            String[] parts = log.split(",");
            String ip = parts[0];
            String ad = parts[2];

            clickMap.put(ad, clickMap.getOrDefault(ad, 0) + 1);

            String userid = ipMap.get(ip);
            if (userid == null) continue;

            if (!userActMap.containsKey(userid)) {
                userActMap.put(userid, new HashSet<>());
            }
            userActMap.get(userid).add(ad);
        }

        for (String userid : userids) {
            if (!userActMap.containsKey(userid)) continue;

            for (String ad : userActMap.get(userid)) {
                conversionMap.put(ad, conversionMap.getOrDefault(ad, 0) + 1);
            }
        }

        List<String> res = new ArrayList<>();
        for (String ad : clickMap.keySet()) {
            int click = clickMap.get(ad);
            int conversion = conversionMap.get(ad);
            String s = conversion + "-" + click + "-" + ad;
            res.add(s);
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

        String[] userids = {"3123122444","234111110", "8321125440", "99911063"};
        String[] clicks = {
                 "122.121.0.1,2016-11-03 11:41:19,Buy wool coats for your pets",
                 "96.3.199.11,2016-10-15 20:18:31,2017 Pet Mittens",
                 "122.121.0.250,2016-11-01 06:13:13,The Best Hollywood Coats",
                 "82.1.106.8,2016-11-12 23:05:14,Buy wool coats for your pets",
                 "92.130.6.144,2017-01-01 03:18:55,Buy wool coats for your pets",
                 "92.130.6.145,2017-01-01 03:18:55,2017 Pet Mittens"
        };
        String[] ips = {
                "2339985511,122.121.0.155",
                "234111110,122.121.0.1",
                "3123122444,92.130.6.145",
                "39471289472,2001:0db8:ac10:fe01:0000:0000:0000:0000",
                "8321125440,82.1.106.8",
                "99911063,92.130.6.144"
        };

        printStrList("conversion data", getAdData(userids, clicks, ips));


    }
}
