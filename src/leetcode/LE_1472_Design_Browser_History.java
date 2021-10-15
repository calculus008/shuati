package leetcode;

import java.util.*;

public class LE_1472_Design_Browser_History {
    /**
     * You have a browser of one tab where you start on the homepage and you can visit another url, get back in the
     * history number of steps or move forward in the history number of steps.
     *
     * Implement the BrowserHistory class:
     *
     * - BrowserHistory(string homepage) Initializes the object with the homepage of the browser.
     * - void visit(string url) Visits url from the current page. It clears up all the forward history.
     * - string back(int steps) Move steps back in history. If you can only return x steps in the history and steps > x,
     *   you will return only x steps. Return the current url after moving back in history at most steps.
     * - string forward(int steps) Move steps forward in history. If you can only forward x steps in the history and
     *   steps > x, you will forward only x steps. Return the current url after forwarding in history at most steps.
     *
     * Example:
     * Input:
     * ["BrowserHistory","visit","visit","visit","back","back","forward","visit","forward","back","back"]
     * [["leetcode.com"],["google.com"],["facebook.com"],["youtube.com"],[1],[1],[1],["linkedin.com"],[2],[2],[7]]
     * Output:
     * [null,null,null,null,"facebook.com","google.com","facebook.com",null,"linkedin.com","google.com","leetcode.com"]
     *
     * Explanation:
     * BrowserHistory browserHistory = new BrowserHistory("leetcode.com");
     * browserHistory.visit("google.com");       // You are in "leetcode.com". Visit "google.com"
     * browserHistory.visit("facebook.com");     // You are in "google.com". Visit "facebook.com"
     * browserHistory.visit("youtube.com");      // You are in "facebook.com". Visit "youtube.com"
     * browserHistory.back(1);                   // You are in "youtube.com", move back to "facebook.com" return "facebook.com"
     * browserHistory.back(1);                   // You are in "facebook.com", move back to "google.com" return "google.com"
     * browserHistory.forward(1);                // You are in "google.com", move forward to "facebook.com" return "facebook.com"
     * browserHistory.visit("linkedin.com");     // You are in "facebook.com". Visit "linkedin.com"
     * browserHistory.forward(2);                // You are in "linkedin.com", you cannot move forward any steps.
     * browserHistory.back(2);                   // You are in "linkedin.com", move back two steps to "facebook.com" then to "google.com". return "google.com"
     * browserHistory.back(7);                   // You are in "google.com", you can move back only one step to "leetcode.com". return "leetcode.com"
     *
     * leetcode.com -> google.com -> facebook.com -> youtube.com  linkedin.com
     *                    |__________|   |    |__________|             |
     *                                   |_____________________________|
     * Constraints:
     * 1 <= homepage.length <= 20
     * 1 <= url.length <= 20
     * 1 <= steps <= 100
     * homepage and url consist of  '.' or lower case English letters.
     * At most 5000 calls will be made to visit, back, and forward.
     *
     * Medium
     *
     * https://leetcode.com/problems/design-browser-history/
     */

    /**
     * Double Linked List
     *
     * Takes O(n) for back and forward
     */
    class BrowserHistory1 {
        class Node {
            Node pre;
            Node next;
            String val;

            public Node(String val) {
                this.val = val;
            }
        }

        Node head;
        Node cur;

        public BrowserHistory1(String homepage) {
            cur = new Node(homepage);
        }

        public void visit(String url) {
            Node n = new Node(url);
            if (cur.next != null) {
                cur.next.pre = null;
            }
            cur.next = n;
            n.pre = cur;
            cur = n;
        }

        public String back(int steps) {
            int x = steps;
            while (cur.pre != null && x > 0) {
                cur = cur.pre;
                x--;
            }

            return cur.val;
        }

        public String forward(int steps) {
            int x = steps;
            while (cur.next != null && x > 0) {
                cur = cur.next;
                x--;
            }

            return cur.val;
        }
    }

    /**
     * Use ArrayList
     *
     * Use tow pointers to remember current index and tail index.
     *
     * Takes O(1) for back and forward
     */
    class BrowserHistory2 {
        List<String> list;
        int tail;
        int cur;

        public BrowserHistory2(String homepage) {
            list = new ArrayList<>();
            list.add(homepage);
            tail = 0;
            cur = 0;
        }

        public void visit(String url) {
            if (cur == list.size() - 1) {
                list.add(url);
                cur++;
            } else {
                cur++;
                list.set(cur, url);
            }
            /**
             * tail controls how far we can go forward
             */
            tail = cur;
        }

        public String back(int steps) {
            int x = Math.max(0, cur - steps);
            cur = x;
            return list.get(cur);
        }

        public String forward(int steps) {
            /**
             * Use tail to control max forward steps
             */
            int x = Math.min(cur + steps, tail);
            cur = x;
            return list.get(cur);
        }
    }
}
