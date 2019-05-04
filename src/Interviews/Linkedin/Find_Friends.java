package Interviews.Linkedin;

import java.util.*;

/**
 * 已知一个函数，输入用户ID，可以返回该用户的所有友好（degree 1 friends），按好友ID从小到大排序。
 要求实现函数来输出返回一个用户的所有好友的好友(degree 2 friends), 以及 degree 3 friends

 给你一个函数 可以返回当前的指定的id的friends，然后利用这个函数 找出给你的id（input） 的 两个以外（相距两层）
 的有最多mutual friends 的 id有哪些是 我的这个ID与friends' friend的共同好友最多的，因为friend是双向的
 所以只要判断 我的friend有多少也是第三层的人的friend就行了， 也就是in-degree
 但是也有可能， A-B-C. D不是A的好友，但是D是C的好友，这样也是indegree啊

 我的思路： bfs 然后计算第二层的每层的入度， 入度最多的就是有mutual friends最多的

 https://discuss.leetcode.com/topic/54969/fb-08-2016-phone-interview-find-2nd-degree-connections/3

 Find 2nd degree connections ( friends’ friends), output these 2nd degree connections ranked by
 number of common friends (i.e 1st degree connections) with you, (example: if 2nd degree connection
 A has 10 common friends (1st degree connections) with you but 2nd degree connection B has 8 common friends
 (1st degree connections)with you, then A should be ranked first) Input is your connection graph
 represented by undirected graph nodes, output is list of 2nd degree connections represented by graph nodes

 public List<UndirectedGraphNode> findSecDegreeConnections(UndirectedGraphNode myself){

 }

 You can just apply BFS for two layers and for the node of second layer(2nd degree friends), count the number of
 1st degree friends who expand to it. Then just sort the 2nd layer.
 */
public class Find_Friends {
    //Assume adjacent list is given as input, if not, need to process and generate one.
    Map<String, List<String>> map;
    public Find_Friends(Map<String, List<String>> input) {
        map = new HashMap<>(input);
    }

    public List <String> get2DegreeFriend(String host) {
        List<String> res = new ArrayList<String>();
        List<String> degree1 = getFriends(host);

        if (degree1.isEmpty()) {
            return res;
        }

        Map<String, Integer> countMap = new HashMap<>();

        for (String friend : degree1) {
            List <String> degree2 = getFriends(friend);

            for (String name : degree2) {
                if (name.equals(host) || degree1.contains(name)) {
                        continue;
                }

                countMap.put(name, countMap.getOrDefault(name, 0) + 1);
            }
        }

        /**
         * !!!
         * sort hashmap by value :
         * Put all EntrySet into a list, then sort the list.
         */
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(countMap.entrySet());

        Collections.sort(list, (a, b) -> b.getValue() - a.getValue());

        for (Map.Entry<String, Integer> e : list) {
            res.add(e.getKey());
        }

        return res;
    }

    private List<String> getFriends(String host) {
        List<String> res = new ArrayList<>();

        if (null == host || host.length() == 0) {
            return res;
        }

        if (map.containsKey(host)) {
            return map.get(host);
        }

        return res;
    }
}

