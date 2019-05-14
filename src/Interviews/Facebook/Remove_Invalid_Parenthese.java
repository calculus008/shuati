package Interviews.Facebook;

public class Remove_Invalid_Parenthese {
    /**
     * 不需要return 所有，只需要return 一个valid的解即可。
     * 返回一个结果只需要用stack， 不需要dfs，简单很多了
     * 变形，要求只输出一个有效结果，不用输出所有，用DFS/BFS面试官会觉得太复杂，我用的直接遍历两遍，欢迎讨论，C++：
     *
     * string removeParenth(string s) {
     *     if (s.empty()) return "";
     *     int numOpen = 0;
     *     int numClose = 0;
     *     string temp="";
     *     for (int i = 0; i < int(s.size()); i++) {
     *         if (s[i] == '(') numOpen++;
     *         if (s[i] == ')') numClose++;
     *         if (numClose > numOpen) {//close > open
     *             numClose--;
     *         }
     *         else {
     *             temp += s[i];
     *         }
     *     }
     *     string res="";
     *     numOpen = 0;
     *     numClose = 0;
     *     for (int i = int(temp.size()); i >= 0; i--) {
     *         if (temp[i] == '(') numOpen++;
     *         if (temp[i] == ')') numClose++;
     *         if (numOpen > numClose) {//open > close
     *             numOpen--;
     *         }
     *         else {
     *             res += temp[i];
     *         }
     *     }
     *     reverse(res.begin(), res.end());
     *     return res;
     * }
     */
}
