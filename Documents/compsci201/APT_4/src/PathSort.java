public class PathSort {
	public static void main(String[] args){
		String[] dire = {"/","/usr/","/usr/local/","/usr/local/bin/","/games/","/games/tetris/","/hw/","/temp/downloads/"};
		PathSort ps = new PathSort();
		ps.sortPath(dire);
		for(int i = 0; i < dire.length; i++){
			System.out.println(dire[i]);
		}
	}
	
	public String[] sortPath(String[] dire) {
		SString[] array = new SString[dire.length];
		for (int i = 0; i < array.length; i++) {
			array[i] = new SString();// create a new string and put it into our
										// string
			array[i].string = dire[i];// then put string into our new array
		}
		insertionSort(array); // now we will sort it using insertion sort

		// this loop places our values that have been sorted in our comparable
		// function back into our original array
		for (int i = 0; i < array.length; i++) {
			dire[i] = array[i].string;
		}
		
		return dire;
	}

	public static class SString implements Comparable { // we have to make a new
														// class with new Array
														// string because theres
														// no way to override
														// the comparable
														// interface to make it
														// the way we want to

		String string;

		@Override
		public int compareTo(Object o) {
			SString object = (SString) o;
			String original[] = this.string.split("/");
			String second[] = object.string.split("/");
			if(original.length > second.length){
				return 1;
			}
			if(original.length < second.length){
				return -1;
			}
			for(int i = 0; i < original.length; i++){
				if(!original[i].equals(second[i])){
					return original[i].compareTo(second[i]);
				}
			}
			return 0;
		}

	}
	
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	
	private static void exch(Comparable[] a, int i, int j) {
		Comparable swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	
	public static void insertionSort(Comparable[] a) {
		
		
		int N = a.length;
		for (int i = 0; i < N; i++) {
			for (int j = i; j > 0; j--) {
				if (less(a[j], a[j - 1])) {
					exch(a, j, j - 1);
				} else {
					break;
				}
			}
		}
	}
}
