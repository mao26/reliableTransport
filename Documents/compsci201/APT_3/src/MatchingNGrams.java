import java.util.*;

public class MatchingNGrams {
	public int totalMatches(String[] ngrams) {
		int total = 0;
		for(int i = 0; i < ngrams.length; i++){
			for(int j = i + 1; j < ngrams.length; j++){
				if(nGramCount(ngrams[i], ngrams[j])){
					total++;
				}
			}
		}
		return total;
	}

	private boolean nGramCount(String string, String string2) {
		Map<Character, Character> map = new TreeMap<Character, Character>();
		for(int i = 0; i < string.length(); i++){
			Character c1 = string.charAt(i);
			Character c2 = string2.charAt(i);
			Character trial = map.get(c1);
			if(trial == null){
				if(map.containsValue(c2)){
					return false;
				}
				map.put(c1, c2);
			}
			else if(trial != c2){
				return false;
			}
		}
		return true;
	}
	/*
	public static void main(String[] args){
		String[] bracket = new String[] {"aa", "ab", "bb", "cc", "cd"};
		MatchingNGrams n = new MatchingNGrams();
		n.totalMatches(bracket);
	}*/
}
