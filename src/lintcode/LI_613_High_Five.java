package lintcode;

import com.sun.prism.impl.Disposer;
import common.Record;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


/**
 * Created by yuank on 9/10/18.
 */
public class LI_613_High_Five {
    /**
         There are two properties in the node student id and scores,
         to ensure that each student will have at least 5 points,
         find the average of 5 highest scores for each person.

         Medium
     */

    public Map<Integer, Double> highFive(Record[] results) {
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
        Map<Integer, Double> res = new HashMap<>();

        for (Record result : results) {
            if (!map.containsKey(result.id)) {
                map.put(result.id, new PriorityQueue<>());
            }
            PriorityQueue<Integer> pq = map.get(result.id);

            //!!!
            if (pq.size() >= 5) {
                if (pq.peek() < result.score) {
                    pq.poll();
                    pq.offer(result.score);
                }
            } else {//!!!
                pq.offer(result.score);
            }
        }

        for (int key : map.keySet()) {//!!! map.keySet()
            PriorityQueue<Integer> q = map.get(key);
            int sum = 0;
            while (!q.isEmpty()) {
                sum += q.poll();
            }
            res.put(key, (double)sum / 5.0);
        }

        return res;
    }
}
