package Interviews.Linkedin;

public class Max_Subsequence_Skip_One {
    /**
     * max subsequence skip one（也就是说，给一个int array，求max sum的subsequence，
     * 你可以跳过一些element，但是不能连续跳两个。举个栗子，[1，-1，-2，3，4] -> 7）
     */
    int maxSkip1(int[] arr) {
        int[] dp1 = new int[arr.length]; // Yes, take this one
        int[] dp2 = new int[arr.length]; // No, don't take this one

        dp1[0] = arr[0];
        dp2[0] = 0;

        for (int i = 1; i < arr.length; i++) {
            dp1[i] = arr[i] + Math.max(dp1[i - 1], dp2[i - 1]);
            dp2[i] = dp1[i - 1]; // 必须取前边的一个
        }

        return Math.max(dp1[arr.length - 1], dp2[arr.length - 1]);
    }

    public static void main(String[] args) {
        Max_Subsequence_Skip_One mss = new Max_Subsequence_Skip_One();
//        int[] arr = {1, 3, -2, -3, 4};
        int[] arr = {1, -1, -2, 3, 4};


        int res = mss.maxSkip1(arr);
        System.out.println(res);
    }
}
