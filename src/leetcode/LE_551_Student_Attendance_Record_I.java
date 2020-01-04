package leetcode;

public class LE_551_Student_Attendance_Record_I {
    /**
     * You are given a string representing an attendance record for a student.
     * The record only contains the following three characters:
     * 'A' : Absent.
     * 'L' : Late.
     * 'P' : Present.
     * A student could be rewarded if his attendance record doesn't contain more
     * than one 'A' (absent) or more than two continuous 'L' (late).
     *
     * You need to return whether the student could be rewarded according to his
     * attendance record.
     *
     * Example 1:
     * Input: "PPALLP"
     * Output: True
     * Example 2:
     * Input: "PPALLL"
     * Output: False
     *
     * Easy
     */
    class Solution1 {
        public boolean checkRecord(String s) {
            int idx = s.indexOf("LLL");
            if (idx >= 0) return false;

            int n1 = s.length();
            s = s.replace("A", "");
            int n2 = s.length();

            if (n1 - n2 >= 2) return false;

            return true;
        }
    }

    class Solution2 {
        public boolean checkRecord(String s) {
            if (s.indexOf("A") != s.lastIndexOf("A") || s.contains("LLL")) return false;
            return true;
        }
    }

    class Solution3 {
        public boolean checkRecord(String s) {
            return !s.matches(".*LLL.*|.*A.*A.*");
        }
    }
}
