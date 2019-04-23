package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuank on 4/30/18.
 */
public class LE_311_Sparse_Matrix_Multiplication {
    /**
         Given two sparse matrices A and B, return the result of AB.

         You may assume that A's column number is equal to B's row number.

         Example:

         A = [
         [ 1, 0, 0],
         [-1, 0, 3]
         ]

         B = [
         [ 7, 0, 0 ],
         [ 0, 0, 0 ],
         [ 0, 0, 1 ]
         ]


              |  1 0 0 |   | 7 0 0 |   |  7 0 0 |
         AB = | -1 0 3 | x | 0 0 0 | = | -7 0 3 |
                           | 0 0 1 |

         Medium
     */

    /**
         时间复杂度分析：

         假设矩阵A，B均为 n x n 的矩阵，
         矩阵A的稀疏系数为a，矩阵B的稀疏系数为b，
         a，b∈[0, 1]，矩阵越稀疏，系数越小。

         方法一：暴力，不考虑稀疏性
         Time (n^2 * (1 + n)) = O(n^2 + n^3)
         Space O(1)

         方法二：改进，仅考虑A的稀疏性
         Time O(n^2 * (1 + a * n) = O(n^2 + a * n^3)
         Space O(1)

         方法三：进一步改进，考虑A与B的稀疏性
         Time O(n^2 * (1 + a * b * n)) = O(n^2 + a * b * n^3) 最优的时间复杂度
         Space O(b * n^2)

         方法四：另外一种思路，将矩阵A, B非0元素的坐标抽出，对非0元素进行运算和结果累加
         Time O(2 * n^2 + a * n^2 * b * n^2) = O(n^2 + a * b * n^4)
         Space O(a * n^2 + b * n^2)

         解读：矩阵乘法的两种形式，假设 A(n, t) * B(t, m) = C(n, m)

            形式一：外层两个循环遍历C (常规解法)

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    for (int k = 0; k < t; k++) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }

        // 或者写成下面这样子
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    int sum = 0;
                    for (int k = 0; k < t; k++) {
                        sum += A[i][k] * B[k][j];
                    }
                    C[i][j] = sum;
                }
            }


            形式二：外层两个循环遍历A

            for (int i = 0; i < n; i++) {
                for (int k = 0; k < t; k++) {
                    for (int j = 0; j < m; j++) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }

         两种方法的区别

         代码上的区别（表象）：
         调换了第二三层循环的顺序

         核心区别（内在）：
         形式一以C为核心进行遍历，每个C[i][j]只会被计算一次，就是最终答案。
         形式二以A为核心进行遍历，每个A[i][k] 乘上 B[k][j]之后，会被累加到 C[i][j]，每个C[i][j]将被累加t次。

         举个例子，若A矩阵2x3，B矩阵3x2，C矩阵2x2
         A                 B              C
         a00 , a01 , a02      b00 , b01      c00 , c01
         a10 , a11 , a12      b10 , b11      c10 , c11
         b20 , b21

         形式一的计算过程：遍历C，假设遍历到c00，计算c00 = a00 * b00 + a01 * b10 + a02 * b20
         形式二的计算过程：遍历A，
         假设遍历到a00，a00 * b00 累加到 c00， a00 * b01 累加到c01；
         假设遍历到a01，a01 * b10 累加到 c00， a01 * b11 累加到c01；

         再回到本题目，可以发现是否为稀疏矩阵，对于上述形式一来说，并无法进行优化，因为是以C为核心
         但是对于形式二来说，以A为核心，若A[i][k]为0，那么该元素就不必进行对应相乘并累加的操作了。
         故方法二，就是基于此进行优化的。
     **/

    // 方法一
    public class Solution1 {
        /**
         * @param A: a sparse matrix
         * @param B: a sparse matrix
         * @return: the result of A * B
         */
        public int[][] multiply(int[][] A, int[][] B) {
            // write your code here
            // A(n, t) * B(t, m) = C(n, m)
            int n = A.length;
            int t = A[0].length;
            int m = B[0].length;
            int[][] C = new int[n][m];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    int sum = 0;
                    for (int k = 0; k < t; k++) {
                        sum += A[i][k] * B[k][j];
                    }
                    C[i][j] = sum;
                }
            }

