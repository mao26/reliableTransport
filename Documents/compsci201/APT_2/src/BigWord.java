import java.util.Map;
import java.util.TreeMap;

public class BigWord {
	public String most(String[] sentences) {
        // you write code here
		String words = "";
		for(int i = 0; i < sentences.length; i++){
			words = words + " " + sentences[i];
		}
		words = words.toLowerCase();
		String[] new_words = words.split(" ");  
		
		Map<String, Integer> counting = new TreeMap<String, Integer>();
		for(int i = 0; i < new_words.length; i++){
			if(counting.containsKey(new_words[i])){
				counting.put(new_words[i], counting.get(new_words[i]) + 1);
			}
			else{
				counting.put(new_words[i], 1);
			}
		}
		int max = 0; 
		String max_word = "";
		for(String s: counting.keySet()){
			if(counting.get(s) > max){
				max = counting.get(s);
				max_word = s;
			}
		}
		return max_word;
    }
	
	public static void main(String [] args){
		BigWord test = new BigWord();
		String[] sentences = {"one fish two two two", "fish red two fish blue"};
		System.out.println(test.most(sentences));
		
	}
}
