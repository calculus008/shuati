package lintcode;

import java.util.*;

public class LI_550_Top_K_Frequent_Words_II {
    /**
         Find top k frequent words in realtime data stream.

         Implement three methods for Topk Class:

         TopK(k). The constructor.
         add(word). Add a new word.
         topk(). Get the current top k frequent words.
         If two words have the same frequency, rank them by alphabet.

         Have you met this question in a real interview?
         Example
         TopK(2)
         add("lint")
         add("code")
         add("code")
         topk()
         >> ["code", "lint"]

         Variation:
         假设有个网站，后台有update接口：接一个字符串string，还有查询接口get:
         返回按出现频率排序后的所有字符串。接受数据是以stream的形式，会不断接受新的string。
         让设计update(string), get() 接口

         For this variation, it returns all words, not just top k, so no need to
         to the following in TreeSet in topK():
             if (topk.size() > k) {
                topk.pollLast();
             }

     */

    public class TopK {
        private Map<String, Integer> words = null;
        private NavigableSet<String> topk = null;
        private int k;

        /**
         * !!!
         * 定义如何在topK里排序
         * 如果a, b频率不同， 频率高的排在前面
         * 如果a, b频率相同， 按字典顺序排。
         */
        private Comparator<String> myComparator = new Comparator<String>() {
            public int compare(String left, String right) {
                if (left.equals(right))
                    return 0;

                int left_count = words.get(left);
                int right_count = words.get(right);
                if (left_count != right_count) {
                    return right_count - left_count;
                }
                return left.compareTo(right);
            }
        };

        public TopK(int k) {
            // initialize your data structure here
            this.k = k;
            words = new HashMap<String, Integer>();
            topk = new TreeSet<String>(myComparator);
        }

        /**
         * O(logk)
         */
        public void add(String word) {
            if (words.containsKey(word)) {
                /**
                 * 删除，在后面加入，enforce sorting
                 */
                if (topk.contains(word)) {
                    topk.remove(word);
                }
                words.put(word, words.get(word) + 1);
            } else {
                words.put(word, 1);
            }

            topk.add(word);
            if (topk.size() > k) {
                topk.pollLast();
            }
        }

        /**
         *
         * O(k)
         * @return
         */
        public List<String> topk() {
            List<String> results = new ArrayList<String>();
            Iterator it = topk.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                results.add(str);
            }
            return results;
        }
    }

    public class TopK_Practice {
        HashMap<String, Integer> map;
        TreeSet<String> set;
        int k;

        public TopK_Practice(int k) {
            this.k = k;

            map = new HashMap<>();

            Comparator<String> comparator = (a, b) -> {
                int f1 = map.get(a);
                int f2 = map.get(b);
                if (f1 != f2) {
                    return f2 - f1;
                } else {
                    return a.compareTo(b);
                }
            };

            set = new TreeSet<>(comparator);
        }

        /*
         * @param word: A string
         * @return: nothing
         */
        public void add(String word) {
            if (map.containsKey(word)) {
                set.remove(word);
            }
            map.put(word, map.getOrDefault(word, 0) + 1);
            set.add(word);

            if (set.size() > k) {
                set.pollLast();
            }
        }

        /*
         * @return: the current top k frequent words.
         */
        public List<String> topk() {
            List<String> res = new ArrayList<>();
            Iterator<String> it = set.iterator();

            while (it.hasNext()) {
                res.add(it.next());
            }

            return res;
        }
    }
}