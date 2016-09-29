import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class CountSort {
	public static void main(String[] args){
		CountSort cs = new CountSort();
		String[] wordList = {"art", "part", "cart", "art", "part", "art", "bart"};
		cs.countSorter(wordList);
	}
	
	public String[] countSorter(String[] wordList) {
		ArrayList<StringCount> count = new ArrayList<StringCount>();
		for(int i = 0; i < wordList.length;i++){
			int idx = containsKey(count, wordList[i]);
			if(idx != -1){
				StringCount sc = count.get(idx);
				sc.count++;
			} else {
				StringCount sc = new StringCount();
				sc.string = wordList[i];
				sc.count = 1;
				count.add(sc);
			}
		}
		StringCount[] result = new StringCount[count.size()];
		for(int k = 0; k < result.length; k++){
			result[k] = count.get(k);
		}
		Arrays.sort(result);

		String[] words = new String[result.length];
		for(int i = 0; i < words.length; i++){
			words[i] = result[i].string;
		}
		for(int i = 0; i <result.length; i++){
			System.out.println(result[i].string + " " + result[i].count);
		}
		
		return words;
	}
	
	private int containsKey(ArrayList<StringCount> count, String word) {
		for(int i = 0; i < count.size(); i++){
			if(count.get(i).string.equals(word)){
				return i;
			}
		}
		return -1;
	}

	public class StringCount implements Comparable<StringCount> {
		String string;
		int count;
		@Override
		public int compareTo(StringCount o) {

			if(this.count > o.count) return -1;
			if(this.count < o.count) return 1;
			else{  //this.count == o.count
				System.out.println("enter");

				if(this.string.compareTo(o.string) == 1) return 1;
				if(this.string.compareTo(o.string) == -1) return -1;

				return 0;
			}
		}
	}
}
