package lintcode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by yuank on 7/19/18.
 */
public class LI_624_Remove_Substrings {
    /**
         Given a string s and a set of n substrings. You are supposed to remove every instance of
         those n substrings from s so that s is of the minimum length and output this minimum length.

         Example
         Given s = ccdaabcdbb, substrs = ["ab", "cd"]
         Return 2

         Explanation:
         ccdaabcdbb -> ccdacdbb -> cabb -> cb (length = 2)

         Medium
     */

    /**
     * BFS
     *
     * 变化的地方是如何expand. 这里是loop through dict, 删除所有当前可见的w (dict中的元素).
     * 因为数顺序遍历dict, 会出现这样的情况 ： 先删了所有w1, 当loop到w3时，删掉w3, 又会出现w1.
     * 所以用BFS，把每次处理完的string存入queue, 在后面重新处理。
     *
     * Example : s = ccdaabcdbb, substrs = ["ab", "cd"]
     *
     * 从BFS每层的视角看， 每完成一次对dict的遍历，就产生新的一层。
     *
     * ccdaabcdbb                                                  level 0    Queue - ccdaabcdbb
     *    |
     * ccdacdbb,                      caabcdbb  -> caabbb          level 1    Queue - ccdacdbb, caabcdbb, caabbb
     * (删'ab'）                     （删'cd'1）    （删'cd'2）
     *    |                              |             \
     * 没有‘ab', cacdbb -> cabb       cacdbb, caabbb  cabb, cb     level 2    Queue - cacdbb, cabb, caabbb, cb
     *             |
     *           cabb      cb         cabb    cabb->cb, cb         Now, all newly generated Strings already exist in visited,
     *                                                             none of them is added to queue, now queue is empty,
     *                                                             BFS is done.
     *
     *                                                             We can see the shortest string "cb" is generated during
     *                                                             BFS, so have to keep updating min to get the final anwser.
     *
     */

    public int minLength(String s, Set<String> dict) {
        if (dict == null || dict.size() == 0) {
            return s.length();
        }

        Set<String> visited = new HashSet<>();
        visited.add(s);//!!!

        Queue<String> q = new LinkedList<>();
        q.offer(s);

        int min = s.length();

        while (!q.isEmpty()) {
            s = q.poll();

            for (String w : dict) {
                int idx = s.indexOf(w);

                while (idx >= 0) {
                    String s1 = s.substring(0, idx) + s.substring(idx + w.length());

                    if (!visited.contains(s1)) {
                        min = Math.min(min, s1.length());
                        q.offer(s1);
                        visited.add(s1);//!!!
                    }

                    //!!!
                    idx = s.indexOf(w, idx + 1);
                }

            }
        }

        return min;
    }
}
