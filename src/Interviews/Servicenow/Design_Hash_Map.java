package src.Interviews.Servicenow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Design_Hash_Map {
    public class MyHashMap {
        class Pair<K, V> {
            public K key;
            public V val;

            public Pair(K key, V val) {
                this.key = key;
                this.val = val;
            }
        }

        class Bucket {
            private List<Pair<String, String>> bucket;

            public Bucket() {
                this.bucket = new LinkedList<>();
            }

            public String get(String key) {
                for (Pair<String, String> p : this.bucket) {
                    if (p.key.equals(key)) {
                        return p.val;
                    }
                }

                return null;
            }

            public void update(String key, String val) {
                boolean found = false;
                for (Pair<String, String> p : this.bucket) {
                    if (p.key.equals(key)) {
                        p.val = val;
                        found = true;
                    }
                }

                if (!found) {
                    this.bucket.add(new Pair(key, val));
                }
            }

            public void remove(String key) {
                for (Pair<String, String> p:this.bucket){
                    if (p.key.equals(key)) {
                        this.bucket.remove(p);
                        break;
                    }
                }
            }
        }

        private int key_space;
        private List<Bucket> hash_table;

        public MyHashMap() {
            key_space = 2069;
            hash_table = new ArrayList<>();
            for (int i = 0; i < key_space; i++) {
                hash_table.add(new Bucket());
            }
        }

        public void put(String key, String val) {
            int hashKey = key.hashCode() % this.key_space;
            this.hash_table.get(hashKey).update(key, val);
        }

        public String get(String key) {
            int hashKey = key.hashCode() % this.key_space;
            return this.hash_table.get(hashKey).get(key);
        }

        public void remove(String key) {
            int hashKey = key.hashCode() % this.key_space;
            this.hash_table.get(hashKey).remove(key);
        }
    }
}
