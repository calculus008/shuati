package src.leetcode;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class LE_362_Design_Hit_Counter {
    /**
     * Design a hit counter which counts the number of hits received in the past 5 minutes.
     *
     * Each function accepts a timestamp parameter (in seconds granularity) and you may assume
     * that calls are being made to the system in chronological order (ie, the timestamp is
     * monotonically increasing). You may assume that the earliest timestamp starts at 1.
     *
     * It is possible that several hits arrive roughly at the same time.
     *
     * Example:
     *
     * HitCounter counter = new HitCounter();
     *
     * // hit at timestamp 1.
     * counter.hit(1);
     *
     * // hit at timestamp 2.
     * counter.hit(2);
     *
     * // hit at timestamp 3.
     * counter.hit(3);
     *
     * // get hits at timestamp 4, should return 3.
     * counter.getHits(4);
     *
     * // hit at timestamp 300.
     * counter.hit(300);
     *
     * // get hits at timestamp 300, should return 4.
     * counter.getHits(300);
     *
     * // get hits at timestamp 301, should return 3.
     * counter.getHits(301);
     *
     * Follow up:
     * What if the number of hits per second could be very large? Does your design scale?
     *
     * Medium
     */

    public class HitCounter {
        private int[] times;
        private int[] hits;
        /** Initialize your data structure here. */
        public HitCounter() {
            times = new int[300];
            hits = new int[300];
        }

        /** Record a hit.
         @param timestamp - The current timestamp (in seconds granularity). */
        public void hit(int timestamp) {
            int index = timestamp % 300;
            if (times[index] != timestamp) {
                times[index] = timestamp;
                hits[index] = 1;
            } else {
                hits[index]++;
            }
        }

        /** Return the number of hits in the past 5 minutes.
         @param timestamp - The current timestamp (in seconds granularity). */
        public int getHits(int timestamp) {
            int total = 0;
            for (int i = 0; i < 300; i++) {
                if (timestamp - times[i] < 300) {
                    total += hits[i];
                }
            }
            return total;
        }
    }

    class HitCounter_Concurrent {
        AtomicIntegerArray hits;
        AtomicIntegerArray times;

        /** Initialize your data structure here. */
        public HitCounter_Concurrent() {
            hits = new AtomicIntegerArray(300);
            times = new AtomicIntegerArray(300);
        }

        /** Record a hit.
         @param timestamp - The current timestamp (in seconds granularity). */
        public void hit(int timestamp) {
            int idx = timestamp % 300;
            if (times.get(idx) != timestamp) {
                times.set(idx, timestamp);
                hits.set(idx, 1);
            } else {
                hits.incrementAndGet(idx);
            }
        }

        /** Return the number of hits in the past 5 minutes.
         @param timestamp - The current timestamp (in seconds granularity). */
        public int getHits(int timestamp) {
            int total = 0;
            for (int i = 0; i < 300; i++) {
                if (timestamp - times.get(i) < 300) {
                    total += hits.get(i);
                }
            }
            return total;
        }
    }
}
