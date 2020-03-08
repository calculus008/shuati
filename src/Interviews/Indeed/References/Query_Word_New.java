package src.Interviews.Indeed.References;

import java.util.*;

public class Query_Word_New {
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
     *
     * 1 java(因为目前A: pyhon java, B: java php, search过python的人中还search最多的是java，1次)
     * 1 python php(此时 A: pyhon java, B: java php, C: python)
     * 2 python(此时A: pyhon java, B: java php, C: python java)
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
        int maxCount;
        List<String> words;
        public Pair() {
            maxCount = 0;
            words = new ArrayList<>();
        }
    }

    /**
     * User -> the set of words for the user
     */
    Map<String, Set<String>> users;

    /**
     * word -> map : user -> count
     */
    Map<String, Map<String, Integer>> wordR;

    /**
     * User -> Pair (words and maxCount)
     */
    Map<String, Pair> ans;

    public Query_Word_New() {
        users = new HashMap<>();
        wordR = new HashMap<>();
        ans = new HashMap<>();
    }

    public String search(String user, String word) {
        if (!users.containsKey(user))
            users.put(user, new HashSet<>());
        if (!wordR.containsKey(word))
            wordR.put(word, new HashMap<>());
        if (!ans.containsKey(word))
            ans.put(word, new Pair());

        Pair max = ans.get(word);

        // update maps
        for (String each : users.get(user)) {

            // update word -> each (user)
            if (!wordR.get(word).containsKey(each))
                wordR.get(word).put(each, 1);
            else
                wordR.get(word).put(each, wordR.get(word).get(each) + 1);

            if (ans.get(word).maxCount == wordR.get(word).get(each))
                ans.get(word).words.add(each);
            else if (ans.get(word).maxCount < wordR.get(word).get(each)) {
                ans.put(word, new Pair());
                ans.get(word).maxCount = wordR.get(word).get(each);
                ans.get(word).words.add(each);
            }

            // update each (user) -> word
            if (!wordR.get(each).containsKey(word))
                wordR.get(each).put(word, 1);
            else
                wordR.get(each).put(word, wordR.get(each).get(word) + 1);

            if (ans.get(each).maxCount == wordR.get(each).get(word))
                ans.get(each).words.add(word);
            else if (ans.get(each).maxCount < wordR.get(each).get(word)) {
                ans.put(each, new Pair());
                ans.get(each).maxCount = wordR.get(each).get(word);
                ans.get(each).words.add(word);
            }
        }
        users.get(user).add(word);  // do not forget this

        StringBuilder sb = new StringBuilder();
        sb.append(max.maxCount);
        for (String each : max.words) {
            sb.append(" " + each);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Query_Word_New qr = new Query_Word_New();
        Scanner sc = new Scanner(System.in);
        int numLines = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < numLines; i++) {
            String[] parts = sc.nextLine().split(" ");
            System.out.println(qr.search(parts[0], parts[1]));
        }
    }
}
