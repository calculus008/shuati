package Interviews.Amazon;

import java.util.*;

/**
 * 做一个游戏的即时的排名系统
 * ex: 玩家的分数可能是
 * [10, 22, 1, 2, 5, 5, 2]
 * 求当下分数为5的人排名是多少
 *
 * 备注：此游戏人数会随时增加, 并且分数会随时变动
 * 这轮讲完BQ基本上只给我20分钟, 直接挂掉
 * 弱弱的提出先 sort, 再用 binary search 插入新的分数
 * 求问这题有没有类似的题目？
 *
 * 题目说了求分数5的排名。treemap解决。
 * 后来想了一下面试官确实有再给Tree的提示，提到如果想要变成log（n）的话应该怎么做
 */

public class Game_Ranking {
    public static int getRanking(List<Integer> scores, int score) {
        TreeMap<Integer, Integer> map = new TreeMap<>((a, b) -> b - a);

        for (int num : scores) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int res = 0;

        Iterator<Integer> it =  map.keySet().iterator();
        while (it.hasNext()) {
            int cur = it.next();
            if (cur <= score) break;
            res += map.get(cur);
        }

        return res + 1;
    }

    public static void main(String[] args) {
        List<Integer> scores = Arrays.asList(10, 22, 10, 22, 1, 2, 5, 5, 2);
        System.out.println(getRanking(scores, 5));
    }
}
