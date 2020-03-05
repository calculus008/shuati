import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;

/** =============================================================================
Question Description
=============================================================================
You are given a list of jobs, each job has an ID number(type is long).
Implement two functions,
1.expire(long jobid) to set a job as "expired"
2.isexpired(long jobid) to check if a job is "expired"
/* =============================================================================
code
=============================================================================
//好像写的不太好，如果expire，就可以丢了，不需要继续存在map里面
//所以可以改成map remove expire jobid，但这样就不用map了，用个set就行。
//这也太容易了。
class Job_Storage{
    Map<Long, Boolean> record = new HashMap<>();
    public Job_id_Storage(List<Long> jobids){
        for (Long id: jobids) {
            record.put(id, true);
        }
    }
    public void expire(long jobid){
        if (record.containsKey(jobid)){
            record.put(jobid, false);
        }
    }
    public boolean isexpired(long jobid){
        return record.get(jobid);
    }
}
/* =============================================================================
Follow Up
=============================================================================
全放进map里面空间就不够了，面试中不让用map。
long是64个bit。
64bit的操作系统里面，16GB的内存如何存下4 Billion个jobid。
还有用16MB怎么存下一大堆jobid。
（意思是怎么存比较节约内存）。
expire的job id比较多，可以考虑如何压缩去存expire job id。(bloom filter)

可能用bitSet，还有就是讨论job id的范围。还有trie可以用。
job id
（这里用4位表示long，前面的0省略）
0001
0010
0100
1000

开一个四个长度的bit数组（好像没有bit数组），这样的话如果4个job id都expire，只需要保存1111即可
检查某个job id是否expire，只需要检查某一位上是不是1就可以。
这样的话，存储空间从16位降到了4位。
这里面的hash function就是 id & 1111
/* =============================================================================
Follow Up code
=============================================================================

=============================================================================
题目内容：
=============================================================================
给一群jobid，类型是long，实现两个方法
1.expire(long jobid)
2.isexpired(long jobid)

那这题入门版就很容易了，用一个map，key是id，value是个boolean表示是否expired。
难度还是在follow up。
1个GB是大约10^9 byte，（2^30 bytes），1个Billion是10^9。
1个byte是8个bit。

内存消耗：memory consumption
=============================================================================
地里面经总结
=============================================================================
<A> 印度姐加上国人小哥shadow， 跟我聊了聊简历， 然后题是这样的，
    给你一个
    interface{
        void expire(long jobid)
        boolean isexpired(long jobid)
    }
    然你实现一下就行了。然后如果有很多JobId， 要想办法节省空间之类的。
<B> jobid storage, 就是给你jobid type是long，然后在64 bit的操作系统里，16gb内存 如何能存下4 Billion个jobid。
    然后实现expire 和isExpire的操作，这个其实比较次要的，更多的是比较open的讨论。
<C> 第二问 让实现方法 isExpire ,和expire 但是其实这不是重点，hashmap，大家都能实现，关键是如何用最节省内存的方法存下来，
<D> 给一堆jobid, implement两个function:
    void expire(long id),
    bool isExpired(long id)。
    如果job id 超多，如何用16MB的memory纪录expired 的id。（之前的人说是GB，这听力还出毛病了）

可能需要用到bloom filter的思想了。
http://www.cnblogs.com/heaad/archive/2011/01/02/1924195.html
下面这个博客里面的一些思想可以借鉴。
http://www.drfish.me/大数据/2015/12/07/大数据常用技巧之位图法/


 刚开始⽤用map
 然后map肯定不不给⽤用的
 ⽤用bitmap 如果说单机存不不下
 问问值域看能不不能压缩下， 再之后看能不不能⽤用多机存
 接着就说bloom filter
 bloom filter的主要作⽤用是把bit⽐比较多的hash后存到bit⽐比较⼩小的内存空间⾥里里 ⽐比如64位的存到16位⾥里里

 16G         2 ^ 4 * 10 ^ 9 bytes
 4 billion : 2 ^ 2 * 10 ^ 9

 If one bit represent a number
 4 * 10 ^ 9 / 8 * 1024 * 2014 = 4 * 10 ^ 9 / 8 * 10 ^ 6 = 0.5 * 10 ^ 3 = 5 * 10 ^ 2 = 500 MB

 https://blog.csdn.net/woshilijiuyi/article/details/88778214?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task
**/

