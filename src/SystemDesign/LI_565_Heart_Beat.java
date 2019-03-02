package SystemDesign;

import java.util.*;

public class LI_565_Heart_Beat {
    /**
     * In the Master-Slave architecture, slave server will ping master in every k seconds to
     * tell master server he is alive. If a master server didn't receive any ping request
     * from a slave server in 2 * k seconds, the master will trigger an alarm (for example
     * send an email) to administrator.
     *
     * Let's mock the master server, you need to implement the following three methods:
     *
     * initialize(slaves_ip_list, k). salves_ip_list is a list of slaves' ip addresses.
     * k is define above.
     *
     * ping(timestamp, slave_ip). This method will be called every time master received a ping
     * request from one of the slave server. timestamp is the current timestamp in seconds.
     * slave_ip is the ip address of the slave server who pinged master.
     *
     * getDiedSlaves(timestamp). This method will be called periodically (it's not guaranteed
     * how long between two calls). timestamp is the current timestamp in seconds, and you need
     * to return a list of slaves' ip addresses that died. Return an empty list if no died
     * slaves found.
     *
     * You can assume that when the master started, the timestamp is 0, and every method will
     * be called with an global increasing timestamp.
     */

    public class HeartBeat {

        public Map<String, Integer> slaves_ip_list;
        public int k;

        public HeartBeat() {
            // initialize your data structure here
            slaves_ip_list = new HashMap<String, Integer>();
        }

        // @param slaves_ip_list a list of slaves'ip addresses
        // @param k an integer
        // @return void
        public void initialize(List<String> slaves_ip_list, int k) {
            // Write your code here
            this.k = k;
            for (String ip : slaves_ip_list)
                this.slaves_ip_list.put(ip, 0);
        }

        // @param timestamp current timestamp in seconds
        // @param slave_ip the ip address of the slave server
        // @return nothing
        public void ping(int timestamp, String slave_ip) {
            // Write your code here
            if (!slaves_ip_list.containsKey(slave_ip))
                return;

            slaves_ip_list.put(slave_ip, timestamp);
        }

        // @param timestamp current timestamp in seconds
        // @return a list of slaves'ip addresses that died
        public List<String> getDiedSlaves(int timestamp) {
            // Write your code here
            List<String> results = new ArrayList<String>();

            Iterator iter = slaves_ip_list.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry)iter.next();
                String ip = (String) entry.getKey();
                int time = (Integer) entry.getValue();
                if (time <= timestamp - 2 * k)
                    results.add(ip);
            }
            return results;
        }
    }
}
