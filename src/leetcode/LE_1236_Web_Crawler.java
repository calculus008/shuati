package leetcode;

import java.util.*;

public class LE_1236_Web_Crawler {
    /**
     * Given a url startUrl and an interface HtmlParser, implement a web crawler to crawl
     * all links that are under the same hostname as startUrl.
     *
     * Return all urls obtained by your web crawler in any order.
     *
     * Your crawler should:
     *
     * Start from the page: startUrl
     * Call HtmlParser.getUrls(url) to get all urls from a webpage of given url.
     * Do not crawl the same link twice.
     * Explore only the links that are under the same hostname as startUrl.
     *
     * As shown in the example url above, the hostname is example.org. For simplicity sake,
     * you may assume all urls use http protocol without any port specified. For example,
     * the urls http://leetcode.com/problems and http://leetcode.com/contest are under
     * the same hostname, while urls http://example.org/test and http://example.com/abc
     * are not under the same hostname.
     *
     * The HtmlParser interface is defined as such:
     *
     * interface HtmlParser {
     *   // Return a list of all urls from a webpage of given url.
     *   public List<String> getUrls(String url);
     * }
     *
     * Below are two examples explaining the functionality of the problem, for custom testing
     * purposes you'll have three variables urls, edges and startUrl. Notice that you will only
     * have access to startUrl in your code, while urls and edges are not directly accessible
     * to you in code.
     *
     * Constraints:
     *
     * 1 <= urls.length <= 1000
     * 1 <= urls[i].length <= 300
     * startUrl is one of the urls.
     * Hostname label must be from 1 to 63 characters long, including the dots, may contain only
     * the ASCII letters from 'a' to 'z', digits  from '0' to '9' and the hyphen-minus character ('-').
     * The hostname may not start or end with the hyphen-minus character ('-').
     * See:  https://en.wikipedia.org/wiki/Hostname#Restrictions_on_valid_hostnames
     * You may assume there're no duplicates in url library.
     *
     * Medium
     *
     * LE_1242_Web_Crawler_Multithreaded
     */

    /**
     * // This is the HtmlParser's API interface.
     * // You should not implement it, or speculate about its implementation
     * interface HtmlParser {
     *     public List<String> getUrls(String url) {}
     * }
     */
    interface HtmlParser {
        public List<String> getUrls(String url);
    }

    class Solution_BFS_1 {
        public List<String> crawl(String startUrl, HtmlParser htmlParser) {
            Set<String> visited = new HashSet<>();
            Queue<String> queue = new LinkedList<>();

            String hostname = getHostname(startUrl);

            queue.offer(startUrl);
            visited.add(startUrl);

            while (!queue.isEmpty()) {
                String currentUrl = queue.poll();
                for (String url : htmlParser.getUrls(currentUrl)) {
                    if (url.contains(hostname) && !visited.contains(url)) {
                        queue.offer(url);
                        visited.add(url);
                    }
                }
            }

            return new ArrayList<String>(visited);
        }

        private String getHostname(String Url) {
            String[] ss = Url.split("/");
            return ss[2];
        }
    }

    class Solution_BFS_2 {
        public List<String> crawl(String startUrl, HtmlParser htmlParser) {

            // find hostname
            int index = startUrl.indexOf('/', 7);
            String hostname = (index != -1) ? startUrl.substring(0, index) : startUrl;

            // bfs
            Set<String> result = new HashSet<>();
            Queue<String> queue = new LinkedList<>();
            queue.add(startUrl);

            while(!queue.isEmpty()){
                String tmp = queue.remove();
                if(tmp.startsWith(hostname) && !result.contains(tmp)){
                    result.add(tmp);
                    for(String s: htmlParser.getUrls(tmp)){
                        queue.add(s);
                    }
                }
            }
            return new ArrayList<>(result);
        }
    }


    class Solution_DFS {
        public List<String> crawl(String startUrl, HtmlParser htmlParser) {
            String[] sArr = startUrl.split("/" , -1);
            String hostName = sArr[2];

            Set<String> visited = new HashSet<>(Arrays.asList(startUrl));
            dfs(visited, hostName, startUrl, htmlParser);

            return new ArrayList<>(visited);
        }

        void dfs(Set<String> visited, String hostname, String startUrl, HtmlParser htmlParser){
            for(String url : htmlParser.getUrls(startUrl)){
                if(url.contains(hostname) && visited.add(url)) {
                    dfs(visited,hostname,url,htmlParser);
                }
            }
        }
    }
}
