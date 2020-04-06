package leetcode;

import java.util.*;

/**
 * Created by yuank on 3/17/18.
 */
public class LE_133_Clone_Graph {
    /**
     Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.
      */

    /**Very Important**/

    /**
     * Solution 1: DFS
     *
     * dist (!!!)
     * key : current node
     * value : new copied node
     *
     * Time and Space : O(n)
     **/
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

    /**
     * Solution 4, same as Solution 3, only difference is to use label as key in dist (since the question guaranteed
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
    public UndirectedGraphNode cloneGraph_JiuZhang_2(UndirectedGraphNode node) {
        if(node == null){
            return null;
        }

        Map<Integer, UndirectedGraphNode> map = new HashMap<>();
        Queue<UndirectedGraphNode> queue = new LinkedList<>();
        queue.offer(node);
        map.put(node.label, new UndirectedGraphNode(node.label));

        while(!queue.isEmpty()){
            UndirectedGraphNode cur = queue.poll();
            UndirectedGraphNode curCopy = map.get(cur.label);

            for(UndirectedGraphNode neighbor : cur.neighbors){
                if(!map.containsKey(neighbor.label)){
                    queue.offer(neighbor);
                    map.put(neighbor.label, new UndirectedGraphNode(neighbor.label));
                }
                curCopy.neighbors.add(map.get(neighbor.label));
            }
        }
        return map.get(node.label);
    }

    /**
     * Solution 2 : BFS
     * First go through graph using BFS, put all nodes in a set. Then clone, put into hashmap, link all neighbors
     **/
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

    /**
     * Solution 3 : from JiuZhang, one step BFS
     */
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
                     * If the node is not in dist yet, it is not processed.
                     * Put it in queue, add its entry in dist
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
