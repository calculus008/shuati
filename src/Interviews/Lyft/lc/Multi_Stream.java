package Interviews.Lyft.lc;

import java.util.*;

public class Multi_Stream {

    /**
     * #
     * 假设你有一个Stream class，假设它里面装的都是int（下同）, 只有一个function -- read()，
     * class Stream:
     *     def read(n):
     *    # 至多读取n个int，可能小于n也可能读不出来
     *
     * 你设计一个MultiStream class，要求实现三个function
     * class MultiSteam:
     *     def read(n):
     *     # 类似于Stream的read
     *     def add(stream):
     *     # 把一个Stream加进来
     *     def remove(stream):
     *     # 把内部的这个steam删除
     *
     * 简单来讲就是想O(1)实现add和remove，然后处理read中n很大或者很小的问题。我的实现方法类似于LRU，就是hashtable+doublyLinkedlist。
     *
     * #
     * 问题来了，有点基于read4那个系列的题，但是考点不同，这个是考data structure，给你一个Stream的class，
     * 提供byte[] read(int n)的api，就是说给你一个stream的object的话里面有固定size的byte，你不用care它哪来的，
     * 你只能call这个read api来不断地读，当里面剩的小于n的时候，就会返回size小于n的byte【】，甚至空array。然后问题来了，
     * 基于这个Stream class，如何定义一个新的class MultiStream，实现如下api，byte[] read(int n),
     * void add(Stream s), void remove(Stream s),
     * 使用例子如下。有一些问题开始是要跟面试官澄清一下的，以此来决定你怎么选取data structure来实现这个，
     * 理想的是add read remove都是constant time。remove开始我犹豫了挺久，问清楚了是根据reference来remove，
     * 怎么handle remove了正要下一个read的stream怎么办（没啥问题，记得更新一个cur或则head listnode就行了）。
     * 最终我选用的是double linkedlist和hashmap的结合，和LRU的思路类似，只不过这个省了queue。
     *
     * m.add(s1) s1 = [0, 1, 2, 3]
     * m.add(s2) s2 = [0, 1, 2]
     * m.read(4) -> [0, 1, 2, 3]
     * m.read(4) -> [0, 1, 2]
     * m.read(5) -> []
     * m.read(2) -> []
     *
     * 楼主请问下这题， 设计read 和 remove的时候， 是要讨论具体怎么read 吗？  多个stream的话， 是round robin的形式每次
     * 往下跳一个stream， 还是说call read()的时候 input 是指定了要从哪个stream取的？        因为你说 ‘handle remove了
     * 正要下一个read的stream怎么办’   这个是说明read 是指定了stream的， 那就是按照index 或者设计stream每个都有自己
     * Stream ID是这样吗···  然后具体怎么handle呢？
     *
     * 不知道我是否get到你的问题，这个题的意思是这个multistream class和stream base class都是不care读完的数据的，
     * 用户使用的时候就只管read（n），直到每次都read空集。所以在add的时候就是顺序拼接，内部读完一个stream就“扔掉”
     *
     * 哦哦 有点理解了， 谢谢回复！ 意思是这个MultiStream 里面不管加多少Stream,  那个read() 就按照原来的位置顺序往下，
     * remove的那个stream刚好是read() 正读到一半的stream 就直接换到下一个stream的开头， 是这样吗？
     *
     * 对的！我开始也是停了一下想清楚问清楚才写的。稍微有点绕
     *
     * 我重新想了下，add的时候是按照stream 来的，每个stream 也都会有一个相应的id，
     * remove的时候 就是针对某一个stream id remove ，所以不会出现remove 的number 在cross stream 里面，
     * 这样也不用在乎remove的里面有没有duplicate
     * 如果用hashmap 存 stream id, remove 就是把id 从hashmap 里面删除， 当然要处理下read 目前所对应的pointer
     *
     * 不知道这样理解对不对
     */

    /**
     * assume each stream can only read fixed READ_SIZE of data
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

//            System.out.println("READ_SIZE="+READ_SIZE+", ptr=" + ptr);
            for (int i = ptr; i < ptr + size; i++) {
                res[j++] = list.get(i);
            }
            ptr += size;
            System.out.println("ptr = " + ptr);
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
//            System.out.println("cur : " + this.cur.stream.toString());
//            System.out.println(this.cur.stream.toString());

            /**
             * No stream in map
             */
            if (map.size() == 0) return new int[]{};

            int count = 0;
            List<Integer> res = new ArrayList<>();

            /**
             * 先读buf,如果buf里的数据读完，尝试从当前的stream往buf里读。
             * 如果当前的stream的数据读完，找下一个stream.
             * 如果没有stream了，返回。
             */

            while (count < n) {
                System.out.println("  count="+count + ", bufPtr="+this.bufPtr);

                if (this.cur.stream == null) break;

                int k = READ_SIZE;

                /**
                 * buf is empty
                 */
                if (bufPtr == 0) {
                    k = this.cur.stream.read(this.buf);
                    System.out.println("k = " + k);

                    if (k == 0) {
                        remove(this.cur.stream);
                        continue;
                    }
                }

                while (count < n && bufPtr < k) {
//                    System.out.println("count = " + count + ", bufPtr = " + bufPtr);
                    res.add(buf[bufPtr++]);
                    count++;
                }

                if (bufPtr >= k) {
                    bufPtr = 0;
                }

                System.out.println(Arrays.toString(res.toArray()));
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

//            System.out.println(stream.toString());

            addToTail(n);
            map.put(stream, n);
        }

        public void remove(Stream stream) {
//            System.out.println(this.map.READ_SIZE());

            if (!map.containsKey(stream)) {
                System.out.println(stream.toString() + " does not exist");
                return;
            }

            Node n = map.get(stream);
            System.out.println("remove n - " + n.stream.toString());

            if (this.cur.equals(n)) {
                this.cur = cur.next;
                this.bufPtr = 0;
            }

            /**
             * !!!
             */
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

        List<Integer> l1 = Arrays.asList(1,2,3,4);
        List<Integer> l2 = Arrays.asList(5,6,7);
        List<Integer> l3 = Arrays.asList(8,9,10);

//        List<Integer> l1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
//        List<Integer> l2 = Arrays.asList(9, 10, 11);
//        List<Integer> l3 = Arrays.asList(12, 13, 14, 15, 16);

        Stream s1 = new Stream(l1);
        Stream s2 = new Stream(l2);
        Stream s3 = new Stream(l3);

        ms.add(s1);
        ms.add(s2);
        ms.add(s3);

        print(ms.read(2));
        print(ms.read(2));
        ms.remove(s2);
        print(ms.read(6));
        ms.remove(s2);
        print(ms.read(6));

//        print(ms.read(10));
//        ms.remove(s2);
//        print(ms.read(6));
//        ms.remove(s2);
//        print(ms.read(6));

    }
}

