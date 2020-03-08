package Interviews.Indeed.上机;

import java.util.*;

public class Most_Related_Query_New {
    /**
     * hackerrank做题，这道题是以前题的变形，输入是一堆query，每个query包含了user + query word,
     * 输出是每当进来一个query时，根据之前的query，要返回一个最相关的单词，这题能够保证同一个user，
     * 只会query某个单词一次。
     *
     * 具体看例子：
     * Input:
     * 7
     * A python
     * B java
     * A java
     * B php
     * C python
     * C java
     * D java
     *
     * Output:
     * 0
     * 0
     * 0
     * 0
     * 1 java       : 因为目前 A: pyhon java, B: java php, search过python的人中还search最多的是java，1次
     * 1 python php : 此时 A: pyhon java, B: java php, C: python
     * 2 python     : 此时 A: pyhon java, B: java php, C: python java
     *
     * 这道题我一开始一直在map如何设计上纠结着，naive的方法最后一直有三个case过不了，所以思考的过程是：
     * map1<String, Set<String>> // user => the words he searched before
     * 大概能过6个case，开始优化
     * 加了个map2<String, List<String>> // word => list of users who searched this word before
     * 这个的话大概又过了4个，还是有三个过不了，此时我心怀侥幸，一直在优化中间的过程，而不是在优化思想了，
     * 结果没成功。直到最后还有20分钟时，换掉了思路，用了一个这个map
     *
     * map3<String, Map<String, Integer>> // word => related word and times
     * 这样的话，每次一个新词进来，直接就能找到相关的词，然后找到出现次数最多的就好。然后再利用map1中这
     * 个user之前query的结果，去update map3。这个思路没有debug完，思路讲给了面试官听，他肯定了这个想法。
     */
    class Pair {
        int count;
        List<String> words;

        public Pair() {
            this.count = 0;
            words = new ArrayList<>();
        }
    }

    /**
     * user -> list of the words the user has queried
     * Since each user only queries a word for once, we can use list instead of set
     */
    Map<String, List<String>> userMap;

    /**
     * word -> map : related word -> frequency
     */
    Map<String, Map<String, Integer>> wordMap;

    /**
     * word -> Pair (max count, list of words have the max count), dynamically maintains the result for each word appeared so far.
     */
    Map<String, Pair> res;

    public Most_Related_Query_New() {
        userMap = new HashMap<>();
        wordMap = new HashMap<>();
        res = new HashMap<>();
    }

    public String query_naive(String user, String word) {
        if (!userMap.containsKey(user)) {
            userMap.put(user, new ArrayList<>());
        }
        if (!wordMap.containsKey(word)) {
            wordMap.put(word, new HashMap<>());
        }

        /**
         * Must make deep copy here since the map will be updated later.
         */
        Map<String, Integer> related = new HashMap<>(wordMap.get(word));

        for (String w : userMap.get(user)) {
//            if (w.equals(word)) continue; //since each user only queries a word once, we don't need to this check
            Map<String, Integer> m1 = wordMap.get(w);
            m1.put(word, m1.getOrDefault(word, 0) + 1);

            Map<String, Integer> m2 = wordMap.get(word);
            m2.put(w, m2.getOrDefault(w, 0) + 1);
        }

        /**
         * Must do it here, not before the last for loop
         */
        userMap.get(user).add(word);

        /**
         * find max
         */
        int max = 0;
        List<String> res = new ArrayList<>();
        for (String key : related.keySet()) {
            if (related.get(key) > max) {
                max = related.get(key);
            }
        }

        if (max == 0) return "0";

        for (String key : related.keySet()) {
            if (related.get(key) == max) {
                res.add(key);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(max);
        for (String r : res) {
            sb.append(" ").append(r);
        }

        return sb.toString();
    }

    public String query(String user, String word) {
        if (!userMap.containsKey(user)) {
            userMap.put(user, new ArrayList<>());
        }
        if (!wordMap.containsKey(word)) {
            wordMap.put(word, new HashMap<>());
        }
        if (!res.containsKey(word)) {
            res.put(word, new Pair());
        }

        Pair max = res.get(word);

        StringBuilder sb = new StringBuilder();
        sb.append(max.count);
        for (String w : max.words) {
            sb.append(" ").append(w);
        }

        for (String w : userMap.get(user)) {
            Map<String, Integer> m1 = wordMap.get(w);
            m1.put(word, m1.getOrDefault(word, 0) + 1);

            if (m1.get(word) == res.get(w).count) {
                res.get(w).words.add(word);
            } else if (m1.get(word) > res.get(w).count){
                res.put(w, new Pair());
                res.get(w).count = m1.get(word);
                res.get(w).words.add(word);
            }

            Map<String, Integer> m2 = wordMap.get(word);
            m2.put(w, m2.getOrDefault(w, 0) + 1);

            if (m2.get(w) == res.get(word).count) {
                res.get(word).words.add(w);
            } else if (m2.get(w) > res.get(word).count){
                res.put(word, new Pair());
                res.get(word).count = m2.get(w);
                res.get(word).words.add(w);
            }
        }

        userMap.get(user).add(word);

//        for (String key : res.keySet()) {
//            System.out.println(key + "->" + res.get(key).words);
//        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Most_Related_Query_New test = new Most_Related_Query_New();

        Scanner sc = new Scanner(System.in);
        int number = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < number; i++) {
            String q = sc.nextLine();
            String[] parts = q.split(" ");
            String user = parts[0];
            String word = parts[1];
            System.out.println(test.query(user, word));
        }
    }
}
