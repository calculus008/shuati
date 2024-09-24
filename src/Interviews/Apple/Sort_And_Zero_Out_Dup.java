package Interviews.Apple;

public class Sort_And_Zero_Out_Dup {
    /**
     * givin a number of array, sort and zero out all duplicated number, cannot use any sorting library
     * example: [1,5,2,1,6,2,1] -> [0,0,0,1,2,5,6]
     */

    public void sortAndRemoveDuplicates(int[] arr) {
        int n = arr.length;

        // Bubble sort implementation
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        // Zero out duplicates
        for (int i = 1; i < n; i++) {
            if (arr[i] == arr[i - 1]) {
                arr[i - 1] = 0;
            }
        }

        // In-place move of zeros to the front
        int zeroIndex = n - 1;

        // Traverse the array from the back and move non-zero elements towards the end
        for (int i = n - 1; i >= 0; i--) {
            if (arr[i] != 0) {
                arr[zeroIndex--] = arr[i];
            }
        }

        // Fill the beginning of the array with zeros
        while (zeroIndex >= 0) {
            arr[zeroIndex--] = 0;
        }
    }
}
