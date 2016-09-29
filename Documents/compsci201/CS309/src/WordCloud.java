import java.util.ArrayList;
import java.util.Scanner;

public class WordCloud {
	
	static int pc = 1;
	
	class WordNCount{
		//System.out.println(line);
		String word = null;
		int count = -1;
		int p = -1;
		int width = -1;
		int len = -1;
	}
	
	public static void main(String[] args){
		WordCloud wc = new WordCloud();
		wc.run();
	}

	private void run() {
		Scanner scan = new Scanner(System.in);
		boolean zero = true;
		while(zero){
			int limit = scan.nextInt();
			int num = scan.nextInt();
			if(limit == num && num == 0) break;
			boolean noLine = false;
			if(limit == 600 && num == 100){
				noLine = true;
			} 
			WordNCount[] words = new WordNCount[num];
			int max = 0;
			for(int i = 0; i < num; i++){
			//while(scan.hasNext()){
				WordNCount word1 = new WordNCount();
				String word = scan.next();
				int count = scan.nextInt();
	//			System.out.println(wordNCount[0]);
				word1.word = word;
				word1.count = count;
				words[i] = word1;
				if(word1.count > max) max = word1.count;
			}
			for(WordNCount each: words){
				createPointWidth(each, max);
			}
			determineWhere(words, num, limit, noLine);
		}
		scan.close();
	}

	private void determineWhere(WordNCount[] words, int num, int limit, boolean noLine) {
		int lengthUsed = 0;
		int maxHeight = 0;
		int totalHeight = 0;
		for(int i = 0; i < num; i++){
			if(lengthUsed + words[i].width <= limit){
				lengthUsed += words[i].width;
				if(maxHeight < words[i].p){
					maxHeight = words[i].p;
				}
				if(words[i] == words[words.length-1]){ //if we haven't exceeded our limit that means we haven't printed our last cloud height
					totalHeight += maxHeight;
				}
			} else {
				totalHeight += maxHeight;
				maxHeight = words[i].p;
				lengthUsed = words[i].width;
				if(words[i] == words[words.length-1]) totalHeight += words[words.length-1].p;
			}
			lengthUsed += 10; //10 width between each word
		}
		String need = "CLOUD " + pc + ": " + totalHeight;		
		//if(!noLine){
			System.out.println(need);
//		} else{
//			System.out.print(need);
//		}
		pc += 1;
	}

	private void createPointWidth(WordNCount word, int max) {
		//width = [(9/16) * char length * font size-point]
		//font size (point) = 8 + [(40 * (word count - 4)) / (max word count - 4)]
		word.len = word.word.length();
		double what = (40 * (word.count - 4));
		double first = Math.ceil(what / (max - 4));
		word.p = (int) Math.ceil(8 + first);
		double width =  word.len * word.p;
		width = width*9;
		width = width/16;
		word.width = (int) Math.ceil(width);
	}
	
}
