package leetcode;

public class LE_612_Shortest_Distance_In_A_Plane {
    /**
     * SELECT
     *     ROUND(SQRT(MIN((POW(p1.x - p2.x, 2) + POW(p1.y - p2.y, 2)))), 2) AS shortest
     * FROM
     *     point_2d p1
     *         JOIN
     *     point_2d p2 ON p1.x != p2.x OR p1.y != p2.y
     * ;
     */
}
