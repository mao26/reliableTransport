import java.util.Arrays;

public class ElementarySorts {

	static int insertionComparisons;
	static int insertionExchanges;
	static int selectionComparisons;
	static int selectionExchanges;

	public static void insertionSort(Comparable[] a) {
		insertionComparisons = 0;
		insertionExchanges = 0;
		
		int N = a.length;
		for (int i = 0; i < N; i++) {
			for (int j = i; j > 0; j--) {
				insertionComparisons++;
				if (less(a[j], a[j - 1])) {
					exch(a, j, j - 1);
					insertionExchanges++;
				} else {
					break;
				}
			}
		}
	}

	public static void selectionSort(Comparable[] a) {
		selectionComparisons = 0;
		selectionExchanges = 0;
		
		int N = a.length;
		for (int i = 0; i < N; i++) {
			int min = i;
			for (int j = i + 1; j < N; j++) {
				selectionComparisons++;
				if (less(a[j], a[min])) {
					min = j;
				}
			}
			exch(a, i, min); //if a perfect collection is used thats in ascending order this 
			// code will be triggered every time regardless of if the elements are in the right order
			// and it will exchange an index with that same index. 
			selectionExchanges++;
		}
	}

	/**
	 * Compare elems
	 */
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	/**
	 * Exchange/swap elems
	 */
	private static void exch(Comparable[] a, int i, int j) {
		Comparable swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	/**
	 * Print an array
	 */
	private static void print(Object[] arr) {
		System.out.println(Arrays.asList(arr));
	}

	public static void main(String[] args) {
		// Be careful: the sorting methods above mutate the input arrays
		Integer[] unordered = new Integer[] { 7, 10, 14, 20, 13, 19, 6, 15, 4,
				11, 12, 16, 5, 17, 1, 18, 8, 3, 2, 9 };
		Integer[] sorted = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Integer[] uniform = new Integer[] { 1, 1, 1, 1, 1 };

		print(unordered);

		System.out.println("\n Question 1: ");
		insertionSort(unordered);
		selectionSort(unordered);
		System.out.println(insertionComparisons);
		System.out.println(insertionExchanges);
		System.out.println(selectionComparisons);
		System.out.println(selectionExchanges);
		
		System.out.println("\n Question 2: ");
		insertionSort(sorted);
		selectionSort(sorted);
		
		System.out.println(insertionExchanges);
		
		System.out.println(selectionExchanges);
		
		System.out.println("\n Question 3: ");
		insertionSort(uniform);
		selectionSort(uniform);
		
		System.out.println(insertionExchanges);
		
		System.out.println(selectionExchanges);
	}
}
