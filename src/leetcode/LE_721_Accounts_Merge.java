package leetcode;

import common.UnionFindWithCount1;

import java.util.*;

/**
 * Created by yuank on 10/11/18.
 */
public class LE_721_Accounts_Merge {
    /**
         Given a list accounts, each element accounts[i] is a list of strings,
         where the first element accounts[i][0] is a name, and the rest of the
         elements are emails representing emails of the account.

         Now, we would like to merge these accounts. Two accounts definitely belong to the same person
         if there is some email that is common to both accounts. Note that even if two accounts have
         the same name, they may belong to different people as people could have the same name.
         A person can have any number of accounts initially, but all of their accounts definitely
         have the same name.

         After merging the accounts, return the accounts in the following format:
         the first element of each account is the name, and the rest of the elements
         are emails in sorted order.
         The accounts themselves can be returned in any order.

         Example 1:
         Input:
         accounts = [["John", "johnsmith@mail.com", "john00@mail.com"],
                     ["John", "johnnybravo@mail.com"],
                     ["John", "johnsmith@mail.com", "john_newyork@mail.com"],
                     ["Mary", "mary@mail.com"]]
         Output: [["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],
                  ["John", "johnnybravo@mail.com"],
                  ["Mary", "mary@mail.com"]]

         Explanation:
         The first and third John's are the same person as they have the common email "johnsmith@mail.com".
         The second John and Mary are different people as none of their email addresses are
         used by other accounts.
         We could return these lists in any order, for example the answer
         [['Mary', 'mary@mail.com'],
          ['John', 'johnnybravo@mail.com'],
          ['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.
         Note:

         The length of accounts will be in the range [1, 1000].
         The length of accounts[i] will be in the range [1, 10].
         The length of accounts[i][j] will be in the range [1, 30].

         Medium
     */

    /**
     * UnionFind + Two HashMap
     *
     * If we use brutal force, need to iterator through accounts, compare each email to emails
     * in other accounts, O(n ^ 2 * l ^ 2), n is size of accounts, l is average length of email
     * list for each account.
     *
     * 1.Essence of the problem is to union or categorize all accounts that has AT LEAST one
     *   common email (分类). That's why we think of using UnionFind
     *
     * 2.第一个坑 ：we may think of using UnionFindInMap which uses String as index. Actually,
     *   we don't need to, notice there's an IMPLICIT param in input - index of the List, for example:
     *   [
     *     ["John", "johnsmith@mail.com", "john00@mail.com"],
     *     ["John", "johnnybravo@mail.com"],
     *     ["John", "johnsmith@mail.com", "john_newyork@mail.com"],
     *     ["Mary", "mary@mail.com"]
     *  ]
     *
     *  It is actually:
     *
     *  col
     *   0   ["John", "johnsmith@mail.com", "john00@mail.com"],
     *   1   ["John", "johnnybravo@mail.com"],
     *   2   ["John", "johnsmith@mail.com", "john_newyork@mail.com"]
     *   3    ["Mary", "mary@mail.com"]
     *
     *   So we can use "col" as index in UnionFind, plus, "name" for each account (first element in each list)
     *   is not unique, so we can't use it as index in UnionFind.
     *
     *   3.第二个坑 ： The first HashMap uses email as key and the col that the mail first appears as value.
     *     In the first for loop, loop through accounts, inner loop goes through emails of each account.
     *     If given email is not in HashMap, put it as key and current account col as value;
     *     If given email is already in HashMap, union it with the one that is already in HashMap;
     *
     *     最后，相同的email通过UnifonFind, 会有一个相同的representative. It is in the form of col of account.
     *
     *     这里用UnionFind起到分类功能，有相同性质的个体之间做union, 最后他们有共同的代表, 是一个代表，不是“三个代表”。
     *     应用的时候要确定用哪个属性做union, 这个属性必须有唯一值 (unique). 理想状况是，该属性是非负的integer, 这样
     *     就可以用UnionFind的default implementation (internally it uses integer array as "parents"). 否则，
     *     要用UnionFindInMap(use HashMap as "parents"). 初始化UnifonFind时，size是总类别的最大可能值。
     *
     *   4.第三个坑 ： 分类完成后，要整理输出。用第二个HashMap, 注意，这里， key必须是idx, 不是name, 因为只有idx是unique的，
     *     name会有相同的值。value用TreeSet,因为题意要求email in sorted order.
     *
     *   Time : O(sum (n(i))) , n(i) is length of accounts[i]
     *   Space : O(sum (n(i))
     *
     *
     */

    class Solution1 {
        public List<List<String>> accountsMerge(List<List<String>> accounts) {
            List<List<String>> res = new ArrayList<>();
            if (accounts == null || accounts.size() == 0) return res;

            UnionFindWithCount1 uf = new UnionFindWithCount1(accounts.size());

            /**
             * 1.Union Find, do union
             */
            HashMap<String, Integer> map = new HashMap<>();
            for (int i = 0; i < accounts.size(); i++) {
                List<String> account = accounts.get(i);
                for (int j = 1; j < account.size(); j++) {
                    String email = account.get(j);
                    /**
                     * 用ith account里第一个出现的email作为i的"代表"
                     */
                    map.putIfAbsent(email, i);
                    uf.union(i, map.get(email));
                }
            }

            /**
             * 2.Query from UFS and do merge/sort
             *   用TreeSet实现对email的排序。
             */
            HashMap<Integer, TreeSet<String>> merged = new HashMap<>();
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                int idx = uf.query(entry.getValue());
                String name = accounts.get(idx).get(0);
                if (!merged.containsKey(idx)) {
                    merged.put(idx, new TreeSet<>());
                }
                merged.get(idx).add(entry.getKey());
            }

            /**
             * 3.load into required result form - List<List<String>>
             */
            for (Map.Entry<Integer, TreeSet<String>> entry : merged.entrySet()) {
                Set<String> set = entry.getValue();
                int idx = entry.getKey();
                List<String> list = new ArrayList<>(set);
                list.add(0, accounts.get(idx).get(0));
                res.add(list);
            }

            return res;
        }
    }

    /**
     * DFS
     *
     * Time : O(sum(nlogn)), n is length of accounts[i]
     * Space : O(sum (n))
     */
    class Solution {
        public List<List<String>> accountsMerge(List<List<String>> accounts) {
            Map<String, Set<String>> graph = new HashMap<>();  //<email node, neighbor nodes>
            Map<String, String> name = new HashMap<>();        //<email, username>
            // Build the graph;
            for (List<String> account : accounts) {
                String userName = account.get(0);
                for (int i = 1; i < account.size(); i++) {
                    if (!graph.containsKey(account.get(i))) {
                        graph.put(account.get(i), new HashSet<>());
                    }
                    name.put(account.get(i), userName);

                    if (i == 1) continue;
                    graph.get(account.get(i)).add(account.get(i - 1));
                    graph.get(account.get(i - 1)).add(account.get(i));
                }
            }

            Set<String> visited = new HashSet<>();
            List<List<String>> res = new LinkedList<>();
            // DFS search the graph;
            for (String email : name.keySet()) {
                List<String> list = new LinkedList<>();
                if (visited.add(email)) {
                    dfs(graph, email, visited, list);
                    Collections.sort(list);
                    list.add(0, name.get(email));
                    res.add(list);
                }
            }

            return res;
        }

        public void dfs(Map<String, Set<String>> graph, String email, Set<String> visited, List<String> list) {
            list.add(email);
            for (String next : graph.get(email)) {
                if (visited.add(next)) {
                    dfs(graph, next, visited, list);
                }
            }
        }
    }
}
