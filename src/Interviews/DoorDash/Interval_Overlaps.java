package Interviews.DoorDash;

public class Interval_Overlaps {
    /**
     * DoorDash
     *
     * Version 1:
     * you are given two sets of intervals, one for Doordash, the other for restaurant,
     * you want to find if they all open intervals
     *
     *
     * // example：
     * // D = [(9, 12), (14, 17), (21, 23)]
     * // R = [(8, 10), (11, 22)]
     *
     * // = > [(9, 10), (11, 12), (14, 17), (21, 22)]
     *
     * 允许自己定义interval class
     *
     * Version 2 :
     * You’re given two sets of intervals, one for DoorDash and one for a restaurant,
     * and you want to find when they are both open. These are hours in 24-hour times,
     * but can be any arbitrary floating point numbers.
     *
     * example:
     * D = [(9, 12), (14, 17), (21, 23)]
     * R = [(8, 10), (11, 22)]
     * => [(9, 10), (11, 12), (14, 17), (21, 22)]
     *
     * 说白了就是个merge sort的merge步骤。但要注意的是每次merge之后，较长的那个interval剩余的部分不能丢弃，
     * 到下一轮可能会从新用上。
     */

    /**
     * LE_986_Interval_List_Intersections
     */
}
