package Linkedin;

public class Cut_Pizza {
    /**
     * 一个pizza 切n刀 问最多能切几块 注意不一定要所有切线都在同一个交点
     * 在切第n刀的时候 最多能和之前的n-1刀相交 这个多出来的1刀多制造出了n块pizza
     */
    int max(int N) {
        int[] hash = new int[N + 1];

        hash[0] = 1;
        for (int i = 1; i < N + 1; i++) {
            hash[i] = hash[i - 1] + i;
        }

        return hash[N];
    }
}
