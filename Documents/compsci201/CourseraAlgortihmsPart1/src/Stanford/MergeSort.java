package Stanford;

/*
 * Divide and conquer --> divide problem into smaller subproblems that are solved recursively
 * Improves over selection, insertion, bubble sort --> old known algorithm --> motivates asymptotic analysis
 */

public class MergeSort {
	/*
	 * Given as input, an array of n numbers, unsorted and produce an output with the same numbers, sorted in order
	 * RECURSIVE ALGORITHM / divide and conquer where we divide the array by half and sort each half, then we combine
	 */
	
	/*
	 * C = output array[length = n]
	 * A = left sorted array [n/2]
	 * B = right sorted array [n/2]
	 * int i traverses through A and int j traverses though B and both started at 1
	 * the minimum element is always goign to be at the beginning of either subarray due to us sorting the subrray before they are merged
	 * we pick smallest number between the two beginning elements
	 * for k = 1 to n 
	 *   if A[i] < B[j]
	 *      C[k] = A[i]
	 *      i++
	 *   else --> B[j] < A[i]
	 *      C[k] = B[j]
	 *      j++
	 * end
	 * need to keep track of when we reach the end of either subarray--> at which point we just add the remaining elements of the other array to our output array
	 * 
	 */
	
	/*
	 * Merge sort running time? approximately equal to the number of lines of code executed
	 * number of comparisons is 1 after our conditional statements/ two initializations (int i, j) and one for loop
	 * running time of merge on array of m numbers is <= 4m + 2 --> equal to 6m (since m >= 1)
	 * we have a number of log n levels 
	 * so merge sort requires <= 6n log n + 6n operations to sort n numbers
	 * 
	 */
	 private int[] array;
	    private int[] tempMergArr;
	    private int length;
	 
	    public static void main(String a[]){
	         
	        int[] inputArr = {45,23,11,89,77,98,4,28,65,43};
	        MergeSort mms = new MergeSort();
	        mms.sort(inputArr);
	        for(int i:inputArr){
	            System.out.print(i);
	            System.out.print(" ");
	        }
	    }
	     
	    public void sort(int inputArr[]) {
	        this.array = inputArr;
	        this.length = inputArr.length;
	        this.tempMergArr = new int[length];
	        doMergeSort(0, length - 1);
	    }
	 
	    private void doMergeSort(int lowerIndex, int higherIndex) {
	         
	        if (lowerIndex < higherIndex) {
	            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
	            // Below step sorts the left side of the array
	            doMergeSort(lowerIndex, middle);
	            // Below step sorts the right side of the array
	            doMergeSort(middle + 1, higherIndex);
	            // Now merge both sides
	            mergeParts(lowerIndex, middle, higherIndex);
	        }
	    }
	 
	    private void mergeParts(int lowerIndex, int middle, int higherIndex) {
	 
	        for (int i = lowerIndex; i <= higherIndex; i++) {
	            tempMergArr[i] = array[i];
	        }
	        int i = lowerIndex;
	        int j = middle + 1;
	        int k = lowerIndex;
	        while (i <= middle && j <= higherIndex) {
	            if (tempMergArr[i] <= tempMergArr[j]) {
	                array[k] = tempMergArr[i];
	                i++;
	            } else {
	                array[k] = tempMergArr[j];
	                j++;
	            }
	            k++;
	        }
	        while (i <= middle) {
	            array[k] = tempMergArr[i];
	            k++;
	            i++;
	        }
	 
	    }
	// http://www.java2novice.com/java-sorting-algorithms/merge-sort/#sthash.nJNH6XdF.dpuf
}
