package src.Interviews.Karat.Practice1;

import java.util.*;

class Domain {
    public static Map<String, Integer> getDomainCount(Map<String, Integer> input) {
        Map<String, Integer> res = new HashMap<>();
        if (input == null || input.size() == 0) return res;

        for (String key : input.keySet()) {
            int num = input.get(key);
            res.put(key, res.getOrDefault(key, 0) + num);

            for (int i = 0; i < key.length(); i++) {
                if (key.charAt(i) == '.') {
                    String subdomain = key.substring(i + 1);
                    res.put(subdomain, res.getOrDefault(subdomain, 0) + num);
                }
            }
        }

        return res;
    }

    public static List<String> longestCommonHistory(String[] user1, String[] user2) {
        List<String> res = new ArrayList<>();
        if (user1 == null || user2 == null) return res;

        int m = user1.length;
        int n = user2.length;

        if (m == 0 || n == 0) return res;

        int[][] dp = new int[m + 1][n + 1];
        int maxLen = 0;
        int endIdx = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (user1[i - 1].equals(user2[j - 1])) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > maxLen) {
                        endIdx = i - 1;
                        maxLen = dp[i][j];
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

    public static List<String> getAdsData(String[] userids, String[] logs, String[] ips) {
        /**
         * !!!
         * Four HashMap get the job done.
         *
         * Two count map:
         * clickMap      :  ad -> number of clicks
         * conversionMap :  ad -> number of conversions(purchases)
         *
         * One mapping map:
         * ip mapping    :  ip -> userid
         *
         * The important one:
         * user activity map :  userid -> SET(!!!) of ads that this user clicked.
         *
         * One implied condition (!!!):
         * If userid is in userids list, then all ads that this user clicked will get one conversion. (!!!)
         */
        Map<String, String> ipMap = new HashMap<>();
        /**
         * Build ip to userid mapping
         */
        for (String ip : ips) {
            String[] parts = ip.split(",");
            String userid = parts[0];
            String addr = parts[1];
            ipMap.put(addr, userid);
        }

        Map<String, Integer> clickMap = new HashMap<>();
        Map<String, Integer> conversionMap = new HashMap<>();
        Map<String, Set<String>> userMap = new HashMap<>();

        /**
         * Build click map and user activity map from logs
         */
        for (String log : logs) {
            String[] parts = log.split(",");
            String ip = parts[0];
            String ad = parts[2];

            clickMap.put(ad, clickMap.getOrDefault(ad, 0) + 1);

            String userid = ipMap.get(ip);
            if (userid == null) continue;

            if (!userMap.containsKey(userid)) {
                userMap.put(userid, new HashSet<>());//SET !!!
            }
            userMap.get(userid).add(ad);
        }

        /**
         * Build conversion count map
         */
        for (String userid : userids) {
            if (!userMap.containsKey(userid)) continue;

            for (String ad : userMap.get(userid)) {
                conversionMap.put(ad, conversionMap.getOrDefault(ad, 0) + 1);
            }
        }

        List<String> res = new ArrayList<>();
        for (String ad : clickMap.keySet()) {
            int click = clickMap.get(ad);
            /**
             * !!!
             * Must take care the case when conversion is zero, hence converionMap does not have the given ad as key.
             */
            int conversion = conversionMap.get(ad) == null ? 0 : conversionMap.get(ad);
            String s = conversion + "-" + click + "-" + ad;
            res.add(s);
        }

        return res;
    }

    public static void printMap(String title, Map<String, Integer> map) {
        System.out.println(title);
        for (String key : map.keySet()) {
            System.out.println(key + "->" + map.get(key));
        }
    }

    public static void printList(String title, List<String> list) {
        System.out.println(title);
        System.out.println(Arrays.toString(list.toArray()));
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("google.com", 60);
        map.put("yahoo.com", 50);
        map.put("yahoo.yahoo.com", 20);
        map.put("sports.yahoo.com", 80);

        printMap("===domain visit count===", getDomainCount(map));

        String[] user11 = new String[] {"3234.html", "xys.html", "7hsaa.html"};
        String[] user22 = new String[] {"3234.html", "sdhsfjdsh.html", "xys.html", "7hsaa.html"};
        String[] user0 = new String[] {"/nine.html", "/four.html", "/six.html", "/seven.html", "/one.html"};
        String[] user1 = new String[] {"/one.html", "/two.html", "/three.html", "/four.html", "/six.html"};
        String[] user2 = new String[] {"/nine.html", "/two.html", "/three.html", "/four.html", "/six.html", "/seven.html" };
        String[] user3 = new String[] {"/three.html", "/eight.html"};

        printList("===user0 and user1===", longestCommonHistory(user0, user1));
        printList("===user0 and user2===", longestCommonHistory(user0, user2));
        printList("===user0 and user3===", longestCommonHistory(user0, user3));
        printList("===user1 and user2===", longestCommonHistory(user1, user2));

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

        printList("===Ads Data===", getAdsData(userids, clicks, ips));

    }
}
