package Interviews.Lyft.lc;

import java.util.*;

public class Common_Numbers_In_Two_Arrays {
    /**
     * LE_349_Intersection_Of_Two_Arrays
     * LE_350_Intersection_Of_Two_Arrays_II
     *
     * Follow up 1: 给定 l1 和 l2 都是 iterator，要求实现一个 Iterator。这个 Iterator 有 next 和 has_next 两个方法，
     * call next 会返回下一个common number。has_next 返回 true or false 表示是否有下一个 common number。我实现的时候
     * 把找下一个 common number 的逻辑都放在 has_next 里，然后用一个全局变量存 next common number。
     *
     * Assume both l1 and l2 are sorted?
     *
     * Follow up 2: 如果 l1 和 l2 里有 duplicates 而我们不想 返回 duplicates怎么办？ 比如 l1 = [1, 2, 2, 3, 3, 6, 8, 10],
     * l2 = [2, 2, 3, 3, 5, 7, 10, 12] 返回 [2, 3] 而不是 [2, 2, 3, 3]。每次call next(l1) 的时候保证 next(l1) 不等于上一个 number。
     *
     * Follow up 3: 如果输入不止两个数组而是多个数组怎么办？这里只要求讲思路不用写码。可以用 min heap 存每个 iterator 的 next，
     * 然后用 counter 记录某个数字出现的次数，如果等于 iterator 数量就可以返回。
     *
     * Intersection_Union_Of_K_Sorted_Lists
     * Merge_K_Sorted_Stream_Practice
     */

    /**
     * Solution for LE_350_Intersection_Of_Two_Arrays_II
     */
    public int[] intersect1(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();

        for (int num : nums1) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (int num : nums2) {
            if (map.containsKey(num) && map.get(num) > 0) {//!!! just check bigger than 0 and keep subtracting, avoid using "remove()"
                map.put(num, map.get(num) - 1);
                list.add(num);
            }
        }

        int[] res = new int[list.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = list.get(i);
        }

        return res;
    }

