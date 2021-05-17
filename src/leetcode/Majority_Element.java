package leetcode;

public class Majority_Element {
    /**
     * https://www.geeksforgeeks.org/majority-element/
     *
     * Write a function which takes an array and prints the majority element (if it exists),
     * otherwise prints “No Majority Element”. A majority element in an array A[] of size n is an element that appears more than n/2 times (and hence there is at most one such element).
     *
     * Examples :
     *
     * Input : {3, 3, 4, 2, 4, 4, 2, 4, 4}
     * Output : 4
     * Explanation: The frequency of 4 is 5 which is greater
     * than the half of the size of the array size.
     *
     * Input : {3, 3, 4, 2, 4, 4, 2, 4}
     * Output : No Majority Element
     * Explanation: There is no element whose frequency is
     * greater than the half of the size of the array size.
     */

    /**
     * Same algorithm as in LE_169_Majority_Element, here since we don't know
     * if there's majority element in given array, after get the element in
     * findCandidate(), do a second iteration to verify if it is majority
     * element.
     */
    void printMajority(int a[], int size) {
        /* Find the candidate for Majority*/
        int cand = findCandidate(a, size);

        /* Print the candidate if it is Majority*/
        if (isMajority(a, size, cand)) {
            System.out.println(" " + cand + " ");
        } else {
            System.out.println("No Majority Element");
        }
    }

    /* Function to find the candidate for Majority */
    int findCandidate(int a[], int size) {
        int maj_index = 0, count = 1;
        int i;
        for (i = 1; i < size; i++) {
            if (a[maj_index] == a[i]) {
                count++;
            } else {
                count--;
            }

            if (count == 0) {
                maj_index = i;
                count = 1;
            }
        }
        return a[maj_index];
    }

    /* Function to check if the candidate occurs more
       than n/2 times */
    boolean isMajority(int a[], int size, int cand) {
        int i, count = 0;
        for (i = 0; i < size; i++) {
            if (a[i] == cand) count++;
        }

        if (count > size / 2) {
            return true;
        } else {
            return false;
        }
    }

    /* Driver program to test the above functions */
    public static void main(String[] args) {
        Majority_Element majorelement = new Majority_Element();
        int a[] = new int[]{1, 3, 3, 1, 2};
        int size = a.length;
        majorelement.printMajority(a, size);
    }
}
