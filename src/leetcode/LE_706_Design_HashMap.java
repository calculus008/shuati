package leetcode;

import java.util.*;

public class LE_706_Design_HashMap {
    /**
     * Design a HashMap without using any built-in hash table libraries
     *
     * Easy
     *
     * https://leetcode.com/problems/design-hashmap
     */

    /**
     * String hash function
     *
     * Usually hashes wouldn't do sums, otherwise "stop" and "pots" will have the same hash.
     * and you wouldn't limit it to the first n characters because otherwise house and
     * houses would have the same hash.
     *
     * Generally hash take values and multiply it by a prime number (makes it more likely
     * to generate unique hashes) So you could do something like:
     *
     * int hash = 7;
     * for (int i = 0; i < strlen; i++) {
     *     hash = hash*31 + charAt(i);
     * }
     *
     * Or use Java str.hashCode()
     * https://www.tutorialspoint.com/java/java_string_hashcode.htm
     *
     */

    class HashMap_with_hash{
        class ListNode {
            int key, val;
            ListNode next;
            public ListNode(int key, int val, ListNode next) {
                this.key = key;
                this.val = val;
                this.next = next;
            }
        }
        class MyHashMap {
            static final int size = 19997;
            static final int mult = 12582917;
            ListNode[] data;
            public MyHashMap() {
                this.data = new ListNode[size];
            }

            private int hash(int key) {
                return (int)((long)key * mult % size);
            }

            public void put(int key, int val) {
                remove(key);
                int h = hash(key);
                ListNode node = new ListNode(key, val, data[h]);
                data[h] = node;
            }

            public int get(int key) {
                int h = hash(key);
                ListNode node = data[h];

                while(node != null) {
                    if (node.key == key) {
                        return node.val;
                    }
                    node = node.next;
                }
                return -1;
            }

            public void remove(int key) {
                int h = hash(key);
                ListNode node = data[h];
                if (node == null) return;

                if (node.key == key) {
                    data[h] = node.next;
                }else {
                    while (node.next != null) {
                        if (node.next.key == key) {
                            node.next = node.next.next;
                            return;
                        }
                        node = node.next;
                    }
                }
            }
        }
    }

    /**
     * ******************************
     */

    class Pair<U, V> {
        public U first;
        public V second;

        public Pair(U first, V second) {
            this.first = first;
            this.second = second;
        }
    }


    class Bucket {
        private List<Pair<Integer, Integer>> bucket;

        public Bucket() {
            this.bucket = new LinkedList<Pair<Integer, Integer>>();
        }

        public Integer get(Integer key) {
            for (Pair<Integer, Integer> pair : this.bucket) {
                if (pair.first.equals(key))
                    return pair.second;
            }
            return -1;
        }

        public void update(Integer key, Integer value) {
            boolean found = false;
            for (Pair<Integer, Integer> pair : this.bucket) {
                if (pair.first.equals(key)) {
                    pair.second = value;
                    found = true;
                }
            }
            if (!found)
                this.bucket.add(new Pair<Integer, Integer>(key, value));
        }

        public void remove(Integer key) {
            for (Pair<Integer, Integer> pair : this.bucket) {
                if (pair.first.equals(key)) {
                    this.bucket.remove(pair);
                    break;
                }
            }
        }
    }

    class MyHashMap {
        private int key_space;
        private List<Bucket> hash_table;

        /** Initialize your data structure here. */
        public MyHashMap() {
            this.key_space = 2069;
            this.hash_table = new ArrayList<Bucket>();
            for (int i = 0; i < this.key_space; ++i) {
                this.hash_table.add(new Bucket());
            }
        }

        /** value will always be non-negative. */
        public void put(int key, int value) {
            int hash_key = key % this.key_space;
            this.hash_table.get(hash_key).update(key, value);
        }

        /**
         * Returns the value to which the specified key is mapped, or -1 if this dist contains no mapping
         * for the key
         */
        public int get(int key) {
            int hash_key = key % this.key_space;
            return this.hash_table.get(hash_key).get(key);
        }

        /** Removes the mapping of the specified value key if this dist contains a mapping for the key */
        public void remove(int key) {
            int hash_key = key % this.key_space;
            this.hash_table.get(hash_key).remove(key);
        }
    }

    /**
     * Your MyHashMap object will be instantiated and called as such: MyHashMap obj = new MyHashMap();
     * obj.put(key,value); int param_2 = obj.get(key); obj.remove(key);
     */



    class MyHashMap1 {
        class ListNode {
            int key;
            int val;
            ListNode next;

            ListNode(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        final ListNode[] nodes = new ListNode[10000];

        public void put(int key, int value) {
            int i = getIdx(key);
            if (nodes[i] == null) {
                nodes[i] = new ListNode(-1, -1);
            }

            ListNode prev = find(nodes[i], key);

            if (prev.next == null) {
                prev.next = new ListNode(key, value);
            } else {
                prev.next.val = value;
            }
        }

        public int get(int key) {
            int i = getIdx(key);
            if (nodes[i] == null) {
                return -1;
            }

            ListNode node = find(nodes[i], key);

            return node.next == null ? -1 : node.next.val;
        }

        public void remove(int key) {
            int i = getIdx(key);
            if (nodes[i] == null) {
                return;
            }

            ListNode prev = find(nodes[i], key);
            if (prev.next == null) {
                return;
            }

            prev.next = prev.next.next;
        }

        int getIdx(int key) {
            return Integer.hashCode(key) % nodes.length;
        }//or return key % nodes.length;

        ListNode find(ListNode bucket, int key) {
            ListNode node = bucket, prev = null;
            while (node != null && node.key != key) {
                prev = node;
                node = node.next;
            }
            return prev;
        }

    }

    class MyHashMap_with_array {
        int[] data;
        public MyHashMap_with_array() {
            data = new int[1000001];
            Arrays.fill(data, -1);
        }

        public void put(int key, int val) {
            data[key] = val;
        }

        public int get(int key) {
            return data[key];
        }

        public void remove(int key) {
            data[key] = -1;
        }
    }

}
