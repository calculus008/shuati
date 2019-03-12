package Linkedin;

public class Lexicographically_Smallest_And_Largest_Substring_Of_Size_K {

    private static void getSmallestAndLargest(String s, int k) {
        // Initialize min and max as first substring of size k
        String currStr = s.substring(0, k);
        String lexMin = currStr;
        String lexMax = currStr;

        // Consider all remaining substrings. We consider
        // every substring ending with index i.
        for (int i = k; i < s.length(); i++) {
            currStr = currStr.substring(1, k) + s.charAt(i);
            if (lexMax.compareTo(currStr) < 0)
                lexMax = currStr;
            if (lexMin.compareTo(currStr) > 0)
                lexMin = currStr;
        }

        // Print result.
        System.out.println(lexMin);
        System.out.println(lexMax);
    }

    // Driver Code
    public static void main(String[] args)
    {
        String str = "GeeksForGeeks";
        int k = 3;
        getSmallestAndLargest(str, k);
    }
}
