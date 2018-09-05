package lintcode;

import java.util.*;

/**
 * Created by yuank on 9/3/18.
 */
public class LI_545_Top_K_Largest_Numbers_II {
    /**
         Implement a data structure, provide two interfaces:

         add(number). Add a new number in the data structure.
         topk(). Return the top k largest numbers in this data structure. k is given when we create the data structure.
         Example
         s = new Solution(3);
         >> create a new data structure.
         s.add(3)
         s.add(10)
         s.topk()
         >> return [10, 3]
         s.add(1000)
         s.add(-99)
         s.topk()
         >> return [1000, 10, 3]
         s.add(4)
         s.topk()
         >> return [1000, 10, 4]
         s.add(100)
         s.topk()
         >> return [1000, 100, 10]

         Medium
     */
    public class Solution {
        PriorityQueue<Integer> pq;
        int size;
        /*
        * @param k: An integer
        */public Solution(int k) {
            pq = new PriorityQueue<>();
            size = k;
        }

        /*
         * @param num: Number to be added
         * @return: nothing
         */
        public void add(int num) {
            if (pq.size() < size) {
                pq.offer(num);
                return;
            }

            //优化 ：先判断num是否比当前最小的大。
            if (num > pq.peek()) {
                pq.poll();//!!!先删除，这样加入时的复杂度小
                pq.offer(num);
            }
        }

        /*
         * @return: Top k element
         */
        public List<Integer> topk() {
            /**
             * !!! 关键：用Iterator遍历qp,因为我们不能用poll()把元素拿出来，
             *           要保留在pq中。
             * **/
            List<Integer> res = new ArrayList<>();
            Iterator<Integer> it = pq.iterator();
            while (it.hasNext()) {
                res.add((Integer)it.next());
            }
            //题意要求从大到小
            Collections.sort(res, Collections.reverseOrder());//!!!
            return res;
        }

    }
}
