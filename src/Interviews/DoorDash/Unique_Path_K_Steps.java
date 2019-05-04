package Interviews.DoorDash;

public class Unique_Path_K_Steps {
    /**
     * DoorDash
     *
     * 题目是unique path的升级版，给了起始位置和结束位置，8个方向都可以走，求问能在K步到达的path总量，刚开始写的dfs解，一开始还有点小bug；然后让写DP解，奈何这是个3维DP，不是很熟悉，只写了大概思路
     *
     * def count_paths(dims, start, target, K):
     *     """
     *     @param dims, a tuple (width, height) of the dimensions of the board
     *     @param start, a tuple (x, y) of the king's starting coordinate
     *     @param target, a tuple (x, y) of the king's destination
     *
     *
     *     [url=home.php?mod=space&uid=160137]@return[/url] the number of distinct paths there are for a king in chess (can move one square vertically, horizontally, or diagonally)
     *             to move from the start to target coordinates on the given board in K moves
     *
     *
     *
     *
     *
     *
     * if __name__ == "__main__":
     *     print "Running tests..."
     *     assert(count_paths((3, 3), (0, 0), (2, 2), 2) == 1)-baidu 1point3acres
     *     print "Passed test 1"
     *     assert(count_paths((3, 3), (0, 0), (2, 2), 3) == 6)
     *     print "Passed test 2"
     *     assert(count_paths((4, 4), (3, 2), (3, 2), 3) == 12)
     *     print "Passed test 3"
     *     assert(count_paths((4, 4), (3, 2), (1, 1), 4) == 84)
     *     print "Passed test 4"
     *     assert(count_paths((4, 6), (0, 2), (3, 4), 12) == 122529792)
     *     print "Passed test 5"
     */
}