    public int[] intersect2(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
            return new int[]{};
        }

        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> res = new ArrayList<>();
        int i = 0, j = 0;

        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                i++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                res.add(nums1[i]);
                i++;
                j++;
            }
        }

        int[] ans = new int[res.size()];
        int k = 0;
        for (int num : res) {
            ans[k++] = num;
        }

        return ans;
    }


    /**
     * Follow up 1:
     * 给定 l1 和 l2 都是 iterator，要求实现一个 Iterator。这个 Iterator 有 next 和 has_next 两个方法，
     * call next 会返回下一个common number。has_next 返回 true or false 表示是否有下一个 common number。我实现的时候
     * 把找下一个 common number 的逻辑都放在 has_next 里，然后用一个全局变量存 next common number。
     *
     * NOTE:
     * Assumption for the following solutions are that the given List of numbers are already SORTED.
     *
     * Otherwise, need to do sorting first.
     * Collections.sort(list)
     */
    static class CommonNumberIterator {
        Iterator<Integer> it1;
        Iterator<Integer> it2;
        Integer next;
        Integer n1;
        Integer n2;

        public CommonNumberIterator(Iterator<Integer> it1, Iterator<Integer> it2) {
            this.it1 = it1;
            this.it2 = it2;
            next = null;
            if (it1.hasNext()) {
                n1 = it1.next();
            }
            if (it2.hasNext()) {
                n2 = it2.next();
            }
        }

        public boolean hasNext() {
            while (n1 != null && n2 != null) {
                System.out.println("n1="+n1+", n2="+n2);
                if (n1 < n2) {
                    n1 = it1.hasNext() ? it1.next() : null;
                } else if (n1 > n2) {
                    n2 = it2.hasNext() ? it2.next() : null;
                } else {
                    next = n1;
                    n1 = it1.hasNext() ? it1.next() : null;
                    n2 = it2.hasNext() ? it2.next() : null;
                    System.out.println("return true, n1="+n1+", n2="+n2);
                    return true;
                }
            }

            return false;
        }

        public Integer next() {
            Integer res = next;
            next = null;
            return res;
        }
    }

    /**
     * Use Node class to encapsulate both iterator and current value
     */
    static class CommonNumberIterator1 {
        Node node1;
        Node node2;
        Integer next;


        public CommonNumberIterator1(Iterator<Integer> it1, Iterator<Integer> it2) {
            next = null;

            if (it1 != null && it1.hasNext()) {
                node1 = new Node(it1, it1.next());
            } else {
                node1 = null;
            }

            if (it2 != null && it2.hasNext()) {
                node2 = new Node(it2, it2.next());
            } else {
                node2 = null;
            }
        }

        public boolean hasNext() {
            if (node1 == null || node2 == null) return false;

            while (node1.val != null && node2.val != null) {
//                System.out.println("n1="+n1+", n2="+n2);
                if (node1.val < node2.val) {
                    node1.val = node1.it.hasNext() ? node1.it.next() : null;
                } else if (node1.val > node2.val) {
                    node2.val = node2.it.hasNext() ? node2.it.next() : null;
                } else {
                    next = node1.val;
                    node1.val = node1.it.hasNext() ? node1.it.next() : null;
                    node2.val = node2.it.hasNext() ? node2.it.next() : null;
                    return true;
                }
            }

            return false;
        }

        public Integer next() {
            Integer res = next;
            next = null;
            return res;
        }
    }

    /**
     * Follow up 2:
     * 如果 l1 和 l2 里有 duplicates 而我们不想 返回 duplicates怎么办？ 比如 l1 = [1, 2, 2, 3, 3, 6, 8, 10],
     * l2 = [2, 2, 3, 3, 5, 7, 10, 12] 返回 [2, 3] 而不是 [2, 2, 3, 3]。
     * 每次call next(l1) 的时候保证 next(l1) 不等于上一个 number。
     */
    static class CommonUniqueNumberIterator {
        Iterator<Integer> it1;
        Iterator<Integer> it2;
        Integer next;
        Integer n1;
        Integer n2;

        public CommonUniqueNumberIterator(Iterator<Integer> it1, Iterator<Integer> it2) {
            this.it1 = it1;
            this.it2 = it2;
            next = null;
            if (it1.hasNext()) {
                n1 = it1.next();
            }
            if (it2.hasNext()) {
                n2 = it2.next();
            }
        }

        public boolean hasNext() {
            while (n1 != null && n2 != null) {
                System.out.println("n1="+n1+", n2="+n2);
                if (n1 < n2) {
                    n1 = it1.hasNext() ? it1.next() : null;
                } else if (n1 > n2) {
                    n2 = it2.hasNext() ? it2.next() : null;
                } else {
                    next = n1;
                    /**
                     * 只能是AND，不能是OR
                     */
                    while (n1 == next && n2 == next) {
                        n1 = it1.hasNext() ? it1.next() : null;
                        n2 = it2.hasNext() ? it2.next() : null;
                    }
                    System.out.println("return true, n1="+n1+", n2="+n2);
                    return true;
                }
            }

            return false;
        }

        public Integer next() {
            Integer res = next;
            next = null;
            return res;
        }
    }

    static class CommonUniqueNumberIterator1 {
        Node node1;
        Node node2;
        Integer next;


        public CommonUniqueNumberIterator1(Iterator<Integer> it1, Iterator<Integer> it2) {
            next = null;

            if (it1 != null && it1.hasNext()) {
                node1 = new Node(it1, it1.next());
            } else {
                node1 = null;
            }

            if (it2 != null && it2.hasNext()) {
                node2 = new Node(it2, it2.next());
            } else {
                node2 = null;
            }
        }

        public boolean hasNext() {
            if (node1 == null || node2 == null) return false;

            while (node1.val != null && node2.val != null) {
//                System.out.println("n1="+n1+", n2="+n2);
                if (node1.val < node2.val) {
                    node1.val = node1.it.hasNext() ? node1.it.next() : null;
                } else if (node1.val > node2.val) {
                    node2.val = node2.it.hasNext() ? node2.it.next() : null;
                } else {
                    next = node1.val;

                    while (node1.val == next && node2.val == next) {
                        node1.val  = node1.it.hasNext() ? node1.it.next() : null;
                        node2.val  = node2.it.hasNext() ? node2.it.next() : null;
                    }
                    return true;
                }
            }

            return false;
        }

        public Integer next() {
            Integer res = next;
            next = null;
            return res;
        }
    }

    /**
     * Follow up 3:
     * 如果输入不止两个数组而是多个数组怎么办？这里只要求讲思路不用写码。
     * 可以用 min heap 存每个 iterator 的 next，
     * 然后用 counter 记录某个数字出现的次数，如果等于 iterator 数量就可以返回。
     */
    static class Node {
        Iterator<Integer> it;
        Integer val;

        public Node(Iterator<Integer> it, Integer val) {
            this.it = it;
            this.val = val;
        }
    }

    static class CommonUniqueNumberIteratorMultiIt {
        Integer pre;
        Integer next;
        PriorityQueue<Node> pq;
        int size;

        public CommonUniqueNumberIteratorMultiIt(List<Iterator<Integer>> its) {
            pq = new PriorityQueue<>((a, b) -> a.val - b.val);

            for (Iterator<Integer> it : its) {
                if (it == null || !it.hasNext()) continue;
                pq.offer(new Node(it, it.next()));
            }

            size = pq.size();
            pre = null;
            next = null;
//            System.out.println("init done, READ_SIZE = " + READ_SIZE);
        }

        public boolean hasNext() {
            if (pq == null || pq.size() < size) {
                System.out.println("#1.pq READ_SIZE is smaller than " + size + ", return false");
                if (pq != null) {
                    pq = null;
                }
                return false;
            }

            int count = 0;

            while (!pq.isEmpty()) {
                Node cur = pq.poll();
                System.out.println(cur.val);

                if (pre == null) {
                    pre = cur.val;
                    count = 1;
                } else {
                    if (cur.val == pre) {
                        count++;
                    } else {
                        count = 1;
                        pre = cur.val;
                    }
                }

//                System.out.println("count = " + count);

                //In the same iterator, move to the next unique number
                while (cur.it.hasNext()) {
                    int val = cur.it.next();
                    if (val != pre) {
                        cur.val = val;
                        pq.offer(cur);
                        break;
                    }
                }

                if (count == size) {
                    next = pre;
                    return true;
                }

                /**
                 * !!!
                 * can't return true here, it will miss the valid ones at the end
                 */
//                if (pq.READ_SIZE() < READ_SIZE) {
//                    System.out.println("#2.pq READ_SIZE is smaller than " + READ_SIZE + ", return false");
//                    return false;
//                }
            }

            return false;
        }

        public Integer next() {
            Integer res = next;
            next = null;
            return res;
        }
    }

        public static void main(String[] args) {
        int[] nums1 = {1, 2, 2, 3, 3, 6, 8, 10, 10, 10};
        int[] nums2 = {2, 2, 3, 3, 5, 7, 10, 10, 10, 12};

//        int[] nums1 = {6, 8, 11};
//        int[] nums2 = {5, 7, 10, 12};

//        int[] nums1 = {1, 2, 10};
//        int[] nums2 = {5, 6, 7, 10, 12};

//        int[] nums1 = {4, 5, 9};
//        int[] nums2 = {3, 4, 7, 10, 12};

//        int[] nums1 = {1, 5, 9};
//        int[] nums2 = {3, 4, 5, 6, 7, 10, 12};
//
//        int[] nums1 = {2, 2, 2};
//        int[] nums2 = {1, 1, 2, 2, 2};

//        int[] nums1 = {2, 2, 2};
//        int[] nums2 = {2, 2, 2};

//        int[] nums1 = {1, 2, 2, 7, 10, 11, 22, 22, 22, 22, 23, 50, 60, 70, 100};
//        int[] nums2 = {1, 1, 2, 2, 2, 10, 14, 22, 22, 22, 23};

        int[] nums3 = {1, 2, 5, 6, 10, 22, 22, 23, 55, 66};

        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        List<Integer> l3 = new ArrayList<>();


        for (int num1 : nums1) {
            l1.add(num1);
        }

        for (int num2 : nums2) {
            l2.add(num2);
        }

        for (int num3 : nums3) {
            l3.add(num3);
        }

//        CommonNumberIterator it = new CommonNumberIterator(l1.iterator(), l2.iterator());

//        CommonNumberIterator1 it = new CommonNumberIterator1(l1.iterator(), l2.iterator());
//
        CommonUniqueNumberIterator1 it = new CommonUniqueNumberIterator1(l1.iterator(), l2.iterator());

//        CommonUniqueNumberIterator it = new CommonUniqueNumberIterator(l1.iterator(), l2.iterator());


        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());

        List<Iterator<Integer>> its = new ArrayList<>();
        its.add(l1.iterator());
        its.add(l2.iterator());
        its.add(l3.iterator());

//        CommonUniqueNumberIteratorMultiIt itMulti = new CommonUniqueNumberIteratorMultiIt(its);

//        System.out.println(itMulti.hasNext());
//        System.out.println(itMulti.next());
//        System.out.println(itMulti.hasNext());
//        System.out.println(itMulti.next());
//        System.out.println(itMulti.hasNext());
//        System.out.println(itMulti.next());
//        System.out.println(itMulti.hasNext());
//        System.out.println(itMulti.next());
//        System.out.println(itMulti.hasNext());
//        System.out.println(itMulti.next());
//        System.out.println(itMulti.hasNext());
//        System.out.println(itMulti.next());
    }
}
