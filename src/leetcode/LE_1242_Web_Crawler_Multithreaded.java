package leetcode;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LE_1242_Web_Crawler_Multithreaded {
    /**
     * Given a url startUrl and an interface HtmlParser, implement a Multi-threaded
     * web crawler to crawl all links that are under the same hostname as startUrl.
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
     * As shown in the example url above, the hostname is example.org. For simplicity
     * sake, you may assume all urls use http protocol without any port specified.
     * For example, the urls http://leetcode.com/problems and http://leetcode.com/contest
     * are under the same hostname, while urls http://example.org/test and http://example.com/abc
     * are not under the same hostname.
     *
     * The HtmlParser interface is defined as such:
     *
     * interface HtmlParser {
     *   // Return a list of all urls from a webpage of given url.
     *   // This is a blocking call, that means it will do HTTP request and return when this request is finished.
     *   public List<String> getUrls(String url);
     * }
     *
     * Note that getUrls(String url) simulates performing a HTTP request.
     * You can treat it as a blocking function call which waits for a HTTP request to finish.
     * It is guaranteed that getUrls(String url) will return the urls within 15ms.
     * Single-threaded solutions will exceed the time limit so, can your multi-threaded web crawler do better?
     *
     * Below are two examples explaining the functionality of the problem, for custom testing purposes
     * you'll have three variables urls, edges and startUrl. Notice that you will only have access to
     * startUrl in your code, while urls and edges are not directly accessible to you in code.
     *
     * Follow up:
     *
     * Assume we have 10,000 nodes and 1 billion URLs to crawl. We will deploy the same software onto
     * each node. The software can know about all the nodes. We have to minimize communication between
     * machines and make sure each node does equal amount of work. How would your web crawler design change?
     *
     * What if one node fails or does not work?
     * How do you know when the crawler is done?
     *
     * Constraints:
     *
     * 1 <= urls.length <= 1000
     * 1 <= urls[i].length <= 300
     * startUrl is one of the urls.
     * Hostname label must be from 1 to 63 characters long, including the dots, may contain only the
     * ASCII letters from 'a' to 'z', digits from '0' to '9' and the hyphen-minus character ('-').
     * The hostname may not start or end with the hyphen-minus character ('-').
     * See:  https://en.wikipedia.org/wiki/Hostname#Restrictions_on_valid_hostnames
     * You may assume there're no duplicates in url library.
     *
     * Medium
     */


    /**
     * https://leetcode.com/problems/web-crawler-multithreaded/solutions/699006/java-blockingqueue-executorservice/
     *
     * Use main thread to take URLs from queue, and submit tasks into a thread pool. Threads in thread pool perform
     * I/O and add URLs back to the queue.
     *
     * The program exits when queue is empty and all tasks submitted to thread pool are completed.
     *
     * BlockingQueue is thread-safe.
     *
     * Other objects are only modified by the main thread, so no synchronization is needed.
     */
    class Solution {
        public List<String> crawl(String startUrl, HtmlParser htmlParser) {
            String hostName = getHostName(startUrl);

            List<String> res = new ArrayList<>();
            Set<String> visited = new HashSet<>();
            BlockingQueue<String> queue = new LinkedBlockingQueue<>();
            Deque<Future> tasks = new ArrayDeque<>();

            queue.offer(startUrl);

            // Create a thread pool of 4 threads to perform I/O operations.
            ExecutorService executor = Executors.newFixedThreadPool(4, r -> {
                Thread t = new Thread(r);
                // Leetcode doesn't allow executor.shutdown().
                // Use daemon threads so the program can exit.
                t.setDaemon(true);
                return t;
            });

            while (true) {
                String url = queue.poll();
                if (url != null) {
                    if (getHostName(url).equals(hostName) && !visited.contains(url)) {
                        res.add(url);
                        visited.add(url);
                        // Use a thread in thread pool to fetch new URLs and put them into the queue.
                        tasks.add(executor.submit(() -> {
                            List<String> newUrls = htmlParser.getUrls(url);
                            for (String newUrl : newUrls) {
                                queue.offer(newUrl);
                            }
                        }));
                    }
                } else {
                    if (!tasks.isEmpty()) {
                        // Wait for the next task to complete, which may supply new URLs into the queue.
                        Future nextTask = tasks.poll();
                        try {
                            nextTask.get();
                        } catch (InterruptedException | ExecutionException e) {}
                    } else {
                        // Exit when all tasks are completed.
                        break;
                    }
                }
            }
            return res;
        }

        private String getHostName(String url) {
            url = url.substring(7);
            String[] parts = url.split("/");
            return parts[0];
        }
    }


    interface HtmlParser {
        public List<String> getUrls(String url);
    }

    /**
     * https://leetcode.com/problems/web-crawler-multithreaded/discuss/427435/Java-Solution-from-MonoThread-(78)-to-MultiThread-(2ms)-beats-100-time-and-100-space
     *
     * Multi-Thread version using ConcurrentHashMap
     *
     * Runtime: 2 ms
     * Memory: 89.5 Mb
     */
    class Solution_Multi_Thread {
        public List<String> crawl(String startUrl, HtmlParser htmlParser) {

            // find hostname
            int index = startUrl.indexOf('/', 7);
            String hostname = (index != -1) ? startUrl.substring(0, index) : startUrl;

            // multi-thread
            Crawler crawler = new Crawler(startUrl, hostname, htmlParser);
            crawler.map = new ConcurrentHashMap<>(); // reset result as static property belongs to class, it will go through all of the test cases
            crawler.result = crawler.map.newKeySet();

            Thread thread = new Thread(crawler);
            thread.start();

            crawler.joinThread(thread); // wait for thread to complete

            return new ArrayList<>(crawler.result);
        }
    }

    static class Crawler implements Runnable {
        String startUrl;
        String hostname;
        HtmlParser htmlParser;

        public static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        /**
         * "map.newKeySet()": create a new Set backed by ConcurrentHashMap
         */
        public static Set<String> result = map.newKeySet();

        public Crawler(String startUrl, String hostname, HtmlParser htmlParser) {
            this.startUrl = startUrl;
            this.hostname = hostname;
            this.htmlParser = htmlParser;
        }

        @Override
        public void run() {
            /**
             * This is basically the logic inside while and for loops of the single thread BFS solution
             */
            if (this.startUrl.startsWith(hostname) && !this.result.contains(this.startUrl)) {
                this.result.add(this.startUrl);
                List<Thread> threads = new ArrayList<>();

                /**
                 * "htmlParser.getUrls(startUrl)" - blocking call
                 *
                 * Instantiate a new Crawler object and start it in a new thread for each child url.
                 */
                for (String s : htmlParser.getUrls(startUrl)) {
                    Crawler crawler = new Crawler(s, hostname, htmlParser);
                    Thread thread = new Thread(crawler);
                    thread.start();
                    threads.add(thread);
                }

                for (Thread t : threads) {
                    joinThread(t); // wait for all threads to complete
                }
            }
        }

        //        public static void joinThread(Thread thread) {
        public static void joinThread(Thread thread) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Java Streams + ConcurrentHashMap (60 ms)
     */
    class Solution1 {
        public List<String> crawl(String startUrl, HtmlParser htmlParser) {
            String hostname = getHostname(startUrl);

            Set<String> visited = ConcurrentHashMap.newKeySet();
            visited.add(startUrl);

            return crawl(startUrl, htmlParser, hostname, visited)
                    .collect(Collectors.toList());
        }

        private Stream<String> crawl(String startUrl, HtmlParser htmlParser, String hostname, Set<String> visited) {
            Stream<String> stream = htmlParser.getUrls(startUrl)
                    .parallelStream()
                    .filter(url -> isSameHostname(url, hostname))
                    .filter(url -> visited.add(url))
                    .flatMap(url -> crawl(url, htmlParser, hostname, visited));

            return Stream.concat(Stream.of(startUrl), stream);
        }

        private String getHostname(String url) {
            int idx = url.indexOf('/', 7);
            return (idx != -1) ? url.substring(0, idx) : url;
        }

        private boolean isSameHostname(String url, String hostname) {
            if (!url.startsWith(hostname)) {
                return false;
            }
            return url.length() == hostname.length() || url.charAt(hostname.length()) == '/';
        }
    }

    /**
     * Solution with bounded thread pool
     */
    class Solution_Bounded_Thread_Pool {
        public List<String> crawl(String startUrl, HtmlParser htmlParser) {
            ExecutorService executorService = Executors.newFixedThreadPool(100);
            int index = startUrl.indexOf('/', 7);
            String hostname = (index != -1) ? startUrl.substring(0, index) : startUrl;
            Set<String> masterList = ConcurrentHashMap.newKeySet();
            List<Future<UrlCrawler>> futures = new ArrayList<>();

            submitNewUrl(startUrl, hostname, futures, masterList, htmlParser, executorService);

            while (futures.size() > 0) {
                crawlUrls(futures, hostname, masterList, htmlParser, executorService);
            }

            return new ArrayList<>(masterList);
        }

        private void crawlUrls(List<Future<UrlCrawler>> futures, String hostname,
                               Set<String> masterList, HtmlParser htmlParser, ExecutorService executorService) {
            Set<UrlCrawler> urlCrawlers = new HashSet<>();

            Iterator<Future<UrlCrawler>> iterator = futures.iterator();
            while (iterator.hasNext()) {
                Future<UrlCrawler> f = iterator.next();
                if (f.isDone()) {
                    iterator.remove();
                    try {
                        urlCrawlers.add(f.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (UrlCrawler crawler : urlCrawlers) {
                for (String s : crawler.getUrlList()) {
                    submitNewUrl(s, hostname, futures, masterList, htmlParser, executorService);
                }
            }
        }

        private void submitNewUrl(
                String startUrl, String hostname, List<Future<UrlCrawler>> futures,
                Set<String> masterList, HtmlParser htmlParser, ExecutorService executorService) {
            if (masterList.contains(startUrl)) {
                return;
            }

            masterList.add(startUrl);
            UrlCrawler crawler = new UrlCrawler(startUrl, htmlParser, hostname);
            futures.add(executorService.submit(crawler));
        }

        class UrlCrawler implements Callable<UrlCrawler> {
            private final String startUrl;
            private final HtmlParser htmlParser;
            private final String hostName;
            Set<String> urls = new HashSet<>();

            UrlCrawler(String startUrl, HtmlParser htmlParser, String hostName) {
                this.startUrl = startUrl;
                this.htmlParser = htmlParser;
                this.hostName = hostName;
            }

            public UrlCrawler call() {
                for (String s : htmlParser.getUrls(startUrl)) {
                    if (s.startsWith(hostName)) {
                        urls.add(s);
                    }
                }
                return this;
            }

            public Set<String> getUrlList() {
                return urls;
            }
        }
    }
}
