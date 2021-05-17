package Interviews.Lyft.lc;

import java.util.*;

public class Multi_Stream_1 {
    /**
     *     /**
     *      * #
     *      * 题目是implement一个multistream class。 这个class有3个funciton:
     *      * 1. Add, 输入是一个integer list，把这个list里的数字加到这个class里, return void。
     *      * 2. Remove， 输入是一个integer list， 把这个list从class里移除, return void。
     *      * 3. Read, 输入是一个integer n, 这个function需要从之前加过的那些list里面读n个数字。
     *      *    如果没有足够的数字就有多少读多少，返回integer list。
     *      *
     *      * 例子:
     *      * ms = new MultiStream();
     *      * ms.add(new ArrayList<Integer>(Arrays.asList(1,2,3,4)));
     *      * ms.add(new ArrayList<Integer>(Arrays.asList(5,6,7)));
     *      * ms.read(3) -> 返回 [1,2,3]
     *      * ms.remove(new ArrayList<Integer>(Arrays.asList(4)));
     *      * ms.read(2) -> 返回 [5,6]
     *      * ms.read(2) -> 返回 [7]
     *      *
     *      * 没啥太fancy的算法， brute force应该就是最优解，注意好index就行了
     */


    static final int READ_SIZE = 4;

    static class Stream {
        List<Integer> list;
        int ptr;

        public Stream(List<Integer> list) {
            this.list = new ArrayList<>(list);
            ptr = 0;
        }

        public int read(int[] res) {
            if (ptr >= this.list.size()) return 0;

            int size = Math.min(res.length, list.size() - ptr);
            int j = 0;

            for (int i = ptr; i < ptr + size; i++) {
                res[j++] = list.get(i);
            }
            ptr += size;

//            System.out.println("ptr = " + ptr);
            return j;
        }

        public int hashCode() {
            return this.list.hashCode();
        }

        public boolean equals(Object o) {
            Stream s = (Stream)o;
            return this.list.equals(s.list);
        }

        public String toString() {
            return Arrays.toString(this.list.toArray());
        }
    }

    static class MultiStream {
        class Node {
            Node pre;
            Node next;

            Stream stream;

            public Node(Stream stream) {
                this.stream = stream;
            }
        }

        Node head;
        Node tail;
        Node cur;
        int[] buf;
        int bufPtr;

        Map<Stream, Node> map;

        public MultiStream() {
            map = new HashMap<>();

            head = new Node(null);
            tail = new Node(null);
            head.next = tail;
            tail.pre = head;

            this.buf = new int[READ_SIZE];
            this.bufPtr = 0;
            this.cur = null;
        }

        public int[] read(int n) {
            System.out.println("read number : " + n);

            if (map.size() == 0) return new int[]{};

            int count = 0;
            List<Integer> res = new ArrayList<>();

            while (count < n) {
                if (this.cur.stream == null) break;

                int k = READ_SIZE;

                if (bufPtr == 0) {
                    k = this.cur.stream.read(this.buf);

                    if (k == 0) {
                        remove(this.cur.stream);
                        continue;
                    }
                }

                while (count < n && bufPtr < k) {
                    res.add(buf[bufPtr++]);
                    count++;
                }

                if (bufPtr >= k) {
                    bufPtr = 0;
                }
            }

            int[] nums = new int[res.size()];
            int idx = 0;
            for (int num : res) {
                nums[idx++] = num;
            }

            return nums;
        }

        public void add(Stream stream) {
            Node n = new Node(stream);

            if (head.next == tail) {
                this.cur = n;
                bufPtr = 0;
            }

            addToTail(n);
            map.put(stream, n);
        }

        public void remove(Stream stream) {
            if (!map.containsKey(stream)) {
                return;
            }

            Node n = map.get(stream);
            if (this.cur.equals(n)) {
                this.cur = cur.next;
                bufPtr = 0;
            }

            map.remove(stream);
            removeNode(n);
        }

        private void removeNode(Node n) {
            n.pre.next = n.next;
            n.next.pre = n.pre;
            n.next = null;
            n.pre = null;
        }

        private void addToTail(Node n) {
            tail.pre.next = n;
            n.pre = tail.pre;
            n.next = tail;
            tail.pre = n;
        }
    }

    private static void print(int[] nums) {
        System.out.println("");
        System.out.println("---read-----");
        System.out.println(Arrays.toString(nums));
        System.out.println("");
    }

    public static void main(String[] args) {
        MultiStream ms = new MultiStream();

//        List<Integer> l1 = Arrays.asList(1,2,3,4);
//        List<Integer> l2 = Arrays.asList(5,6,7);
//        List<Integer> l3 = Arrays.asList(8,9,10);

        List<Integer> l1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> l2 = Arrays.asList(9, 10, 11);
        List<Integer> l3 = Arrays.asList(12, 13, 14, 15, 16);

        Stream s1 = new Stream(l1);
        Stream s2 = new Stream(l2);
        Stream s3 = new Stream(l3);

        ms.add(s1);
        ms.add(s2);
        ms.add(s3);

//        print(ms.read(2));
//        print(ms.read(2));
//        ms.remove(s2);
//        print(ms.read(6));
//        ms.remove(s2);
//        print(ms.read(6));
        print(ms.read(10));
        ms.remove(s2);
        print(ms.read(6));
        ms.remove(s2);
        print(ms.read(6));

    }



//    static class MultiStream {
//        class Node {
//            Node pre;
//            Node next;
//
//            Iterator<Integer> it;
//
//            public Node(Iterator<Integer> it) {
//                this.it = it;
//            }
//        }
//
//        Map<List<Integer>, Node> map;
//        Node head;
//        Node tail;
//        Node cur;
//
//        public MultiStream() {
//            map = new HashMap<>();
//            head = new Node(null);
//            tail = new Node(null);
//            head.next = tail;
//            tail.pre = head;
//            cur = null;
//        }
//
//        public List<Integer> read(int n) {
//            List<Integer> res = new ArrayList<>();
//            if (cur.it == null) return res;
//
//            int i = 0;
//            while (i < n && cur.it != null) {
//                while (cur.it.hasNext()) {
//                    res.add(cur.it.next());
//                    i++;
//                }
//
//                if (!cur.it.hasNext()) {
//                    cur = cur.next;
//                }
//            }
//
//            return res;
//        }
//
//        public void add(List<Integer> list) {
//            if (list == null || list.READ_SIZE() == 0) return;
//
//            Node node = new Node(list.iterator());
//            addToTail(node);
//            map.put(list, node);
//        }
//
//        public void remove(List<Integer> list) {
//            if (!map.containsKey(list)) return;
//
//            Node node = map.get(list);
//            removeNode(node);
//            map.remove(list);
//        }
//
//        private void removeNode(Node n) {
//            if (cur.equals(n) && !cur.equals(tail)) {
//                cur = cur.next;
//            }
//
//            n.pre.next = n.next;
//            n.next.pre = n.pre;
//            n.next = null;
//            n.pre = null;
//        }
//
//        private void addToTail(Node n) {
//            if (tail.pre.equals(head)) {
//                cur = n;
//            }
//
//            tail.pre.next = n;
//            n.pre = tail.pre;
//            n.next = tail;
//            tail.pre = n;
//        }
//    }
//
//    public static void main(String[] args) {
//        MultiStream ms = new MultiStream();
//
//        List<Integer> l1 = Arrays.asList(1,2,3,4);
//        List<Integer> l2 = Arrays.asList(5,6,7);
//
//    }
}
