package leetcode;

import java.util.*;

/**
 * Created by yuank on 10/4/18.
 */
public class LE_381_Insert_Delete_GetRandom_O1_Duplicates_Allowed {
    /**
     Design a data structure that supports all following operations in average O(1) time.

     Note: Duplicate elements are allowed.

     insert(val): Inserts an item val to the collection.
     remove(val): Removes an item val from the collection if present.
     getRandom: Returns a random element from current collection of elements.
                The probability of each element being returned is linearly related to
                the number of same value the collection contains.
     Example:

     // Init an empty collection.
     RandomizedCollection collection = new RandomizedCollection();

     // Inserts 1 to the collection. Returns true as the collection did not contain 1.
     collection.insert(1);

     // Inserts another 1 to the collection. Returns false as the collection contained 1. Collection now contains [1,1].
     collection.insert(1);

     // Inserts 2 to the collection, returns true. Collection now contains [1,1,2].
     collection.insert(2);

     // getRandom should return 1 with the probability 2/3, and returns 2 with the probability 1/3.
     collection.getRandom();

     // Removes 1 from the collection, returns true. Collection now contains [1,2].
     collection.remove(1);

     // getRandom should return 1 and 2 both equally likely.
     collection.getRandom();
     */

    /**
     *  Solution 1
        Solution with HashMap, LinkedHashSet and List

        Modified from LE_380_Insert_Delete_GetRandom_O1:
        Instead of using Map<Integer, Integer>, use Map<Integer, LinkedHashSet<>>, because now we may have duplicates,
        so, for each inserted value, need to save its index in list in LinkedHashSet.

        1.To start, we know HashMap satisfies insert/remove in O(1), but not random retrieval.
          So we need to another data structure.
        2.ArrayList "list" is the data structure satisfies random retrieval requirements.
        3.HashMap uses the input val as key, the value is set, which stores the indexes of the key value
          in ArrayList "nums"
        4.So that remove can trace the it back to "list" and remove it in O(1) by using copy and remove trick
        5.In HashMap, why use LinkedHashSet, not list?
          Remove()
          For list, we can remove by index or remove an object from list.
          For Set, it only supports removing object.
          So when we use remove on map, using set avoids confusion and potential bug.

          因为普通的HashSet iterate时间复杂度不是O(1)而是O(h/n) h是capacity

          This solution is based on assumption :
          LinkedHashSet.remove() is O(1)

          "Using LinkedHashSet can be considered as O(1) if we only get the first element to remove."

     **/
    class RandomizedCollection1 {
        HashMap<Integer, LinkedHashSet<Integer>> map;
        List<Integer> list;
        java.util.Random rand = new java.util.Random();


        /** Initialize your data structure here. */
        public RandomizedCollection1() {
            map = new HashMap<>();
            list = new ArrayList<>();
        }

        /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
        public boolean insert(int val) {
            boolean ret = false;
            if (!map.containsKey(val)) {
                map.put(val, new LinkedHashSet<>());
                ret = true;
            }

            int idx = list.size();
            list.add(val);
            //!!!
            map.get(val).add(idx);

            return ret;
        }

        /** Removes a value from the collection. Returns true if the collection contained the specified element. */
        public boolean remove(int val) {
            if (!map.containsKey(val)) {
                return false;
            }

            /**
             * 1.Get the first element in set, remove it from the set in HashMap.
             **/
            int idx = map.get(val).iterator().next();//Use iterator to get the first element in set
            map.get(val).remove(idx);//remove the value from set

            /**
             * 2. if the target index is not the last one in list :
             *  a.Get the last value in list.
             *  b.Set the value at index "col".
             *  c.Update HashMap:
             *    For value "last" in HashMap, use it as key to find the set in HashMAp
             *    delete the value of the current last index of list from set,
             *    add value "col" to set.
             */
            if (idx < list.size() - 1) {//!!! not the last one in list
                int last = list.get(list.size() - 1);
                list.set(idx, last);
                map.get(last).remove(list.size() - 1);//remove the value from set
                map.get(last).add(idx);
            }

            /**
             * 3.Remove the last element in list
             */
            list.remove(list.size() - 1);

            /**
             * 4.If set is empty, remove entry from HashMap
             */
            if (map.get(val).isEmpty()) {
                map.remove(val);
            }

            return true;
        }

        /** Get a random element from the collection. */
        public int getRandom() {
            return list.get(rand.nextInt(list.size()));
        }
    }

