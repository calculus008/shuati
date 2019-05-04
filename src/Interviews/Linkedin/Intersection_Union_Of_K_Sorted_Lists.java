package Interviews.Linkedin;

import java.util.*;

public class Intersection_Union_Of_K_Sorted_Lists {
    /**
     * An variation of K-way merge problem.
     *
     */
    class Node {
        Integer val = null;

        /**
         * !!!
         * Iterator<>
         */
        Iterator<Integer> it = null;

        Node(Integer val, Iterator<Integer> it) {
            this.val = val;
            this.it = it;
        }
    }

    /**
     * Union : K个sorted list, union all (no duplicate) in output in sorted way.
     */
    public List<Integer> union(List<List<Integer>> source) {
        int k = source.size();

//        PriorityQueue<Node> heap = new PriorityQueue<Node>(k, new Comparator<Node>(){
//            public int compare(Node node1, Node node2) {
//                return node1.val - node2.val;
//            }
//        });

        PriorityQueue<Node> heap = new PriorityQueue<Node>(k, (a, b) -> a.val - b.val);

        for (List<Integer> list : source) {
            Iterator<Integer> it = list.iterator();
            Node node = new Node(null, it);

            if (node.it.hasNext()) {
                node.val = it.next();
            }

            if (node.val != null) {
                heap.offer(node);
            }
        }

        List<Integer> res = new ArrayList<Integer>();

        if (heap.isEmpty()) {
            return res;
        }

        while (!heap.isEmpty()) {
            Node node = heap.poll();

            /**
             * !!!
             * 去重， 添加不一样的
             * "res.get(res.size() - 1) != node.val" : 当前node.val和res里最后一个元素不一样。
             */
            if (res.isEmpty()
                    || res.get(res.size() - 1) != node.val) {
                res.add(node.val);
            }

            if (node.it.hasNext()) {
                node.val = node.it.next();
                heap.offer(node);
            } // If there is no next(), no add back to heap
        }

        for (int val : res) {
            System.out.print(val + "--");
        }

        System.out.println();
        return res;
    }


    /**
     * Intersection : elements that appear in more than one list among K lists
     */
    public List<Integer> intersection(List<List<Integer>> source) {
        int k = source.size();

        PriorityQueue<Node> heap = new PriorityQueue<Node>(k, new Comparator<Node>(){
            public int compare(Node node1, Node node2) {
                return node1.val - node2.val;
            }
        });

        for (List<Integer> list : source) {
            Iterator<Integer> it = list.iterator();
            Node node = new Node(null, it);

            if (node.it.hasNext()) {
                node.val = it.next();
            }

            if (node.val != null) {
                heap.offer(node);
            }
        }

        List<Integer> res = new ArrayList<Integer>();

        if (heap.isEmpty()) {
            return res;
        }

        Integer prev = null;
        while (!heap.isEmpty()) {
            Node node = heap.poll();

            if (prev == null) {
                prev = node.val;
                System.out.println("prev null,set prev="+prev);
            } else {
                /**
                 * !!!
                 * 总是滞后一步，当发现duplicate时，添加prev.
                 *
                 * For example :
                 *   prev
                 * 1-4-4-4-4-6-7
                 *
                 * prev stays at the first 4, when we come to 2nd 4, we add 4 to res.
                 * When we come to 3rd and 4th 4, condition "res.get(res.size() - 1) != node.val"
                 * is FALSE, we do nothing. Until we come to 6, it is different from prev value 4,
                 * now we set prev to 6.
                 **/
                if (node.val == prev) {
                    if (res.isEmpty() || res.get(res.size() - 1) != node.val) {
                        System.out.println("add " + prev);
                        res.add(prev);
                    }
                } else {
                    prev = node.val;
                }
            }

            if (node.it.hasNext()) {
                node.val = node.it.next();
                heap.offer(node);
            } // If there is no next(), don't add back to heap
        }

        for (int val : res) {
            System.out.print(val + "--");
        }
        return res;
    }

    public static void main(String[] args) {
        Integer[] arr1 = {3, 4, 8, 9, 12, 14, 18};
        Integer[] arr2 = {1, 4, 7, 8, 10, 14, 19};
        Integer[] arr3 = {1, 2, 4, 6, 12, 13, 17};
        Integer[] arr4 = {7, 14, 17, 18, 120, 124, 129};
        List<Integer> list1 = Arrays.asList(arr1);
        List<Integer> list2 = Arrays.asList(arr2);
        List<Integer> list3 = Arrays.asList(arr3);
        List<Integer> list4 = Arrays.asList(arr4);

        Intersection_Union_Of_K_Sorted_Lists inK = new Intersection_Union_Of_K_Sorted_Lists();

        List<List<Integer>> source = new ArrayList<List<Integer>>();
        source.add(list1);
        source.add(list2);
        source.add(list3);
        source.add(list4);

        /**
         * Output for union():
         * 1--2--3--4--6--7--8--9--10--12--13--14--17--18--19--120--124--129--
         */
        inK.union(source);

        /**
         * Output for intersection():
         * 1--4--7--8--12--14--17--18--
         */
        inK.intersection(source);
    }

}
