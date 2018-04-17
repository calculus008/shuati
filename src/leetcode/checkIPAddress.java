package leetcode;

/**
 * Created by yuank on 8/15/17.
 */
public class checkIPAddress {
    public static String  validIPAddress(String IP) {
        if (isValidIP4Address(IP)) return "IPv4";
        if (isValidIP6Address(IP)) return "IPv6";
        return "Neither";
    }

    private static boolean  isValidIP4Address(String IP) {
        //!!! validate length first before using charAt()

        System.out.println(IP.length());
        if (IP.length() == 0 || IP.length() < 8) return false;

        if (IP.charAt(0) == '.') return false;
        if (IP.charAt(IP.length() - 1) == '.') return false;
        String[] tokens = IP.split("\\.");
        System.out.println(tokens.length);
        if (tokens.length != 4) return false;

        for (String s : tokens) {
            System.out.println(s);
            if (!isValidIP4Token(s)) return false;
        }

        return true;
    }

    private  static boolean isValidIP4Token(String token) {
        //!!! check length first, otherwise, if length is 0, then charAt(0) will prompt outofbound exception
        if (token.length() > 3 || token.length() == 0) return false;
        if (token.charAt(0) == '0' && token.length() > 1) return false;

        char[] ch = token.toCharArray();
        for (char c : ch) {
            if (!Character.isDigit(c)) return false;
        }

        int n = Integer.parseInt(token);
        System.out.println(n);
        if (n > 255) return false;

        return true;
    }

    private static boolean isValidIP6Address(String IP) {
        if (IP.length() == 0 || IP.length() < 16) return false;
        if (IP.charAt(0) == ':') return false;
        if (IP.charAt(IP.length() - 1) == ':') return false;
        String[] tokens = IP.split(":");
        if (tokens.length != 8) return false;

        for (String s : tokens) {
            if (!isValidIP6Token(s)) return false;
        }

        return true;
    }

    private static boolean isValidIP6Token(String token) {
        if (token.length() > 4 || token.length() == 0) return false;

        char[] ch = token.toCharArray();
        for (char c : ch) {
            boolean isDigit = Character.isDigit(c);
            boolean isLetter = Character.isLetter(c);

            if (!(isDigit || isLetter)) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        String s = "1.1.1.1";
        String res = validIPAddress(s);
        System.out.println("result:" +res);
    }
}
