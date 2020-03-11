package src.Interviews.Indeed.Practice;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Expiring_Map_Practice <K, V>{
    Map<K, Item> map = new HashMap<>();
    PriorityQueue<Item> pq = new PriorityQueue<>((a, b) -> Long.compare(a.expirationTime, b.expirationTime));
    final static ReentrantLock lock = new ReentrantLock();

    class Item {
        V val;
        K key;
        long expirationTime;

        public Item(V val, K key, long time) {
            this.val = val;
            this.key = key;
            this.expirationTime = time;
        }
    }

    class ExpMap {
        public synchronized void put(K key, V val, long ttl) {
            lock.lock();
            try {
                Item it = new Item(val, key, System.currentTimeMillis() + ttl);
                /**
                 * double action
                 */
                map.put(key, it);
                pq.offer(it);
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }

        public synchronized V get(K key) {
            clean();

            V ret = null;

            lock.lock();
            try {
                if (!map.containsKey(key)) ret = null;

                Item it = map.get(key);
                if (it.expirationTime < System.currentTimeMillis()) {
                    ret =  it.val;
                }

                /**
                 * !!!
                 */
                map.remove(key);

                ret = null;
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }

            return ret;
        }

        private synchronized void clean() {
            lock.lock();
            try {
                while (!pq.isEmpty() && pq.peek().expirationTime > System.currentTimeMillis()) {
                    Item it = pq.poll();
                    map.remove(it.key);
                }
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }
    }

    class Cleaner extends Thread {
        public void run() {
            try {
                clean();
                sleep(5000);
            } catch (Exception e) {

            }
        }

        private synchronized void clean() {
            lock.lock();
            try {
                while (!pq.isEmpty() && pq.peek().expirationTime > System.currentTimeMillis()) {
                    Item it = pq.poll();
                    map.remove(it.key);
                }
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }
    }


}
