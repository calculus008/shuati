package Interviews.Indeed;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Expiring_Map {
    /**
     * 题还是那个老题，就是ExpiringMap, 这个题本身不难，但是follow up 特别多， 有4个。我当时就醉了，感觉要挂，
     * 不过面试官人很nice一直给提示，最后还是把题做出来了，不知道结果咋样。
     *
     * 给一个expiring map， 你可以一直往里面put东西，这些东西都有个过期值，一旦过期就get不到了。
     * //put(null, null, 2000);
     *
     * // 10:00:00 - put(10, 25, 5000)
     * /**
     * * (10, (25, 10:00:05))
     * *
     * // 10:00:04 - get(10) -> 25
     * // 10:00:05 - get(10) -> null
     * // 10:00:06 - get(10) -> null
     *
     * public class ExpiringMap<K, V> {
     *     HashMap<K, <Duration>> map = new HashMap();
     *     private class Duration {
     *         private V value;
     *         private long expirationTime;
     *         public Duration(V val, long durationMillis) {
     *             this.value = val;
     *             this.expirationTime = durationMillis + System.currentTimeMillis();
     *         }
     *     }
     *     void put(final K key, final V value, final long durationMillis) {
     *         map.put(key, new Duration(value, durationMillis);
     *     }
     *
     *     V get(final K key) {
     *         //if map does not have it
     *         if(!map.containsKey(key)) {
     *             return null;
     *         } else {
     *         //map contains key
     *             Duration temp = map.get(key);
     *             //is valid entry
     *             if(temp.expirationTime > System.currentTimeMillis()) {
     *                 return temp.value;
     *             } else {
     *                 map.remove(key);
     *                 return null;
     *             }
     *         }
     *     }
     * }
     * //put(null, null, 2000);
     *
     * // 10:00:00 - put(10, 25, 5000)
     * /**
     * * (10, (25, 10:00:05))
     * *
     * // 10:00:04 - get(10) -> 25
     * /**
     * *
     * // 10:00:05 - get(10) -> null
     * // 10:00:06 - get(10) -> null
     *
     * 第二个 follow up： 如果用heap的话可以解决上面的问题，但是put时间变成了 O(k log n）怎么办？
     * lz是在put的时候remove 已经expired的东西，这样会remove k个已经expired的东西，每次remove时间是log n，
     * 所以put 从原来o（1） 变成了O(k log n）， 怎么解决
     *
     * 第三 follow up： put 从原来o（1） 变成了O(k log n）， 怎么解决？
     * lz说用多线程，put 只是put 到 hashmap 和 heap 里面， 但是不从heap remove。 put 从原来的 O(1）变成了 O(log n），
     * 然后用另外一个线程来做remove 操作。（幸好没叫写多线程代码，不然死定了）
     *
     * 老題, exist map, 寫get 和 put 同時給now()回傳當前時間. lz 表示需要建class來存 duration, 並寫了個 isExpired(),
     * 面試官表示滿意寫完後 followup問有memory leak怎辦, 答曰再寫個clean, 不定時呼叫. clean就把掃map的key, 如果過期的話 就移除,
     *  面試官表示滿意.
     *
     * 大家都能实现，关键是如何用最节省内存的方法存下来，
     *
     * 面经题目expiremap. Follow up问了clean up的问题，楼主提出了一个o(n)的方法但是好像不是他想要的结果，
     * 我一开始写的code只在get时候会删除过期数据，然后她问如果有个server用你的东西过了一小时了没人get东西会怎么样，你该怎么解决它？
     * 具体回答我也不知道其实，因为我回答后感觉他不满意因为他问我怎么改进，后来我支支吾吾也说的不太好。
     *
     * clean up应该是想让你写一个函数，每次调用就把应该expire的项目全pop掉吧。如果是的话你可以一开始用 Linkedlist + hashMap，
     * 也就是把hashmap中的东西都用指针连在一起
     *
     * (!!!)
     * Expire map
     * circular 解法主要就是 create 一个固定size for 5分钟的array, keep 一个lastTimeStamp, 每次放入的位置为 timestamp % size. 然后因为有lastTimeStamp, 可以算出哪些array裡面的范围是invalid,不要算进去.
     * list 解法主要就是 用list maintain 所有timestamp, 每次要算sum的时候 从最尾,扫到第一个过期的时间,斩断.
     *
     * follow up 没有问到multithread, follow up 主要就是时间空间複杂度
     */

    public class ExpiringMap<K, V> {
        Map<K, Item> map = new HashMap<>();

        public class Item {
            private V val;
            private long expirationTime;

            public Item(V val, long expirationTime) {
                this.val = val;
                this.expirationTime = expirationTime;
            }
        }

        public void put(K key, V val, long ttl) {
            Item it = new Item(val, System.currentTimeMillis() + ttl);
            map.put(key, it);
        }

        public V get(K k) {
            if (!map.containsKey(k)) return null;

            Item it = map.get(k);

            if (it.expirationTime > System.currentTimeMillis()) {
                return it.val;
            }

            map.remove(k);
            return null;
        }
    }

    /**
     * Follow up : how to remove expired items efficiently?
     *
     * Use pq, each time do get(), clear expired from both pq and map.
     */
    public class ExpiringMapWithPQ<K, V> {
        Map<K, Item> map = new HashMap<>();
        /**
         * !!!
         * "Long.compare(b.expirationTime, a.expirationTime))"
         */
        PriorityQueue<Item> pq = new PriorityQueue<>((a, b) -> Long.compare(b.expirationTime, a.expirationTime));

        public class Item {
            private K key;
            private V val;
            private long expirationTime;

            public Item(K key, V val, long expirationTime) {
                this.key = key;
                this.val = val;
                this.expirationTime = expirationTime;
            }
        }

        public void put(K key, V val, long ttl) {
            Item it = new Item(key, val, System.currentTimeMillis() + ttl);
            pq.offer(it);
            map.put(key, it);
        }

        public V get(K k) {
            clearExpired();

            if (!map.containsKey(k)) return null;

            Item it = map.get(k);
            return it.val;

//            if (it.expirationTime > System.currentTimeMillis()) {
//                return it.val;
//            }

//            map.remove(k);
//            return null;
        }

        public void clearExpired() {
            while (!pq.isEmpty() && pq.peek().expirationTime >= System.currentTimeMillis()) {
                Item cur = pq.poll();
                map.remove(cur.key);
            }
        }
    }

    /**
     * Follow up : more efficient? Multi-thread, cleaning expired in a separate thread.
     */
}
