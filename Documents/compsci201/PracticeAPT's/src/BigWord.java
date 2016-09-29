import java.util.*;

public class BigWord {
	public String most(String[] sentences){
		Map<String, Integer> words = new TreeMap<String, Integer>();
		String blank = "";
		for(int i = 0; i < sentences.length; i++){
			blank += sentences[i] + " ";
		}
		sentences = blank.split(" ");
		for(int i = 0; i < sentences.length; i++){
			if(words.containsKey(sentences[i])){
				int count = words.get(sentences[i]);
				count += 1;
				words.put(sentences[i], count);
			}
			else{
				words.put(sentences[i], 1);
			}	
		}
		int maxValue = 0;
		String maxWord = "";
		for(String key: words.keySet()){
			int keyValue = words.get(key);
			if(keyValue > maxValue){
				maxValue = keyValue;
				maxWord = key;
			}
		}
		return maxWord;
	}
	/*public static void main(String[] args){
		String[] sentences = {"one fish two", "fish red fish blue", "fish this fish is black"};
		
		System.out.println(most(sentences));
	}*/
}
