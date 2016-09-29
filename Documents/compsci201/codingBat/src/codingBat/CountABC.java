package codingBat;

public class CountABC {
	public static void main(String[] args){
		CountABC abc = new CountABC();
		String str = "abcxxabc";
		System.out.println("The number of abcs or abas is: " + abc.countAbc(str));
	}
	public int countAbc(String str) {
		if (str.length() <= 2)
			return 0;
		System.out.println(str.substring(0,3));
		if (str.substring(0, 3).equals( "abc") || str.substring(0, 3).equals( "aba")){
			return 1 + countAbc(str.substring(1, str.length()));
		}
		return countAbc(str.substring(1, str.length()));
	}
}
