package Interviews.Apple;

public class Click_Stream {
    /**
     * 给一串log，格式如下：
     * timestamp  userId  sessionId  url
     *
     * 找出最高频长度为3的clickstream。clickstream 按 userId 和 sessionId 聚集。找不到返回空list。
     *
     * 如：
     * 1001  userA  session_a  page1
     * 1002 userB session_k page3
     * 1004 userC session_h page1
     * 1007 userB session_k page3
     * 1010 userA  session_a  page3
     * 1011 userB session_k page7
     * 1012 userA session_a page7
     * 1014 userA session_a page9
     * 1016 userC session_h page3
     * 1018 userC session_h page7
     *
     * (userA, session_a): page 1 -> 3 -> 7 -> 9
     * (userB, session_k): page 3 -> 7
     * (userB, session_p): page 9
     * (userC, session_h): page 1 -> 3 -> 7
     *
     * page 1 -> 3 -> 7 为最高频，返回。
     *
     *
     * 我的做法就是先 Map<userId + sessionId, list of pages by timestamp order>
     * 然后Map<clickstream with length 3, count>。 key 是string，就把三个url用空格连起来。
     *
     * follow-up:
     * clickstream of k ?
     * 大数据 ？
     */
}
