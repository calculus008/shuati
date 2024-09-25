package Interviews.Linkedin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implement hashmap using Array and Generic type
 *
 * Java ReentrantReadWriteLock
 *
 * readLock.lock();
 * This means that if any other thread is writing (i.e. holds a write lock) then stop here
 * until no other thread is writing.
 *
 * Once the lock is granted no other thread will be allowed to write (i.e. take a write lock)
 * until the lock is released.
 *
 * writeLock.lock();
 * This means that if any other thread is reading or writing, stop here and wait until no other
 * thread is reading or writing.
 *
 * Once the lock is granted, no other thread will be allowed to read or write (i.e. take a read
 * or write lock) until the lock is released.
 *
 * Combining these you can arrange for only one thread at a time to have write access but as
 * many readers as you like can read at the same time except when a thread is writing.
 *
 * Put another way. Every time you want to read from the structure, take a read lock.
 * Every time you want to write, take a write lock. This way whenever a write happens no-one
 * is reading (you can imagine you have exclusive access), but there can be many readers reading
 * at the same time so long as no-one is writing.
 *
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

    public CustomizedHashMap(int capacity) {
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

    public boolean isEmpty() {
        return count.intValue() == 0;
    }

    public boolean isFull() {
        return count.intValue() == capacity;
    }

    public void put(K key, V val) {
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

    public V get(K key) {
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

    /**
     * remove() operation will require a write lock for the entry, since it modifies the HashMap by removing
     * an entry.
     *
     * Additionally, after removing the entry from the HashMap, you should also remove the associated
     * ReentrantReadWriteLock for that entry, as it is no longer needed. Failing to do so may cause memory leaks,
     * as the lock would remain in memory even though the corresponding entry has been deleted.
     */
    public boolean remove(K key) {
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

