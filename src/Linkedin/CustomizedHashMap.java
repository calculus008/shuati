package Linkedin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implement hashmap using Array and Generic type
 */

public class CustomizedHashMap<K, V> {
    /**
     * !!!
     */
    static class Entry<K, V> {
        Entry<K, V> next;
        K key;
        V val;

        Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    Entry<K, V>[] arr = null;
    int capacity = 0;
    List<ReentrantReadWriteLock> locks = null;
    AtomicInteger count;

    CustomizedHashMap(int capacity) {
        /**
         * !!!
         */
        arr = (Entry<K, V>[])new Object[capacity];
        // arr = new Entry<K, V>[capacity]; In java, we couldnt create generic array
        this.capacity = capacity;

        locks = new ArrayList<ReentrantReadWriteLock>(capacity);
        for (int i = 0; i < capacity; i++) {
            locks.add(new ReentrantReadWriteLock());
        }
    }

    boolean isEmpty() {
        return count.intValue() == 0;
    }

    boolean isFull() {
        return count.intValue() == capacity;
    }

    void put(K key, V val) {
        if (isFull()) {
            throw new RuntimeException("Map is full");
        }

        int index = key.hashCode() % capacity;
        Entry<K, V> newEntry = new Entry<K, V>(key, val);

        try {
            locks.get(index).writeLock().lock();
            if (arr[index] == null) {
                arr[index] = newEntry;
            } else {
                Entry<K, V> curEntry = arr[index];
                Entry<K, V> preEntry = null;

                while (curEntry != null) {
                    if (curEntry.key.equals(key)) {
                        curEntry.val = val;
                        return;
                    }
                    preEntry = curEntry;
                    curEntry = curEntry.next;
                }

                preEntry.next = newEntry;
            }

            count.incrementAndGet();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            locks.get(index).writeLock().unlock();
        }
    }

    V get(K key) {
        if (isEmpty()) {
            throw new RuntimeException("Map is empty");
        }

        int index = key.hashCode() % capacity;

        try {
            locks.get(index).readLock().lock();
            if (arr[index] == null) {
                return null;
            } else {
                Entry<K, V> curEntry = arr[index];

                while (curEntry != null) {
                    if (curEntry.key.equals(key)) {
                        return curEntry.val;
                    }
                    curEntry = curEntry.next;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            locks.get(index).readLock().unlock();
        }

        return null;
    }

    boolean remove(K key) {
        if (isEmpty()) {
            throw new RuntimeException("Map is empty");
        }

        int index = key.hashCode() % capacity;

        if (arr[index] == null) {
            return false;
        } else {
            Entry<K, V> curEntry = arr[index];
            Entry<K, V> preEntry = null;

            while (curEntry != null) {
                if (curEntry.key.equals(key)) {
                    if (preEntry == null) {
                        arr[index] = curEntry.next;
                    } else {
                        preEntry.next = curEntry.next;
                    }

                    count.decrementAndGet();
                    return true;
                }

                preEntry = curEntry;
                curEntry = curEntry.next;
            }
        }

        return false;
    }

//    void resize() {
//        int oldSize = this.capacity;
//        this.capacity = this.capacity * 2;
//        Entry<K, V>[] oldArr = copyFromOld();
//        this.arr = (Entry<K, V>[])new Object[this.capacity];
//
//        // Rehash
//        for (int i = 0; i < oldSize; i++) {
//            Entry<K, V> entry = oldArr[i];
//
//            while (entry != null) {
//                K key = entry.key;
//                V val = entry.val;
//
//                put(key, val); // Set to a new position
//                entry = entry.next;
//            }
//        }
//    }
}

