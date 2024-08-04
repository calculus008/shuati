package leetcode;

public class LE_1108_Defanging_An_IP_Address {
    /**
     * Given a valid (IPv4) IP address, return a defanged version of that IP address.
     *
     * A defanged IP address replaces every period "." with "[.]".
     *
     * Example 1:
     * Input: address = "1.1.1.1"
     * Output: "1[.]1[.]1[.]1"
     *
     * Example 2:
     * Input: address = "255.100.50.0"
     * Output: "255[.]100[.]50[.]0"
     *
     * Constraints:
     * The given address is a valid IPv4 address.
     *
     * Easy
     *
     * https://leetcode.com/problems/defanging-an-ip-address/
     */

    public String defangIPaddr1(String address) {
        return address.replace(".", "[.]");
    }

    public String defangIPaddr2(String address) {
        return String.join("[.]", address.split("\\."));
    }

    public String defangIPaddr3(String address) {
        return address.replaceAll("\\.", "[.]");
    }

    public String defangIPaddr4(String address) {
        StringBuilder sb = new StringBuilder();
        for (char c : address.toCharArray()) {
            sb.append(c == '.' ? "[.]" : c);
        }
        return sb.toString();
    }
}
