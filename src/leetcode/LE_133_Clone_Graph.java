package leetcode;

import java.util.*;

/**
 * Created by yuank on 3/17/18.
 */
public class LE_133_Clone_Graph {
    /**
     Given a reference of a node in a connected undirected graph.

     Return a deep copy (clone) of the graph.

     Each node in the graph contains a value (int) and a list (List[Node]) of its neighbors.

     class Node {
     public int val;
     public List<Node> neighbors;
     }


     Test case format:

     For simplicity, each node's value is the same as the node's index (1-indexed). For example,
     the first node with val == 1, the second node with val == 2, and so on. The graph is represented
     in the test case using an adjacency list.

     An adjacency list is a collection of unordered lists used to represent a finite graph. Each list describes
     the set of neighbors of a node in the graph.

     The given node will always be the first node with val = 1. You must return the copy of the given node as
     a reference to the cloned graph.

     Medium

     https://leetcode.com/problems/clone-grap

     */

    /**Very Important**/


    class Solution_dfs_clean {
        Map<Node, Node> map;
        public Node cloneGraph(Node node) {
            map = new HashMap<>();
            return helper(node);
        }

        public Node helper(Node node) {
            if (node == null) return null;
            if (map.containsKey(node)) return map.get(node);

            Node dup = new Node(node.val);//!!!
            map.put(node, dup);           //!!! clone and put into map

            for (Node n : node.neighbors) {
                Node clone = helper(n);
                dup.neighbors.add(clone);
            }

            return dup;
        }
    }

    class Solution_BFS_clean {
        public Node cloneGraph(Node node) {
            if (node == null) return null;

            Map<Node, Node> map = new HashMap<>();
            Queue<Node> q = new LinkedList<>();
            q.add(node);
            map.put(node, new Node(node.val));

            while (!q.isEmpty()) {
                Node cur = q.poll();
                Node copy = map.get(cur);

                for (Node n : cur.neighbors) {
                    if (!map.containsKey(n)) {
                        map.put(n, new Node(n.val));
                        q.offer(n);
                    }
                    copy.neighbors.add(map.get(n));
                }
            }

            return map.get(node);
        }
    }

    /**
     * Solution 1: DFS
     *
     * Map (!!!)
     * key : current node
     * value : new copied node
     *
     * Time and Space : O(n)
     **/
    class Solution1 {
        Map<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();

        public UndirectedGraphNode cloneGraph1(UndirectedGraphNode node) {
            return helper(node);
        }

        public UndirectedGraphNode helper(UndirectedGraphNode node) {
            if (node == null) return null;

            if (map.containsKey(node)) {
                return map.get(node);
            }

            /**
             * make copy of the node
             */
            UndirectedGraphNode dup = new UndirectedGraphNode(node.label);

            /**
             * 记录原node和copied node的mapping
             * must be here.
             * **/
            map.put(node, dup);

            /**
             * Then make copy of current node's neighbors
             */
            for (UndirectedGraphNode neighbor : node.neighbors) {
                /**
                 * recursive call to make copy of neighbor node
                 */
                UndirectedGraphNode clone = helper(neighbor);
                dup.neighbors.add(clone);
            }

            return dup;
        }
    }

    /**
     * Solution 4, same as Solution 3, only difference is to use label as key in map (since the question guaranteed
     * label is unique)
     *   BFS + HashMap
     Time : O(nm)，Space : O(n)

     1.用queue存放已经复制好但复制品node还没添加neighbor的node。
     2.Map放对应label的复制品node。
     3.做queue的BFS，每次从queue和map拿到一个原node和对应的复制品node，创建neighbors中还没有复制品的node，
     将新创建的node放入queue。这样保证queue不会重复。(!!!)
     4.创建新node的条件是map里面没有对应的label，这个条件与限定放入queue的规则一样。
     5.最后返回map中node对应label的node复制品。
     */
    class Solution_4_BFS {
        public UndirectedGraphNode cloneGraph_JiuZhang_2(UndirectedGraphNode node) {
            if (node == null) {
                return null;
            }

            Map<Integer, UndirectedGraphNode> map = new HashMap<>();
            Queue<UndirectedGraphNode> queue = new LinkedList<>();
            queue.offer(node);
            map.put(node.label, new UndirectedGraphNode(node.label));

            while (!queue.isEmpty()) {
                UndirectedGraphNode cur = queue.poll();
                UndirectedGraphNode curCopy = map.get(cur.label);

                for (UndirectedGraphNode neighbor : cur.neighbors) {
                    if (!map.containsKey(neighbor.label)) {
                        queue.offer(neighbor);
                        map.put(neighbor.label, new UndirectedGraphNode(neighbor.label));
                    }
                    curCopy.neighbors.add(map.get(neighbor.label));
                }
            }
            return map.get(node.label);
        }
    }

    /**
     * Solution 2 : BFS
     * First go through graph using BFS, put all nodes in a set. Then clone, put into hashmap, link all neighbors
     **/
    class Solution_3_BFS {
        public UndirectedGraphNode cloneGraph2(UndirectedGraphNode node) {
            if (node == null) return node;
            List<UndirectedGraphNode> nodes = getNodes(node);
            HashMap<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();

            for (UndirectedGraphNode cur : nodes) {
                map.put(cur, new UndirectedGraphNode(cur.label));
            }
            for (UndirectedGraphNode cur : nodes) {
                UndirectedGraphNode newNode = map.get(cur);
                for (UndirectedGraphNode neighbor : cur.neighbors) {
                    newNode.neighbors.add(map.get(neighbor));//!!!dist.get(neighbor)
                }
            }

            return map.get(node);
        }

        public List<UndirectedGraphNode> getNodes(UndirectedGraphNode node) {
            Queue<UndirectedGraphNode> queue = new LinkedList<>();
            Set<UndirectedGraphNode> set = new HashSet<>();
            queue.offer(node);
            set.add(node);

            while (!queue.isEmpty()) {
                UndirectedGraphNode cur = queue.poll();
                for (UndirectedGraphNode neighbor : cur.neighbors) {
                    if (!set.contains(neighbor)) {
                        set.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }
            return new ArrayList<>(set);
        }
    }
    /**
     * Solution 3 : from JiuZhang, one step BFS
     */
    class Solution_BFS {
        public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
            if (node == null) return null;

            HashMap<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
            Queue<UndirectedGraphNode> queue = new LinkedList<>();
            queue.offer(node);
            map.put(node, new UndirectedGraphNode(node.label));

            while (!queue.isEmpty()) {
                UndirectedGraphNode cur = queue.poll();
                UndirectedGraphNode curCopy = map.get(cur);

                for (UndirectedGraphNode neighbor : cur.neighbors) {
                    if (!map.containsKey(neighbor)) {
                        map.put(neighbor, new UndirectedGraphNode(neighbor.label));
                        /**
                         * If the node is not in map yet, it is not processed.
                         * Put it in queue, add its entry in map
                         *!!!Add original node, not the copy
                         **/
                        queue.offer(neighbor);
                    }

                    curCopy.neighbors.add(map.get(neighbor));
                }
            }

            return map.get(node);
        }
    }
    class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
}
