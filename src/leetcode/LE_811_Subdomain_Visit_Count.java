package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LE_811_Subdomain_Visit_Count {
    /**
     * A website domain like "discuss.leetcode.com" consists of various subdomains.
     * At the top level, we have "com", at the next level, we have "leetcode.com",
     * and at the lowest level, "discuss.leetcode.com". When we visit a domain like
     * "discuss.leetcode.com", we will also visit the parent domains "leetcode.com"
     * and "com" implicitly.
     *
     * Now, call a "count-paired domain" to be a count (representing the number of
     * visits this domain received), followed by a space, followed by the address.
     * An example of a count-paired domain might be "9001 discuss.leetcode.com".
     *
     * We are given a list cpdomains of count-paired domains. We would like a list
     * of count-paired domains, (in the same format as the input, and in any order),
     * that explicitly counts the number of visits to each subdomain.
     *
     * Example 1:
     * Input:
     * ["9001 discuss.leetcode.com"]
     * Output:
     * ["9001 discuss.leetcode.com", "9001 leetcode.com", "9001 com"]
     * Explanation:
     * We only have one website domain: "discuss.leetcode.com". As discussed above,
     * the subdomain "leetcode.com" and "com" will also be visited. So they will all be visited 9001 times.
     *
     * Example 2:
     * Input:
     * ["900 google.mail.com", "50 yahoo.com", "1 intel.mail.com", "5 wiki.org"]
     * Output:
     * ["901 mail.com","50 yahoo.com","900 google.mail.com","5 wiki.org","5 org","1 intel.mail.com","951 com"]
     * Explanation:
     * We will visit "google.mail.com" 900 times, "yahoo.com" 50 times, "intel.mail.com" once and "wiki.org" 5 times. For the subdomains, we will visit "mail.com" 900 + 1 = 901 times, "com" 900 + 50 + 1 = 951 times, and "org" 5 times.
     *
     * Notes:
     *
     * The length of cpdomains will not exceed 100.
     * The length of each domain name will not exceed 100.
     * Each address will have either 1 or 2 "." characters.
     * The input count in any count-paired domain will not exceed 10000.
     * The answer output can be returned in any order.
     *
     * Easy
     */

    /**
     * 这道题坏就坏在诱导你用string.split(),然后用StringBuilder拼装。实际上，这样做行不通。
     * 应为 ： "google.mail.com"， 要分为 ： "google.mail.com"， "mail.com"， "com",
     * StringBuilder 根本做不到，这能用substring()来做。
     */
    class Solution1 {
        public List<String> subdomainVisits(String[] cpdomains) {
            List<String> res = new ArrayList<>();
            if (cpdomains == null || cpdomains.length == 0) return res;

            Map<String, Integer> map = new HashMap<>();
            for (String d : cpdomains) {
                String[] s = d.split(" ");
                int num = Integer.valueOf(s[0]);

                for (int i = 0; i < s[1].length(); i++) {
                    if (s[1].charAt(i) == '.') {
                        String key = s[1].substring(i + 1);
                        map.put(key, map.getOrDefault(key, 0) + num);
                    }
                }
                /**
                 * 整个的URL最后单独处理。
                 */
                map.put(s[1], map.getOrDefault(s[1], 0) + num);
            }

            for (String key : map.keySet()) {
                res.add(String.valueOf(map.get(key)) + " " + key);
            }

            return res;
        }
    }

    /**
     * no use String.split(), much faster
     */
    class Solution2 {
        public List<String> subdomainVisits(String[] cpdomains) {
            List<String> res = new ArrayList<>();
            if (cpdomains == null || cpdomains.length == 0) return res;

            Map<String, Integer> map = new HashMap<>();
            for (String d : cpdomains) {
                int idx = 0;
                while (d.charAt(idx) != ' ') {
                    idx++;
                }
                int num = Integer.valueOf(d.substring(0, idx));
                idx++;

                String url = d.substring(idx);

                map.put(url, map.getOrDefault(url, 0) + num);
                for (int i = idx; i < d.length(); i++) {
                    if (d.charAt(i) == '.') {
                        String key = d.substring(i + 1);
                        map.put(key, map.getOrDefault(key, 0) + num);
                    }
                }
            }

            for (String key : map.keySet()) {
                res.add(String.valueOf(map.get(key)) + " " + key);
            }

            return res;
        }
    }
}
