import java.util.ArrayList;
import java.util.Collections;


public class SubstringSort {
	
	public static void main(String[] args){
		String[] wordList = {"yiaoyiao", "zanahaxa", "haxazana"};
		SubstringSort ss = new SubstringSort();
		ss.sortSubstrings(wordList);
		for(int i = 0; i < wordList.length; i++){
			System.out.println(wordList[i]);
		}
	}
	
	
	public String[] sortSubstrings(String[] wordList) {
		NewString[] array = new NewString[wordList.length]; // array must be able to hold comparables (NewString class)
		for(int i = 0; i < wordList.length; i++){
			//accessing each string in our wordList
			String word = wordList[i];
			array[i] = new NewString(word);
		}
		insertionSort(array);
		for(int i = 0; i < wordList.length; i++){
			//now we put our sorted string back into a string array so we can return that
			wordList[i] = array[i].string; //grabs the string from our NewString
		}
		return wordList;
		
	}
	public static class NewString implements Comparable{
		String string;
		ArrayList<String> subs;
		ArrayList<String> unsorted_subs;
		public NewString(String string){
			this.string = string;
			this.convert(); // constructor calls this method to split our string into subs
		}
		//method to produce an alphabetical list of substrings (subs)
		public void convert(){
			this.subs = new ArrayList<String>();
			boolean foundVowel = false;
			int wordStart = 0;
			String vowels = "aeiou";
			for(int i = 0; i < string.length();i++){
				char c = string.charAt(i);
				if(vowels.indexOf(c) == -1){
					if(foundVowel){
						subs.add(string.substring(wordStart, i));
						wordStart = i;
						foundVowel = false;
					}
					else{

					}
				}else{
					if(foundVowel){
						
					}
					else{
						foundVowel = true;
					}
				}
			}
			if(wordStart < string.length() -1){
				subs.add(string.substring(wordStart ));
			}
			this.unsorted_subs = new ArrayList<String>();
			this.unsorted_subs.addAll(subs);
			Collections.sort(subs);
			return;
		}
		
		
		@Override
		public int compareTo(Object o) {
			NewString object = (NewString) o;
			int minString = Math.min(this.subs.size(), object.subs.size());
			for(int i = 0; i < minString; i++){
				if(!this.subs.get(i).equals(object.subs.get(i))){
					return this.subs.get(i).compareTo(object.subs.get(i));
				}
			}
			if(this.subs.size() > object.subs.size()){
				return 1;
			}
			if(this.subs.size() < object.subs.size()){
				return -1;
			}
			for(int i = 0; i < minString; i++){
				if(!this.unsorted_subs.get(i).equals(object.unsorted_subs.get(i))){
					return this.unsorted_subs.get(i).compareTo(object.unsorted_subs.get(i));
				}
			}
			if(this.unsorted_subs.size() > object.unsorted_subs.size()){
				return 1;
			}
			if(this.unsorted_subs.size() < object.unsorted_subs.size()){
				return -1;
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
