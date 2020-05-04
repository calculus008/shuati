package leetcode;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LE_1360_Number_Of_Days_Between_Two_Dates {
    /**
     * Write a program to count the number of days between two dates.
     *
     * The two dates are given as strings, their format is YYYY-MM-DD as shown in the examples.
     *
     * Example 1:
     * Input: date1 = "2019-06-29", date2 = "2019-06-30"
     * Output: 1
     *
     * Example 2:
     * Input: date1 = "2020-01-15", date2 = "2019-12-31"
     * Output: 15
     *
     * Constraints:
     *
     * The given dates are valid dates between the years 1971 and 2100.
     *
     * Easy
     */
    class Solution_Use_API {
        public int daysBetweenDates(String date1, String date2) {
            return Math.abs((int) ChronoUnit.DAYS.between(LocalDate.parse(date1), LocalDate.parse(date2)));
        }
    }

    class Solution_No_API_1 {
        int monthDays[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        public int daysBetweenDates(String date1, String date2) {
            int[] d1 = stringDateConvertor(date1);
            int[] d2 = stringDateConvertor(date2);

            return Math.abs(countDays(d1) - countDays(d2));
        }

        public int[] stringDateConvertor(String date) {
            int[] dateTrans = new int[3];

            String[] d = date.split("-");
            dateTrans[0] = Integer.valueOf(d[0]);
            dateTrans[1] = Integer.valueOf(d[1]);
            dateTrans[2] = Integer.valueOf(d[2]);

            return dateTrans;
        }

        public int countDays(int[] date){
            int days = date[0] * 365 + date[2];
            for (int i = 0; i < date[1] - 1; i++) {
                days += monthDays[i];
            }
            days += countLeapYear(date[0], date[1]);

            return days;
        }

        public int countLeapYear(int year, int month) {
            // If the current year not reach to Feb., then we do not need to consider it
            // for the count of leap years.
            if (month <= 2) {
                year--;
            }

            // A leap year is a multiple of 4, multiple of 400 BUT not a multiple of 100.
            return year / 4 - year / 100 + year / 400;
        }
    }

    class Solution_No_API_2_Since_1971{
        public int daysBetweenDates(String date1, String date2) {
            return Math.abs(countSince1971(date1) - countSince1971(date2));
        }

        public int countSince1971(String date) {
            int[] monthDays = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            String[] data = date.split("-");

            int year = Integer.parseInt(data[0]);
            int month = Integer.parseInt(data[1]);
            int day = Integer.parseInt(data[2]);

            for (int i = 1971; i < year; i++) {
                day += isALeapYear(i) ? 366 : 365;
            }

            for (int i = 1; i < month; i++) {
                if (isALeapYear(year) && i == 2) {
                    day += 1;
                }
                day += monthDays[i];
            }

            return day;
        }

        public boolean isALeapYear(int year) {
            return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
        }
    }

}
