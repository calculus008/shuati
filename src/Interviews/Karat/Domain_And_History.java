package Interviews.Karat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Domain_And_History {
    /**
     * 1.给广告在每个domain上被click的次数,要求返回domain及其所有sub domain 被click的总次数
     *
     * 输入：[
     *            ["google.com", "60"],
     *            ["yahoo.com", "50"],
     *            ["sports.yahoo.com", "80"]
     *       ]
     *
     * 输出：[
     *             ["com", "190"], (60+50+80)
     *             ["google.com", "60"], 
     *             ["yahoo.com", "130"] (50+80)
     *             ["sports.yahoo.com", "80"]
     *          ]
     *
     * LE_811_Subdomain_Visit_Count
     *
     * 2.给每个user访问历史记录，找出两个user之间longest continuous common history
     * 输入： [
     *              ["3234.html", "xys.html", "7hsaa.html"], // user1
     *              ["3234.html", ''sdhsfjdsh.html", "xys.html", "7hsaa.html"] // user2
     *        ],
     *
     * user1 and user2 （指定两个user求intersect）
     *
     * 输出：["xys.html", "7hsaa.html"]
     *
     * Original Question:
     * We have some clickstream data that we gathered on our client's website. Using cookies, we collected snippets of users' anonymized URL histories while they browsed the site. The histories are in chronological order and no URL was visited more than once per person.
     * Write a function that takes two users' browsing histories as input and returns the longest contiguous sequence of URLs that appears in both.
     *
     * Sample input:
     * user0 = ["/start", "/pink", "/register", "/orange", "/red", "a"]
     * user1 = ["/start", "/green", "/blue", "/pink", "/register", "/orange", "/one/two"]
     * user2 = ["a", "/one", "/two"]
     * user3 = ["/red", "/orange", "/yellow", "/green", "/blue", "/purple", "/white", "/amber", "/HotRodPink", "/BritishRacingGreen"]
     * user4 = ["/red", "/orange", "/amber", "/random", "/green", "/blue", "/purple", "/white", "/lavender", "/HotRodPink", "/BritishRacingGreen"]
     * user5 = ["a"]
     *
     * Sample output:
     * findContiguousHistory(user0, user1)
     *    /pink
     *    /register
     *    /orange
     *
     * findContiguousHistory(user1, user2)
     *    (empty)
     *
     * findContiguousHistory(user2, user0)
     *    a
     *
     * findContiguousHistory(user5, user2)
     *    a
     *
     * findContiguousHistory(user3, user4)
     *    /green
     *    /blue
     *    /purple
     *    /white
     *
     * findContiguousHistory(user4, user3)
     *    /green
     *    /blue
     *    /purple
     *    /white
     *
     *
     * for time and space complexity analysis:
     * n: length of the first user's browsing history
     * m: length of the second user's browsing history
     *
     *
     *
     *
     * Question3
     *  The people who buy ads on our network don't have enough data about how ads are working for
     * their business. They've asked us to find out which ads produce the most purchases on their website.
     *
     *  Our client provided us with a list of user IDs of customers who bought something on a landing page
     * after clicking one of their ads:
     *
     *  # Each user completed 1 purchase.
     *  completed_purchase_user_ids = [
     *    "3123122444","234111110", "8321125440", "99911063"]
     *
     *  And our ops team provided us with some raw log data from our ad server showing every time a
     * user clicked on one of our ads:
     *  ad_clicks = [
     *   #"IP_Address,Time,Ad_Text",
     *   "122.121.0.1,2016-11-03 11:41:19,Buy wool coats for your pets",
     *   "96.3.199.11,2016-10-15 20:18:31,2017 Pet Mittens",
     *   "122.121.0.250,2016-11-01 06:13:13,The Best Hollywood Coats",
     *   "82.1.106.8,2016-11-12 23:05:14,Buy wool coats for your pets",
     *   "92.130.6.144,2017-01-01 03:18:55,Buy wool coats for your pets",
     *   "92.130.6.145,2017-01-01 03:18:55,2017 Pet Mittens",
     * ]
     *
     * The client also sent over the IP addresses of all their users.
     *
     * all_user_ips = [
     *   #"User_ID,IP_Address",
     *    "2339985511,122.121.0.155",
     *   "234111110,122.121.0.1",
     *   "3123122444,92.130.6.145",
     *   "39471289472,2001:0db8:ac10:fe01:0000:0000:0000:0000",
     *   "8321125440,82.1.106.8",
     *   "99911063,92.130.6.144"
     * ]
     *
     *  Write a function to parse this data, determine how many times each ad was clicked,
     * then return the ad text, that ad's number of clicks, and how many of those ad clicks
     * were from users who made a purchase.
     *
     *
     *  Expected output:
     *  Bought Clicked Ad Text
     *  1 of 2  2017 Pet Mittens
     *  0 of 1  The Best Hollywood Coats
     *  3 of 3  Buy wool coats for your pets
     *
     *     用几个map来回倒腾就行。
     *
     *  Another one:
     * 第三题:
     * 这道题好像没在地里看到过. 统计点击广告的数量以及最后购买的数量. given 三个 list, 包括 purchasedUser(所有购买用户id),
     * ipaddressUser(IP地址和用户对应列表), history(浏览记录, 包括IP地址, 时间 和 商品(广告)). 应该就 split 一下,
     * 然后提取一下所有的数据, 遍历一下 history 就好.
     *
     * 第三题相当于是这样
     * String[] purchasedUser = ["203948535", "56856", "b86785"]
     * String[] history = ["234.64.23.123,2018.10.3,item A",
     * "234.457.45.123,2018.10.3,item A",
     * "34.62.34.3,2018.10.3,item B"]
     * String[] ipaddressUser = ["203948535,234.457.2345.123",
     * "74545,234.457.2345.123"
     * "2347,234.64.23.123"
     * ]
     * 比如 item A 有两个点击记录, 但实际上对应的 ip 地址所对应的用户 id 只有一人最终购买, 所以输出的就是这样的形式:
     * 1 of 2 item A
     * 给出两个pair vector
     * {访问者ip和对应访问网站的text}
     * {用户id和对应ip}
     * 再加上购买者的id vector
     * 返回每个网站text 所对应购买数和访问数
     *
     */
    static Map<String, Integer> count(Map<String, Integer> ori) {
        Map<String, Integer> subCount = new HashMap<>();

        for(String domain: ori.keySet()){
            int num = ori.get(domain);
            subCount.put(domain, subCount.getOrDefault(domain,0) + num);
            int index = domain.indexOf('.');

            while( index >= 0) {
                domain = domain.substring(index + 1);
                subCount.put(domain, subCount.getOrDefault(domain, 0) + num);
                index = domain.indexOf('.');
            }
        }
        printMap("domain counts: ", subCount);
        return subCount;
    }

    static List<String> longestCommonHistory(String[] user1, String[] user2) {
        int len1 = user1.length, len2 = user2.length;
        int[][] dp = new int[2][len2 + 1];
        int current = 0, maxLen = 0, end = 0;
        for(int i = 0; i <= len1; i++) {
            for(int j = 0; j <= len2; j++) {
                if(i == 0 || j == 0) {
                    dp[current][j] = 0;
                } else if (user1[i - 1].equals(user2[j - 1])) {
                    dp[current][j] = dp[ 1- current][j - 1] + 1;
                    if(dp[current][j] > maxLen) {
                        maxLen = dp[current][j];
                        end = j;
                    }
                } else {
                    dp[current][j] = 0;
                }
            }
            current = 1- current;
        }
        List<String> res = new ArrayList<>();
        while(maxLen-- > 0) {
            res.add(0, user2[--end]);
        }
        printList("history: ", res);
        return res;
    }

    static void printList(String s, List<String> list) {
        System.out.println(s);
        for(String i: list){
            System.out.println(i + " --> ");
        }
        System.out.println();
    }

    static void printMap(String s, Map<String, Integer> map) {
        System.out.println(s);
        for(String ss: map.keySet()){
            System.out.println(ss + " --> " + map.get(ss));
        }
        System.out.println();
    }

    // Driver Code
    public static void main(String args[])
    {
        Map<String, Integer> map = new HashMap<>();
        map.put("google.com", 60);
        map.put("yahoo.com", 50);
        map.put("sports.yahoo.com", 80);

        count(map);
        String[] user11 = new String[] {"3234.html", "xys.html", "7hsaa.html"};
        String[] user22 = new String[] {"3234.html", "sdhsfjdsh.html", "xys.html", "7hsaa.html"};
        String[] user0 = new String[] {"/nine.html", "/four.html", "/six.html", "/seven.html", "/one.html"};
        String[] user1 = new String[] {"/one.html", "/two.html", "/three.html", "/four.html", "/six.html"};
        String[] user2 = new String[] {"/nine.html", "/two.html", "/three.html", "/four.html", "/six.html", "/seven.html" };
        String[] user3 = new String[] {"/three.html", "/eight.html"};
        longestCommonHistory(user11, user22);
        longestCommonHistory(user0, user2);
        longestCommonHistory(user1, user2);
        longestCommonHistory(user0, user3);
        longestCommonHistory(user1, user3);
    }

}