/**
public class BitMap {
    int ARRNUM = 800;
    int LEN_INT = 32;
    int mmax = 9999;
    int mmin = 1000;
    int N = mmax - mmin + 1;

    public static void main(String args[]) {
        new BitMap().findDuplicate();
        new BitMap().findDup_jdk();
    }

    public void findDup_jdk() {
        System.out.println("*******调用JDK中的库方法--开始********");
        BitSet bitArray = new BitSet(N);
        int[] array = getArray(ARRNUM);
        for (int i = 0; i < ARRNUM; i++) {
            bitArray.set(array[i] - mmin);
        }
        int count = 0;
        for (int j = 0; j < bitArray.length(); j++) {
            if (bitArray.get(j)) {
                System.out.print(j + mmin + " ");
                count++;
            }
        }
        System.out.println();
        System.out.println("排序后的数组大小为：" + count );
        System.out.println("*******调用JDK中的库方法--结束********");
    }

    public void findDuplicate() {
        int[] array = getArray(ARRNUM);
        int[] bitArray = setBit(array);
        printBitArray(bitArray);
    }

    public void printBitArray(int[] bitArray) {
        int count = 0;
        for (int i = 0; i < N; i++) {
            if (getBit(bitArray, i) != 0) {
                count++;
                System.out.print(i + mmin + "\t");
            }
        }
        System.out.println();
        System.out.println("去重排序后的数组大小为：" + count);
    }

    public int getBit(int[] bitArray, int k) {// 1右移 k % 32位 与上 数组下标为 k/32 位置的值
        return bitArray[k / LEN_INT] & (1 << (k % LEN_INT));
    }

    public int[] setBit(int[] array) {// 首先取得数组位置下标 i/32, 然后 或上
        // 在该位置int类型数值的bit位：i % 32
        int m = array.length;
        int bit_arr_len = N / LEN_INT + 1;
        int[] bitArray = new int[bit_arr_len];
        for (int i = 0; i < m; i++) {
            int num = array[i] - mmin;
            bitArray[num / LEN_INT] |= (1 << (num % LEN_INT));
        }
        return bitArray;
    }

    public int[] getArray(int ARRNUM) {
        @SuppressWarnings("unused")
        int array1[] = { 1000, 1002, 1032, 1033, 6543, 9999, 1033, 1000 };

        int array[] = new int[ARRNUM];
        System.out.println("数组大小：" + ARRNUM);
        Random r = new Random();
        for (int i = 0; i < ARRNUM; i++) {
            array[i] = r.nextInt(N) + mmin;
        }

        System.out.println(Arrays.toString(array));
        return array;
    }
}
 **/


/**
public class BigMapTest {

    private int[] bigArray;

    public BigMapTest(long  size){
        bigArray = new int[(int) (size/ 32 + 1)];
    }

    public void set1(int  num){
        //确定数组 index
        int arrayIndex = num >> 5;
        //确定bit index
        int bitIndex = num & 31;
        //设置0
        bigArray[arrayIndex] |= 1 << bitIndex;
    }

    public void set0(int  num){
        //确定数组 index
        int arrayIndex = num >> 5;
        //确定bit index
        int bitIndex = num & 31;
        //设置0
        bigArray[arrayIndex] &= ~(1 << bitIndex);
        System.out.println(get32BitBinString(bigArray[arrayIndex]));
    }

    public boolean isExist(int  num){
        //确定数组 index
        int arrayIndex = num >> 5;
        //确定bit index
        int bitIndex = num & 31;

        //判断是否存在
        return (bigArray[arrayIndex] & ((1 << bitIndex)))!=0 ? true : false;
    }


//     将整型数字转换为二进制字符串，一共32位，不舍弃前面的0
//     @param number 整型数字
//     @return 二进制字符串

    private static String get32BitBinString(int number) {
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < 32; i++){
            sBuilder.append(number & 1);
            number = number >>> 1;
        }
        return sBuilder.reverse().toString();
    }

    public static void main(String[] args) {

        int[] arrays = new int[]{1, 2, 35, 22, 56, 334, 245, 2234, 54};

        BigMapTest bigMapTest = new BigMapTest(2234-1);

        for (int i : arrays) {
            bigMapTest.set1(i);
        }
        System.out.println(bigMapTest.isExist(35));
    }
}
**/