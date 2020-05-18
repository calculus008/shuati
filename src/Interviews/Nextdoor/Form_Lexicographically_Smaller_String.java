package Interviews.Nextdoor;

public class Form_Lexicographically_Smaller_String {
    /**
     * Given two strings S and T consisting of digits and lowercase letters,
     * you are allowed to remove only one digit from either string, count how
     * many ways of removal to make S lexicographically smaller than T.
     */
    public static int countWays(String s, String t) {
        int ways = 0;

        StringBuilder sbs = new StringBuilder(s);

        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(Character.isDigit(c)){
                sbs.deleteCharAt(i);
                String sub = sbs.toString();
                if(sub.compareTo(t) < 0) {
                    System.out.println(sub);
                    ways++;
                }
                sbs.insert(i, c);
            }
        }

        StringBuilder sbt = new StringBuilder(t);

        for(int i = 0; i < t.length(); i++){
            char c = s.charAt(i);
            if(Character.isDigit(t.charAt(i))){
                sbt.deleteCharAt(i);
                String sub = sbt.toString();
                if(s.compareTo(sub) < 0){
                    System.out.println(sub);
                    ways++;
                }
                sbt.insert(i, c);
            }
        }
        return ways;
    }

    public static void main(String[] args) {
        String s = "123ab";
        String t = "423cd";

        System.out.println(countWays(s, t));
    }
}

