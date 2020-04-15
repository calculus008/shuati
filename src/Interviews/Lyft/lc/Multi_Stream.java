package Interviews.Lyft.lc;

public class Multi_Stream {
    /**
     * #
     * 题目是implement一个multistream class。 这个class有3个funciton:
     * 1. Add, 输入是一个integer list，把这个list里的数字加到这个class里, return void。
     * 2. Remove， 输入是一个integer list， 把这个list从class里移除, return void。
     * 3. Read, 输入是一个integer n, 这个function需要从之前加过的那些list里面读n个数字。
     *    如果没有足够的数字就有多少读多少，返回integer list。
     *
     * 栗子:
     * ms = new MultiStream();
     * ms.add(new ArrayList<Integer>(Arrays.asList(1,2,3,4)));
     * ms.add(new ArrayList<Integer>(Arrays.asList(5,6,7)));
     * ms.read(3) -> 返回 [1,2,3]
     * ms.remove(new ArrayList<Integer>(Arrays.asList(4)));
     * ms.read(2) -> 返回 [5,6]
     * ms.read(2) -> 返回 [7]
     *
     * 没啥太fancy的算法， brute force应该就是最优解，注意好index就行了
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
}

