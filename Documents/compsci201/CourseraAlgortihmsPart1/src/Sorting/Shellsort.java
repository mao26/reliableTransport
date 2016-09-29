package Sorting;

public class Shellsort {
	public static void sort(Comparable[] a){
		int N = a.length;
		int h = 1;
		while(h < N/3) h = 3*h +1; // the 3x+1 increment sequence ==> 1, 4, 13, 40, 121, 364
		while(h >= 1){
			//h sort the array and it uses inertion sort inside of it
			for(int i = h; i < N; i++){
				for(int j = i; j >= h && less(a[j], a[j-h]); j += h){ //here is my insertion sort
					exch(a, j, j - h);
				}
			}
			h = h/3; //move to the next increment
		}
	}

	public static boolean less(Comparable v, Comparable w){
		if(v.compareTo(w) > 0) return false;
		else if(v.compareTo(w) < 0) return true;
		return false;
	}
	
	public static void exch(Comparable[]a, int i, int j){
		
	}
	
}
