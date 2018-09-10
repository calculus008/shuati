package lintcode;

import java.util.*;

/**
 * Created by yuank on 9/9/18.
 */
public class LI_526_Load_Balancer {
    /**
         Implement a load balancer for web servers. It provide the following functionality:

         Add a new server to the cluster => add(server_id).
         Remove a bad server from the cluster => remove(server_id).
         Pick a server in the cluster randomly with equal probability => pick().
         Example
         At beginning, the cluster is empty => {}.

         add(1)
         add(2)
         add(3)
         pick()
         >> 1         // the return value is random, it can be either 1, 2, or 3.
         pick()
         >> 2
         pick()
         >> 1
         pick()
         >> 3
         remove(1)
         pick()
         >> 2
         pick()
         >> 3
         pick()
         >> 3

         Medium
     */

    public class LoadBalancer {
        Map<Integer, Integer> map;
        List<Integer> list;

        public LoadBalancer() {
            map = new HashMap<>();
            list = new ArrayList<>();
        }

        /*
         * @param server_id: add a new server to the cluster
         * @return: nothing
         */
        public void add(int server_id) {
            int size = list.size();
            map.put(server_id, size);//!!!
            list.add(server_id);
        }

        /*
         * @param server_id: server_id remove a bad server from the cluster
         * @return: nothing
         */
        public void remove(int server_id) {
            int size = list.size();
            int idx = map.get(server_id);

            map.put(list.get(size - 1), idx);//!!!
            list.set(idx, list.get(size - 1));
            list.remove(size - 1);
            map.remove(server_id);
        }

        /*
         * @return: pick a server in the cluster randomly with equal probability
         */
        public int pick() {
            Random r = new Random();
            int pick = r.nextInt(list.size());//!!! 0(inclusive) to given range(exclusive)
            return list.get(pick);
        }
    }
}
