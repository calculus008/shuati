package SystemDesign;

import java.util.*;

public class LI_502_Mini_Cassandra {
    /**
     * Cassandra is a NoSQL database (a.k.a key-value storage). One individual data entry
     * in cassandra constructed by 3 parts:
     *
     * row_key. a.k.a hash_key, partition key or sharding_key.
     * column_key.
     * value
     * row_key is used to hash and can not support range query. let's simplify this to a string.
     * column_key is sorted and support range query. let's simplify this to integer.
     * value is a string. you can serialize any data into a string and store it in value.
     *
     * implement the following methods:
     *
     * insert(row_key, column_key, value)
     * query(row_key, column_start, column_end) // return a list of entries
     */


     //Definition of Column:
     public class Column {
          public int key;
          public String value;
          public Column(int key, String value) {
              this.key = key;
              this.value = value;
         }
      }

    public class MiniCassandra {

        private Map<String, NavigableMap<Integer, String>> hash;
        public MiniCassandra() {
            // initialize your data structure here.
            hash = new HashMap<String, NavigableMap<Integer, String>>();
        }

        /**
         * @param raw_key a string
         * @param column_start an integer
         * @param column_end an integer
         * @return void
         */
        public void insert(String raw_key, int column_key, String column_value) {
            // Write your code here
            if (!hash.containsKey(raw_key))
                hash.put(raw_key, new TreeMap<Integer, String>());
            hash.get(raw_key).put(column_key, column_value);
        }

        /**
         * @param raw_key a string
         * @param column_start an integer
         * @param column_end an integer
         * @return a list of Columns
         */
        public List<Column> query(String raw_key, int column_start, int column_end) {
            // Write your code here
            List<Column> rt = new ArrayList<Column>();
            if (!hash.containsKey(raw_key))
                return rt;
            for (Map.Entry<Integer, String> entry :
                    hash.get(raw_key).subMap(column_start, true, column_end, true).entrySet()) {
                rt.add(new Column(entry.getKey(), entry.getValue()));
            }
            return rt;
        }
    }
}