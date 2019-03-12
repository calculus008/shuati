package Linkedin;

import java.util.*;

public class Retain_Best_Cache {
    /**
    Constructor with a data source (assumed to be slow) and a cache size

//    public RetainBestCache(DataSource<K, T> ds, int entriesToRetain) {
//    }

    Gets some data. If possible, retrieves it from cache to be fast. If the data is not cached,
    retrieves it from the data source. If the cache is full, attempt to cache the returned data,
    evicting the T with lowest rank among the ones that it has available
    If there is a tie, the cache may choose any T with lowest rank to evict.

//    public T get(K key) {
//    }


    For reference, here are the Rankable and DataSource interfaces.
    You do not need to implement them, and should not make assumptions
    about their implementations.

    Returns the Rank of this object, using some algorithm and potentially
    the internal state of the Rankable.

//    public interface Rankable {
//        long getRank();
//
//    }
//
//
//    public interface DataSource<K, T extends Rankable> {
//        T get(K key);
//
//    }
    **/



    public class RetainBestCache<K, T extends Rankable> {
        /**
         * Map + TreeMap
         *
         * Map : HashMap used to keep KV pair
         * TreeMap : Key is ranking, Value is set which saves the keys that have the same ranking
         *
         *
         */

        private Map<K, T> cache;
        private NavigableMap<Long, Set<K>> rankingOfObject;
        private DataSource<K, T> dataSource;
        private int maxSizeOfCache;

        /* Constructor with a data source (assumed to be slow) and a cache size */
        public RetainBestCache(DataSource<K,T> ds, int entriesToRetain) {
            // Implementation here
            cache = new HashMap<>();
            rankingOfObject = new TreeMap<>();
            dataSource = ds;
            maxSizeOfCache = entriesToRetain;
        }

        /* Gets some data. If possible, retrieves it from cache to be fast. If the data is not cached,
         * retrieves it from the data source. If the cache is full, attempt to cache the returned data,
         * evicting the T with lowest rank among the ones that it has available
         * If there is a tie, the cache may choose any T with lowest rank to evict.
         */
        public T get(K key) {
            // Implementation here
            if(cache.containsKey(key)) {
                return cache.get(key);
            }
            return fetchDataFromDs(key);
        }

        private T fetchDataFromDs(K key) {
            if(cache.size() >= maxSizeOfCache) {
                evictElement();
            }

            T object = dataSource.get(key);

            /**
             * 1.put object into map
             */
            cache.put(key, object);

            /**
             * get ranking
             */
            long rankOfObject = object.getRank();

            /**
             * update TreeMap for ranking info.
             */
            if(!rankingOfObject.containsKey(rankOfObject)) {
                rankingOfObject.put(rankOfObject, new HashSet<>());
            }

            rankingOfObject.get(rankOfObject).add(key);
            return object;
        }

        private void evictElement() {
            Map.Entry<Long, Set<K>> entry = rankingOfObject.firstEntry();

            /**
             * Remove the first element in set with iterator
             */
            K key = entry.getValue().iterator().next();
            /**
             * remove from TreeMap
             */
            entry.getValue().remove(key);
            /**
             * remove from Map
             */
            cache.remove(key);

            /**
             * Clear entry from TreeMap if set is empty
             */
            if(entry.getValue().size() == 0) {
                rankingOfObject.remove(entry.getKey());
            }
        }


    }

    /**
     * Follow ups
     * 1.What if rank is defined as number of reads of element in cache?
     *   LRU
     *
     * 2.Let's assume that rank can change dynamically. It is not immutable, but it is not LRU.
     *   We do not know how it is changed
     *
     * **/


    /*
     * For reference, here are the Rankable and DataSource interfaces.
     * You do not need to implement them, and should not make assumptions
     * about their implementations.
     */

    public interface Rankable {
        /**
         * Returns the Rank of this object, using some algorithm and potentially
         * the internal state of the Rankable.
         */
        long getRank();
    }

    public interface DataSource<K, T extends Rankable> {
        T get (K key);
    }

}
