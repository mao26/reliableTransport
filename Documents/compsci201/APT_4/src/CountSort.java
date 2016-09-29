import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class CountSort {
	public String[] countSorter(String[] wordList) {
		ArrayList<StringCount> count = new ArrayList<StringCount>();
		for (int i = 0; i < wordList.length; i++) {
			String key = wordList[i];
			int idx = containsKey(count, key);
			if (idx == -1) {
				StringCount sc = new StringCount();
				sc.string = key;
				sc.count = 1;
				count.add(sc);
			} else {
				StringCount sc = count.get(idx);//.count++;
				sc.count++;
			}
		}
		StringCount[] array = new StringCount[count.size()];
		for(int i = 0; i < count.size(); i++){
			array[i] = count.get(i);
		}
		insertionSort(array);
		String[] word = new String[array.length];
		for(int i = 0; i < array.length; i++){
			word[i] = array[i].string;
		}
		for (StringCount w : array) {
			System.out.println(w.string + " " + w.count);
		}
		
		// now I need to access each element in the map, and place them back
		// into a string array
		// according to which one has the highest frequency, then if two or more
		// elements have the same
		// frequency I sort them in alphabetical order
		return word;
	}
	
	public static int containsKey(ArrayList<StringCount> count, String key){// if this method can't find the key in our array then returns -1, else returns the index
		for(int i = 0; i < count.size(); i++){
			if(count.get(i).string.equals(key)){
				return i; 
			}
		}
		return -1;
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

	public static void main(String[] args) {
		String[] wordList = { "art", "part", "cart", "art", "part", "art","bart" };
		new CountSort().countSorter(wordList);
	}
	
	public static class StringCount implements Comparable{
		String string; 
		int count;
		@Override
		public int compareTo(Object o) {
			StringCount object = (StringCount) o;
			if(this.count > object.count){
				return -1;
			}
			if(this.count < object.count){
				return 1;
			}
			return this.string.compareTo(object.string);
		}
	}
	
}
