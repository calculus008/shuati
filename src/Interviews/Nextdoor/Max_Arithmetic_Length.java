package Interviews.Nextdoor;

import java.util.HashSet;
import java.util.LinkedList;

public class Max_Arithmetic_Length {
    /**
     * Suppose we have array a and b (no duplicates & sorted) a = [0,4,8,20]
     * b = [5,7,12,16,22]
     *
     * Suppose u can pick any number of element from b (could be 0), and u want to insert them
     * into array a such that all elements in a are increasing by certain number,
     * so in this example u can pick "12, 16" from b and append into a such that a = [0,4,8,12,16,20],
     * which increase by 4 for each elem​​​​​​​​​​​​​​​​​​​ent write a function to return the maximum number of element
     * in a after appending elements from b (in the example above the result is 6), if there is no such
     * case, return -1
     */

    public static int max_arithmetic_length(int[] a, int[] b) {
        HashSet<Integer> set_b = new HashSet();
        for (int n : b) {
            set_b.add(n);
        }

        int min_interval = (a[0] == 0 ? Integer.MAX_VALUE : a[0]);
        for (int i = 1; i < a.length; i++) {
            min_interval = Math.min(min_interval, a[i] - a[i - 1]);
        }

        if (min_interval == 0) {
            return -1;
        }

        // we need to make sure that the modified array has interval that less or equal to min_interval
        int i = 1;
        int result = -1;
        //System.out.println(min_interval);

        while (i <= min_interval) {
            if (min_interval % i == 0) {
                int tmp = try_insert(a, i, set_b);
                result = Math.max(result, tmp);
            }
            i++;
        }

        result = (result == -1 ? -1 : result + a.length);
        return result;
    }

    public static int try_insert(int[] a, int i, HashSet<Integer> set) {
        int result = 0;
        int front = a[0];
        int tail = a[a.length - 1];

        while (true) {
            front -= i;
            if (set.contains(front)) {
                result++;
            } else {
                break;
            }
        }

        while (true) {
            tail += i;
            if (set.contains(tail)) {
                result++;
            } else {
                break;
            }
        }

        // try insert in the mid
        for (int j = 1; j < a.length; j++) {
            int low = a[j - 1];
            int high = a[j];
            int tmp = 0;

            while (low + i < high) {
                if (set.contains(low + i) == false) {
                    return -1;
                } else {
                    tmp++;
                }
                low += i;
            }
            result += tmp;
        }

        return result;
    }

    public static void main(String[] args) {
        int result = max_arithmetic_length(new int[]{4, 8, 20}, new int[]{0, 5, 7, 12, 20, 24, 28});
        System.out.println(result);
    }


    /**
     * =======================================
     */

    public static void addAfter(int[] b, int idxB, int diff, LinkedList<Integer> temp) {
        while (idxB < b.length) {
            if (b[idxB] == diff + temp.get(temp.size() - 1)) {
                temp.add(b[idxB]);
            }
            idxB++;
        }
    }

    public static void addFront(int[] b, int idxB, int diff, LinkedList<Integer> temp) {
        while (idxB >= 0) {
            if (b[idxB] == temp.get(0) - diff) {
                temp.addFirst(b[idxB]);
            }
            idxB--;
        }
    }

    public static int checkIdxA(int[] a, int idxA, int diff, LinkedList<Integer> temp) {
        while (idxA < a.length) {
            if (a[idxA] == diff + temp.get(temp.size() - 1)) {
                temp.add(a[idxA++]);
            } else {
                break;
            }
        }
        return idxA;
    }

    public static int findLong(int[] b, int val, int pos, int loc) {
        LinkedList<Integer> temp = new LinkedList<Integer>();
        temp.add(val);
        int diff = Math.abs(val - b[loc]);
        int res = 0;
        if (pos == -1) {
            addAfter(b, 0, diff, temp);
        } else if (pos == b.length - 1) {
            addFront(b, b.length - 1, diff, temp);
        } else {
            addAfter(b, pos, diff, temp);
            addFront(b, pos, diff, temp);
        }
        res = Math.max(res, temp.size());
        return res;
    }

    public static int maxArithmeticLength(int[] a, int[] b) {
        int lenA = a.length;
        int lenB = b.length;

        // find the place a[0] in b
        int left = 0, right = lenB - 1;
        int pos = -1;
        while (left <= right) {
            int mid = (right + left) / 2;
            if (b[mid] >= a[0]) {
                right = mid - 1;
            } else {
                pos = mid;
                left = mid + 1;
            }
        }

        // pos is the first b[pos] strictly less than a[0]
        int res = -1;
        if (a.length == 1) {
            // only have a[0] but not sure about the difference
            // the problem is equivalent to find the max Arithmetic length
            // contains A[0]
            for (int i = 0; i < b.length; i++) {
                res = Math.max(res, findLong(b, a[0], pos, i));
            }
        } else {
            // get the range of the difference
            int diffMax = a[1] - a[0];
            for (int i = 1; i < lenA; i++) {
                diffMax = Math.min(diffMax, a[i] - a[i - 1]);
            }
            for (int diff = 0; diff <= diffMax; diff++) {

                LinkedList<Integer> temp = new LinkedList<Integer>();
                temp.add(a[0]);

                if (pos == -1) {
                    // all elements in b is greater than A[0]
                    int idxA = 1, idxB = 0;
                    while (idxA < lenA && idxB < lenB) {
                        if (a[idxA] == diff + temp.get(temp.size() - 1)) {
                            temp.add(a[idxA++]);
                        } else if (b[idxB] == diff + temp.get(temp.size() - 1)) {
                            temp.add(b[idxB++]);
                        } else {
                            idxB++;
                        }
                    }
                    idxA = checkIdxA(a, idxA, diff, temp);
                    if (idxA == lenA) {
                        addAfter(b, idxB, diff, temp);
                    }
                } else if (pos == lenB - 1) {
                    // all elements in B is smaller than a[0]
                    int idxA = 1;
                    idxA = checkIdxA(a, idxA, diff, temp);
                    if (idxA == lenA) {
                        addFront(b, b.length - 1, diff, temp);
                    }
                } else {
                    // a[0] split [0, pos] and [pos + 1, lenB - 1]
                    int idxA = 1, idxB = pos + 1;
                    while (idxA < lenA && idxB < lenB) {
                        if (a[idxA] == diff + temp.get(temp.size() - 1)) {
                            temp.add(a[idxA++]);
                        } else if (b[idxB] == diff + temp.get(temp.size() - 1)) {
                            temp.add(b[idxB++]);
                        } else {
                            idxB++;
                        }
                    }
                    idxA = checkIdxA(a, idxA, diff, temp);
                    // add range [0, pos]
                    if (idxA == lenA) {
                        addFront(b, pos, diff, temp);
                        addAfter(b, idxB, diff, temp);
                    }
                }
                res = Math.max(res, temp.size());
            }
        }
        return res;
    }
}
