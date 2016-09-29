
public class CountOccurences {
	public static int numberOccurrences(int number, int digit){
		int count = 0;
		int copy = number;
		while(copy > 0){
			if(copy % 10 == digit){
				count++;
				copy = copy/10;
			}
			if(number % 10 != digit){
				copy = copy/10;
			}
		}
		System.out.println(count);
		return count;
	}
	public static void main(String[] args){
		numberOccurrences(56854, 9);
	}
}
