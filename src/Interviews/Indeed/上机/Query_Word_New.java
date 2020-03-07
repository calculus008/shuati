package src.Interviews.Indeed.上机;

import java.util.*;

public class Query_Word_New {
    /**
     * Most Related Word
     *
     * 找相关词那道，就是A输⼊入java，php，B输⼊入java，python，然后C输⼊入了了 php，问你给C推荐的下⼀一个单词应该是什什么。
     * 搜词，然后按关联度推荐词
     *
     * 上机题貌似就万年不变的query与job description去match。
     * 具体来说，给了一堆job description，每个是一个string，确保了没有标点符号、只有小写字母，
     * 比如{"we want someone good at java and spring", "are you good at cpp"}；
     * 然后给一堆query，要求对每个query找出最match的最多10个job description的id，依次输出这些id，
     * 如果一个都没有就输出-1。对于match程度的定义，是有一个叫match count的概念，就是query与某个jd匹配了多少单词。
     * 例如一条query是"good at java"，那么对于上述的两条jd的match count分别是2和1。match count越大的jd，
     * 就代表match程度越高，如果有两个jd具有相同的match count，那就认为id小的比id大的更match。
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
