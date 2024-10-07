package leetcode.Concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ExpiringMap<K, V> {
    private final Map<K, Item> map = new HashMap<>(); // Internal map to store key-value items
    private final PriorityQueue<Item> pq = new PriorityQueue<>((a, b) -> Long.compare(a.expirationTime, b.expirationTime)); // PriorityQueue to store items based on their expiration time
    private final ReentrantLock lock = new ReentrantLock();// Lock to ensure thread-safety

    class Item {// Item class to represent map entries with expiration time
        V val;
        K key;
        long expirationTime;

        public Item(K key, V val, long expirationTime) {
            this.val = val;
            this.key = key;
            this.expirationTime = expirationTime;
        }
    }

    // ExpiringMap class with put, get, and clean methods
    class ExpMap {
        public void put(K key, V val, long ttl) { // Adds a new entry to the map with a given TTL (time to live)
            lock.lock(); //!!!
            try {
                long expirationTime = System.currentTimeMillis() + ttl;
                Item item = new Item(key, val, expirationTime);
                map.put(key, item); // Add or update the item in both map and priority queue
                pq.offer(item);
            } finally {
                lock.unlock();
            }
        }

        // Retrieves the value associated with the key if it's not expired, else returns null
        public V get(K key) {
            lock.lock(); //!!!
            try {
                clean();  // Clean expired entries before getting the value

                Item item = map.get(key);
                if (item == null || item.expirationTime < System.currentTimeMillis()) {
                    return null;  // !!! Key not present or expired
                }

                return item.val;  // Return the valid value
            } finally {
                lock.unlock();
            }
        }

        // Cleans up expired items in the map and priority queue
        private void clean() {
            lock.lock(); //!!!
            try {
                long currentTime = System.currentTimeMillis();

                while (!pq.isEmpty() && pq.peek().expirationTime <= currentTime) { // Remove all expired items from both the priority queue and map
                    Item expiredItem = pq.poll();
                    map.remove(expiredItem.key);  // Remove from map if expired
                }
            } finally {
                lock.unlock();
            }
        }
    }

    // Cleaner thread class to periodically clean the expired entries
    class Cleaner extends Thread {
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(5000);  // Sleep for 5 seconds
                    clean();  // Perform periodic cleaning
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();  // Restore interrupt status
                    break;  // Exit loop if interrupted
                }
            }
        }

        public void stopCleaning() {// Stop the cleaner thread
            running = false;
            this.interrupt();  // Interrupt the sleep to stop immediately
        }

        private void clean() {// Clean method reused from the ExpMap class
            lock.lock();
            try {
                long currentTime = System.currentTimeMillis();
                while (!pq.isEmpty() && pq.peek().expirationTime <= currentTime) {
                    Item expiredItem = pq.poll();
                    map.remove(expiredItem.key);
                }
            } finally {
                lock.unlock();
            }
        }
    }
}




//public class ExpiringMapTest {
//    public static void main(String[] args) throws InterruptedException {
//        // Create an instance of ExpiringMap
//        ExpiringMap<String, String> expiringMap = new ExpiringMap<>();
//        ExpiringMap<String, String>.ExpMap map = expiringMap.new ExpMap();
//        ExpiringMap<String, String>.Cleaner cleaner = expiringMap.new Cleaner();
//
//        // Start the Cleaner thread to automatically remove expired items
//        cleaner.start();
//
//        // Adding some entries with different TTL (time-to-live in milliseconds)
//        System.out.println("Putting key1 with TTL 3000ms");
//        map.put("key1", "value1", 3000);  // Will expire in 3 seconds
//
//        System.out.println("Putting key2 with TTL 5000ms");
//        map.put("key2", "value2", 5000);  // Will expire in 5 seconds
//
//        System.out.println("Putting key3 with TTL 10000ms");
//        map.put("key3", "value3", 10000); // Will expire in 10 seconds
//
//        // Retrieving values immediately after insertion
//        System.out.println("key1 (immediate get): " + map.get("key1"));  // Expect "value1"
//        System.out.println("key2 (immediate get): " + map.get("key2"));  // Expect "value2"
//        System.out.println("key3 (immediate get): " + map.get("key3"));  // Expect "value3"
//
//        // Wait for 4 seconds (after which key1 should have expired but key2 and key3 should still be valid)
//        Thread.sleep(4000);
//
//        // Trying to retrieve values after 4 seconds
//        System.out.println("key1 (after 4 seconds): " + map.get("key1"));  // Expect null (expired)
//        System.out.println("key2 (after 4 seconds): " + map.get("key2"));  // Expect "value2"
//        System.out.println("key3 (after 4 seconds): " + map.get("key3"));  // Expect "value3"
//
//        // Wait for another 2 seconds (total of 6 seconds, after which key2 should expire)
//        Thread.sleep(2000);
//
//        // Trying to retrieve values after 6 seconds
//        System.out.println("key2 (after 6 seconds): " + map.get("key2"));  // Expect null (expired)
//        System.out.println("key3 (after 6 seconds): " + map.get("key3"));  // Expect "value3" (not expired yet)
//
//        // Wait for another 4 seconds (total of 10 seconds, after which key3 should also expire)
//        Thread.sleep(4000);
//
//        // Trying to retrieve all values after 10 seconds
//        System.out.println("key1 (after 10 seconds): " + map.get("key1"));  // Expect null (already expired)
//        System.out.println("key2 (after 10 seconds): " + map.get("key2"));  // Expect null (already expired)
//        System.out.println("key3 (after 10 seconds): " + map.get("key3"));  // Expect null (expired now)
//
//        // Stopping the Cleaner thread after testing
//        cleaner.stopCleaning();
//    }
//}




