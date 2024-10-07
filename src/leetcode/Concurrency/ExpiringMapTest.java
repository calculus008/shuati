package leetcode.Concurrency;

/**
 * Test code for ExpiringMap, to run:
 *  javac ExpiringMap.java ExpiringMapTest.java
 *  cd ../../
 *  java -cp  .  leetcode.Concurrency.ExpiringMapTest
 *
 *
 *
 * Expected output:
 *
 * Putting key1 with TTL 3000ms
 * Putting key2 with TTL 5000ms
 * Putting key3 with TTL 10000ms
 * key1 (immediate get): value1
 * key2 (immediate get): value2
 * key3 (immediate get): value3
 * key1 (after 4 seconds): null
 * key2 (after 4 seconds): value2
 * key3 (after 4 seconds): value3
 * key2 (after 6 seconds): null
 * key3 (after 6 seconds): value3
 * key1 (after 10 seconds): null
 * key2 (after 10 seconds): null
 * key3 (after 10 seconds): null
 *
 *
 */
public class ExpiringMapTest {
    public static void main(String[] args) throws InterruptedException {
        ExpiringMap<String, String> expiringMap = new ExpiringMap<>();
        ExpiringMap<String, String>.ExpMap map = expiringMap.new ExpMap();
        ExpiringMap<String, String>.Cleaner cleaner = expiringMap.new Cleaner();

        cleaner.start();

        map.put("key1", "value1", 3000);
        map.put("key2", "value2", 5000);
        map.put("key3", "value3", 10000);

        System.out.println("key1 (immediate get): " + map.get("key1"));
        System.out.println("key2 (immediate get): " + map.get("key2"));
        System.out.println("key3 (immediate get): " + map.get("key3"));

        Thread.sleep(4000);

        System.out.println("key1 (after 4 seconds): " + map.get("key1"));
        System.out.println("key2 (after 4 seconds): " + map.get("key2"));
        System.out.println("key3 (after 4 seconds): " + map.get("key3"));

        Thread.sleep(2000);

        System.out.println("key2 (after 6 seconds): " + map.get("key2"));
        System.out.println("key3 (after 6 seconds): " + map.get("key3"));

        Thread.sleep(4000);

        System.out.println("key1 (after 10 seconds): " + map.get("key1"));
        System.out.println("key2 (after 10 seconds): " + map.get("key2"));
        System.out.println("key3 (after 10 seconds): " + map.get("key3"));

        cleaner.stopCleaning();
    }
}