            return C;
        }
    }

    // 方法二
    public class Solution2 {
        /**
         * @param A: a sparse matrix
         * @param B: a sparse matrix
         * @return: the result of A * B
         */
        public int[][] multiply(int[][] A, int[][] B) {
            // write your code here
            // A(n, t) * B(t, m) = C(n, m)
            int n = A.length;
            int t = A[0].length;
            int m = B[0].length;
            int[][] C = new int[n][m];

            for (int i = 0; i < n; i++) {
                for (int k = 0; k < t; k++) {
                    if (A[i][k] == 0) {
                        continue;
                    }
                    for (int j = 0; j < m; j++) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }

            return C;
        }
    }

    // 方法三
    public class Solution3 {
        /**
         * @param A: a sparse matrix
         * @param B: a sparse matrix
         * @return: the result of A * B
         */
        public int[][] multiply(int[][] A, int[][] B) {
            // write your code here
            // A(n, t) * B(t, m) = C(n, m)
            int n = A.length;
            int t = A[0].length;
            int m = B[0].length;
            int[][] C = new int[n][m];

            List<List<Integer>> B_nonZero_colIndices = new ArrayList<>();
            for (int k = 0; k < t; k++) {
                List<Integer> colIndices = new ArrayList<>();
                for (int j = 0; j < m; j++) {
                    if (B[k][j] != 0) {
                        colIndices.add(j);
                    }
                }
                B_nonZero_colIndices.add(colIndices);
            }

            for (int i = 0; i < n; i++) {
                for (int k = 0; k < t; k++) {
                    if (A[i][k] == 0) {
                        continue;
                    }
                    for (int colIndex : B_nonZero_colIndices.get(k)) {
                        C[i][colIndex] += A[i][k] * B[k][colIndex];
                    }
                }
            }

            return C;
        }
    }

    // 方法四 : Best Solution
    public class Solution4 {
        public int[][] multiply(int[][] A, int[][] B) {
            // A(n, t) * B(t, m) = C(n, m)
            int n = A.length;
            int t = A[0].length;
            int m = B[0].length;
            int[][] C = new int[n][m];

            List<Point> A_Points = getNonZeroPoints(A);
            List<Point> B_Points = getNonZeroPoints(B);

            for (Point pA : A_Points) {
                for (Point pB : B_Points) {
                    //!!!
                    if (pA.j == pB.i) {
                        C[pA.i][pB.j] += A[pA.i][pA.j] * B[pB.i][pB.j];
                    }
                }
            }

            return C;
        }


        private List<Point> getNonZeroPoints(int[][] matrix) {
            List<Point> nonZeroPoints = new ArrayList<>();
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (matrix[i][j] != 0) {
                        nonZeroPoints.add(new Point(i, j));
                    }
                }
            }
            return nonZeroPoints;
        }

        class Point {
            int i, j;
            Point(int i, int j) {
                this.i = i;
                this.j = j;
            }
        }
    }

    /**
     * My version, similar to Solution4,
     */
    public class Solution5 {
        class Element{
            int x, val;

            public Element(int x, int val) {
                this.x = x;
                this.val = val;
            }
        }

        public int[][] multiply(int[][] A, int[][] B) {
            int res[][] = new int[A.length][B[0].length];

            List<List<Element>> vectorA = getVector(A, true);
            List<List<Element>> vectorB = getVector(B, false);

            for (int i = 0; i < vectorA.size(); i++) {
                for (int j = 0; j < vectorB.size(); j++) {
                    List<Element> l1 = vectorA.get(i);
                    List<Element> l2 = vectorB.get(j);

                    int size1 = l1.size();
                    int size2 = l2.size();

                    if (size1 == 0 || size2 == 0) continue;

                    int idx1 = 0, idx2 = 0;
                    while (idx1 < size1 && idx2 < size2) {
                        Element e1 = l1.get(idx1);
                        Element e2 = l2.get(idx2);

                        if (e1.x < e2.x) {
                            idx1++;
                        } else if (e1.x > e2.x) {
                            idx2++;
                        } else {
                            res[i][j] += e1.val * e2.val;
                            idx1++;
                            idx2++;
                        }
                    }
                }
            }

            return res;
        }

        private List<List<Element>> getVector(int[][] A, boolean isRow) {
            int n = isRow ? A.length : A[0].length;
            int m = isRow ? A[0].length : A.length;

            List<List<Element>> res = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                List<Element> l = new ArrayList<>();
                for (int j = 0; j < m; j++) {
                    int val = isRow ? A[i][j] : A[j][i];
                    if(val == 0) continue;
                    l.add(new Element(j, val));
                }
                res.add(l);
            }

            return res;
        }
    }

    public class Solution6 {
        public int[][] multiply(int[][] A, int[][] B) {
            int m = A.length, n = A[0].length, nB = B[0].length;
            int[][] C = new int[m][nB];

            for(int i = 0; i < m; i++) {
                for(int k = 0; k < n; k++) {
                    if (A[i][k] != 0){
                        for (int j = 0; j < nB; j++) {
                            if (B[k][j] != 0) {
                                C[i][j] += A[i][k] * B[k][j];
                            }
                        }
                    }
                }
            }
            return C;
        }
    }
}