    /**
     * Solution 2
     * Save both number and its list index
     *
     * 假设你已经会了 Insert Delete GetRandom O(1),然后在这个题的基础上，基本算法是不变的，只不过需要一个办法来处理重复的数。
     * 这里的解决办法是，用 HashMap 存储 number to a list of indices in numbers array. 也就是说，把某个数在数组中出现的所有的位置用
     * List 的形式存储下来,这样子的话，删除一个数的时候，就是从这个 list 里随便拿走一个数（比如最后一个数）,
     * 但是还需要解决的问题是，原来的算法中，删除一个数的时候，需要拿 number array 的最后个位置的数，来覆盖掉被删除的数。
     * 那这样原本在最后一个位置的数，他在 HashMap 里的记录就应该相应的变化。但是，我们只能得到这个移动了的数是什么，而这个被移动过的数，
     * 可能出现在好多个位置上，去要从 HashMap 里得到的 indices 列表里，改掉那个 index=当前最后一个位置的下标。
     * 所以这里的做法是，修改 number array，不只是存储 Number，同时存储，这个数在 HashMap 的 indices 列表中是第几个数。
     * 这样就能直接去改那个数的值了。否则还得 for 循环一次，就无法做到 O(1),还有一种办法是用 LinkedHashSet，不过其他语言没有这个东西。
     *
     */
    class NumberAndIndex {
        public int number, index;
        public NumberAndIndex(int number, int index) {
            this.number = number;
            this.index = index;
        }
    }

    public class RandomizedCollection2 {
        // pair.number is the number, pair.index is the index in map value
        private List<NumberAndIndex> nums;
        // key is the number, value if the indices list in nums;
        private Map<Integer, List<Integer>> map;
        private Random rand;

        /** Initialize your data structure here. */
        public RandomizedCollection2() {
            map = new HashMap<>();
            nums = new ArrayList<>();
            rand = new Random();
        }

        /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
        public boolean insert(int val) {
            boolean existed = map.containsKey(val);

            if (!existed) {
                map.put(val, new ArrayList<Integer>());
            }
            List<Integer> indices = map.get(val);
            indices.add(nums.size());
            nums.add(new NumberAndIndex(val, indices.size() - 1));

            return !existed;
        }

        /** Removes a value from the collection. Returns true if the collection contained the specified element. */
        public boolean remove(int val) {
            if (!map.containsKey(val)) {
                return false;
            }

            List<Integer> indices = map.get(val);
            int index = indices.get(indices.size() - 1);

            NumberAndIndex numIndex = nums.get(nums.size() - 1);
            nums.set(index, numIndex);
            nums.remove(nums.size() - 1);
            map.get(numIndex.number).set(numIndex.index, index);

            indices.remove(indices.size() - 1);
            if (indices.size() == 0) {
                map.remove(val);
            }

            return true;
        }

        /** Get a random element from the collection. */
        public int getRandom() {
            int index = rand.nextInt(nums.size());
            return nums.get(index).number;
        }
    }

    /**
     * Solution 3
     * Same as Solutin 2, my version
     *
     * 比较LE_380_Insert_Delete_GetRandom_O1，我们需要存储相同value在list中出现的所有的indexes。
     * 问题的焦点是，如何保证remove()是O(1)。这里，remove()要有两个操作：
     *
     * 1.从map的list里删除，因为没有删除list 里元素的顺序的要求，所以，取map里list的最后一个元素。
     *   删除它（O(1))，并用其值（也就是list 里的index）去删除list里的元素。
     * 2.从list里删除。我们用#1里从map里拿到的index，用copy_delete-last可以保证删除是O(1)
     *
     * 最后在删除的同时，作为copy_delete-last的相应处理，应该把原先的list 里最后的元素在map里
     * 相应的index值改变。这就是为什么我们要用Element class (have both val and getIdx). 如果
     * 没有idx, 我们不知道在map的list里的什么位置去做改变。
     */
    class RandomizedCollection3 {
        /**
         * Element has both value and the index or location in the list retrieved from
         * HashMap by value.
         *
         * We save it in list, the purpose:
         * When doing remove(), use it to find the location to update in the HashMap list
         * when we move the end element to overwrite the to be deleted element in list.
         */
        class Element {
            int idx, val;
            public Element(int idx, int val) {
                this.idx = idx;
                this.val = val;
            }
        }

        HashMap<Integer, List<Integer>> map;
        List<Element> list;
        java.util.Random rand = new java.util.Random();


        /** Initialize your data structure here. */
        public RandomizedCollection3() {
            map = new HashMap<>();
            list = new ArrayList<>();
        }

        /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
        public boolean insert(int val) {
            boolean ret = false;
            if (!map.containsKey(val)) {
                map.put(val, new ArrayList<>());
                ret = true;
            }

            list.add(new Element(map.get(val).size(), val));
            map.get(val).add(list.size() - 1);

            return ret;
        }

        /** Removes a value from the collection. Returns true if the collection contained the specified element. */
        public boolean remove(int val) {
            if(!map.containsKey(val)) {
                return false;
            }

            /**
             * !!!
             * list.remove(), return value : the element previously at the specified position.
             *
             * Also, remove last element in ArrayList is O(1).
             */
            int targetIdx = map.get(val).remove(map.get(val).size() - 1);

            /**
             * !!!
             */
            if (map.get(val).size() == 0) {
                map.remove(val);
            }


            if (targetIdx < list.size() - 1) {
                Element e = list.get(list.size() - 1);
                list.set(targetIdx, e);
                map.get(e.val).set(e.idx, targetIdx);//!!! list.set(col,value)
            }
            list.remove(list.size() - 1);

            return true;
        }

        /** Get a random element from the collection. */
        public int getRandom() {
            return list.get(rand.nextInt(list.size())).val; //!!! ".val"
        }
    }
}
