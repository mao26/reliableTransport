import java.util.ArrayList;

public class Huffman {
	public static void main(String[] args){
		String encoded = "10111010";
		String[] dictionary = {"0", "111", "10" };
		Huffman hd = new Huffman();
		String person = hd.translate(encoded, dictionary);
		System.out.println(person);
		String encoded2 = "1001001100100101001";
		String[] dictionary2 = {"1","0"};
		String person2 = hd.translate(encoded2, dictionary2);
		System.out.println(person2);
		String encoded3 = "001011011000100110";
		String[] dictionary3 = {"010","00","0110","0111","11","100","101"};
		String person3 = hd.translate(encoded3, dictionary3);
		System.out.println(person3);
	}
	
	//passing it my encoded string, and the key that we are currently searching for, and returning the string with the replaced numbers with the char
//	public String switchNumWithChar(String encoded, String key){
//		int index = encoded.indexOf(key);
//	} //going to use this for my recursive call to find the certain dictionary key

	public String translate(String encoded, String[] dictionary) {
		String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		String[] keys = new String[dictionary.length];
		String need = encoded;
		for(int k = 0; k < dictionary.length; k++){
			//keys[k] = "" + ('A' + k);
			keys[k] = alphabet[k];
			System.out.println(keys[k]);
		}
		ArrayList<String> AlreadyUsed = new ArrayList<String>();
		String[] decodedAlph = new String[dictionary.length];
		boolean contain_one_zero = true;
		while(contain_one_zero){
			for(int i = 0; i < encoded.length(); i++){ // for the length of the word
				for(int j = 0; j < dictionary.length; j++){
					if(!encoded.contains("1") && !encoded.contains("0")){
						contain_one_zero = false;
						break;
					}
					int len = dictionary[j].length();
					//int index = encoded.indexOf(dictionary[j]);
					//if(index == i){
					// we found what we should replace with
					//encoded.replace(j
//					encoded.replaceFirst(dictionary[j], keys[j]);
					if(dictionary[j].length() > encoded.length()-i){
						continue;
					}
					String check = encoded.substring(i, i+len);
					if(check.equals(dictionary[j])){
						encoded = encoded.substring(0,i) + keys[j] + encoded.substring(i+len, encoded.length());
						i = encoded.indexOf(keys[j]);
					}
					need = encoded; 
				}
			}
		}
		//going to put all of the variables into a string[] decodedAlph to be able to run my while loop, then take those chars and put it into result
		return need;
	}
}
